package jglsl;

public abstract class JVertexShader extends JShader {
	protected final Mat4 gl_ModelViewMatrix = null;
	protected final Mat4 gl_ProjectionMatrix = null;
	protected final Mat3 gl_NormalMatrix  = null;
	protected final Vec4 gl_Vertex = null;
	protected final Vec3 gl_Normal = null;
	protected final Vec4 gl_MultiTexCoord0 = null;
	
	protected Vec4 gl_Position;
}
