package forest;

import jglsl.*;

public class SpriteVertexShader extends JVertexShader {

	@Uniform
	protected Vec3 lightPosU;
	
	@Varying
	protected Vec4 texCoord;
	@Override
	public void main() {
		Mat4 modelView = gl_ModelViewMatrix;
		float w=length(modelView.get(0));
		modelView.get(0).x = w;
		modelView.get(0).y = 0f;
		modelView.get(0).z = 0f;
		
		modelView.get(1).x = 0f;
		modelView.get(1).y = 0f;
		modelView.get(1).z = 1f;

		modelView.get(2).x = 0f;
		modelView.get(2).y = 0f;
		modelView.get(2).z = w;
		gl_Position = mul(mul(gl_ProjectionMatrix,modelView),gl_Vertex);
		texCoord = gl_MultiTexCoord0;
	}

}
