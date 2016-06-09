package base;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;

public class Camera extends Object3d implements Applyable {

	public Object3d target=null;
	public float angle=60;
	public float param=1;
	public float farPlane=200;
	public float nearPlane=0.01f;
	public void apply(){
		glMatrixMode (GL_PROJECTION);								// Select The Projection Matrix
		glLoadIdentity ();											// Reset The Projection Matrix	
		//GLU.gluPerspective (angle, param*p, nearPlane, farPlane);
		float width = Display.getWidth();
		float height = Display.getHeight();
		GLU.gluPerspective (angle, param*width/height, nearPlane, farPlane);
		Matrix4f matrix = getPosition().getAbsoluteMatrix();
		//System.out.println(matrix);
		if(target!=null){
			Matrix4f targetMatrix = target.getPosition().getAbsoluteMatrix();
			GLU.gluLookAt(matrix.m30, matrix.m31, matrix.m32,
					matrix.m30-targetMatrix.m30, matrix.m31-targetMatrix.m31, matrix.m32-targetMatrix.m32,
					matrix.m10, matrix.m11, matrix.m12);			
		}
		else{
			GLU.gluLookAt(matrix.m30, matrix.m31, matrix.m32,
				matrix.m30-matrix.m00, matrix.m31-matrix.m01, matrix.m32-matrix.m02,
				matrix.m10, matrix.m11, matrix.m12);
		}
		glMatrixMode (GL_MODELVIEW);
	}
}
