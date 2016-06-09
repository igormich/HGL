package materials;

import static org.lwjgl.opengl.GL11.GL_CLAMP;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
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
import static org.lwjgl.opengl.GL11.glTexImage2D;
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

public class Texture2D implements Texture{
	
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
	width=image.getWidth();
	height=image.getHeight();
	
	DataBufferInt dbi=(DataBufferInt) image.getRaster().getDataBuffer(); 
	int[] pixels=dbi.getData();
	
	ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
	
	for(int y = 0; y < image.getHeight(); y++){
		for(int x = 0; x < image.getWidth(); x++){
			int pixel = pixels[y * image.getWidth() + x];
			buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
			buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
			buffer.put((byte) (pixel & 0xFF));               // Blue component
			buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
		}
	}//*/
	buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS
	ID = glGenTextures(); //Generate texture ID
	glBindTexture(GL_TEXTURE_2D, ID); //Bind texture ID
	glTexParameteri(GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL_TRUE);
	glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
	glTexParameteri(GL_TEXTURE_2D,  GL12.GL_TEXTURE_MAX_LEVEL, 3);
	if(repeatW)
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	else
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
	if(repeatH)
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	else	
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);      
	//Setup texture scaling filtering 
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);   
	
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);       
	//Return the texture ID so we can bind it later again
	return ID;
    }
	
    public Texture2D(String filename, boolean repeat) throws IOException {		
    	this(filename,repeat,repeat);
    }
	public Texture2D(String filename, boolean repeatW, boolean repeatH) throws IOException {		
		BufferedImage image=loadImage(filename);
		hasAlpha=image.getColorModel().hasAlpha();
		loadTexture(image,repeatW,repeatH);
		System.out.println(filename);		
	}

	public Texture2D(String filename) throws IOException {
		this(filename, true);
	}

	@Override
	public void apply() {
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, ID);
	}

	@Override
	public boolean isTransparent() {
		return hasAlpha;
	}

	@Override
	public void applyAs(int i) {
		glActiveTexture(GL_TEXTURE0+i);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D,ID);
		glDisable(GL_TEXTURE_2D);
	}
	@Override
	public void unApply() {
		glDisable(GL_TEXTURE_2D);
	}

}
