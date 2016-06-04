package base;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.opengl.Display;

import properties.Camera;

public class Scene {
	
	private Object3d root=new Object3d();
	
	public Object3d add(Object3d object3d){
		return root.addChild(object3d);
	}
	public void render(Camera camera){
		int width = Display.getWidth();
		int height = Display.getHeight();
		glViewport (0, 0, width, height);
		glClearColor (0.0f, 0.0f, 0.0f, 1.0f);	
		glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity ();	
		camera.apply();
		SimpleRenderContex renderContex=new SimpleRenderContex();;
		root.render(renderContex);
	}
}
