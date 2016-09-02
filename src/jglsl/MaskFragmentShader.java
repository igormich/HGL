package jglsl;

public class MaskShader extends JFragmentShader {

	@Uniform
	Sampler2D color1Texture;
	@Uniform
	Sampler2D color2Texture;
	@Uniform
	Sampler2D maskTexture;
	@Varying
	protected final Vec4 texCoord=null;
	@Varying
	protected final Vec3 normal=null;
	@Varying
	protected final Vec3 lightPos=null;
	@Override
	public void main() {
		Vec4 color1 = texture2D(color1Texture,texCoord.ts);
		Vec4 color2 = texture2D(color2Texture,texCoord.ts);
		Float mask = texture2D(maskTexture,texCoord.ts).r;
		Vec3 normalN=normalize(normal);
		Vec3 lightPosN=normalize(lightPos);
		Vec4 diffColor=add(mul(color1,mask),mul(color2,1-mask));
		gl_FragColor = mul(diffColor,max(0f,dot(normalN,lightPosN)));
	}

}
