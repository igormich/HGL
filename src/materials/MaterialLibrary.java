package materials;


public interface MaterialLibrary {
	Material getMaterial(String materialName);
	Material addMaterial(String s, Material load);
}
