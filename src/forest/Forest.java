package forest;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import base.Camera;
import base.Object3d;
import base.Scene;
import physics.PhysicController;
import primitives.Cone;
import primitives.Plane;
import properties.Mesh;


public class Forest {
	
	static void genError() throws Exception{
		throw new IllegalArgumentException();
	}
	static Object3d buildCristmasstree(){
		Object3d box=new Object3d();
		Mesh conoid = Cone.buildConoid();
		box.addChild(new Object3d(conoid).getPosition().move(0, 0, -3).turn(90).getOwner());
		Mesh cone = Cone.buildCone();
		int count = 40;
		for(int i=0;i<count;i++){
			float scale = (float) (Math.sqrt(count-i)/Math.sqrt(count));
			box.addChild(new Object3d(cone).getPosition()
					.move(0, 0, -3+i*0.125f).turn(90).roll(120*i+i*i*15).setScale(scale,1,scale).getOwner());
		}
		return box;
	}
	static Object3d buildPine(){
		Object3d box=new Object3d();
		Mesh conoid = Cone.buildConoid();
		box.addChild(new Object3d(conoid).getPosition().move(0, 0, -3).turn(90).getOwner());
		Mesh cone = Cone.buildCone();
		int count = 10;
		for(int i=0;i<count;i++){
			float scale = 0.5f;
			box.addChild(new Object3d(cone).getPosition()
					.move(0, 0, 1+i*0.125f).turn(180*(i%2)).turn(60)
					.roll(60*i).setScale(scale).getOwner());
			box.addChild(new Object3d(cone).getPosition()
					.move(0, 0, 1+i*0.125f).turn(180*((i+1)%2)).turn(-60)
					.roll(60*i).setScale(scale).getOwner());
		}
		return box;
	}
	public static void main(String[] args) throws Exception {
		float horisontCoeff = 0.01f;
		Utils.initDisplay(512,512,false);
		Scene scene=new Scene();
		Camera camera=new Camera();
		camera.farPlane=1000;
		camera.getPosition().setTranslation(0, 6, 2).pitch(90).turn(90).pitch(15);

		//Mesh test_plane1 = Plane.build();//MeshLoaderSMD.loadMultiMesh("models/test_plane", null, false);
		//scene.add(test_plane1).getPosition().move(0, 0, -2).turn(90);
		Object3d box=buildPine();
		scene.add(box);

		while(!Display.isCloseRequested())
		{		
			box.getPosition().pitch(1);
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
