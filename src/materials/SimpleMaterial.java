package materials;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL14;

import base.Object3d;


public class SimpleMaterial implements Material{
	public static final int OPAQUE = 0;
	public static final int TRANSPARENCY = 1;
	public static final int ADDITIVE = 2;
	public static final int ALPHATEST50 = 3;
	public static final int ALPHATEST100 = 4;
	public static final int MODULATE = 5;
	public static final int OVERLAY = 6;
	public static final int MODULATE_ADD = 7;
	public static final int CUSTOM = 8;

	private int blendMode=OPAQUE;
	private float r=1,g=1,b=1,a=1;	
	private int blendSrc=GL_SRC_ALPHA;
	private int blendDst=GL_ONE_MINUS_SRC_ALPHA;
	private int blendFunc=GL14.GL_FUNC_ADD;
	private boolean colorWrite=true;	
	private boolean zWrite=true;	
	
	
	public void setColor(float r,float g,float b,float a){
		this.r=r;
		this.g=g;
		this.b=b;
		this.a=a;
	}
	public void setColor(float r,float g,float b){
		setColor(r,g,b,1);
	}
	
	public boolean isColorWrite() {
		return colorWrite;
	}
	public void setColorWrite(boolean colorWrite) {
		this.colorWrite = colorWrite;
	}
	public boolean iszWrite() {
		return zWrite;
	}
	public void setzWrite(boolean zWrite) {
		this.zWrite = zWrite;
	}
	public boolean isTransparent() {
		return !((getBlendMode()==SimpleMaterial.OPAQUE)||(getBlendMode()==SimpleMaterial.ALPHATEST100)||(getBlendMode()==SimpleMaterial.ALPHATEST50));
	}
	public int getBlendMode() {
		return blendMode;
	}
	public void setBlendMode(int blendMode) {
		if((blendMode<0)||(blendMode>CUSTOM))
			throw new IllegalArgumentException("Argument blendMode out of range.");
		this.blendMode = blendMode;
	}
	//need add check values of arguments or create class for blendmode
	public void setCustomMode(int blendSrc,int blendDst,int blendFunc) {
		setBlendMode(CUSTOM);
		this.blendSrc=blendSrc;
		this.blendDst=blendDst;
		this.blendFunc=blendFunc;	
	}
	public void apply(Object3d owner) {
		
		if(!isColorWrite())
			glColorMask(false, false, false, false);
		else
			glColorMask(true, true, true, true);
		glDepthMask(iszWrite());
		switch (getBlendMode()) {
		case TRANSPARENCY:
			glEnable(GL_ALPHA_TEST);
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			GL14.glBlendEquation(GL14.GL_FUNC_ADD);	
			glAlphaFunc(GL_GREATER, 0);
			break;
		case ADDITIVE:
			glEnable(GL_ALPHA_TEST);
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE);
			GL14.glBlendEquation(GL14.GL_FUNC_ADD);
			glAlphaFunc(GL_GREATER, 0);
			break;
		case ALPHATEST50:
			glEnable(GL_ALPHA_TEST);
			glDisable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE);
			GL14.glBlendEquation(GL14.GL_FUNC_ADD);
			glAlphaFunc(GL_GEQUAL, 0.5f);
			break;	
		case ALPHATEST100:
			glEnable(GL_ALPHA_TEST);
			glDisable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE);
			GL14.glBlendEquation(GL14.GL_FUNC_ADD);
			glAlphaFunc(GL_GEQUAL, 1.0f);
			break;	
		case MODULATE:
			glEnable(GL_ALPHA_TEST);
			glEnable(GL_BLEND);
			glBlendFunc(GL_DST_COLOR, GL_ZERO);
			GL14.glBlendEquation(GL14.GL_FUNC_ADD);
			glAlphaFunc(GL_GREATER, 0.0f);
			break;
		case MODULATE_ADD:
			glEnable(GL_ALPHA_TEST);
			glEnable(GL_BLEND);
			glBlendFunc(GL_DST_COLOR, GL_SRC_COLOR);
			GL14.glBlendEquation(GL14.GL_FUNC_ADD);
			glAlphaFunc(GL_GREATER, 0.5f);
			break;	
		case OVERLAY:
			glEnable(GL_ALPHA_TEST);
			glEnable(GL_BLEND);
			//glBlendFunc(GL_DST_COLOR, GL_SRC_COLOR);
			glBlendFunc(GL_DST_COLOR, GL_SRC_ALPHA);
			GL14.glBlendEquation(GL14.GL_FUNC_ADD);
			glAlphaFunc(GL_GREATER, 0.0f);
			break;	
		case CUSTOM:
			glEnable(GL_ALPHA_TEST);
			glEnable(GL_BLEND);
			glBlendFunc(blendSrc, blendDst);
			GL14.glBlendEquation(blendFunc);
			glAlphaFunc(GL_GREATER, 0.0f);
			break;			
		default:
			glDisable(GL_ALPHA_TEST);
			glDisable(GL_BLEND);
			break;
		}
		glColor4f(r, g, b, a);
	}

	public void unApply() {
		
	}

	
}