package nullEngine.graphics.renderer;

import com.sun.istack.internal.NotNull;
import math.MathUtil;
import math.Matrix4f;
import math.Vector4f;
import nullEngine.control.Application;
import nullEngine.control.layer.Layer;
import nullEngine.graphics.Material;
import nullEngine.graphics.buffer.PixelPackBuffer;
import nullEngine.graphics.framebuffer.Framebuffer2D;
import nullEngine.graphics.framebuffer.Framebuffer2DHDR;
import nullEngine.graphics.framebuffer.FramebufferDeferred;
import nullEngine.graphics.framebuffer.FramebufferMousePick;
import nullEngine.graphics.model.Model;
import nullEngine.graphics.model.Quad;
import nullEngine.graphics.postfx.PostFXOutput;
import nullEngine.graphics.postfx.TextureOutput;
import nullEngine.graphics.shader.GammaShader;
import nullEngine.graphics.shader.HDRShader;
import nullEngine.graphics.shader.ModelMatrixShader;
import nullEngine.graphics.shader.Shader;
import nullEngine.graphics.shader.deferred.DeferredBasicShader;
import nullEngine.graphics.shader.deferred.lighting.DeferredAmbientLightShader;
import nullEngine.graphics.shader.deferred.lighting.DeferredDirectionalLightShader;
import nullEngine.graphics.shader.deferred.lighting.DeferredPointLightShader;
import nullEngine.graphics.shader.deferred.lighting.DeferredSpotLightShader;
import nullEngine.graphics.shader.mousePick.MousePickShader;
import nullEngine.input.EventListener;
import nullEngine.input.Input;
import nullEngine.input.MousePickInfo;
import nullEngine.input.NotificationEvent;
import nullEngine.input.PostResizeEvent;
import nullEngine.object.GameComponent;
import nullEngine.object.component.graphics.ModelComponent;
import nullEngine.object.component.graphics.light.DirectionalLight;
import nullEngine.object.component.graphics.light.PointLight;
import nullEngine.object.component.graphics.light.SpotLight;
import nullEngine.util.logs.Logs;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import util.BitFieldInt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Deferred renderer
 */
public class DeferredRenderer extends Renderer {

	private ModelMatrixShader shader = DeferredBasicShader.INSTANCE;

	private static final float LOD_DROPOFF_FACTOR = 2f;
	private static final float MOUSE_PICK_LOD_OFFSET = 1;
	private Map<Material, ArrayList<ModelComponent>> models = new HashMap<>();
	private Vector4f ambientColor = new Vector4f();
	private List<DirectionalLight> directionalLights = new ArrayList<>();
	private List<PointLight> pointLights = new ArrayList<>();
	private List<SpotLight> spotLights = new ArrayList<>();

	private boolean hdr;

	private float exposureTime = 1;

	/**
	 * The size decrease for the mouse picking frame buffer
	 */
	public static final int MOUSE_PICK_BUFFER_DOWN_SCALE = 4;

	private FramebufferDeferred dataBuffer;
	private Framebuffer2D lightBuffer;
	private Framebuffer2DHDR hdrBuffer = null;
	private FramebufferMousePick mousePickBuffer;
	private float far;
	private float near;

	private boolean wireframe = false;

	private PostFXOutput postFX;
	private TextureOutput colorOutput;
	private TextureOutput positionOutput;
	private TextureOutput normalOutput;
	private TextureOutput specularOutput;
	private TextureOutput depthOutput;
	private TextureOutput lightOutput;

	private BitFieldInt flags;
	private Map<Material, List<ModelComponent>> mousePickModels = new HashMap<>();
	private List<ModelComponent> orderedMousePickModels = new ArrayList<>();

	/**
	 * Create a new deferred renderer
	 *
	 * @param width  The width
	 * @param height The height
	 * @param far    The far plane
	 * @param near   The near plane
	 */
	public DeferredRenderer(int width, int height, float far, float near, boolean hdr) {
		dataBuffer = new FramebufferDeferred(width, height);
		lightBuffer = new Framebuffer2D(width, height);
		if (hdr)
			hdrBuffer = new Framebuffer2DHDR(width, height);
		mousePickBuffer = new FramebufferMousePick(width / MOUSE_PICK_BUFFER_DOWN_SCALE, height / MOUSE_PICK_BUFFER_DOWN_SCALE);
		colorOutput = new TextureOutput(dataBuffer.getColorTextureID());
		positionOutput = new TextureOutput(dataBuffer.getPositionTextureID());
		normalOutput = new TextureOutput(dataBuffer.getNormalTextureID());
		specularOutput = new TextureOutput(dataBuffer.getSpecularTextureID());
		depthOutput = new TextureOutput(dataBuffer.getDepthTexutreID());
		lightOutput = new TextureOutput(lightBuffer.getColorTextureID());
		postFX = lightOutput;
		this.far = far;
		this.near = near;
		this.hdr = hdr;
	}

	public void setHDR(boolean hdr) {
		this.hdr = hdr;
	}

	public void setExposureTime(float exposureTime) {
		this.exposureTime = exposureTime;
	}

	/**
	 * Add a game component to be rendered
	 * <ul>
	 * <li>If the game component is a ModelComponent it is rendered as a model</li>
	 * <li>If the game component is a light it is used to light the scene</li>
	 * </ul>
	 *
	 * @param component The component
	 */
	@Override
	public void add(GameComponent component) {
		if (flags.get(Layer.DEFERRED_RENDER_BIT)) {
			if (component instanceof ModelComponent) {
				ModelComponent model = (ModelComponent) component;
				ArrayList<ModelComponent> list = models.get(model.getMaterial());
				if (list == null) {
					models.put(model.getMaterial(), list = new ArrayList<>());
				}
				list.add(model);
			} else if (component instanceof DirectionalLight) {
				directionalLights.add((DirectionalLight) component);
			} else if (component instanceof PointLight) {
				pointLights.add((PointLight) component);
			} else if (component instanceof SpotLight) {
				spotLights.add((SpotLight) component);
			}
		}
		if (flags.get(Layer.MOUSE_PICK_RENDER_BIT)) {
			if (component instanceof ModelComponent) {
				ModelComponent model = (ModelComponent) component;
				if (model.enableMousePicking) {
					List<ModelComponent> list = mousePickModels.get(model.getMaterial());
					if (list == null) {
						mousePickModels.put(model.getMaterial(), list = new ArrayList<>());
					}
					list.add(model);
				}
			}
		}
	}

	/**
	 * Render all of the added components
	 *
	 * @param flags The render flags
	 */
	@Override
	public void postRender(BitFieldInt flags) {
		boolean rendered = false;
		if (flags.get(Layer.DEFERRED_RENDER_BIT)) {
			BitFieldInt modelFlags = new BitFieldInt();
			modelFlags.set(Layer.DEFERRED_RENDER_BIT, true);
			rendered = true;
			dataBuffer.bind();
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			if (wireframe) {
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			}
			for (Map.Entry<Material, ArrayList<ModelComponent>> components : models.entrySet()) {
				shader = (ModelMatrixShader) components.getKey().getShader(Material.DEFERRED_SHADER_INDEX);
				shader.bind();
				shader.loadMaterial(components.getKey());

				for (ModelComponent compnent : components.getValue()) {
					setModelMatrix(compnent.getObject().getRenderMatrix());
					Vector4f pos = modelMatrix.getPos(null);
					Model model = compnent.getModel(modelFlags);
					float radius = modelMatrix.transform(new Vector4f(model.getRadius(), 0, 0, 0)).length();
					pos.z += radius;
					if (-pos.z <= far || components.getKey().isAlwaysRender()) {
						int lod = MathUtil.clamp(
								(int) (Math.floor(Math.pow(-pos.z / far, LOD_DROPOFF_FACTOR) * model.getLODCount()) + compnent.getLodBias()),
								0, model.getLODCount() - 1);
						model.render(lod);
					}
				}
			}
			if (wireframe) {
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
			}
			models.clear();

			GL11.glDisable(GL11.GL_DEPTH_TEST);

			if (hdrBuffer != null)
				hdrBuffer.bind();
			else
				lightBuffer.bind();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);

			DeferredAmbientLightShader.INSTANCE.bind();
			DeferredAmbientLightShader.INSTANCE.loadAmbientColor(ambientColor);

			dataBuffer.render();

			if (directionalLights.size() > 0) {
				DeferredDirectionalLightShader.INSTANCE.bind();
				DeferredDirectionalLightShader.INSTANCE.loadViewMatrix(viewMatrix);
				for (DirectionalLight light : directionalLights) {
					DeferredDirectionalLightShader.INSTANCE.loadLight(light);
					dataBuffer.render();
				}
				directionalLights.clear();
			}

			if (pointLights.size() > 0) {
				DeferredPointLightShader.INSTANCE.bind();
				DeferredPointLightShader.INSTANCE.loadViewMatrix(viewMatrix);
				for (PointLight light : pointLights) {
					DeferredPointLightShader.INSTANCE.loadLight(light);
					dataBuffer.render();
				}
				pointLights.clear();
			}

			if (spotLights.size() > 0) {
				DeferredSpotLightShader.INSTANCE.bind();
				DeferredSpotLightShader.INSTANCE.loadViewMatrix(viewMatrix);
				for (SpotLight light : spotLights) {
					DeferredSpotLightShader.INSTANCE.loadLight(light);
					dataBuffer.render();
				}
				spotLights.clear();
			}

			GL11.glDisable(GL11.GL_BLEND);
			Quad.get().preRender();

			if (hdrBuffer != null) {
				lightBuffer.bind();
				HDRShader.INSTANCE.bind();
				HDRShader.INSTANCE.loadMVP(Matrix4f.IDENTITY);
				HDRShader.INSTANCE.loadExposureTime(exposureTime);
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, hdrBuffer.getColorTextureID());
				Quad.get().lazyRender(0);
			}

			postFX.preRender();
			postFX.render(viewMatrix);
			int out = postFX.getTextureID();

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			if (Application.get().getRenderTarget() != null)
				Application.get().getRenderTarget().bind();
			else
				Framebuffer2D.unbind();

			GammaShader.INSTANCE.bind();
			GammaShader.INSTANCE.loadMVP(Matrix4f.IDENTITY);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			if (Input.keyPressed(Input.KEY_M)) {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, mousePickBuffer.getWorldPositionTextureID());
			} else
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, out);
			Quad.get().lazyRender(0);
			GL11.glDisable(GL11.GL_BLEND);
			Quad.get().postRender();
		}
		if (flags.get(Layer.MOUSE_PICK_RENDER_BIT)) {
			BitFieldInt modelFlags = new BitFieldInt();
			modelFlags.set(Layer.MOUSE_PICK_RENDER_BIT, true);
			rendered = true;
			mousePickBuffer.bind();
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			orderedMousePickModels = new ArrayList<>(orderedMousePickModels.size());

			for (Map.Entry<Material, List<ModelComponent>> components : mousePickModels.entrySet()) {
				shader = (MousePickShader) components.getKey().getShader(Material.MOUSE_PICKING_SHADER_INDEX);
				shader.bind();
				shader.loadMaterial(components.getKey());

				for (ModelComponent component : components.getValue()) {
					orderedMousePickModels.add(component);
					((MousePickShader) shader).loadIdToColor(orderedMousePickModels.size());
					setModelMatrix(component.getObject().getRenderMatrix());
					Vector4f pos = modelMatrix.getPos(null);
					Model model = component.getModel(modelFlags);
					float radius = modelMatrix.transform(new Vector4f(model.getRadius(), 0, 0, 0)).length();
					pos.z += radius;
					if (-pos.z <= far || components.getKey().isAlwaysRender()) {
						int lod = MathUtil.clamp(
								(int) (Math.floor(Math.pow(-pos.z / far, LOD_DROPOFF_FACTOR) * model.getLODCount()) + component.getLodBias() + MOUSE_PICK_LOD_OFFSET),
								0, model.getLODCount() - 1);
						model.render(lod);
					}
				}

			}
			mousePickModels.clear();

			GL11.glDisable(GL11.GL_DEPTH_TEST);
			mousePickBuffer.unbind();
		}

		if (mousePickRequests.size() > 0)
			mousePickImpl();

		if (!rendered) {
			Logs.w("No recognised flags passed to deferred renderer");
		}
	}

	/**
	 * How many pixel buffers the mouse picking system should use
	 */
	public static final int MOUSE_PICK_PBO_COUNT = 4;
	private PixelPackBuffer[] hitIdPbos = new PixelPackBuffer[MOUSE_PICK_PBO_COUNT];
	private PixelPackBuffer[] worldPositionPbos = new PixelPackBuffer[MOUSE_PICK_PBO_COUNT];
	private PixelPackBuffer[] localPositionPbos = new PixelPackBuffer[MOUSE_PICK_PBO_COUNT];

	private BlockingQueue<Integer> freePbos;
	private List<MousePickRequest> mousePickRequests = Collections.synchronizedList(new ArrayList<MousePickRequest>(MOUSE_PICK_PBO_COUNT));

	private static class MousePickRequest {
		private final int x;
		private final int y;
		public final MousePickInfo info;
		private final NotificationEvent finished;

		private final DeferredRenderer renderer;
		private int workingPboIndex = -1;
		private boolean done = false;

		private long hitIdSync;
		private long worldPositionSync;
		private long localPositionSync;

		private final List<ModelComponent> orderedMousePickModels;

		private static ByteBuffer hitIdDest = null;
		private static ByteBuffer worldPositionDest = null;
		private static ByteBuffer localPositionDest = null;

		public MousePickRequest(int x, int y, MousePickInfo info, DeferredRenderer renderer, EventListener notify) {
			this.x = x;
			this.y = y;
			this.info = info;
			this.renderer = renderer;
			this.orderedMousePickModels = renderer.orderedMousePickModels;
			finished = new NotificationEvent(notify, NotificationEvent.NOTIFICATION_MOUSE_PICK_COMPLETE, info);
		}

		public boolean isActive() {
			return workingPboIndex >= 0;
		}

		public boolean update() {
			if (isActive()) {
				if (hitIdSync != -1) {
					int result = GL32.glClientWaitSync(hitIdSync, 0, 0);
					if (result == GL32.GL_ALREADY_SIGNALED || result == GL32.GL_CONDITION_SATISFIED) {
						GL32.glDeleteSync(hitIdSync);
						hitIdSync = -1;
						hitIdDest = renderer.getHitIdPbo(workingPboIndex).get(hitIdDest);
						hitIdDest.order(ByteOrder.BIG_ENDIAN);
						int hitId = hitIdDest.asIntBuffer().get(0);
						if (hitId <= orderedMousePickModels.size() && hitId > 0)
							info.model = orderedMousePickModels.get(hitId - 1);
					}
				}

				if (worldPositionSync != -1) {
					int result = GL32.glClientWaitSync(worldPositionSync, 0, 0);
					if (result == GL32.GL_ALREADY_SIGNALED || result == GL32.GL_CONDITION_SATISFIED) {
						GL32.glDeleteSync(worldPositionSync);
						worldPositionSync = -1;
						worldPositionDest = renderer.getWorldPositionPbo(workingPboIndex).get(worldPositionDest);
						FloatBuffer fBuf = worldPositionDest.asFloatBuffer();
						info.worldPosition = new Vector4f(fBuf.get(0), fBuf.get(1), fBuf.get(2));
					}
				}

				if (localPositionSync != -1) {
					int result = GL32.glClientWaitSync(localPositionSync, 0, 0);
					if (result == GL32.GL_ALREADY_SIGNALED || result == GL32.GL_CONDITION_SATISFIED) {
						GL32.glDeleteSync(localPositionSync);
						localPositionSync = -1;
						localPositionDest = renderer.getLocalPositionPbo(workingPboIndex).get(localPositionDest);
						FloatBuffer fBuf = localPositionDest.asFloatBuffer();
						info.localPosition = new Vector4f(fBuf.get(0), fBuf.get(1), fBuf.get(2));
					}
				}


				done = hitIdSync == -1 && worldPositionSync == -1 && localPositionSync == -1;
				if (done) {
					renderer.freePbos.add(workingPboIndex);
					workingPboIndex = -1;
					Application.get().queueEvent(finished);
				}
				return done;
			} else if (isDone()) {
				return true;
			} else {
				Integer idx = renderer.freePbos.poll();
				if (idx != null) {
					workingPboIndex = idx;

					renderer.getHitIdPbo(workingPboIndex).bind();
					renderer.mousePickBuffer.bind();
					GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT0);
					GL11.glReadPixels(x, y, 1, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, 0);
					hitIdSync = GL32.glFenceSync(GL32.GL_SYNC_GPU_COMMANDS_COMPLETE, 0);

					renderer.getWorldPositionPbo(workingPboIndex).bind();
					GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT1);
					GL11.glReadPixels(x, y, 1, 1, GL11.GL_RGB, GL11.GL_FLOAT, 0);
					worldPositionSync = GL32.glFenceSync(GL32.GL_SYNC_GPU_COMMANDS_COMPLETE, 0);

					renderer.getLocalPositionPbo(workingPboIndex).bind();
					GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT2);
					GL11.glReadPixels(x, y, 1, 1, GL11.GL_RGB, GL11.GL_FLOAT, 0);
					localPositionSync = GL32.glFenceSync(GL32.GL_SYNC_GPU_COMMANDS_COMPLETE, 0);
					renderer.getLocalPositionPbo(workingPboIndex).unbind();
				}
				return false;
			}
		}

		public void cancel() {
			if (hitIdSync != -1) {
				GL32.glDeleteSync(hitIdSync);
				hitIdSync = -1;
			}

			if (worldPositionSync != -1) {
				GL32.glDeleteSync(worldPositionSync);
				worldPositionSync = -1;
			}

			if (localPositionSync != -1) {
				GL32.glDeleteSync(localPositionSync);
				localPositionSync = -1;
			}

			if (workingPboIndex != -1) {
				renderer.freePbos.add(workingPboIndex);
			}
		}

		public boolean isDone() {
			return done;
		}

	}

	/**
	 * Request a mouse pick to run asynchronously
	 *
	 * @param x      The x
	 * @param y      The y
	 * @param info   The mouse pick info to use
	 * @param notify The listener to notify when the mouse pick is completed
	 */
	public void mousePick(int x, int y, @NotNull MousePickInfo info, @NotNull EventListener notify) {
		if (freePbos == null) {
			initPboQueue();
		}

		mousePickRequests.add(new MousePickRequest(x / MOUSE_PICK_BUFFER_DOWN_SCALE, y / MOUSE_PICK_BUFFER_DOWN_SCALE, info, this, notify));
	}

	private void initPboQueue() {
		freePbos = new LinkedBlockingQueue<>(MOUSE_PICK_PBO_COUNT);
		for (int i = 0; i < MOUSE_PICK_PBO_COUNT; i++)
			freePbos.add(i);
	}

	private void mousePickImpl() {
		synchronized (mousePickRequests) {
			Iterator<MousePickRequest> itr = mousePickRequests.iterator();
			while (itr.hasNext()) {
				MousePickRequest request = itr.next();
				if (request.update())
					itr.remove();
			}
		}
	}

	private PixelPackBuffer getHitIdPbo(int i) {
		if (hitIdPbos[i] == null) {
			hitIdPbos[i] = new PixelPackBuffer(4);
		}
		return hitIdPbos[i];
	}

	private PixelPackBuffer getWorldPositionPbo(int i) {
		if (worldPositionPbos[i] == null) {
			worldPositionPbos[i] = new PixelPackBuffer(12);
		}
		return worldPositionPbos[i];
	}

	private PixelPackBuffer getLocalPositionPbo(int i) {
		if (localPositionPbos[i] == null) {
			localPositionPbos[i] = new PixelPackBuffer(12);
		}
		return localPositionPbos[i];
	}

	/**
	 * Save the flags
	 *
	 * @param flags The rneder flags
	 */
	@Override
	public void preRender(BitFieldInt flags) {
		this.flags = flags;
		if (hdr) {
			if (hdrBuffer == null)
				hdrBuffer = new Framebuffer2DHDR(Application.get().getWidth(), Application.get().getHeight());
		} else if (hdrBuffer != null) {
			hdrBuffer.delete();
			hdrBuffer = null;
		}
	}

	/**
	 * Cleanup after the renderer
	 */
	@Override
	public void cleanup() {
		for (int i = 0; i < MOUSE_PICK_PBO_COUNT; i++) {
			if (hitIdPbos[i] != null)
				hitIdPbos[i].dispose();
			if (worldPositionPbos[i] != null)
				worldPositionPbos[i].dispose();
			if (localPositionPbos[i] != null)
				localPositionPbos[i].dispose();
		}
		dataBuffer.delete();
		lightBuffer.delete();
	}

	/**
	 * Set the ambient lighting color
	 *
	 * @param ambientColor The ambient color
	 */
	public void setAmbientColor(Vector4f ambientColor) {
		this.ambientColor = ambientColor;
	}

	/**
	 * Set the model matrix
	 *
	 * @param modelMatrix The model matrix
	 */
	@Override
	public void setModelMatrix(Matrix4f modelMatrix) {
		super.setModelMatrix(modelMatrix);
		if (Shader.bound() == shader)
			shader.loadModelMatrix(modelMatrix);
	}

	/**
	 * Set the post processing tree
	 *
	 * @param postFX The tree
	 */
	public void setPostFX(PostFXOutput postFX) {
		this.postFX = postFX;
	}

	/**
	 * Recreate the framebuffers
	 *
	 * @param event The event
	 */
	@Override
	public void postResize(PostResizeEvent event) {
		initPboQueue();
		dataBuffer = new FramebufferDeferred(event.width, event.height);
		lightBuffer = new Framebuffer2D(event.width, event.height);
		if (hdr)
			hdrBuffer = new Framebuffer2DHDR(event.width, event.height);
		mousePickBuffer = new FramebufferMousePick(event.width / MOUSE_PICK_BUFFER_DOWN_SCALE, event.height / MOUSE_PICK_BUFFER_DOWN_SCALE);
		colorOutput.setTextureID(dataBuffer.getColorTextureID());
		positionOutput.setTextureID(dataBuffer.getPositionTextureID());
		normalOutput.setTextureID(dataBuffer.getNormalTextureID());
		specularOutput.setTextureID(dataBuffer.getSpecularTextureID());
		depthOutput.setTextureID(dataBuffer.getDepthTexutreID());
		lightOutput.setTextureID(lightBuffer.getColorTextureID());
		postFX.postResize(event);
	}

	/**
	 * Delete the framebuffers
	 */
	@Override
	public void preResize() {
		synchronized (mousePickRequests) {
			mousePickRequests.clear();
		}
		dataBuffer.delete();
		lightBuffer.delete();
		if (hdrBuffer != null)
			hdrBuffer.delete();
		mousePickBuffer.delete();
		postFX.preResize();
	}

	/**
	 * Get wether wireframe mode is enabled
	 *
	 * @return Wether wireframe mode is enabled
	 */
	public boolean isWireframe() {
		return wireframe;
	}

	/**
	 * Set wether wireframe mode is enabled
	 *
	 * @param wireframe Wether wireframe mode is enabled
	 */
	public void setWireframe(boolean wireframe) {
		this.wireframe = wireframe;
	}

	/**
	 * Get the color postfx output
	 *
	 * @return The color postfx output
	 */
	public TextureOutput getColorOutput() {
		return colorOutput;
	}

	/**
	 * Get the position postfx output
	 *
	 * @return The position postfx output
	 */
	public TextureOutput getPositionOutput() {
		return positionOutput;
	}

	/**
	 * Get the normal postfx output
	 *
	 * @return The normal postfx output
	 */
	public TextureOutput getNormalOutput() {
		return normalOutput;
	}

	/**
	 * Get the specular postfx output
	 *
	 * @return The specular postfx output
	 */
	public TextureOutput getSpecularOutput() {
		return specularOutput;
	}

	/**
	 * Get the depth postfx output
	 *
	 * @return The depth postfx output
	 */
	public TextureOutput getDepthOutput() {
		return depthOutput;
	}

	/**
	 * Get the light postfx output
	 *
	 * @return The light postfx output
	 */
	public TextureOutput getLightOutput() {
		return lightOutput;
	}
}
