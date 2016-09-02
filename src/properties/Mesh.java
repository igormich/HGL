package properties;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDeleteLists;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.ArrayList;
import java.util.Iterator;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import physics.TriangleStrip;

import com.bulletphysics.util.ObjectArrayList;

import base.Object3d;
import base.RenderContex;
import materials.Material;
import materials.MaterialLibrary;
import materials.Textured;

public class Mesh implements Property3d,Textured,TriangleStrip {

	/**
	 * 
	 */
	private static final long serialVersionUID = -460385300096058453L;
	public static final int NONE=0;
	public static final int TEXTURE=1;
	public static final int NORMAL=2;
	public static final int COLOR=4;
	public static final int ALL=TEXTURE+NORMAL+COLOR;
	private static int globalId=1;
	
	private Material material;
	private String materialName;
	private MaterialLibrary materialLibrary;
	
	private ArrayList<Vector3f> vert=new ArrayList<Vector3f>();
	private ArrayList<Vector3f> normals=new ArrayList<Vector3f>();
	private ArrayList<Vector3f> color=new ArrayList<Vector3f>();
	private ArrayList<Vector2f> tex=new ArrayList<Vector2f>();
	private int renderParts=ALL;
	protected int listId=-1;
	boolean isPrepared=false;

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
	public void render(RenderContex renderContex,Object3d owner) {
		Material material=getMaterial();
		if(material!=null)
		{
			if(renderContex.storeTransparent()&&(material.isTransparent()))
				renderContex.store(this,owner);
			if(renderContex.skipTransparent()&&(material.isTransparent()))
				return;
			if(renderContex.useMaterial())
			{
				material.apply(owner);
			}
		}
		renderMesh();
		if(renderContex.useMaterial()&&material!=null)
		{
			material.unApply();
		}
	}

	private void renderMesh() {
		if(!isPrepared)
			prepare();
		glCallList(listId);
	}
	
	public Mesh(){
		super();
		listId=getId();
	}
	private static int getId() {
		return globalId++;
	}

	public ObjectArrayList<Vector3f> trinaglesList(){
		ObjectArrayList<Vector3f> result=new ObjectArrayList<Vector3f>();
			for(Vector3f v:vert){
				result.add(v);
			}
		return result;		
	}
	
	public void add(Vector3f v,Vector3f n,Vector3f c, Vector2f t) {
		if(v!=null)vert.add(v);
		if(n!=null)normals.add(n);
		if(c!=null)color.add(c);
		if(t!=null)tex.add(t);
		isPrepared=false;
	}
	
	protected void renderPoint(int i) {
		if((renderParts & TEXTURE)>0)
			glTexCoord2f(tex.get(i).x,tex.get(i).y);
		if((renderParts & NORMAL)>0)
			glNormal3f(normals.get(i).x,normals.get(i).y,normals.get(i).z);
		if((renderParts & COLOR)>0)
			glColor3f(color.get(i).x,color.get(i).y,color.get(i).z);
		glVertex3f(vert.get(i).x,vert.get(i).y,vert.get(i).z);	
	}
	
	public void prepare() {
		if(listId>-1)
			glDeleteLists(listId, 1);
		glNewList(listId, GL_COMPILE);
		glBegin(GL_TRIANGLES);
		for(int i=0;i<vert.size();i++){
			renderPoint(i);
		}
		glEnd();
		glEndList();
		vert.clear();
		color.clear();
		normals.clear();
		tex.clear();
		isPrepared=true;
	}

	public void setRenderParts(int renderParts) {
		this.renderParts = renderParts;
	}

	@Override
	public Iterator<Vector3f> iterator() {
		return vert.iterator();
	}

	@Override
	public int getSize() {
		return vert.size();
	}	
	
}
