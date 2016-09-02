package horizontshader;

import jglsl.*;

public class VoxVertexShader extends JVertexShader {

	@Uniform
	Vec3 lightPosU;
	
	@Varying
	Vec3 vertex;
	@Varying
	Vec3 normal;
	@Varying
	Vec3 lightDir;
	@Override
	public void main() {
		Vec3 position = vec3(mul(gl_ModelViewMatrix,gl_Vertex));
		//normal = normalize(mul(gl_NormalMatrix,gl_Normal));
		normal = gl_Normal;
		//Vec3 lightPosMod = vec3(mul(gl_ModelViewMatrix,vec4(lightPosU,0f)));
		//Vec3 lightDirGlobal = normalize(sub(lightPosU,gl_Vertex.xyz));
		//lightDir = vec3(mul(gl_ModelViewMatrix,vec4(lightDirGlobal,1f)));
		//lightDir = normalize(mul(gl_NormalMatrix,lightDirGlobal));
		lightDir = normalize(mul(inverse(mul(gl_ProjectionMatrix,gl_ModelViewMatrix)),vec4(0f,0f,-1f,0f)).xyz);
		gl_Position = mul(mul(gl_ProjectionMatrix,gl_ModelViewMatrix),gl_Vertex);
		vertex = add(mul(gl_Vertex.xyz,0.5f),0.5f);
	}

}
