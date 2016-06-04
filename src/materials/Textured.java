package materials;


public interface Textured {
	
	void setMaterial(Material material);
	Material getMaterial();
	
	void setMaterialName(String name);
	String getMaterialName();
	void setMaterialLibrary(MaterialLibrary materialLibrary);
	MaterialLibrary getMaterialLibrary();
	
	default boolean isMaterialAssigned(){
		return (getMaterial()!=null)||isAssignedToMaterialLibrary();
	}
	default boolean isAssignedToMaterialLibrary(){
		return (getMaterialLibrary()!=null)&&(getMaterialName()!=null);
	}
}
