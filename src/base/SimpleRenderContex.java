package base;

import java.util.ArrayList;
import java.util.List;

import properties.Property3d;
import utils.Pair;

public class SimpleRenderContex implements RenderContex {

	private List<Pair<Property3d,Position>> transparentPool=new ArrayList<Pair<Property3d,Position>>();
	private boolean useMaterial=true;
	private boolean skipTransparent;
	
	@Override
	public boolean useMaterial() {
		return useMaterial;
	}

	@Override
	public boolean skipTransparent() {
		return skipTransparent;
	}

	@Override
	public boolean storeTransparent() {
		return skipTransparent;
	}

	@Override
	public void store(Property3d transparentObject) {
		if(!storeTransparent())
			return;		
		Position position=new Position(null);
		position.loadFromOpenGL();
		transparentPool.add(new Pair<Property3d, Position>(transparentObject, position));
	}

	@Override
	public List<Pair<Property3d, Position>> getTransparentObjects() {
		return transparentPool;
	}

}
