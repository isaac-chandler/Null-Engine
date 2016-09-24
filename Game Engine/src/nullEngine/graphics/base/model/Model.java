package nullEngine.graphics.base.model;

import nullEngine.util.Sizeof;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import nullEngine.util.logs.Logs;

import java.util.ArrayList;

public class Model {

	private static final ArrayList<Model> models = new ArrayList<Model>();

	private int vaoID;
	private int[] vertexCounts;
	private int[] vertexOffsets;
	private int ibo;
	private int vertexVBO;
	private int texCoordVBO;
	private int normalVBO;
	private float radius;

	public static Model QUAD;

	public Model(int vaoID, int[] vertexCounts, int[] vertexOffsets, int ibo, int vertexVBO, int texCoordVBO, int normalVBO, float radius) {
		this.vaoID = vaoID;
		this.vertexCounts = vertexCounts;
		this.vertexOffsets = vertexOffsets;
		this.ibo = ibo;
		this.vertexVBO = vertexVBO;
		this.texCoordVBO = texCoordVBO;
		this.normalVBO = normalVBO;
		this.radius = radius;
		models.add(this);
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
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
	}

	public void postRender() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
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

	public void delete() {
		GL15.glDeleteBuffers(vertexVBO);
		GL15.glDeleteBuffers(texCoordVBO);
		GL15.glDeleteBuffers(normalVBO);
		GL30.glDeleteVertexArrays(vaoID);
		models.remove(this);
	}

	private void recreate() {
		vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexVBO);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texCoordVBO);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalVBO);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);

		GL30.glBindVertexArray(0);
	}
}
