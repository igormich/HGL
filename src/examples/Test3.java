package examples;

import io.MeshLoaderSMD;

import javax.vecmath.Vector3f;

import materials.Texture2D;
import materials.TexturedMaterial;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import physics.AbstractCollisionBody;
import physics.PhysicController;
import properties.ConvexCollisionBody;
import properties.MultiMesh;
import properties.NotConvexCollisionBody;
import properties.Property3d;
import base.Camera;
import base.Object3d;
import base.Scene;


public class Test3 {
	private static Object3d test_box1;
	private static Object3d trap;
	private static Object3d bullet;
	
	static void genError() throws Exception{
		throw new IllegalArgumentException();
	}
	public static void main(String[] args) throws Exception {
		Utils.initDisplay(512,512,false);
		Scene scene=new Scene();
		Camera camera=new Camera();
		camera.getPosition().setTranslation(0, 15, 5).pitch(90).turn(90).pitch(15);
		MultiMesh test_cube = MeshLoaderSMD.loadMultiMesh("models/test_cube", null, false);
		MultiMesh test_plane = MeshLoaderSMD.loadMultiMesh("models/test_plane", null, false);
		AbstractCollisionBody staticBody = new NotConvexCollisionBody().setMass(0);
		AbstractCollisionBody dynamicBody = new ConvexCollisionBody().setMass(1);
		AbstractCollisionBody dynamicFlyBody = new ConvexCollisionBody().setMass(10).setGravity(false);
		
		scene.add(test_cube,dynamicBody).getPosition().move(2.5f, 0, -3);
		scene.add(test_cube,dynamicBody).getPosition().move(-2.5f, 0, -3);
		scene.add(test_cube,dynamicBody).getPosition().move(0, 0, -3);
		
		scene.add(test_cube,dynamicBody).getPosition().move(1.5f, 0, -1);
		scene.add(test_cube,dynamicBody).getPosition().move(-1.5f, 0, -1);

		scene.add(test_cube,dynamicBody).getPosition().move(0, 0, 1);
		
		scene.add(test_plane,staticBody).getPosition().move(3, 0, -5).turn(90);
		scene.add(test_plane,staticBody).getPosition().move(0, 0, -5).turn(90);
		scene.add(test_plane,staticBody).getPosition().move(-3, 0, -5).turn(90);
		
		bullet=scene.add(test_cube,dynamicFlyBody);
		bullet.getPosition().move(0, 5, 0);
		bullet.get(ConvexCollisionBody.class).setGravity(false);
		TexturedMaterial plasterMaterial = new TexturedMaterial(new Texture2D("textures/plaster_up0.jpg"));
		test_cube.setMaterialForAll(plasterMaterial);
		
		Property3d script=new Property3d() {

			@Override
			public void tick(float deltaTime, float time, Object3d owner) {
				float x = MyMouse.getX();
				float y = MyMouse.getY();
				owner.getPosition().setPitch(x*50);
				owner.getPosition().setTurn(y*50);
			}
		};
		bullet.add(script);
		while(!Display.isCloseRequested())
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				org.lwjgl.util.vector.Vector3f vec = bullet.getPosition().getFrontVector();
				//org.lwjgl.util.vector.Vector3f vec = new org.lwjgl.util.vector.Vector3f(0, 1, 0);
				vec = (org.lwjgl.util.vector.Vector3f) vec.scale(5).negate();
				//bullet.get(ConvexCollisionBody.class).applyCentralImpulse(bullet,vec);
				bullet.get(ConvexCollisionBody.class).applyCentralImpulse(bullet,vec);
			}
			
			PhysicController.getDefault().step(1f/60, 10);
			scene.tick(1f/60);
			scene.render(camera);
			Display.update();
			Display.sync(60);
		}
	}

}
