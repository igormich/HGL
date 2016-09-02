package jglsl;

public abstract class JShader {
	
	
	
	protected Float add(Float a,Float b) {return null;}
	protected Float sub(Float a,Float b) {return null;}
	protected Float div(Float a,Float b) {return null;}
	protected Float mul(Float a,Float b) {return null;}
	
	protected Float sin(Float a) {return null;}
	protected Float cos(Float a) {return null;}
	protected Float max(Float a,Float b) {return null;}
	protected Float min(Float a,Float b) {return null;}
	protected Float clamp(Float a,Float min,Float max) {return null;}
	
	protected Vec2 add(Vec2 a,Vec2 b) {return null;}
	protected Vec2 add(Float a,Vec2 b) {return null;}
	protected Vec2 add(Vec2 a,Float b) {return null;}

	protected Vec3 add(Vec3 a,Vec3 b) {return null;}
	protected Vec3 add(Float a,Vec3 b) {return null;}
	protected Vec3 add(Vec3 a,Float b) {return null;}
	
	protected Vec4 add(Vec4 a,Vec4 b) {return null;}
	protected Vec4 add(Float a,Vec4 b) {return null;}
	protected Vec4 add(Vec4 a,Float b) {return null;}	
	
	protected Vec2 sub(Vec2 a,Vec2 b) {return null;}
	protected Vec2 sub(Float a,Vec2 b) {return null;}
	protected Vec2 sub(Vec2 a,Float b) {return null;}

	protected Vec3 sub(Vec3 a,Vec3 b) {return null;}
	protected Vec3 sub(Float a,Vec3 b) {return null;}
	protected Vec3 sub(Vec3 a,Float b) {return null;}
	
	protected Vec4 sub(Vec4 a,Vec4 b) {return null;}
	protected Vec4 sub(Float a,Vec4 b) {return null;}
	protected Vec4 sub(Vec4 a,Float b) {return null;}		

	protected Vec2 mul(Vec2 a,Vec2 b) {return null;}
	protected Vec2 mul(Float a,Vec2 b) {return null;}
	protected Vec2 mul(Vec2 a,Float b) {return null;}

	protected Vec3 mul(Vec3 a,Vec3 b) {return null;}
	protected Vec3 mul(Float a,Vec3 b) {return null;}
	protected Vec3 mul(Vec3 a,Float b) {return null;}
	
	protected Vec4 mul(Vec4 a,Vec4 b) {return null;}
	protected Vec4 mul(Float a,Vec4 b) {return null;}
	protected Vec4 mul(Vec4 a,Float b) {return null;}	
	
	protected Vec2 div(Vec2 a,Vec2 b) {return null;}
	protected Vec2 div(Vec2 a,Float b) {return null;}	
	protected Vec3 div(Vec3 a,Vec3 b) {return null;}
	protected Vec3 div(Vec3 a,Float b) {return null;}	
	protected Vec4 div(Vec4 a,Vec4 b) {return null;}
	protected Vec4 div(Vec4 a,Float b) {return null;}	
	
	protected Vec3 cross(Vec3 a,Vec3 b) {return null;}
	
	protected Float length(Vec2 a) {return null;}
	protected Float length(Vec3 a) {return null;}
	protected Float length(Vec4 a) {return null;}
	
	protected Float distance(Vec2 a,Vec2 b) {return null;}
	protected Float distance(Vec3 a,Vec3 b) {return null;}
	protected Float distance(Vec4 a,Vec4 b) {return null;}
	
	protected Vec2 normalize(Vec2 a) {return null;}
	protected Vec3 normalize(Vec3 a) {return null;}
	protected Vec4 normalize(Vec4 a) {return null;}
	
	protected Vec2 reflect(Vec2 in,Vec2 normal) {return null;}
	protected Vec3 reflect(Vec3 in,Vec3 normal) {return null;}
	protected Vec4 reflect(Vec4 in,Vec4 normal) {return null;}
	
	protected Vec2 refract(Vec2 ain,Vec2 normal,Float refractionRatio) {return null;}
	protected Vec3 refract(Vec3 ain,Vec3 normal,Float refractionRatio) {return null;}
	protected Vec4 refract(Vec4 ain,Vec4 normal,Float refractionRatio) {return null;}
	
	protected <T> Float dot(T a,T b) {return null;}
	protected Float clamp(Float a,Float b) {return null;}
	
	protected Mat4 add(Mat4 a,Mat4 b) {return null;}
	protected Mat4 sub(Mat4 a,Mat4 b) {return null;}
	protected Mat4 mul(Mat4 a,Mat4 b) {return null;}
	protected Vec4 mul(Mat4 a,Vec4 b) {return null;}
	
	protected Mat3 add(Mat3 a,Mat3 b) {return null;}
	protected Mat3 sub(Mat3 a,Mat3 b) {return null;}
	protected Mat3 mul(Mat3 a,Mat3 b) {return null;}
	protected Vec3 mul(Mat3 a,Vec3 b) {return null;}
	
	protected Mat3 inverse(Mat3 m) {return null;}
	protected Mat4 inverse(Mat4 m) {return null;}
	
	protected Vec2 vec2(Float x,Float y) {return null;}
	protected Vec2 vec2(Vec3 v) {return null;}
	protected Vec2 vec2(Vec4 v) {return null;}
	
	protected Vec3 vec3(Float x,Float y,Float z) {return null;}
	protected Vec3 vec3(Vec2 xy,Float z) {return null;}
	protected Vec3 vec3(Vec4 v) {return null;}
	
	protected Vec4 vec4(Float x,Float y,Float z,Float w) {return null;}
	protected Vec4 vec4(Vec2 xy,Float z,Float w) {return null;}
	protected Vec4 vec4(Vec2 xy,Vec2 zw) {return null;}
	protected Vec4 vec4(Vec3 xyz,Float w) {return null;}
	
	protected Vec4 texture2D(Sampler2D colorTexture, Vec2 ts) {return null;}
	protected Vec4 texture2D(Sampler2D colorTexture, Vec2 ts,Float bias) {return null;}
	
	protected Vec4 texture3D(Sampler3D colorTexture, Vec3 xyz) {return null;}
	protected Vec4 texture3D(Sampler3D colorTexture, Vec3 xyz,Float bias) {return null;}
	
	
	public abstract void main(); 
	
}
