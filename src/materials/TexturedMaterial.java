package materials;

import base.Object3d;

public class TexturedMaterial extends SimpleMaterial {

	private Texture texture;

	public TexturedMaterial(Texture texture) {
		this.texture = texture;
	
	}

	@Override
	public boolean isTransparent() {
		return texture.isTransparent();
	}

	@Override
	public void apply(Object3d owner) {
		super.apply(owner);
		texture.apply();
	}

	@Override
	public void unApply() {
		texture.unApply();
	}

}
