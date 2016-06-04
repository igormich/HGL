package base;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;


public class Position implements Applyable {

	
	public static final Vector3f VECTOR_X = new Vector3f(1, 0, 0);
	public static final Vector3f VECTOR_Y = new Vector3f(0, 1, 0);
	public static final Vector3f VECTOR_Z = new Vector3f(0, 0, 1);
	
	
	public static float gradToRad(float angle) {
		return (float) (angle*Math.PI/180);
	}
	
	private float radToGrad(float angle) {
		return (float) (angle*180/Math.PI);
	}
	
	public float dist(Vector3f v1,Vector3f v2){
		return (float) Math.sqrt((v1.x-v2.x)*(v1.x-v2.x)+(v1.y-v2.y)*(v1.y-v2.y)+(v1.z-v2.z)*(v1.z-v2.z));
	}
	
	private boolean cashed=false;
	private FloatBuffer cash=BufferUtils.createFloatBuffer(16);
	
	private Matrix4f matrix=new Matrix4f();
	private float turn,roll,pitch;
	private Vector3f scale=new Vector3f(1, 1, 1);
	private boolean absolyte;
	private Object3d owner;
	
	
	public boolean isAbsolyte() {
		return absolyte;
	}
	
	public void setAbsolyte(boolean absolyte) {
		this.absolyte = absolyte;
	}
	
	public Position(Object3d owner){
		this.owner = owner;
		Matrix4f.setIdentity(matrix);
		cache();
	}
	private void cache() {
		matrix.store(cash);	
		cash.flip();
		cashed=true;
	}
	
	private void invalidate() {
		cashed=false;;
	}
	
	//rotate operations
	public Position turn(float angle){
		angle=gradToRad(angle);
		turn+=angle;
		matrix.rotate(angle, VECTOR_X);
		invalidate();
		return this;
	}

	public Position roll(float angle){
		angle=gradToRad(angle);
		roll+=angle;
		matrix.rotate(angle, VECTOR_Y);
		invalidate();
		return this;
	}
	public Position pitch(float angle){
		angle=gradToRad(angle);
		pitch+=angle;
		matrix.rotate(angle, VECTOR_Z);
		invalidate();
		return this;
	}
	public Position setTurn(float angle){
		angle=gradToRad(angle);
		matrix.rotate(angle-turn, VECTOR_X);
		turn=angle;		
		invalidate();
		return this;
	}
	public Position setRoll(float angle){
		angle=gradToRad(angle);
		matrix.rotate(angle-roll, VECTOR_Y);
		roll=angle;		
		invalidate();
		return this;
	}
	public Position setPitch(float angle){
		angle=gradToRad(angle);
		matrix.rotate(angle-pitch, VECTOR_Z);
		pitch=angle;		
		invalidate();
		return this;
	}
	public Position rotate(float angle,float x,float y,float z){
		angle=gradToRad(angle);
		matrix.rotate(angle, new Vector3f(x, y, z));		
		invalidate();
		return this;
	}
	public float getPitch() {
		return radToGrad(pitch);
	}

	public float getRoll() {
		return radToGrad(roll);
	}
	public float getTurn() {
		return radToGrad(turn);
	}
	//translations operaion
	public Position setTranslation(float x,float y,float z) {
		matrix.m30=x;
		matrix.m31=y;
		matrix.m32=z;
		invalidate();
		return this;
	}
	public Position setTranslation(Vector3f pos) {
		return setTranslation(pos.x,pos.y,pos.z);
	}
	public Position move(float x,float y,float z) {
		matrix.m30+=x;
		matrix.m31+=y;
		matrix.m32+=z;
		invalidate();
		return this;
	}
	public Position move(Vector3f pos) {	
		return move(new Vector3f(pos.x, pos.y, pos.z));
	}
	public Vector3f getTranslation() {
		return new Vector3f(new Vector3f(matrix.m30, matrix.m31, matrix.m32));
	}
	
	//scale operations
	public Position setScale(float x,float y,float z) {
		return setScale(new Vector3f(x, y, z));
	}
	public Position setScale(Vector3f scale){
		Vector3f temp=new Vector3f(scale.x/this.scale.x,scale.y/this.scale.y,scale.z/this.scale.z);
		matrix.scale(temp);
		this.scale=scale;
		invalidate();
		return this;
	}
	public Position setScale(float f) {
		setScale(f, f, f);	
		return this;
	}
	public Position mulScale(float f) {
		Vector3f s = getScale();	
		setScale(s.x*f, s.y*f, s.z*f);		
		return this;
	}
	public Vector3f getScale() {
		return new Vector3f(scale);
	}
	
	
	public float gistanceTo(Object3d object) {
		return gistanceTo(object.getPosition());
	}
	public float gistanceTo(Position position) {
		return dist(getAbsoluteTranslation(),position.getAbsoluteTranslation());
	}
	private Vector3f getAbsoluteTranslation() {
		Matrix4f m = getAbsoluteMatrix();		
		return new Vector3f(m.m30,m.m31,m.m32);
	}

	public Matrix4f getAbsoluteMatrix() {
		if(isAbsolyte()){
			return new Matrix4f(matrix);
		}
		if(owner==null)
			return new Matrix4f(matrix);
		Object3d parent=owner.getParent();
		if(parent==null)
			return new Matrix4f(matrix);
		Matrix4f m2=parent.getPosition().getAbsoluteMatrix();
		Matrix4f result=new Matrix4f();
		Matrix4f.mul(m2, matrix, result);
		return result;	
	}

	public Object3d getOwner() {
		return owner;		
	}

	@Override
	public void apply() {
		if(!cashed)
			cache();
		glPushMatrix();
		if(isAbsolyte())
			glLoadIdentity();
		glMultMatrix(cash);	
	}

	@Override
	public void unApply() {
		glPopMatrix();
	}

	public void loadFromOpenGL() {
		glLoadMatrix(cash);
		matrix.load(cash);
		cash.flip();
	}

}
