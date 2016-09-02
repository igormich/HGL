package materials;

import static org.lwjgl.opengl.GL11.GL_CLAMP;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_3D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL12.glTexImage3D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;

public class Texture3D implements Texture{
	
	private static final int BYTES_PER_PIXEL = 4;//3 for RGB, 4 for RGBA
    public int ID;
    public boolean hasAlpha;
    public int width;
    public int height;
	
	public static BufferedImage loadImage(String filename) {
		Image imageX = new javax.swing.ImageIcon(filename).getImage();
	    BufferedImage image = new BufferedImage(imageX.getWidth(null),
	    imageX.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D ig = image.createGraphics();
	    ig.drawImage(imageX, 0, 0, null); 
		return image;
	}
    
    private int loadTexture(BufferedImage image, boolean repeatW,boolean repeatH){
	//width=image.getWidth();
	//height=image.getHeight();
	
//	DataBufferInt dbi=(DataBufferInt) image.getRaster().getDataBuffer(); 
//	int[] pixels=dbi.getData();
	
	ByteBuffer buffer = BufferUtils.createByteBuffer(256*256*256*4); //4 for RGBA, 3 for RGB
	
	for(int y = 0; y < 256; y++){
		for(int x = 0; x < 256; x++){
			for(int z = 0; z < 256; z++){
				float r=(float) Math.sqrt((x-128)*(x-128)+(y-128)*(y-128)+(z-128)*(z-128));
				buffer.put((byte) 64);     // Red component
				buffer.put((byte) 0);     // Green component
				if(r<50)
					buffer.put((byte) 255);     // Blue component
				else
					buffer.put((byte) 0);     // Blue component
				buffer.put((byte) 255);   // Alpha component. Only for RGBA
			}
		}
	}//*/
	buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS
	ID = glGenTextures(); //Generate texture ID
	glBindTexture(GL_TEXTURE_3D, ID); //Bind texture ID
	glTexParameteri(GL_TEXTURE_3D, GL14.GL_GENERATE_MIPMAP, GL_TRUE);
	glTexParameteri(GL_TEXTURE_3D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
	glTexParameteri(GL_TEXTURE_3D,  GL12.GL_TEXTURE_MAX_LEVEL, 3);    
	//Setup texture scaling filtering 
	glTexParameteri(GL_TEXTURE_3D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
	glTexParameteri(GL_TEXTURE_3D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);   
	
	//glTexImage2D(GL_TEXTURE_3D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);       
	glTexImage3D(GL_TEXTURE_3D, 0, GL_RGBA8, 256, 256,256, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	//Return the texture ID so we can bind it later again
	return ID;
    }
	
    public Texture3D(String filename, boolean repeat) throws IOException {		
    	this(filename,repeat,repeat);
    }
	public Texture3D(String filename, boolean repeatW, boolean repeatH) throws IOException {		
		BufferedImage image=loadImage(filename);
		hasAlpha=image.getColorModel().hasAlpha();
		loadTexture(image,repeatW,repeatH);	
	}

	public Texture3D(String filename) throws IOException {
		this(filename, true);
	}

	public Texture3D() {
		loadTexture(null,false,false);	
	}

	@Override
	public void apply() {
		glEnable(GL_TEXTURE_3D);
		glBindTexture(GL_TEXTURE_3D, ID);
	}

	@Override
	public boolean isTransparent() {
		return hasAlpha;
	}

	@Override
	public void applyAs(int i) {
		glActiveTexture(GL_TEXTURE0+i);
		glEnable(GL_TEXTURE_3D);
		glBindTexture(GL_TEXTURE_3D,ID);
		glDisable(GL_TEXTURE_3D);
	}
	@Override
	public void unApply() {
		glDisable(GL_TEXTURE_3D);
	}

}
