package modelConverter;

import modelConverter.obj.OBJModel;

public interface Simplifier {
	OBJModel simplify(double lodBias, OBJModel model);
	double getDefaultLodBias();
	boolean getDefaultShouldLodChain();
}
