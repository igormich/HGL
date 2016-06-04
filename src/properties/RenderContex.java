package properties;

public interface RenderContex {
	boolean useMaterial();
	boolean skipTransparent();
	boolean storeTransparent();
	void store(Property3d transparentObject);
}
