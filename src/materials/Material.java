package materials;

import base.Object3d;



public interface Material {
	boolean isTransparent();
	void apply(Object3d owner);
	void unApply();
}
