package primitives;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector2f;


import properties.Mesh;

public class Plane {

	public static Mesh build() {
		Mesh result = new Mesh();
		result.setRenderParts(Mesh.TEXTURE + Mesh.NORMAL + Mesh.COLOR);
		
		Vector3f red =new Vector3f(1, 0, 0);
		Vector3f green =new Vector3f(0, 1, 0);
		Vector3f blue =new Vector3f(0, 0, 1);
		Vector3f yellow =new Vector3f(1, 1, 0);
		
		Vector3f centerV=new Vector3f(0.0f, 0, 0.0f);
		Vector3f leftDownV=new Vector3f(-0.5f, 0, -0.5f);
		Vector3f rightDownV=new Vector3f(0.5f, 0, -0.5f);
		Vector3f leftUpV=new Vector3f(-0.5f, 0, 0.5f);
		Vector3f rightUpV=new Vector3f(0.5f, 0, 0.5f);
		result.add(leftDownV, new Vector3f(0, 1, 0), red , new Vector2f(0, 0));
		result.add(centerV, new Vector3f(0, 1, 0), red, new Vector2f(0.5f, 0.5f));
		result.add(rightDownV, new Vector3f(0, 1, 0), red, new Vector2f(1, 0));
		
		result.add(leftUpV, new Vector3f(0, 1, 0), green, new Vector2f(0, 1));
		result.add(centerV, new Vector3f(0, 1, 0), green, new Vector2f(0.5f, 0.5f));
		result.add(leftDownV, new Vector3f(0, 1, 0), green, new Vector2f(0, 0));
		
		result.add(rightUpV, new Vector3f(0, 1, 0), blue , new Vector2f(1, 1));
		result.add(centerV, new Vector3f(0, 1, 0), blue, new Vector2f(0.5f, 0.5f));
		result.add(leftUpV, new Vector3f(0, 1, 0), blue, new Vector2f(0, 1));
		
		result.add(rightDownV, new Vector3f(0, 1, 0), yellow, new Vector2f(1, 0));
		result.add(centerV, new Vector3f(0, 1, 0), yellow, new Vector2f(0.5f, 0.5f));
		result.add(rightUpV, new Vector3f(0, 1, 0), yellow, new Vector2f(1, 1));
		
		return result;
	}

}
