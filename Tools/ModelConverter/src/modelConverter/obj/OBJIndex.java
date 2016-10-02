package modelConverter.obj;

public class OBJIndex {
	public int positionIndex;
	public int texCoordIndex;
	public int normalIndex;

	public OBJIndex(String token, boolean hasTexCoords, boolean hasNormals) {
		String[] tokens = token.split("/");
		positionIndex = Integer.parseInt(tokens[0]) - 1;
		if (hasTexCoords)
			texCoordIndex = Integer.parseInt(tokens[1]) - 1;
		if (hasNormals)
			normalIndex = Integer.parseInt(tokens[2]) - 1;
	}
}
