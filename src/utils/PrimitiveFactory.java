package utils;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import properties.Mesh;

public final class PrimitiveFactory {

	public static Mesh createPlane(Mesh result,float width,float heigth){
		result.vert.add(new Vector3f(-width/2, 0, -heigth/2));
		result.vert.add(new Vector3f(width/2, 0, -heigth/2));
		result.vert.add(new Vector3f(width/2, 0, heigth/2));
		
		result.vert.add(new Vector3f(width/2, 0, heigth/2));
		result.vert.add(new Vector3f(-width/2, 0, heigth/2));
		result.vert.add(new Vector3f(-width/2, 0, -heigth/2));
		
		result.normals.add(new Vector3f(0, 1, 0));
		result.normals.add(new Vector3f(0, 1, 0));
		result.normals.add(new Vector3f(0, 1, 0));
		result.normals.add(new Vector3f(0, 1, 0));
		result.normals.add(new Vector3f(0, 1, 0));
		result.normals.add(new Vector3f(0, 1, 0));		
	
		result.tex.add(new Vector2f(1, 0));
		result.tex.add(new Vector2f(0, 0));
		result.tex.add(new Vector2f(0, 1));
		
		result.tex.add(new Vector2f(0, 1));
		result.tex.add(new Vector2f(1, 1));
		result.tex.add(new Vector2f(1, 0));
		
		result.renderParts=Mesh.TEXTURE+Mesh.NORMAL;
		return result;
	}
	public static Mesh createPlane(float width,float heigth){		
		return createPlane(new Mesh(),width,heigth);
	}

}
