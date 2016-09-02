package properties;

import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Vector3f;

import physics.AbstractCollisionBody;
import physics.TriangleStrip;
import base.Object3d;

import com.bulletphysics.collision.shapes.ConvexHullShape;
import com.bulletphysics.util.ObjectArrayList;

public class ConvexCollisionBody extends MeshCollisionBody {
	
	@Override
	public void tick(float deltaTime, float time, Object3d owner) {
		
	}

	public void init(TriangleStrip triangleMesh,Object3d owner) {
		ObjectArrayList<Vector3f> data=new ObjectArrayList<Vector3f>(triangleMesh.getSize());
		for(Vector3f v:triangleMesh)
			data.add(v);
		shape=new ConvexHullShape(data);
		super.init(owner);
	}


}
