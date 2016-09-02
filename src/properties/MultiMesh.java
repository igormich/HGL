package properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.vecmath.Vector3f;

import physics.TriangleStrip;
import base.Object3d;
import base.RenderContex;
import materials.Material;
import materials.MaterialLibrary;

public class MultiMesh implements Property3d,TriangleStrip {

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

	@Override
	public Iterator<Vector3f> iterator() {
		return new MultiMeshTriangleStrip();
	}
	private class MultiMeshTriangleStrip implements Iterator<Vector3f>{
		private Iterator<Mesh> currentMesh;
		private Iterator<Vector3f> currentIterator;
		
		public MultiMeshTriangleStrip() {
			super();
			currentMesh=meshes.iterator();
			currentIterator=currentMesh.next().iterator();
		}

		@Override
		public boolean hasNext() {
			if(currentIterator.hasNext())
				return true; 
			while(!currentIterator.hasNext())
				if(currentMesh.hasNext())
					currentIterator=currentMesh.next().iterator();
				else
					return false;
			return true;
		}
		@Override
		public Vector3f next() {
			return currentIterator.next();
		}
		
	}
	@Override
	public int getSize() {
		
		return meshes.stream().mapToInt(m ->m.getSize()).sum();
	}
}
