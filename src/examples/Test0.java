package examples;

import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_DST_COLOR;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SRC_COLOR;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glClearStencil;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glShadeModel;

import java.awt.Dimension;
import java.awt.Toolkit;

import materials.SimpleMaterial;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import properties.Camera;
import properties.Mesh;
import utils.PrimitiveFactory;
import base.Object3d;
import base.Scene;


public class Test0 {
	public static void initDisplay(int w,int h,boolean fullscreen) {
		try {

			Dimension screenSize = new Dimension(w, h);
			if(w==0)
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			
			//screenSize =new Dimension(1600, 900);
			//Dimension screenSize = new Dimension(512, 512);
			DisplayMode targetDisplayMode=null;
			//System.out.println(Arrays.toString(Display.getAvailableDisplayModes()));
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
			//Display.setDisplayModeAndFullscreen(new DisplayMode(screenSize.width,screenSize.height));
			//frame = new JFrame();
			
			//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//frame.setUndecorated(true);
			//Canvas canvas = new Canvas();
			//canvas.setSize(screenSize.width,screenSize.height);
			//Display.setLocation(0, 0);
			Display.setVSyncEnabled(true);
			if(fullscreen)
				Display.setFullscreen(true);
			Display.create();
			
			
			//if(!Display.isFullscreen())
			/*{
				frame.add(canvas);

				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				//frame.dispose(); //Destroys the whole JFrame but keeps organized every Component                               
                //Needed if you want to use Undecorated JFrame
                //dispose() is the reason that this trick doesn't work with videos
				

				//frame.setBounds(0,0,frame.getToolkit().getScreenSize().width,frame.getToolkit().getScreenSize().height);
				//frame.setVisible(true);
				
			}else*/{


				
				//frame.setUndecorated(true);
				//frame.add(canvas);
				//frame.pack();
				//frame.setLocationRelativeTo(null);
				//frame.setVisible(true);
				//GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].setFullScreenWindow(frame);
				//frame.setBounds(0,0,frame.getToolkit().getScreenSize().width,frame.getToolkit().getScreenSize().height);
				//frame.setVisible(true);
				//Mouse.setGrabbed(false);
				
				 
				    
			}
			//Display.setParent(canvas);	
			/*glHint(GL_PERSPECTIVE_CORRECTION_HINT,  GL_NICEST);
			
			glHint(GL_POLYGON_SMOOTH_HINT,  GL_NICEST);
			glHint(GL_POINT_SMOOTH_HINT,  GL_NICEST);
			glHint(GL_LINE_SMOOTH_HINT,  GL_NICEST);
			glEnable(GL_POLYGON_SMOOTH);
			glEnable(GL_POINT_SMOOTH);
			glEnable(GL_LINE_SMOOTH);*/
			//glEnable(GL_MULTISAMPLE);  
			//org.lwjgl.input.Cursor emptyCursor = new org.lwjgl.input.Cursor(1, 1, 0, 0, 1, BufferUtils.createIntBuffer(1), null);
			//Mouse.setNativeCursor(emptyCursor);
			
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
		// Start Of User Initialization
		glClearColor (1.0f, 1.0f, 1.0f, 0.5f);						// Black Background
		glClearDepth (1.0f);										// Depth Buffer Setup
		glClearStencil(0); 
		glDepthFunc (GL_LEQUAL);									// The Type Of Depth Testing (Less Or Equal)
		glBlendFunc(GL_DST_COLOR, GL_SRC_COLOR);
		glEnable (GL_DEPTH_TEST);									// Enable Depth Testing
		glShadeModel (GL_SMOOTH);									// Select Smooth Shading
		glHint (GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);			// Set Perspective Calculations To Most Accurate
		glColorMaterial ( GL_FRONT, GL_DIFFUSE ) ;		
		glEnable ( GL_COLOR_MATERIAL ) ;
		glEnable(GL_CULL_FACE);
		glCullFace(GL_FRONT);
	}
	public static void main(String[] args) {
		initDisplay(512,512,false);
		Scene scene=new Scene();
		Camera camera=new Camera();
		camera.getPosition().setTranslation(0, 5, 0).pitch(90).turn(90);;
		
		Mesh mesh = PrimitiveFactory.createPlane(1, 1);
		mesh.setMaterial(new SimpleMaterial());
		Object3d plane1=new Object3d();
		plane1.add(mesh);
		scene.add(plane1);
		
		Object3d plane2=new Object3d();
		plane2.add(mesh);
		plane2.getPosition().pitch(180);
		plane1.addChild(plane2);
		while(!Display.isCloseRequested())
		{
			plane1.getPosition().pitch(1);
			scene.render(camera);
			Display.update();
			Display.sync(60);
		}
	}

}
