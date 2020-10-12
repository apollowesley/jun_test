package org.nature.framework.cache;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.nature.framework.core.NatureCtrl;
import org.nature.framework.util.ReflectionUtil;

public class InvocationContext implements Serializable{
	private static final long serialVersionUID = 1L;
	
	Object returnValue;
	NatureCtrl targetObject;
	public InvocationContext(Object returnValue, NatureCtrl targetObject) {
		this.returnValue = returnValue;
		this.targetObject = targetObject;
	}
	public Object getReturnValue() {
		return returnValue;
	}
	public NatureCtrl getTargetObject() {
		return targetObject;
	}
	
	public InvocationContext serialize(){
		Class<? extends NatureCtrl> natureClass = targetObject.getClass();
		Field[] fields = natureClass.getDeclaredFields();
		for (Field field : fields) {
			Class<?> type = field.getType();
			if (!Serializable.class.isAssignableFrom(type)) {
				ReflectionUtil.setField(targetObject, field, null);
			}
		}
		return this;
	}
	
}
