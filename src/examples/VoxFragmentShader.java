package examples;

import jglsl.*;

public class VoxFragmentShader extends JFragmentShader {

	@Uniform
	Sampler3D voxData;
	
	@Varying
	Vec3 normal;
	@Varying
	Vec3 vertex;
	@Varying
	Vec3 lightDir;
	
	@Override
	public void main() {
		float n=0.0f;
//		Vec4 color1 = texture3D(voxData,vertex);
		Vec4 color1 = vec4(0f,0f,0f,0f);
		Vec3 voxPos=add(vertex,mul(lightDir,-n));
		while((max(voxPos.x,max(voxPos.y, voxPos.z))<=1)&&(min(voxPos.x,min(voxPos.y, voxPos.z))>=0)){
			//color1 = add(color1,texture3D(voxData,add(vertex,mul(normal,n))));
			//Vec4 colorTemp = vec4(0f, 0f,0f,0f);
			Vec4 colorTemp = texture3D(voxData,voxPos);
			color1 = add(color1,mul(colorTemp,0.01f));
			n = n+0.01f;
			voxPos=add(vertex,mul(lightDir,-n));
		}
		gl_FragColor = mul(color1,1f);
	//	gl_FragColor = vec4(lightDir, 1f);
	}

}
