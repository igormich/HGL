package materials;

import static org.lwjgl.opengl.GL20.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import base.Object3d;

public class Shader extends SimpleMaterial {
	private static final int LOG_SIZE = 2048;
	private int shaderprogram;
	private List<Texture> textures=new ArrayList<Texture>();

	public void init(String vShader,String fShader){
		int vertexShaderProgram = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShaderProgram = glCreateShader(GL_FRAGMENT_SHADER);
        compileShader(vertexShaderProgram,vShader);
        compileShader(fragmentShaderProgram,fShader);
        shaderprogram = glCreateProgram();
        glAttachShader(shaderprogram, vertexShaderProgram);
        glAttachShader(shaderprogram, fragmentShaderProgram);
        glLinkProgram(shaderprogram);
        glValidateProgram(shaderprogram);
        int compiled=glGetShaderi(shaderprogram, GL_LINK_STATUS);
        if(compiled==0)
        {
            String log=glGetShaderInfoLog(shaderprogram, LOG_SIZE);
            System.err.println(this.getClass().getCanonicalName()+" Error compiling the vertex shader: " + new String(log));
        }
	}

	private void compileShader(int shaderProgram, String shaderCode) {
		ByteBuffer bb=BufferUtils.createByteBuffer(shaderCode.length()*2);
        bb.put(shaderCode.getBytes());
        bb.flip();
        glShaderSource(shaderProgram,shaderCode);
        glCompileShader(shaderProgram);
        int compiled=glGetShaderi(shaderProgram, GL_COMPILE_STATUS);
        if(compiled==0)
        {
            String log=glGetShaderInfoLog(shaderProgram, LOG_SIZE);
            System.err.println(this.getClass().getCanonicalName()+" Error compiling the vertex shader: " + new String(log));
        }
	}
	public void addTexture(Texture texture, String name) {
		glUseProgram(shaderprogram);
		
		int i=glGetUniformLocation(shaderprogram, name);
		if(i>-1){
			textures.add(texture);	
			glUniform1i(i,textures.size()-1);
		}
		glUseProgram(0);
	}
	@Override
	public void apply(Object3d owner) {
		super.apply(owner);
		int i=0;
		for(Texture t:textures){
			t.applyAs(i++);
		}
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		glUseProgram(shaderprogram);
	}
	@Override
	public void unApply() {
		super.unApply();
		glUseProgram(0);
	}

	public void setUniform(Vector3f vec3, String name) {
		glUseProgram(shaderprogram);
		
		int i=glGetUniformLocation(shaderprogram, name);
		if(i>-1){
			glUniform3f(i,vec3.x,vec3.y,vec3.z);
		}
		glUseProgram(0);
	}
	
	public void setUniform(Vector4f vec4, String name) {
		glUseProgram(shaderprogram);
		
		int i=glGetUniformLocation(shaderprogram, name);
		if(i>-1){
			glUniform4f(i,vec4.x,vec4.y,vec4.z,vec4.w);
		}
		glUseProgram(0);
	}
	
	public void setUniform(float value, String name) {
		glUseProgram(shaderprogram);
		
		int i=glGetUniformLocation(shaderprogram, name);
		if(i>-1){
			glUniform1f(i,value);
		}
		glUseProgram(0);
		
	}
}
