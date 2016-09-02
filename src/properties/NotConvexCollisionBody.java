package properties;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Set;

import javax.vecmath.Vector3f;

import org.lwjgl.BufferUtils;

import base.Object3d;

import com.bulletphysics.collision.shapes.BvhTriangleMeshShape;
import com.bulletphysics.collision.shapes.IndexedMesh;
import com.bulletphysics.collision.shapes.ScalarType;
import com.bulletphysics.collision.shapes.TriangleIndexVertexArray;

import physics.AbstractCollisionBody;
import physics.TriangleStrip;

public class NotConvexCollisionBody extends MeshCollisionBody {
	
	public void init(TriangleStrip triangleMesh,Object3d owner) {
		int size=triangleMesh.getSize();
		IndexedMesh im=new IndexedMesh();
		im.indexType=ScalarType.INTEGER;
		im.numTriangles=size/3;
		im.numVertices=size;
		im.vertexStride= 12;
		im.triangleIndexStride=12;
		im.triangleIndexBase=BufferUtils.createByteBuffer(size*4).order(ByteOrder.nativeOrder());
		im.vertexBase=BufferUtils.createByteBuffer(size*4*3).order(ByteOrder.nativeOrder());
		FloatBuffer fb=im.vertexBase.asFloatBuffer();
		IntBuffer ib=im.triangleIndexBase.asIntBuffer();
		int i=0;
		for(Vector3f v:triangleMesh){
			fb.put(v.x);
			fb.put(v.y);
			fb.put(v.z);
			ib.put(i++);
		}
		im.vertexBase.position(0);
		im.triangleIndexBase.position(0);
		TriangleIndexVertexArray tiva=new TriangleIndexVertexArray(
				im.numTriangles,im.triangleIndexBase,im.triangleIndexStride,
				im.numVertices,im.vertexBase,im.vertexStride);
		shape=new BvhTriangleMeshShape(tiva,true);
		super.init(owner);
	}
}
