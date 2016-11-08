package nullEngine.gl.model;

import nullEngine.gl.buffer.IndexBuffer;
import nullEngine.gl.buffer.VertexBuffer;
import nullEngine.managing.ManagedResource;
import nullEngine.util.logs.Logs;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import util.Sizeof;

import java.util.ArrayList;

public class Model extends ManagedResource {

	private static final ArrayList<Model> models = new ArrayList<Model>();
	private static int currentModelID = 0;
	private int modelID;

	private int vaoID;
	private int[] vertexCounts;
	private int[] vertexOffsets;
	private IndexBuffer ibo;
	private VertexAttribPointer[] attribs;
	private float radius;

	public Model(int vaoID, int[] vertexCounts, int[] vertexOffsets, float radius, IndexBuffer ibo, VertexAttribPointer... attribs) {
		super(String.valueOf(currentModelID), "model", join(ibo, attribs));
		modelID = currentModelID++;
		this.vaoID = vaoID;
		this.vertexCounts = vertexCounts;
		this.vertexOffsets = vertexOffsets;
		this.ibo = ibo;
		this.attribs = attribs;
		this.radius = radius;
		models.add(this);

		GL30.glBindVertexArray(vaoID);

		ibo.bind();
		int attribIdx = 0;
		for (VertexAttribPointer attrib : attribs) {
			attribIdx = attrib.bind(attribIdx);
		}
	}

	public void setVertexCounts(int[] vertexCounts) {
		this.vertexCounts = vertexCounts;
	}

	public void setVertexOffsets(int[] vertexOffsets) {
		this.vertexOffsets = vertexOffsets;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount(int lod) {
		return vertexCounts[lod];
	}

	public int getVertexOffset(int lod) {
		return vertexOffsets[lod];
	}

	public float getRadius() {
		return radius;
	}

	public void render(int lod) {
		preRender();
		lazyRender(lod);
		postRender();
	}

	public void lazyRender(int lod) {
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCounts[lod], GL11.GL_UNSIGNED_INT, vertexOffsets[lod] * Sizeof.INT);
	}

	public void preRender() {
		GL30.glBindVertexArray(vaoID);
		ibo.update();
		for (VertexAttribPointer attrib : attribs)
			attrib.update();
	}

	public void postRender() {
		GL30.glBindVertexArray(0);
	}

	public void render() {
		render(0);
	}

	public int getLODCount() {
		return vertexCounts.length;
	}

	public static void contextChanged(ArrayList<Integer> vaos) {
		Logs.d("Recreating vertex arrays");
		for (Model model : models) {
			model.recreate();
			vaos.add(model.getVaoID());
		}
	}

	@Override
	public boolean delete() {
		GL30.glDeleteVertexArrays(vaoID);
		models.remove(this);
		return true;
	}

	private void recreate() {
		vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);

		ibo.bind();
		int attribIdx = 0;
		for (VertexAttribPointer attrib : attribs) {
			attribIdx = attrib.bind(attribIdx);
		}

		GL30.glBindVertexArray(0);
	}

	public IndexBuffer getIndexBuffer() {
		return ibo;
	}

	public VertexAttribPointer getAttrib(int index) {
		return attribs[index];
	}

	public VertexBuffer getVertexBuffer(int index) {
		return attribs[index].getVertexBuffer();
	}
}
