package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import properties.Property3d;

public class Object3d{
	private Position position = new Position(this);
	private List<Object3d> children =  new ArrayList<Object3d>();
	private Object3d parent = null;
	private List<Property3d> properties = new ArrayList<Property3d>();
	
	
	public Position getPosition() {
		return position;
	}
	
	public Position resetPosition() {
		position=new Position(this);
		return position;
	}
	
	
	
	public Object3d addChild(Object3d object3d){
		children.add(object3d);
		object3d.setParent(this);
		return this;
	};
	private void setParent(Object3d parent) {
		if(parent==null)
		{
			this.parent=null;
			return;
		}
		Object3d nextParent=parent;
		while(nextParent!=null){
			if(nextParent==this)
				throw new IllegalStateException("Recursive hierarchy");
			nextParent=nextParent.getParent();
		}
		if(this.parent!=null)
			this.parent.removeChild(this);
		this.parent=parent;
	}

	public void removeChild(Object3d object3d) {
		object3d.setParent(null);
		children.remove(object3d);
	}

	public Object3d getParent() {
		return parent;
	}

	public List<Object3d> getChildren() {
		return Collections.unmodifiableList(children);
	}
	
	public Object3d add(Property3d property){
		if(property.isUnique())
		{
			Optional<Property3d> propertyForReplace = properties.stream().filter(p -> p.getClass() == property.getClass()).findAny();
			if(propertyForReplace.isPresent())
			{
				int index = properties.indexOf(propertyForReplace.get());
				properties.set(index , property);
			}
			else
				properties.add(property);	
		}
		else
		{
			properties.add(property);
		}
		return this;
	}
	
	public <T extends Property3d> T get(Class<T> type){
		return get(type,null);
	}
	
	public <T extends Property3d> T get(Class<T> type,T defaultValue){
		@SuppressWarnings("unchecked")
		Optional<T> result = (Optional<T>) properties.stream().filter(p -> p.getClass() == type).findAny();
		return result.orElse(defaultValue);
	}
	
	public void tick(float deltaTime,float time) {
		position.apply();
		getChildren().forEach(child -> child.tick(deltaTime,time));
		properties.forEach(property -> property.tick(deltaTime, time,this));
		position.unApply();
	}
	
	public void render(RenderContex renderContex){
		position.apply();
		getChildren().forEach(child -> child.render(renderContex));
		properties.forEach(property -> property.render(renderContex,this));
		position.unApply();
	}

	public static void main(String[] args) {
		
		Object3d object3d=new Object3d();
	}




}
