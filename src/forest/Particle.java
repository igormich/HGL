package forest;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import base.Camera;
import base.Object3d;
import base.RenderContex;
import base.Scene;
import jglsl.ColorFragmentShader;
import jglsl.ShaderProcessor;
import jglsl.SimpleVertexShader;
import materials.Shader;
import materials.SimpleMaterial;
import physics.PhysicController;
import primitives.Cone;
import primitives.Plane;
import properties.Mesh;
import properties.Property3d;


public class Particle extends Object3d {
	
	float liveTime;
	public Particle(Property3d ... properties) {
		super(properties);
	}
	public static void main(String[] args) throws Exception {
		float horisontCoeff = 0.01f;
		Utils.initDisplay(900,900,false);
		Scene scene=new Scene();
		Camera camera=new Camera();
		camera.farPlane=1000;
		camera.getPosition().setTranslation(0, 10, 4).pitch(90).turn(90).pitch(15);

		Shader shader = ShaderProcessor.build(SpriteVertexShader.class, ColorFragmentShader.class);
		shader.setUniform(new Vector4f(1, 0, 1, 0.25f), "color");
		Mesh spritePlane = Plane.build();
		shader.setBlendMode(SimpleMaterial.TRANSPARENCY);
		spritePlane.setMaterial(shader);
		
		Mesh base = Plane.build();
		Object3d box=new Object3d(base).getPosition().turn(90).getOwner();
		scene.add(box);
		Random r=new Random();
		Property3d spriteContoller = new Property3d() {
			
			@Override
			public void tick(float deltaTime, float time, Object3d owner) {
				Particle particle = (Particle) owner;
				Vector3f pos = owner.getPosition().getTranslation();
				if(particle.liveTime>5){
					pos.x=r.nextFloat()-0.5f;
					pos.y=r.nextFloat()-0.5f;
					pos.z=r.nextFloat()-0.5f;
					particle.liveTime = 0;
				} else {
					pos.y+=deltaTime*(2-particle.liveTime);
					//pos.x*=1.01f;
					//pos.y*=1.01f;
					pos.x-=deltaTime;
					pos.z*=1.005f;
					particle.liveTime+=deltaTime;
				}
				owner.getPosition().setTranslation(pos).setScale(0.1f+0.05f*particle.liveTime).pitch(10);
			}

			@Override
			public void render(RenderContex renderContex, Object3d owner) {
				Particle particle = (Particle) owner;
				if(renderContex.storeTransparent())
					renderContex.store(this,owner);
				if(renderContex.skipTransparent())
					return;
				shader.setUniform(new Vector4f(1, 0, 1, 0.25f-particle.liveTime*0.05f), "color");
				spritePlane.render(renderContex, owner);
			}
			
		};
		for(int i=0;i<40;i++){
			Object3d sprite = new Particle(spriteContoller,spritePlane)
					.getPosition().setScale(0.1f).move(r.nextFloat()-0.5f, r.nextFloat()-0.5f, r.nextFloat()-0.5f).turn(90).getOwner();
			float warmTime = r.nextFloat()*5;
			for(float d=0;d<warmTime;d+=1f/60)
				sprite.tick(1f/60, d);
			box.addChild(sprite);
		}
		
		while(!Display.isCloseRequested())
		{	
			long time=System.currentTimeMillis();
			if(Keyboard.isKeyDown(Keyboard.KEY_A))
				box.getPosition().roll(1);
			if(Keyboard.isKeyDown(Keyboard.KEY_D))
				box.getPosition().roll(-1);
			camera.tick(1f/60, 0);
			PhysicController.getDefault().step(1f/60, 10);
			scene.tick(1f/60);
			scene.render(camera);
			Display.update();
			Display.sync(60);
			Display.setTitle(""+1000/(System.currentTimeMillis()-time));
		}
	}
}
