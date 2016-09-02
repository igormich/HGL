package horizontshader;

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
		float horisontCoeff = 0.1f;
		Utils.initDisplay(512,512,false);
		Scene scene=new Scene();
		Camera camera=new Camera();
		camera.farPlane=1000;
		camera.getPosition().setTranslation(0, 15, 5).pitch(90).turn(90).pitch(15);

		MultiMesh test_plane1 = MeshLoaderSMD.loadMultiMesh("models/test_plane", null, false);
		MultiMesh test_plane2 = MeshLoaderSMD.loadMultiMesh("models/test_plane", null, false);
		MultiMesh test_cube = MeshLoaderSMD.loadMultiMesh("models/test_cube", null, false);
		Shader horisont1=ShaderProcessor.build(HorisontVertexShader.class, FogFragmentShader.class);
		Shader horisont2=ShaderProcessor.build(HorisontVertexShader.class, FogFragmentShader.class);
		horisont1.setUniform(new Vector4f(1, 0, 0, 1), "colorU");
		horisont1.setUniform(new Vector4f(0.9f, 0.9f, 1f, 1), "fogColorU");
		horisont1.setUniform(camera.getPosition().getAbsoluteTranslation(), "cameraPosU");
		horisont1.setUniform(300, "distMax");
		
		horisont2.setUniform(new Vector4f(1, 1, 1, 1), "colorU");
		horisont2.setUniform(new Vector4f(0.9f, 0.9f, 1f, 1), "fogColorU");
		horisont2.setUniform(camera.getPosition().getAbsoluteTranslation(), "cameraPosU");
		horisont2.setUniform(300, "distMax");
		test_plane1.setMaterialForAll(horisont1);
		test_plane2.setMaterialForAll(horisont2);
		
		for(int x=0;x<100;x++)
			for(int y=0;y<100;y+=1){
				if(y %2 ==0){
					scene.add(test_plane1).getPosition().move(x*5f-250, -y*4f, -5).turn(90);
					scene.add(test_plane2).getPosition().move(x*5f-252.5f, -y*4f, -5).turn(90);
				} else {
					scene.add(test_plane1).getPosition().move(x*5f-252.5f, -y*4f, -5).turn(90);
					scene.add(test_plane2).getPosition().move(x*5f-250, -y*4f, -5).turn(90);
				}
				
			}
		test_cube.setMaterialForAll(horisont1);
		for(int x=0;x<5;x+=1)
			for(int y=0;y<5;y+=1)
				scene.add(test_cube).getPosition().move(-90+x*25, -25-y*25, -3).setScale(2);
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
			if((Keyboard.isKeyDown(Keyboard.KEY_UP))&&(horisontCoeff<1))
				horisontCoeff+=0.01;
			if((Keyboard.isKeyDown(Keyboard.KEY_DOWN))&&(horisontCoeff>0))
				horisontCoeff-=0.01;
			horisont1.setUniform(horisontCoeff, "horisontCoeffU");
			horisont2.setUniform(horisontCoeff, "horisontCoeffU");
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
