package horizontshader;

import jglsl.*;

public class HorisontVertexShader extends JVertexShader {

	@Uniform
	protected Vec3 lightPosU;
	@Uniform
	protected Vec3 cameraPosU;
	@Uniform
	protected float horisontCoeffU;
	@Varying
	protected Vec4 texCoord;
	@Varying
	protected Vec3 normal;
	@Varying
	protected Vec3 lightPos;
	@Varying
	protected float dist;
	@Override
	public void main() {
		Vec4 position = mul(gl_ModelViewMatrix, gl_Vertex);
		Vec3 position3 = vec3(position);
		Vec3 noZComponent=vec3(1f,1f,0f);
		float hdist = distance(mul(position3,noZComponent),mul(cameraPosU,noZComponent))*horisontCoeffU;
		hdist = hdist * hdist;
		dist = distance(position3,cameraPosU);
		normal = normalize(mul(gl_NormalMatrix,gl_Normal));
		lightPos = normalize(sub(lightPosU, position3));
		//gl_Position = mul(mul(gl_ProjectionMatrix,gl_ModelViewMatrix), sub(gl_Vertex,vec4(0f,hdist,0f,0f)));
		gl_Position = mul(gl_ProjectionMatrix, sub(position,vec4(0f,0f,hdist,0f)));
		texCoord = gl_MultiTexCoord0;
	}

}
