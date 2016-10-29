package nullEngine.gl.renderer;

import math.MathUtil;
import math.Matrix4f;
import math.Vector4f;
import nullEngine.control.Application;
import nullEngine.control.Layer;
import nullEngine.gl.Material;
import nullEngine.gl.buffer.PixelBuffer;
import nullEngine.gl.framebuffer.Framebuffer2D;
import nullEngine.gl.framebuffer.FramebufferDeferred;
import nullEngine.gl.framebuffer.FramebufferMousePick;
import nullEngine.gl.model.Quad;
import nullEngine.gl.postfx.PostFXOutput;
import nullEngine.gl.postfx.TextureOutput;
import nullEngine.gl.shader.BasicShader;
import nullEngine.gl.shader.ModelMatrixShader;
import nullEngine.gl.shader.Shader;
import nullEngine.gl.shader.deferred.DeferredBasicShader;
import nullEngine.gl.shader.deferred.lighting.DeferredAmbientLightShader;
import nullEngine.gl.shader.deferred.lighting.DeferredDirectionalLightShader;
import nullEngine.gl.shader.deferred.lighting.DeferredPointLightShader;
import nullEngine.gl.shader.deferred.lighting.DeferredSpotLightShader;
import nullEngine.gl.shader.mousePick.MousePickShader;
import nullEngine.input.EventListener;
import nullEngine.input.MousePickInfo;
import nullEngine.input.NotificationEvent;
import nullEngine.input.PostResizeEvent;
import nullEngine.object.GameComponent;
import nullEngine.object.component.ModelComponent;
import nullEngine.object.component.light.DirectionalLight;
import nullEngine.object.component.light.PointLight;
import nullEngine.object.component.light.SpotLight;
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
import java.util.concurrent.LinkedBlockingQueue;

public class DeferredRenderer extends Renderer {

	private ModelMatrixShader shader = DeferredBasicShader.INSTANCE;

	private static final float LOD_DROPOFF_FACTOR = 2f;
	private static final float MOUSE_PICK_LOD_OFFSET = 1;
	private HashMap<Material, ArrayList<ModelComponent>> models = new HashMap<Material, ArrayList<ModelComponent>>();
	private Vector4f ambientColor = new Vector4f();
	private ArrayList<DirectionalLight> directionalLights = new ArrayList<DirectionalLight>();
	private ArrayList<PointLight> pointLights = new ArrayList<PointLight>();
	private ArrayList<SpotLight> spotLights = new ArrayList<SpotLight>();

	public static final int MOUSE_PICK_BUFFER_DOWN_SCALE = 4;

	private FramebufferDeferred dataBuffer;
	private Framebuffer2D lightBuffer;
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
	private HashMap<Material, ArrayList<ModelComponent>> mousePickModels = new HashMap<Material, ArrayList<ModelComponent>>();
	private ArrayList<ModelComponent> orderedMousePickModels = new ArrayList<ModelComponent>();

	public DeferredRenderer(int width, int height, float far, float near) {
		dataBuffer = new FramebufferDeferred(width, height);
		lightBuffer = new Framebuffer2D(width, height);
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
	}

	@Override
	public void add(GameComponent component) {
		if (flags.get(Layer.DEFERRED_RENDER_BIT)) {
			if (component instanceof ModelComponent) {
				ModelComponent model = (ModelComponent) component;
				ArrayList<ModelComponent> list = models.get(model.getMaterial());
				if (list == null) {
					models.put(model.getMaterial(), list = new ArrayList<ModelComponent>());
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
				ArrayList<ModelComponent> list = mousePickModels.get(model.getMaterial());
				if (list == null) {
					mousePickModels.put(model.getMaterial(), list = new ArrayList<ModelComponent>());
				}
				list.add(model);
			}
		}
	}

	@Override
	public void postRender(BitFieldInt flags) {
		boolean rendered = false;
		if (flags.get(Layer.DEFERRED_RENDER_BIT)) {
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

				for (ModelComponent model : components.getValue()) {
					setModelMatrix(model.getParent().getRenderMatrix());
					Vector4f pos = modelMatrix.getPos(null);
					float radius = modelMatrix.transform(new Vector4f(model.getModel().getRadius(), 0, 0, 0)).length();
					pos.z += radius;
					if (-pos.z <= far || components.getKey().isAlwaysRender()) {
						int lod = MathUtil.clamp(
								(int) (Math.floor(Math.pow(-pos.z / far, LOD_DROPOFF_FACTOR) * model.getModel().getLODCount()) + model.getLodBias()),
								0, model.getModel().getLODCount() - 1);
						model.getModel().render(lod);
					}
				}
			}
			if (wireframe) {
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
			}
			models.clear();

			GL11.glDisable(GL11.GL_DEPTH_TEST);
			dataBuffer.unbind();

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
			postFX.preRender();
			postFX.render(viewMatrix);
			int out = postFX.getTextureID();
			Quad.get().postRender();

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			if (Application.get().getRenderTarget() != null) {
				Application.get().getRenderTarget().bind();
			} else {
				Framebuffer2D.unbind();
			}
			BasicShader.INSTANCE.bind();
			BasicShader.INSTANCE.loadProjectionMatrix(Matrix4f.IDENTITY);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, out);
			Quad.get().render();
			GL11.glDisable(GL11.GL_BLEND);
		}
		if (flags.get(Layer.MOUSE_PICK_RENDER_BIT)) {
			rendered = true;
			mousePickBuffer.bind();
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			orderedMousePickModels = new ArrayList<ModelComponent>(orderedMousePickModels.size());

			for (Map.Entry<Material, ArrayList<ModelComponent>> components : mousePickModels.entrySet()) {
				shader = (MousePickShader) components.getKey().getShader(Material.MOUSE_PICKING_SHADER_INDEX);
				shader.bind();
				shader.loadMaterial(components.getKey());

				for (ModelComponent model : components.getValue()) {
					orderedMousePickModels.add(model);
					((MousePickShader) shader).loadIdToColor(orderedMousePickModels.size());
					setModelMatrix(model.getParent().getRenderMatrix());
					Vector4f pos = modelMatrix.getPos(null);
					float radius = modelMatrix.transform(new Vector4f(model.getModel().getRadius(), 0, 0, 0)).length();
					pos.z += radius;
					if (-pos.z <= far || components.getKey().isAlwaysRender()) {
						int lod = MathUtil.clamp(
								(int) (Math.floor(Math.pow(-pos.z / far, LOD_DROPOFF_FACTOR) * model.getModel().getLODCount()) + model.getLodBias() + MOUSE_PICK_LOD_OFFSET),
								0, model.getModel().getLODCount() - 1);
						model.getModel().render(lod);
					}
				}
			}
			mousePickModels.clear();

			GL11.glDisable(GL11.GL_DEPTH_TEST);
			mousePickBuffer.unbind();
		}
		if (!rendered) {
			Logs.w("No recognised flags passed to deferred renderer");
		}
		if (mousePickRequests.size() > 0)
			mousePickImpl();
	}

	public static final int MOUSE_PICK_PBO_COUNT = 4;
	private PixelBuffer[] hitIdPbos = new PixelBuffer[MOUSE_PICK_PBO_COUNT];
	private PixelBuffer[] worldPositionPbos = new PixelBuffer[MOUSE_PICK_PBO_COUNT];
	private PixelBuffer[] localPositionPbos = new PixelBuffer[MOUSE_PICK_PBO_COUNT];

	private LinkedBlockingQueue<Integer> freePbos;
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

		private final ArrayList<ModelComponent> orderedMousePickModels;

		private static ByteBuffer hitIdDest = null;
		private static ByteBuffer worldPositionDest = null;
		private static ByteBuffer localPositionDest = null;

		public MousePickRequest(int x, int y, MousePickInfo info, DeferredRenderer renderer, EventListener notify) {
			this.x = x;
			this.y = y;
			this.info = info;
			this.renderer = renderer;
			orderedMousePickModels = renderer.orderedMousePickModels;
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
						if (hitId < orderedMousePickModels.size() && hitId >= 0)
							info.model = orderedMousePickModels.get(hitId);
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
			} else if (!isDone()) {
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
			} else {
				return true;
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

	public void mousePick(int x, int y, MousePickInfo info, EventListener notify) {
		if (freePbos == null) {
			initPboQueue();
		}

		mousePickRequests.add(new MousePickRequest(x / MOUSE_PICK_BUFFER_DOWN_SCALE, y / MOUSE_PICK_BUFFER_DOWN_SCALE, info, this, notify));
	}

	private void initPboQueue() {
		freePbos = new LinkedBlockingQueue<Integer>(MOUSE_PICK_PBO_COUNT);
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

	private PixelBuffer getHitIdPbo(int i) {
		if (hitIdPbos[i] == null) {
			hitIdPbos[i] = new PixelBuffer(4);
		}
		return hitIdPbos[i];
	}

	private PixelBuffer getWorldPositionPbo(int i) {
		if (worldPositionPbos[i] == null) {
			worldPositionPbos[i] = new PixelBuffer(12);
		}
		return worldPositionPbos[i];
	}

	private PixelBuffer getLocalPositionPbo(int i) {
		if (localPositionPbos[i] == null) {
			localPositionPbos[i] = new PixelBuffer(12);
		}
		return localPositionPbos[i];
	}

	@Override
	public void preRender(BitFieldInt flags) {
		this.flags = flags;
	}

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

	public void setAmbientColor(Vector4f ambientColor) {
		this.ambientColor = ambientColor;
	}

	@Override
	public void setModelMatrix(Matrix4f modelMatrix) {
		super.setModelMatrix(modelMatrix);
		if (Shader.bound() == shader)
			shader.loadModelMatrix(modelMatrix);
	}

	public void setPostFX(PostFXOutput postFX) {
		this.postFX = postFX;
	}

	@Override
	public void postResize(PostResizeEvent event) {
		initPboQueue();
		dataBuffer = new FramebufferDeferred(event.width, event.height);
		lightBuffer = new Framebuffer2D(event.width, event.height);
		mousePickBuffer = new FramebufferMousePick(event.width / MOUSE_PICK_BUFFER_DOWN_SCALE, event.height / MOUSE_PICK_BUFFER_DOWN_SCALE);
		colorOutput.setTextureID(dataBuffer.getColorTextureID());
		positionOutput.setTextureID(dataBuffer.getPositionTextureID());
		normalOutput.setTextureID(dataBuffer.getNormalTextureID());
		specularOutput.setTextureID(dataBuffer.getSpecularTextureID());
		depthOutput.setTextureID(dataBuffer.getDepthTexutreID());
		lightOutput.setTextureID(lightBuffer.getColorTextureID());
		postFX.postResize(event);
	}

	@Override
	public void preResize() {
		synchronized (mousePickRequests) {
			mousePickRequests.clear();
		}
		dataBuffer.delete();
		lightBuffer.delete();
		mousePickBuffer.delete();
		postFX.preResize();
	}

	public boolean isWireframe() {
		return wireframe;
	}

	public void setWireframe(boolean wireframe) {
		this.wireframe = wireframe;
	}

	public TextureOutput getColorOutput() {
		return colorOutput;
	}

	public TextureOutput getPositionOutput() {
		return positionOutput;
	}

	public TextureOutput getNormalOutput() {
		return normalOutput;
	}

	public TextureOutput getSpecularOutput() {
		return specularOutput;
	}

	public TextureOutput getDepthOutput() {
		return depthOutput;
	}

	public TextureOutput getLightOutput() {
		return lightOutput;
	}
}
