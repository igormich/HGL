package base;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW_MATRIX;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMultMatrix;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL11.glGetFloat;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;









import javax.jws.Oneway;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import properties.MultiMesh;
import properties.Property3d;

public class Scene {
	private class SceneRenderContex implements RenderContex {

		private boolean useMaterial=true;
		private boolean skipTransparent=true;
		private boolean selectMode;
		
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
		public void store(Property3d transparentObject,Object3d owner) {
			if(!storeTransparent())
				return;		
			Scene.this.storeTransparent(transparentObject,owner);
		}

		public void setUseMaterial(boolean useMaterial) {
			this.useMaterial = useMaterial;
		}

		public void setSkipTransparent(boolean skipTransparent) {
			this.skipTransparent = skipTransparent;
		}
		@Override
		public boolean selectMode() {
			return selectMode;
		}

		public void setSelectMode(boolean selectMode) {
			this.selectMode = selectMode;
		}
		

	}

	private static class TransparentContainer implements Comparable<TransparentContainer>{

		private Property3d transparentObject;
		private Object3d owner;
		private FloatBuffer positionBuffer;
		private Vector3f translation;
		private float distance;

		public TransparentContainer(Property3d transparentObject, Object3d owner) {
			this.transparentObject = transparentObject;
			this.owner = owner;
			positionBuffer=BufferUtils.createFloatBuffer(16);
			glGetFloat(GL_MODELVIEW_MATRIX,positionBuffer);
			translation=new Vector3f(positionBuffer.get(12),positionBuffer.get(13),positionBuffer.get(14));
		}

		public void calcDistanceTo(Vector3f cameraTranslation) {
			distance=Position.dist(translation, cameraTranslation);
		}

		@Override
		public int compareTo(TransparentContainer arg0) {
			return Float.compare(arg0.distance,distance);
		}
		
	}
	private Object3d root=new Object3d();
	private SceneRenderContex renderContex=new SceneRenderContex();
	private List<TransparentContainer> transparentPool=new ArrayList<TransparentContainer>();
	
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
		renderContex.setSkipTransparent(true);
		root.render(renderContex);
		renderContex.setSkipTransparent(false);
		renderTransparent(camera);
		transparentPool.clear();
	}
	private void renderTransparent(Camera camera) {
		Vector3f cameraTranslation = camera.getPosition().getAbsoluteTranslation();
		transparentPool.stream().peek(tc -> tc.calcDistanceTo(cameraTranslation)).sorted().forEach(tc -> {
			glLoadIdentity();
			glMultMatrix(tc.positionBuffer);
			tc.transparentObject.render(renderContex, tc.owner);
		});
	}
	private void storeTransparent(Property3d transparentObject,
			Object3d owner) {
		transparentPool.add(new TransparentContainer(transparentObject,owner));
	}
	public Object3d add(Property3d ... properties) {
		Object3d result=new Object3d();
		for(Property3d property:properties)
			result.add(property);
		add(result);
		return result;		
	}
}
