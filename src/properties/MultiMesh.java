package properties;

import java.util.ArrayList;
import java.util.List;

import base.Object3d;
import base.RenderContex;
import materials.Material;
import materials.MaterialLibrary;

public class MultiMesh implements Property3d {

	private List<Mesh> meshes=new ArrayList<Mesh>();

	
	public Mesh addMesh(){
		Mesh result = new Mesh();
		meshes.add(result);
		return result;
	}
	
	public Mesh addMesh(Mesh mesh){
		meshes.add(mesh);
		return mesh;
	}
	public void setMaterialForAll(Material material){
		meshes.forEach(mesh -> mesh.setMaterial(material));
	}
	public void setMaterialLibraryForAll(MaterialLibrary materialLibrary){
		meshes.forEach(mesh -> mesh.setMaterialLibrary(materialLibrary));
	}
	//maybe not actual
	public void setMaterialNameForAll(String materialName){
		meshes.forEach(mesh -> mesh.setMaterialName(materialName));
	}
	@Override
	public void render(RenderContex renderContex,Object3d owner) {
		meshes.forEach(mesh -> mesh.render(renderContex,owner));
	}
}
