package jglsl;

public class MaskFragmentShader extends JFragmentShader {

	@Uniform
	Sampler2D color1Texture;
	@Uniform
	Sampler2D color2Texture;
	@Uniform
	Sampler2D maskTexture;
	@Uniform
	Float textureShiftY;
	
	@Varying
	Vec4 texCoord;
	@Varying
	Vec3 normal;
	@Varying
	Vec3 lightPos;
	
	@Override
	public void main() {
		Vec4 color1 = texture2D(color1Texture,texCoord.ts);
		Vec4 color2 = texture2D(color2Texture,add(texCoord.ts,vec2(textureShiftY,0f)));
		Float mask = texture2D(maskTexture,add(texCoord.ts,sin(textureShiftY)*0.1f)).r;
		Vec3 normalN=normalize(normal);
		Vec3 lightPosN=normalize(lightPos);
		Vec4 diffColor=add(mul(color1,mask),mul(color2,1-mask));
		gl_FragColor = mul(diffColor,max(0f,dot(normalN,lightPosN)));
	}

}
