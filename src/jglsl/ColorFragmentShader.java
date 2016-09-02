package jglsl;

public class ColorFragmentShader extends JFragmentShader {

	@Uniform
	Vec4 color;
	
	@Varying
	Vec4 texCoord;
	@Varying
	Vec3 normal;
	@Varying
	Vec3 lightPos;
	
	@Override
	public void main() {
		gl_FragColor = color;
	}

}
