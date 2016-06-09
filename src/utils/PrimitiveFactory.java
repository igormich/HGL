package utils;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import properties.Mesh;

public final class PrimitiveFactory {

	public static Mesh createPlane(Mesh result,float width,float heigth){
		
		result.add(new Vector3f(-width/2, 0, -heigth/2), new Vector3f(0, 1, 0), null, new Vector2f(1, 0));
		result.add(new Vector3f(width/2, 0, -heigth/2), new Vector3f(0, 1, 0), null, new Vector2f(0, 1));
		result.add(new Vector3f(width/2, 0, heigth/2), new Vector3f(0, 1, 0), null, new Vector2f(1, 1));
		
		result.add(new Vector3f(width/2, 0, heigth/2), new Vector3f(0, 1, 0), null, new Vector2f(0, 1));
		result.add(new Vector3f(-width/2, 0, heigth/2), new Vector3f(0, 1, 0), null, new Vector2f(1, 1));
		result.add(new Vector3f(-width/2, 0, -heigth/2), new Vector3f(0, 1, 0), null, new Vector2f(1, 0));
		
		result.setRenderParts(Mesh.TEXTURE+Mesh.NORMAL);
		return result;
	}
	public static Mesh createPlane(float width,float heigth){		
		return createPlane(new Mesh(),width,heigth);
	}

}
