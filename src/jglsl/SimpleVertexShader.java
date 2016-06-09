package jglsl;

public class SimpleVertexShader extends JVertexShader {

	@Uniform
	protected Vec3 lightPosU;
	
	@Varying
	protected Vec4 texCoord;
	@Varying
	protected Vec3 normal;
	@Varying
	protected Vec3 lightPos;
	@Override
	public void main() {
		Vec3 position = vec3(mul(gl_ModelViewMatrix,gl_Vertex));
		normal = normalize(mul(gl_NormalMatrix,gl_Normal));
		lightPos = normalize(sub(lightPosU, position));
		gl_Position = mul(mul(gl_ProjectionMatrix,gl_ModelViewMatrix),gl_Vertex);
		texCoord = gl_MultiTexCoord0;
	}

}
