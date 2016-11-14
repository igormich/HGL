package forest;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DST_COLOR;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SRC_COLOR;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glClearStencil;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glShadeModel;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Utils {

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
	}

}
