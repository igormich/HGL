package properties;

import java.util.HashSet;
import java.util.Set;

import base.Object3d;
import physics.AbstractCollisionBody;
import physics.TriangleStrip;

public abstract class MeshCollisionBody extends AbstractCollisionBody {
	protected Set<Object3d> owners = new HashSet<Object3d>();
	@Override
	public void register(Object3d owner) {
		if(!owners.contains(owner)){
			TriangleStrip triangleStrip = owner.get(Mesh.class);
			if(triangleStrip == null)
				triangleStrip = owner.get(MultiMesh.class);
			if(triangleStrip != null){
				init(triangleStrip,owner);			
			}
		}
	}
	protected abstract void init(TriangleStrip triangleStrip, Object3d owner);
}
