package properties;

import base.Object3d;
import base.RenderContex;

public interface Property3d {
	default boolean isUnique() {
		return false;
	}
	//use for scripting
	default void tick(float deltaTime,float time,Object3d owner) {
		//DO NOTHING
	}
	//use for visual objects
	default void render(RenderContex renderContex,Object3d owner) {
		//DO NOTHING
	}
	//use for physics objects
	default void collision(Object3d owner,Object3d otherObject) {
		//DO NOTHING
	}
}
