package physics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.vecmath.Vector3f;

import org.lwjgl.util.vector.Matrix4f;

import utils.Pair;
import base.Object3d;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.CollisionFlags;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.narrowphase.PersistentManifold;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

public class PhysicController {
	public static final Vector3f ZERO_GRAVIRY = new Vector3f(0, 0, 0);
	public static final Vector3f NORMAL_GRAVIRY = new Vector3f(0, 0, -9.8f);
	
	private static PhysicController instance = new PhysicController();
	
	private DynamicsWorld dynamicsWorld;
	private CollisionDispatcher dispatcher;
	private Vector3f gravity = NORMAL_GRAVIRY;
	
	private Map<Object3d,RigidBody> bodies = new HashMap<Object3d, RigidBody>();

	private Map<Pair<RigidBody,RigidBody>, PersistentManifold> collisions =new HashMap<Pair<RigidBody,RigidBody>, PersistentManifold>();
	public PhysicController() {
		super();
		BroadphaseInterface broadphase = new DbvtBroadphase();
	    DefaultCollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
	    
	    dispatcher = new CollisionDispatcher(collisionConfiguration);
	    
	    SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();

	    dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
	    dynamicsWorld.setGravity(getGravity());
	}
	public synchronized RigidBody addBody(AbstractCollisionBody body,Object3d owner) {
		Transform t=new Transform();
		//System.out.println("addBody");
		//System.out.println(Arrays.toString(owner.getPosition().getAbsoluteMatrixAsArray()));
		t.setFromOpenGLMatrix(owner.getPosition().getAbsoluteMatrixAsArray());
		DefaultMotionState dms = new DefaultMotionState(t);
	    RigidBodyConstructionInfo rbci = new RigidBodyConstructionInfo(body.getMass(), dms, body.getShape(), body.getInertia()); 
	    
	    RigidBody rb = new RigidBody(rbci); 
	    dynamicsWorld.addRigidBody(rb);
	    rb.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
	    if(body.isGrost())
	    	rb.setCollisionFlags(CollisionFlags.NO_CONTACT_RESPONSE);
	    if(body.useGravity())
	    	rb.setGravity(getGravity());
	    else
	    	rb.setGravity(ZERO_GRAVIRY);
	    rb.setFriction(body.getFriction());
	    
	    bodies.put(owner, rb);	 
	    return rb;
	}
	public synchronized void step(float time, int steps){
		for(Entry<Object3d, RigidBody> b:bodies.entrySet()){
			float[] arr = b.getKey().getPosition().getAbsoluteMatrixAsArray();
			Transform trans = new Transform();
			trans.setFromOpenGLMatrix(arr);
			MotionState ms = b.getValue().getMotionState();
			ms.setWorldTransform(trans);
			b.getValue().setMotionState(ms);
		}
		
		dynamicsWorld.stepSimulation(time, steps);
		
		int numManifolds = dispatcher.getNumManifolds();
		collisions.clear();
        for (int i = 0; i < numManifolds; i++)
        {
            PersistentManifold contactManifold = dispatcher.getManifoldByIndexInternal(i);
            if(contactManifold.getNumContacts()==0)
            	continue;
            RigidBody obA = (RigidBody) contactManifold.getBody0();
            RigidBody obB = (RigidBody) contactManifold.getBody1();
            if(!obA.equals(obB)){
            	collisions.put(new Pair<RigidBody,RigidBody>(obA,obB),contactManifold);
            	collisions.put(new Pair<RigidBody,RigidBody>(obB,obA),contactManifold);
            }
		}
       // System.out.println(collisions.size());
        applyTranforms();
	}
	
	private void applyTranforms() {
		float[] arr=new float[16];
		for(Entry<Object3d, RigidBody> b:bodies.entrySet()){			
			MotionState ms = b.getValue().getMotionState();
			Transform trans = new Transform();
			ms.getWorldTransform(trans);
			trans.getOpenGLMatrix(arr);
			b.getKey().getPosition().setAbsoluteMatrixAsArray(arr);
		}
	}
	public float[] getTransform(Object3d o){
		RigidBody rb=bodies.get(o);
		Transform trans = new Transform();
		rb.getMotionState().getWorldTransform(trans); 
		float[] matrix=new float[16];
		trans.getOpenGLMatrix(matrix); 
		return matrix;
	}

	public static PhysicController getDefault() {
		return instance ;
	}
	public Vector3f getGravity() {
		return gravity;
	}
	public void setGravity(Vector3f gravity) {
		this.gravity = gravity;
		bodies.values().forEach(rb -> rb.setGravity(gravity));
	}
	public boolean collisionTest(Object3d o1, Object3d o2) {
		RigidBody b1 = bodies.get(o1);
		RigidBody b2 = bodies.get(o2);
		return (b1!=null)&&(b2!=null)&&(collisions.containsKey(new Pair<RigidBody,RigidBody>(b1,b2)));
	}
}
