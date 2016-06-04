package properties;

import base.Object3d;

public interface Property3d {
	default boolean isUnique() {
		return false;
	}
	//use for scripting
	default void tick(float deltaTime,float time) {
		//DO NOTHING
	}
	//use for visual objects
	default void render(RenderContex renderContex) {
		//DO NOTHING
	}
	//use for physics objects
	default void collision(Object3d otherObject) {
		//DO NOTHING
	}
}
