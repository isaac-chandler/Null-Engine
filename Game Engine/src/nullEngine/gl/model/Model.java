package nullEngine.gl.model;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import nullEngine.util.logs.Logs;

import java.util.ArrayList;

public class Model {

	private static final ArrayList<Model> models = new ArrayList<Model>();

	private int vaoID;
	private int vertexCount;
	private int ibo;
	private int vertexVBO;
	private int texCoordVBO;
	private int normalVBO;

	public static Model QUAD;

	public Model(int vaoID, int vertexCount, int ibo, int vertexVBO, int texCoordVBO, int normalVBO) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.ibo = ibo;
		this.vertexVBO = vertexVBO;
		this.texCoordVBO = texCoordVBO;
		this.normalVBO = normalVBO;
		models.add(this);
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void render() {
		GL30.glBindVertexArray(vaoID);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
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
