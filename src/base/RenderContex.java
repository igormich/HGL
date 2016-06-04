package base;

import java.util.List;

import properties.Property3d;
import utils.Pair;

public interface RenderContex {
	boolean useMaterial();
	boolean skipTransparent();
	boolean storeTransparent();
	void store(Property3d transparentObject);
	public List<Pair<Property3d, Position>> getTransparentObjects();
}
