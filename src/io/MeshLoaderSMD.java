package io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Point3i;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import materials.Material;
import materials.MaterialLibrary;
import properties.Mesh;
import properties.MultiMesh;

public class MeshLoaderSMD {
	public static MultiMesh loadMultiMesh(String pathToFile,MaterialLibrary materialLibrary, boolean loadMaterial) throws IOException{
		Map<String,Mesh> meshes=new HashMap<String, Mesh>();
		MultiMesh result=new MultiMesh();
		File f=new File(pathToFile+".smd");
		//DataInputStream dis=new DataInputStream(new BufferedInputStream(new FileInputStream(f),(int) f.length()));
		BufferedReader br = new BufferedReader(new FileReader(f));

		skipHeader(br);
		
		int n=0;
		String s = br.readLine().trim();
		while(!"end".equals(s)){
			Mesh mesh=meshes.get(s);
			if(mesh==null){
				mesh=result.addMesh();
				mesh.setRenderParts(Mesh.NORMAL+Mesh.TEXTURE);
				if(loadMaterial){
					if(materialLibrary==null){
						mesh.setMaterial(MaterialLoader.load(s));
					}else{
						Material m=materialLibrary.getMaterial(s);
						if(m==null){
							m=materialLibrary.addMaterial(s, MaterialLoader.load(s));
							
						}
						mesh.setMaterialLibrary(materialLibrary);
						mesh.setMaterialName(s);
					}
				}
				
				meshes.put(s,mesh);
			}
			//texture load here
			s = br.readLine().trim().replaceAll(" +", " ");
			parsePoint(mesh,s);
			s = br.readLine().trim().replaceAll(" +", " ");
			parsePoint(mesh,s);
			s = br.readLine().trim().replaceAll(" +", " ");
			parsePoint(mesh,s); 
			s = br.readLine().trim();
			n+=3;
		}
		return result;
	}

	private static void parsePoint(Mesh mesh, String s) {
		String[] ss = s.split(" ");
		
		Vector3f v=new Vector3f(Float.parseFloat(ss[1]), Float.parseFloat(ss[3]), Float.parseFloat(ss[2]));
		v.z=-v.z;
		Vector3f n=new Vector3f(Float.parseFloat(ss[4]),
				Float.parseFloat(ss[6]),
				Float.parseFloat(ss[5]));
		n.z=-n.z;
		Vector2f t=new Vector2f(Float.parseFloat(ss[7]),1-Float.parseFloat(ss[8]));
		mesh.add(v,n,null,t);
	}

	private static void skipHeader(BufferedReader br) throws IOException {
		String s = br.readLine();
		while(!"nodes".equals(s))
			s = br.readLine();
		s = br.readLine().trim().replaceAll(" +", " ");
		while(!"end".equals(s)){
			s = br.readLine().trim().replaceAll(" +", " ");
		}
		s = br.readLine();
		
		while(!"skeleton".equals(s))
			s = br.readLine();
		s = br.readLine().trim();
		s = br.readLine().trim().replaceAll(" +", " ");
		while(!"end".equals(s)){
			s = br.readLine().trim().replaceAll(" +", " ");
		}
		s = br.readLine();
		while(!"triangles".equals(s))
			s = br.readLine();
	}
}
