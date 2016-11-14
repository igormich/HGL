package primitives;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import properties.Mesh;

public class Cone {
	public static Mesh buildCone() {
		Mesh result = new Mesh();
		result.setRenderParts(Mesh.TEXTURE + Mesh.NORMAL + Mesh.COLOR);
		
		Vector3f green =new Vector3f(0, 1, 0);
		Vector3f blue =new Vector3f(0, 0, 1);
		
		float h = 0.25f;
		Vector3f centerV=new Vector3f(0.0f, h, 0.0f);
		float r = 1f;
		double start = Math.PI+Math.PI/6;
		double end =  Math.PI*2-Math.PI/6;
		double step = Math.PI/30;
		int i=0;
		for(double angle=start;angle<end;angle+=step){
			Vector3f color= (i++ % 2 ==0) ? green: blue;
			double angle1 = angle + step;
			Vector3f leftDownV=new Vector3f((float) (r*Math.cos(angle)), 0, (float) (r*Math.sin(angle)));
			Vector3f rightDownV=new Vector3f((float) (r*Math.cos(angle1)), 0, (float) (r*Math.sin(angle1)));
			result.add(leftDownV, new Vector3f(0, 1, 0), color , new Vector2f(0, 0));
			result.add(centerV, new Vector3f(0, 1, 0), color, new Vector2f(0.5f, 0.5f));
			result.add(rightDownV, new Vector3f(0, 1, 0), color, new Vector2f(1, 0));
		}
		return result;
	}
	public static Mesh buildConoid() {
		Mesh result = new Mesh();
		result.setRenderParts(Mesh.TEXTURE + Mesh.NORMAL + Mesh.COLOR);
		
		Vector3f green =new Vector3f(0, 1, 0);
		Vector3f blue =new Vector3f(0, 0, 1);
		
		float h = 5f;
		float r1 = 0.15f;
		float r2 = 0.05f;
		double start = 0;
		double end =  Math.PI*2;
		double step = Math.PI/30;
		int i=0;
		for(double angle=start;angle<end;angle+=step){
			Vector3f color= (i++ % 2 ==0) ? green: blue;
			double angle1 = angle + step;
			Vector3f leftDownV=new Vector3f((float) (r1*Math.cos(angle)), 0, (float) (r1*Math.sin(angle)));
			Vector3f rightDownV=new Vector3f((float) (r1*Math.cos(angle1)), 0, (float) (r1*Math.sin(angle1)));
			Vector3f leftUpV=new Vector3f((float) (r2*Math.cos(angle)), h, (float) (r2*Math.sin(angle)));
			Vector3f rightUpV=new Vector3f((float) (r2*Math.cos(angle1)), h, (float) (r2*Math.sin(angle1)));
			result.add(leftDownV, new Vector3f(0, 1, 0), green , new Vector2f(0, 0));
			result.add(leftUpV, new Vector3f(0, 1, 0), green , new Vector2f(0, 1));
			result.add(rightDownV, new Vector3f(0, 1, 0), green, new Vector2f(1, 0));
			
			result.add(leftUpV, new Vector3f(0, 1, 0), blue , new Vector2f(0, 0));
			result.add(rightUpV, new Vector3f(0, 1, 0), blue, new Vector2f(1, 0));
			result.add(rightDownV, new Vector3f(0, 1, 0), blue , new Vector2f(0, 1));
		}
		return result;
	}	
}
