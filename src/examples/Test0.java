package examples;

import static org.lwjgl.opengl.GL11.*;
import io.MeshLoaderSMD;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import jglsl.MaskShader;
import jglsl.ShaderProcessor;
import jglsl.SimpleVertexShader;
import materials.Shader;
import materials.SimpleMaterial;
import materials.Texture2D;
import materials.TexturedMaterial;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import properties.Mesh;
import properties.MultiMesh;
import utils.PrimitiveFactory;
import base.Camera;
import base.Object3d;
import base.Scene;


public class Test0 {
	public static void initDisplay(int w,int h,boolean fullscreen) {
		try {
			Dimension screenSize = new Dimension(w, h);
			if(w==0)
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			DisplayMode targetDisplayMode=null;
			for(DisplayMode mode:Display.getAvailableDisplayModes()){ 			
				if((mode.getWidth()==screenSize.width)&&(mode.getHeight()==screenSize.height))
					if ((targetDisplayMode == null) || (mode.getFrequency() >= targetDisplayMode.getFrequency())) 
                        if ((targetDisplayMode == null) || (mode.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) 
                            targetDisplayMode = mode;
		
			}
			System.out.println(targetDisplayMode);
			if(targetDisplayMode==null)
				targetDisplayMode=new DisplayMode(w, h);
			Display.setDisplayMode(targetDisplayMode);
			Display.setVSyncEnabled(true);
			if(fullscreen)
				Display.setFullscreen(true);
			Display.create();
		} catch (LWJGLException e) {
			Display.destroy();
			e.printStackTrace();
			System.exit(0);
		}
		initGL();
	}
	public static void initGL() {		
		//glViewport (0, 0, Display.getWidth(), Display.getHeight());	// Reset The Current Viewport							// Select The Modelview Matrix
		glLoadIdentity ();											// Reset The Modelview Matrix		
		glClearColor (1.0f, 1.0f, 1.0f, 0.5f);						// Black Background
		glClearDepth (1.0f);										// Depth Buffer Setup
		glClearStencil(0); 
		glDepthFunc (GL_LEQUAL);									// The Type Of Depth Testing (Less Or Equal)
		glBlendFunc(GL_DST_COLOR, GL_SRC_COLOR);
		glEnable (GL_DEPTH_TEST);									// Enable Depth Testing
		glShadeModel (GL_SMOOTH);									// Select Smooth Shading
		glHint (GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);			// Set Perspective Calculations To Most Accurate
		glEnable ( GL_COLOR_MATERIAL ) ;
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		//glCullFace(GL_FRONT);
	}
	public static void main(String[] args) throws Exception {
		BufferedImage image = new BufferedImage(512, 512, BufferedImage.TYPE_3BYTE_BGR);
		
		initDisplay(512,512,false);
		Scene scene=new Scene();
		Camera camera=new Camera();
		camera.getPosition().setTranslation(0, 5, 0).pitch(90).turn(90);;
		
		Mesh mesh = PrimitiveFactory.createPlane(1, 1);
		SimpleMaterial material = new TexturedMaterial(new Texture2D("textures/plaster_up0.jpg"));
		material.setBlendMode(SimpleMaterial.TRANSPARENCY);
		material.setColor(1, 1, 1, 0.5f);
		mesh.setMaterial(material);
		Object3d plane1=new Object3d();
		plane1.add(mesh);
		plane1.getPosition().move(1,0,0);
		scene.add(plane1);
		
		Object3d plane2=new Object3d();
		plane2.add(mesh);
		plane2.getPosition().pitch(180).move(0, 0.5f, 0.1f);
		plane1.addChild(plane2);
		
		Object3d plane3=new Object3d();
		plane3.add(mesh);
		plane3.getPosition().pitch(180).move(0, 1, -0.1f);
		plane1.addChild(plane3);
		
		MultiMesh cubeMesh = MeshLoaderSMD.loadMultiMesh("models/test_cube", null, false);
		Object3d cube = scene.add(cubeMesh);
		cube.getPosition().roll(45).turn(45);
		Shader cubeTexture=ShaderProcessor.build(SimpleVertexShader.class, MaskShader.class);
		cubeTexture.addTexture(new Texture2D("textures/plaster_up0.jpg"), "color1Texture");
		cubeTexture.addTexture(new Texture2D("textures/rust1.jpg"), "color2Texture");
		cubeTexture.addTexture(new Texture2D("textures/mask.jpg"), "maskTexture");
		cubeTexture.setUniform(camera.getPosition().getAbsoluteTranslation(),"lightPosU");
		cubeMesh.setMaterialForAll(cubeTexture);
		while(!Display.isCloseRequested())
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_Z))
				material.setBlendMode(SimpleMaterial.OPAQUE);
			if(Keyboard.isKeyDown(Keyboard.KEY_X))
				material.setBlendMode(SimpleMaterial.ADDITIVE);
			plane1.getPosition().pitch(1);
			cube.getPosition().pitch(0.1f);
			scene.render(camera);
			Display.update();
			Display.sync(60);
		}
	}

}
