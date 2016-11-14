package miner;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

import base.Camera;
import base.Object3d;
import base.Scene;
import io.MeshLoaderSMD;
import jglsl.ColorFragmentShader;
import jglsl.ShaderProcessor;
import materials.Shader;
import materials.SimpleMaterial;
import physics.PhysicController;
import properties.MultiMesh;
import properties.Property3d;


public class HorisontAndFog {
	
	static void genError() throws Exception{
		throw new IllegalArgumentException();
	}
	public static void main(String[] args) throws Exception {
		float horisontCoeff = 0.01f;
		Utils.initDisplay(512,512,false);
		Scene scene=new Scene();
		Camera camera=new Camera();
		camera.farPlane=1000;
		camera.getPosition().setTranslation(0, 15, 5).pitch(90).turn(90).pitch(15);

		MultiMesh test_plane1 = MeshLoaderSMD.loadMultiMesh("models/test_plane", null, false);
		MultiMesh test_cube = MeshLoaderSMD.loadMultiMesh("models/test_cube", null, false);
		test_plane1.setMaterialForAll(new SimpleMaterial(1, 0, 0));
		scene.add(test_plane1);
		
		Property3d script=new Property3d() {
			@Override
			public void tick(float deltaTime, float time, Object3d owner) {
				float x = MyMouse.getX();
				float y = MyMouse.getY();
				//owner.getPosition().turn(x);
				//owner.getPosition().setTurn(y*50);
			}
		};
		camera.add(script);
		while(!Display.isCloseRequested())
		{		
			Display.setTitle(""+horisontCoeff);
			camera.tick(1f/60, 0);
			PhysicController.getDefault().step(1f/60, 10);
			scene.tick(1f/60);
			scene.render(camera);
			Display.update();
			Display.sync(60);
		}
	}
}
