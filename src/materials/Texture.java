package materials;

import base.Applyable;

public interface Texture extends Applyable{

	boolean isTransparent();
	void applyAs(int i);
}
