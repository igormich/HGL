package properties;

import materials.Material;
import materials.MaterialLibrary;
import materials.Textured;

public class Mesh implements Property3d,Textured {

	private Material material;
	private String materialName;
	private MaterialLibrary materialLibrary;

	@Override
	public void setMaterial(Material material) {
		materialName = null;
		materialLibrary = null;
		this.material = material;
	}

	@Override
	public Material getMaterial() {
		if(material!=null)
		{
			return material;
		}
		if((materialLibrary!=null)&&(materialName!=null))
		{
			return materialLibrary.getMaterial(materialName);
		}
		return null;
	}

	@Override
	public void setMaterialName(String materialName) {
		material = null;
		this.materialName = materialName;
	}

	@Override
	public String getMaterialName() {
		return materialName;
	}

	@Override
	public void setMaterialLibrary(MaterialLibrary materialLibrary) {
		material = null;
		this.materialLibrary = materialLibrary;
	}

	@Override
	public MaterialLibrary getMaterialLibrary() {
		return materialLibrary;
	}
	@Override
	public void render(RenderContex renderContex) {
		if(renderContex.useMaterial())
		{
			getMaterial().apply();
		}
		renderMesh();
		if(renderContex.useMaterial())
		{
			getMaterial().unApply();
		}
	}

	private void renderMesh() {
		
	}
}