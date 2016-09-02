package examples;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import base.Camera;
import base.Object3d;
import base.Scene;
import io.MeshLoaderSMD;
import materials.Texture2D;
import materials.TexturedMaterial;
import physics.AbstractCollisionBody;
import physics.PhysicController;
import properties.ConvexCollisionBody;
import properties.MultiMesh;
import properties.NotConvexCollisionBody;
import properties.Property3d;


public class Test2 {
	private static Object3d test_box1;
	private static Object3d trap;
	
	static void genError() throws Exception{
		throw new IllegalArgumentException();
	}
	public static void main(String[] args) throws Exception {
	//	BufferedImage image = new BufferedImage(512, 512, BufferedImage.TYPE_3BYTE_BGR);
		Utils.initDisplay(512,512,false);
		Scene scene=new Scene();
		Camera camera=new Camera();
		camera.getPosition().setTranslation(0, 15, 15).pitch(90).turn(90).pitch(45);
		MultiMesh cubeMesh = MeshLoaderSMD.loadMultiMesh("models/test_cube", null, false);
		MultiMesh test_box = MeshLoaderSMD.loadMultiMesh("models/test_box", null, false);
		MultiMesh test_plane = MeshLoaderSMD.loadMultiMesh("models/test_plane", null, false);
		AbstractCollisionBody staticBody = new NotConvexCollisionBody().setMass(0);
		AbstractCollisionBody dynamicBody = new ConvexCollisionBody().setMass(1).setInertia(new javax.vecmath.Vector3f(0,1,0));
		Property3d boxControl=new Property3d() {
			int pos=0;
			@Override
			public void tick(float deltaTime, float time, Object3d owner) {
				if((owner.getPosition().getAbsoluteTranslation().z<-15)||(PhysicController.getDefault().collisionTest(owner,trap)))
				{
					owner.get(ConvexCollisionBody.class).stopLinearVelocity(owner);
					owner.get(ConvexCollisionBody.class).stopAngularVelocity(owner);
					owner.resetPosition().move(-5+pos*5,0,5);
					pos++;
					if(pos==3)
						pos=0;
				}
			}
			@Override
			public void collision(Object3d owner, Object3d otherObject) {

			}
		};
		scene.add(cubeMesh,dynamicBody,boxControl).getPosition().move(3, 0, 5);
		scene.add(cubeMesh,dynamicBody,boxControl).getPosition().move(-3, 0, 5);
		
		scene.add(test_plane,staticBody).getPosition().move(5, 0, 0).roll(45);
		scene.add(test_plane,staticBody).getPosition().move(-7, 0, 0).roll(-45);
		
		test_box1 = scene.add(test_box);
		test_box1.getPosition()
			.move(0, 0, -6)
			.turn(90)
			.roll(90);
		test_box1.add(staticBody);
		trap=new Object3d();
		trap.add(test_plane);
		trap.add(staticBody);
		test_box1.addChild(trap);
		
		TexturedMaterial plasterMaterial = new TexturedMaterial(new Texture2D("textures/plaster_up0.jpg"));
		cubeMesh.setMaterialForAll(plasterMaterial);
		test_box.setMaterialForAll(plasterMaterial);
		
		while(!Display.isCloseRequested())
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				test_box1.getPosition().move(0.1f, 0, 0);
				trap.getPosition().move(0.1f, 0, 0);
				
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				test_box1.getPosition().move(-0.1f, 0, 0);
				trap.getPosition().move(-0.1f, 0, 0);
			}
			PhysicController.getDefault().step(1f/60, 10);
			scene.tick(1f/60);
			scene.render(camera);
			Display.update();
			Display.sync(60);
		}
	}

}
