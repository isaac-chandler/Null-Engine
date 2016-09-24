package nullEngine.graphics.vulkan;

import com.sun.istack.internal.Nullable;
import nullEngine.graphics.base.Window;
import nullEngine.loading.Loader;
import nullEngine.util.logs.Logs;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.LongBuffer;

public class VKWindow extends Window {

	private VkApplicationInfo appInfo;
	private VkInstance instance;
	private VkPhysicalDevice physicalDevice;
	private VkDevice device;
	private long surface;

	public VKWindow(String title, int width, int height, boolean fullscreen, @Nullable GLFWVidMode fullscreenVideoMode, long monitor) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.fullscreen = fullscreen;

		appInfo = VkApplicationInfo.calloc();
		appInfo.sType(VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO);
		appInfo.pApplicationName("Null Engine Application");
		appInfo.pEngineName("Null Engine");
		appInfo.apiVersion(VKUtil.VK_MAKE_VERSION(1, 0, 0));
		PointerBuffer extensions = GLFWVulkan.glfwGetRequiredInstanceExtensions();
		PointerBuffer enabledLayers = MemoryUtil.memAllocPointer(0);
		VkInstanceCreateInfo instanceInfo = VkInstanceCreateInfo.calloc();
		instanceInfo.sType(VK10.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO);
		instanceInfo.pNext(MemoryUtil.NULL);
		instanceInfo.pApplicationInfo(appInfo);
		instanceInfo.ppEnabledExtensionNames(extensions);
		instanceInfo.ppEnabledLayerNames(enabledLayers);
		PointerBuffer instanceBuf = MemoryUtil.memAllocPointer(1);
		int err = VK10.vkCreateInstance(instanceInfo, null, instanceBuf);
		long instanceL = instanceBuf.get(0);
		MemoryUtil.memFree(instanceBuf);
		if (err != VK10.VK_SUCCESS) {
			Logs.f(new RuntimeException("Failed to create vulkan instance: " + VKUtil.translateVulkanResult(err)));
		}

		instance = new VkInstance(instanceL, instanceInfo);
		instanceInfo.free();
		MemoryUtil.memFree(enabledLayers);
		MemoryUtil.memFree(appInfo.pApplicationName());
		MemoryUtil.memFree(appInfo.pEngineName());
		appInfo.free();

		err = VK10.vkEnumeratePhysicalDevices(instance, intBuffer, null);
		if (err != VK10.VK_SUCCESS) {
			Logs.f(new RuntimeException("Failed to get physical device count: " + VKUtil.translateVulkanResult(err)));
		}

		PointerBuffer physicalDevices = MemoryUtil.memAllocPointer(intBuffer.get(0));
		err = VK10.vkEnumeratePhysicalDevices(instance, intBuffer, physicalDevices);
		if (err != VK10.VK_SUCCESS) {
			Logs.f(new RuntimeException("Failed to get physical devices: " + VKUtil.translateVulkanResult(err)));
		} else if (physicalDevices.capacity() == 0) {
			Logs.f(new RuntimeException("Failed to find vulkan supporting device"));
		}

		physicalDevice = new VkPhysicalDevice(physicalDevices.get(0), instance);
		MemoryUtil.memFree(physicalDevices);

		VK10.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, intBuffer, null);
		int queueCount = intBuffer.get(0);
		VkQueueFamilyProperties.Buffer queueFamilyProperties = VkQueueFamilyProperties.calloc(queueCount);
		VK10.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, intBuffer, queueFamilyProperties);
		int idx;
		for (idx = 0; idx < queueCount; idx++) {
			if ((queueFamilyProperties.get(idx).queueFlags() & VK10.VK_QUEUE_GRAPHICS_BIT) != 0)
				break;
		}

		FloatBuffer queuePriorities = MemoryUtil.memAllocFloat(1).put(0);
		queuePriorities.flip();
		VkDeviceQueueCreateInfo.Buffer deviceQueueCreateInfo = VkDeviceQueueCreateInfo.calloc(1);
		deviceQueueCreateInfo.sType(VK10.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO);
		deviceQueueCreateInfo.queueFamilyIndex(idx);
		deviceQueueCreateInfo.pQueuePriorities(queuePriorities);

		extensions = MemoryUtil.memAllocPointer(1);
		ByteBuffer VK_KHR_SWAP_CHAIN = MemoryUtil.memEncodeUTF8(KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME);
		extensions.put(VK_KHR_SWAP_CHAIN).flip();
		enabledLayers = MemoryUtil.memAllocPointer(0);

		VkDeviceCreateInfo deviceCreateInfo = VkDeviceCreateInfo.calloc();
		deviceCreateInfo.sType(VK10.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO);
		deviceCreateInfo.pQueueCreateInfos(deviceQueueCreateInfo);
		deviceCreateInfo.ppEnabledExtensionNames(extensions);
		deviceCreateInfo.ppEnabledLayerNames(enabledLayers);

		PointerBuffer deviceP = MemoryUtil.memAllocPointer(1);
		err = VK10.vkCreateDevice(physicalDevice, deviceCreateInfo, null, deviceP);
		long deviceL = deviceP.get(0);
		MemoryUtil.memFree(deviceP);
		if (err != VK10.VK_SUCCESS) {
			Logs.f(new RuntimeException("Failed to create device: " + VKUtil.translateVulkanResult(err)));
		}

		device = new VkDevice(deviceL, physicalDevice, deviceCreateInfo);
		deviceCreateInfo.free();
		MemoryUtil.memFree(enabledLayers);
		MemoryUtil.memFree(extensions);
		MemoryUtil.memFree(VK_KHR_SWAP_CHAIN);
		MemoryUtil.memFree(queuePriorities);


		GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API);

		if (fullscreen) {
			GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, fullscreenVideoMode.redBits());
			GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, fullscreenVideoMode.greenBits());
			GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, fullscreenVideoMode.blueBits());
			GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, fullscreenVideoMode.refreshRate());
			width = fullscreenVideoMode.width();
			height = fullscreenVideoMode.height();
		}

		window = GLFW.glfwCreateWindow(width, height, title, fullscreen ? monitor : MemoryUtil.NULL, MemoryUtil.NULL);
		LongBuffer surfaceP = MemoryUtil.memAllocLong(1);
		err = GLFWVulkan.glfwCreateWindowSurface(instance, window, null, surfaceP);
		surface = surfaceP.get(0);
		MemoryUtil.memFree(surfaceP);
		if (err != VK10.VK_SUCCESS) {
			Logs.f(new RuntimeException("Failed to create surface: " + VKUtil.translateVulkanResult(err)));
		}



		initCallbacks();
	}

	public void setFullscreen(boolean fullscreen, @Nullable GLFWVidMode fullscreenVideoMode, Loader loader) {

	}

	@Override
	public void resized(int newWidth, int newHeight) {

	}
}
