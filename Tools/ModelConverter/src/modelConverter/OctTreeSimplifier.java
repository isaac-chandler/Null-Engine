package modelConverter;

import modelConverter.obj.OBJModel;

import java.util.ArrayList;
import java.util.Iterator;

public class OctTreeSimplifier implements Simplifier {

	@Override
	public OBJModel simplify(double lodBias, OBJModel model) {
		OBJModel result = new OBJModel();
		int target = (int) (model.positions.size() / 3 / lodBias);

		ArrayList<Integer> indices = new ArrayList<Integer>();

		float minX = Float.MAX_VALUE;
		float minY = Float.MAX_VALUE;
		float minZ = Float.MAX_VALUE;

		float maxX = Float.MIN_VALUE;
		float maxY = Float.MIN_VALUE;
		float maxZ = Float.MIN_VALUE;

		for (int i = 0; i < model.positions.size() / 3; i++) {
			if (model.positions.get(i * 3) < minX)
				minX = model.positions.get(i * 3);

			if (model.positions.get(i * 3 + 1) < minY)
				minY = model.positions.get(i * 3 + 1);

			if (model.positions.get(i * 3 + 2) < minZ)
				minZ = model.positions.get(i * 3 + 2);

			if (model.positions.get(i * 3) > maxX)
				maxX = model.positions.get(i * 3);

			if (model.positions.get(i * 3 + 1) > maxY)
				maxY = model.positions.get(i * 3 + 1);

			if (model.positions.get(i * 3 + 2) > maxZ)
				maxZ = model.positions.get(i * 3 + 2);
		}

		ArrayList indicesList = new ArrayList<Integer>(model.indices.length);
		for (int index : model.indices) {
			indicesList.add(index);
		}

		OctTree tree = new OctTree(model, minX, maxX, minY, maxY, minZ, maxZ, indicesList);


		int numWithVertices;

		while ((numWithVertices = tree.getNumberWithVertices()) < target) {
			if (!tree.split())
				break;
		}

		int[] indexMap = new int[model.indices.length];
		result.positions = new ArrayList<Float>(numWithVertices * 3);
		result.normals = new ArrayList<Float>(numWithVertices * 3);
		result.texCoords = new ArrayList<Float>(numWithVertices * 2);
		tree.createMesh(result.positions, result.normals, result.texCoords, indexMap);

		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		for (int i = 0; i < model.indices.length; i += 3) {
			Triangle triangle = new Triangle(indexMap[model.indices[i]], indexMap[model.indices[i + 1]], indexMap[model.indices[i + 2]]);
			if (!triangle.isLine() && !triangles.contains(triangle)) {
				triangles.add(triangle);
			}
		}

		for (Triangle triangle : triangles) {
			indices.add(triangle.i0);
			indices.add(triangle.i1);
			indices.add(triangle.i2);
		}

		result.indices = toIntArray(indices);
		return result;
	}

	@Override
	public double getDefaultLodBias() {
		return 2;
	}

	@Override
	public boolean getDefaultShouldLodChain() {
		return false;
	}

	private static int[] toIntArray(ArrayList<Integer> list) {
		int[] arr = new int[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}

	private static abstract class Cell {
		protected double minX, maxX, minY, maxY, minZ, maxZ;
		protected OBJModel model;

		public Cell(OBJModel model, double minX, double maxX, double minY, double maxY, double minZ, double maxZ) {
			this.minX = minX;
			this.maxX = maxX;
			this.minY = minY;
			this.maxY = maxY;
			this.minZ = minZ;
			this.maxZ = maxZ;
			this.model = model;
		}

		public abstract int getMaxIndexCount();

		public abstract int getTotalIndexCount();

		public abstract ArrayList<Integer> getContainedIndexes();

		public abstract int getNumberWithVertices();

		public abstract boolean split();

		public abstract void createMesh(ArrayList<Float> positions, ArrayList<Float> normals, ArrayList<Float> texCoords, int[] indexMap);
	}

	private static class OctCell extends Cell {

		private ArrayList<Integer> containedIndexes = new ArrayList<Integer>();

		public OctCell(OBJModel model, double minX, double maxX, double minY, double maxY, double minZ, double maxZ, ArrayList<Integer> indices) {
			super(model, minX, maxX, minY, maxY, minZ, maxZ);
			Iterator<Integer> iterator = indices.iterator();
			Integer index;
			while (iterator.hasNext()) {
				index = iterator.next();
				double x = model.positions.get(index * 3);
				double y = model.positions.get(index * 3 + 1);
				double z = model.positions.get(index * 3 + 2);
				if (x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ) {
					containedIndexes.add(index);
					iterator.remove();
				}
			}
		}

		@Override
		public int getMaxIndexCount() {
			return containedIndexes.size();
		}

		@Override
		public int getTotalIndexCount() {
			return containedIndexes.size();
		}

		@Override
		public ArrayList<Integer> getContainedIndexes() {
			return containedIndexes;
		}

		@Override
		public int getNumberWithVertices() {
			return containedIndexes.size() > 0 ? 1 : 0;
		}

		@Override
		public boolean split() {
			return false;
		}

		@Override
		public void createMesh(ArrayList<Float> positions, ArrayList<Float> normals, ArrayList<Float> texCoords, int[] indexMap) {
			if (containedIndexes.size() > 0) {
				float avgPosX = 0;
				float avgPosY = 0;
				float avgPosZ = 0;
				float avgNormalX = 0;
				float avgNormalY = 0;
				float avgNormalZ = 0;
				float avgTexCoordX = 0;
				float avgTexCoordY = 0;
				int newIndex = positions.size() / 3;
				for (int i = 0; i < containedIndexes.size(); i++) {
					int index = containedIndexes.get(i);
					indexMap[index] = newIndex;
					avgPosX += model.positions.get(index * 3);
					avgPosY += model.positions.get(index * 3 + 1);
					avgPosZ += model.positions.get(index * 3 + 2);
					avgNormalX += model.normals.get(index * 3);
					avgNormalY += model.normals.get(index * 3 + 1);
					avgNormalZ += model.normals.get(index * 3 + 2);
					avgTexCoordX += model.texCoords.get(index * 2);
					avgTexCoordY += model.texCoords.get(index * 2 + 1);
				}

				avgPosX /= containedIndexes.size();
				avgPosY /= containedIndexes.size();
				avgPosZ /= containedIndexes.size();
				avgNormalX /= containedIndexes.size();
				avgNormalY /= containedIndexes.size();
				avgNormalZ /= containedIndexes.size();
				avgTexCoordX /= containedIndexes.size();
				avgTexCoordY /= containedIndexes.size();
				positions.add(avgPosX);
				positions.add(avgPosY);
				positions.add(avgPosZ);
				normals.add(avgNormalX);
				normals.add(avgNormalY);
				normals.add(avgNormalZ);
				texCoords.add(avgTexCoordX);
				texCoords.add(avgTexCoordY);
			}
		}
	}

	private static class OctTree extends Cell {
		static OctTree lastSplit;
		private Cell ixiyiz, ixiyaz, ixayiz, ixayaz, axiyiz, axiyaz, axayiz, axayaz;
		private ArrayList<Integer> containedIndexes;

		public OctTree(OBJModel model, double minX, double maxX, double minY, double maxY, double minZ, double maxZ, ArrayList<Integer> indices) {
			super(model, minX, maxX, minY, maxY, minZ, maxZ);
			containedIndexes = new ArrayList<Integer>(indices);
			ixiyiz = new OctCell(model, minX, (minX + maxX) / 2, minY, (minY + maxY) / 2, minZ, (minZ + maxZ) / 2, indices);
			ixiyaz = new OctCell(model, minX, (minX + maxX) / 2, minY, (minY + maxY) / 2, (minZ + maxZ) / 2, maxZ, indices);
			ixayiz = new OctCell(model, minX, (minX + maxX) / 2, (minY + maxY) / 2, maxY, minZ, (minZ + maxZ) / 2, indices);
			ixayaz = new OctCell(model, minX, (minX + maxX) / 2, (minY + maxY) / 2, maxY, (minZ + maxZ) / 2, maxZ, indices);
			axiyiz = new OctCell(model, (minX + maxX) / 2, maxX, minY, (minY + maxY) / 2, minZ, (minZ + maxZ) / 2, indices);
			axiyaz = new OctCell(model, (minX + maxX) / 2, maxX, minY, (minY + maxY) / 2, (minZ + maxZ) / 2, maxZ, indices);
			axayiz = new OctCell(model, (minX + maxX) / 2, maxX, (minY + maxY) / 2, maxY, minZ, (minZ + maxZ) / 2, indices);
			axayaz = new OctCell(model, (minX + maxX) / 2, maxX, (minY + maxY) / 2, maxY, (minZ + maxZ) / 2, maxZ, indices);
		}

		public OctTree(Cell cell) {
			this(cell.model, cell.minX, cell.maxX, cell.minY, cell.maxY, cell.minZ, cell.maxZ, cell.getContainedIndexes());
		}

		@Override
		public int getMaxIndexCount() {
			int max = 0;
			int mixiyiz = ixiyiz.getMaxIndexCount();
			int mixiyaz = ixiyaz.getMaxIndexCount();
			int mixayiz = ixayiz.getMaxIndexCount();
			int mixayaz = ixayaz.getMaxIndexCount();
			int maxiyiz = axiyiz.getMaxIndexCount();
			int maxiyaz = axiyaz.getMaxIndexCount();
			int maxayiz = axayiz.getMaxIndexCount();
			int maxayaz = axayaz.getMaxIndexCount();
			if (mixiyiz > max) max = mixiyiz;
			if (mixiyaz > max) max = mixiyaz;
			if (mixayiz > max) max = mixayiz;
			if (mixayaz > max) max = mixayaz;
			if (maxiyiz > max) max = maxiyiz;
			if (maxiyaz > max) max = maxiyaz;
			if (maxayiz > max) max = maxayiz;
			if (maxayaz > max) max = maxayaz;
			return max;
		}

		@Override
		public int getTotalIndexCount() {
			int tixiyiz = ixiyiz.getTotalIndexCount();
			int tixiyaz = ixiyaz.getTotalIndexCount();
			int tixayiz = ixayiz.getTotalIndexCount();
			int tixayaz = ixayaz.getTotalIndexCount();
			int taxiyiz = axiyiz.getTotalIndexCount();
			int taxiyaz = axiyaz.getTotalIndexCount();
			int taxayiz = axayiz.getTotalIndexCount();
			int taxayaz = axayaz.getTotalIndexCount();
			return tixiyiz + tixiyaz + tixayiz + tixayaz + taxiyiz + taxiyaz + taxayiz + taxayaz;
		}

		@Override
		public ArrayList<Integer> getContainedIndexes() {
			return containedIndexes;
		}

		@Override
		public int getNumberWithVertices() {
			int tixiyiz = ixiyiz.getNumberWithVertices();
			int tixiyaz = ixiyaz.getNumberWithVertices();
			int tixayiz = ixayiz.getNumberWithVertices();
			int tixayaz = ixayaz.getNumberWithVertices();
			int taxiyiz = axiyiz.getNumberWithVertices();
			int taxiyaz = axiyaz.getNumberWithVertices();
			int taxayiz = axayiz.getNumberWithVertices();
			int taxayaz = axayaz.getNumberWithVertices();
			return tixiyiz + tixiyaz + tixayiz + tixayaz + taxiyiz + taxiyaz + taxayiz + taxayaz;
		}

		@Override
		public boolean split() {
			int max = 1;
			int mixiyiz = ixiyiz.getMaxIndexCount();
			int mixiyaz = ixiyaz.getMaxIndexCount();
			int mixayiz = ixayiz.getMaxIndexCount();
			int mixayaz = ixayaz.getMaxIndexCount();
			int maxiyiz = axiyiz.getMaxIndexCount();
			int maxiyaz = axiyaz.getMaxIndexCount();
			int maxayiz = axayiz.getMaxIndexCount();
			int maxayaz = axayaz.getMaxIndexCount();
			if (mixiyiz > max) max = mixiyiz;
			if (mixiyaz > max) max = mixiyaz;
			if (mixayiz > max) max = mixayiz;
			if (mixayaz > max) max = mixayaz;
			if (maxiyiz > max) max = maxiyiz;
			if (maxiyaz > max) max = maxiyaz;
			if (maxayiz > max) max = maxayiz;
			if (maxayaz > max) max = maxayaz;

			if (mixiyiz == max) {
				if (!ixiyiz.split()) ixiyiz = new OctTree(ixiyiz);
				lastSplit = (OctTree) ixiyiz;
				return true;
			}
			if (mixiyaz == max) {
				if (!ixiyaz.split()) ixiyaz = new OctTree(ixiyaz);
				lastSplit = (OctTree) ixiyaz;
				return true;
			}
			if (mixayiz == max) {
				if (!ixayiz.split()) ixayiz = new OctTree(ixayiz);
				lastSplit = (OctTree) ixayiz;
				return true;
			}
			if (mixayaz == max) {
				if (!ixayaz.split()) ixayaz = new OctTree(ixayaz);
				lastSplit = (OctTree) ixayaz;
				return true;
			}
			if (maxiyiz == max) {
				if (!axiyiz.split()) axiyiz = new OctTree(axiyiz);
				lastSplit = (OctTree) axiyiz;
				return true;
			}
			if (maxiyaz == max) {
				if (!axiyaz.split()) axiyaz = new OctTree(axiyaz);
				lastSplit = (OctTree) axiyaz;
				return true;
			}
			if (maxayiz == max) {
				if (!axayiz.split()) axayiz = new OctTree(axayiz);
				lastSplit = (OctTree) axayiz;
				return true;
			}
			if (maxayaz == max) {
				if (!axayaz.split()) axayaz = new OctTree(axayaz);
				lastSplit = (OctTree) axayaz;
				return true;
			}
			System.out.println("a");
			return true;
		}

		@Override
		public void createMesh(ArrayList<Float> positions, ArrayList<Float> normals, ArrayList<Float> texCoords, int[] indexMap) {
			ixiyiz.createMesh(positions, normals, texCoords, indexMap);
			ixiyaz.createMesh(positions, normals, texCoords, indexMap);
			ixayiz.createMesh(positions, normals, texCoords, indexMap);
			ixayaz.createMesh(positions, normals, texCoords, indexMap);
			axiyiz.createMesh(positions, normals, texCoords, indexMap);
			axiyaz.createMesh(positions, normals, texCoords, indexMap);
			axayiz.createMesh(positions, normals, texCoords, indexMap);
			axayaz.createMesh(positions, normals, texCoords, indexMap);
		}
	}
}
