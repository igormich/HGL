package horizontshader;

import jglsl.*;

public class FogFragmentShader extends JFragmentShader {

	@Uniform
	Vec4 colorU;
	@Uniform
	Vec4 fogColorU;
	@Uniform
	float distMax;
	
	@Varying
	Vec4 texCoord;
	@Varying
	Vec3 normal;
	@Varying
	Vec3 lightPos;
	@Varying
	float dist;
	@Override
	public void main() {
		gl_FragColor = add(mul(colorU,1 - dist/distMax),mul(fogColorU, dist/distMax));
	}

}
