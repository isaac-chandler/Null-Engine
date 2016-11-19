package nullEngine.gl.model;

import nullEngine.gl.buffer.IndexBuffer;
import nullEngine.gl.buffer.VertexBuffer;
import nullEngine.managing.ManagedResource;
import nullEngine.util.logs.Logs;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import util.Sizeof;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A mesh
 */
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

	/**
	 * Create a model
	 * @param vaoID The vertex array
	 * @param vertexCounts The vertex count for each level of detail
	 * @param vertexOffsets The vertex offset for each level of detail
	 * @param radius The radius
	 * @param ibo The index buffer
	 * @param attribs The vertex attributes
	 */
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

	/**
	 * Set the vertex counts
	 * @param vertexCounts The vertex counts
	 */
	public void setVertexCounts(int[] vertexCounts) {
		this.vertexCounts = vertexCounts;
	}

	/**
	 * Set the vertex offsets
	 * @param vertexOffsets The vertex offsets
	 */
	public void setVertexOffsets(int[] vertexOffsets) {
		this.vertexOffsets = vertexOffsets;
	}

	/**
	 * Set the radius
	 * @param radius The radius
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}

	/**
	 * Get the vertex array
	 * @return The vertex array id
	 */
	public int getVaoID() {
		return vaoID;
	}

	/**
	 * Get the vertex count
	 * @param lod The level of detail
	 * @return The vertex count
	 */
	public int getVertexCount(int lod) {
		return vertexCounts[lod];
	}

	/**
	 * Set the vertex offset
	 * @param lod The level of detail
	 * @return The vertex offset
	 */
	public int getVertexOffset(int lod) {
		return vertexOffsets[lod];
	}

	/**
	 * Get the radius
	 * @return The radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * Render the model
	 * @param lod The level of detail
	 */
	public void render(int lod) {
		preRender();
		lazyRender(lod);
		postRender();
	}

	/**
	 * Render the model without binding the model
	 * @param lod The level of detail
	 */
	public void lazyRender(int lod) {
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCounts[lod], GL11.GL_UNSIGNED_INT, vertexOffsets[lod] * Sizeof.INT);
	}

	/**
	 * Bind the model
	 */
	public void preRender() {
		GL30.glBindVertexArray(vaoID);
		ibo.update();
		for (VertexAttribPointer attrib : attribs)
			attrib.update();
	}

	/**
	 * Unbind the model
	 */
	public void postRender() {
		GL30.glBindVertexArray(0);
	}

	/**
	 * Render the model at the highest level of detail
	 */
	public void render() {
		render(0);
	}

	/**
	 * Get the number of levels of detail
	 * @return The number of levels of detail
	 */
	public int getLODCount() {
		return vertexCounts.length;
	}

	/**
	 * Recreate the models
	 * @param vaos The vertex arrays
	 */
	public static void contextChanged(Collection<Integer> vaos) {
		Logs.d("Recreating vertex arrays");
		for (Model model : models) {
			model.recreate();
			vaos.add(model.getVaoID());
		}
	}

	/**
	 * Delete this model
	 * @return <code>true</code>
	 */
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

	/**
	 * Get the index buffer
	 * @return The index buffer
	 */
	public IndexBuffer getIndexBuffer() {
		return ibo;
	}

	/**
	 * Get an attribute
	 * @param index The index
	 * @return The attribute
	 */
	public VertexAttribPointer getAttrib(int index) {
		return attribs[index];
	}

	/**
	 * Get the vertex buffer
	 * @param index The attribute index
	 * @return The vertex buffer
	 */
	public VertexBuffer getVertexBuffer(int index) {
		return attribs[index].getVertexBuffer();
	}
}
