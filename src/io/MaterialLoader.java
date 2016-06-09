package io;


import java.io.IOException;

import materials.Material;
import materials.Texture2D;
import materials.TexturedMaterial;

public class MaterialLoader {

	public static Material load(String name) {
		try {
			return new TexturedMaterial(new Texture2D(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
