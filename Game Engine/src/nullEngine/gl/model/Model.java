package nullEngine.gl.model;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import nullEngine.util.logs.Logs;

import java.util.ArrayList;

public class Model {

	private static final ArrayList<Model> models = new ArrayList<Model>();

	private int[] vaoIDs;
	private int[] vertexCounts;
	private int[] ibos;
	private int[] vertexVBOs;
	private int[] texCoordVBOs;
	private int[] normalVBOs;

	public static Model QUAD;

	public Model(int[] vaoIDs, int[] vertexCounts, int[] ibos, int[] vertexVBOs, int[] texCoordVBOs, int[] normalVBOs) {
		this.vaoIDs = vaoIDs;
		this.vertexCounts = vertexCounts;
		this.ibos = ibos;
		this.vertexVBOs = vertexVBOs;
		this.texCoordVBOs = texCoordVBOs;
		this.normalVBOs = normalVBOs;
		models.add(this);
	}

	public int getVaoID(int lod) {
		return vaoIDs[lod];
	}

	public int getVertexCount(int lod) {
		return vertexCounts[lod];
	}

	public void render(int lod) {
		preRender(lod);
		lazyRender(lod);
		postRender();
	}

	public void lazyRender(int lod) {
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCounts[lod], GL11.GL_UNSIGNED_INT, 0);
	}

	public void preRender(int lod) {
		GL30.glBindVertexArray(vaoIDs[lod]);
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
			for (int i = 0; i < model.getLODCount(); i++)
				vaos.add(model.getVaoID(i));
		}
	}

	public void delete() {
		for (int vertexVBO : vertexCounts)
			GL15.glDeleteBuffers(vertexVBO);
		for (int texCoordVBO : texCoordVBOs)
			GL15.glDeleteBuffers(texCoordVBO);
		for (int normalVBO : normalVBOs)
			GL15.glDeleteBuffers(normalVBO);
		for (int vaoID : vaoIDs)
			GL30.glDeleteVertexArrays(vaoID);
		models.remove(this);
	}

	private void recreate() {
		for (int i = 0; i < getLODCount(); i++) {
			vaoIDs[i] = GL30.glGenVertexArrays();
			GL30.glBindVertexArray(vaoIDs[i]);

			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibos[i]);

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexVBOs[i]);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texCoordVBOs[i]);
			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalVBOs[i]);
			GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);

			GL30.glBindVertexArray(0);
		}
	}
}
