package base;

import java.util.List;

import properties.Property3d;

public interface RenderContex {
	boolean useMaterial();
	boolean selectMode();
	boolean skipTransparent();
	boolean storeTransparent();
	void store(Property3d transparentObject, Object3d owner);
}
