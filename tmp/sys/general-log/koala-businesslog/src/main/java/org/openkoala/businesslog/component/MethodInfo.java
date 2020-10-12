package org.openkoala.businesslog.component;

/**
 * 方法信息
 * @author xmfang
 *
 */
public class MethodInfo {

	private String className;
	
	private String methodName;
	
	private String methodLocation;
	
	private Object[] argumentValues;
	
	private Object returnValue;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodLocation() {
		return methodLocation;
	}

	public void setMethodLocation(String methodLocation) {
		this.methodLocation = methodLocation;
	}

	public Object[] getArgumentValues() {
		return argumentValues;
	}

	public void setArgumentValues(Object[] argumentValues) {
		this.argumentValues = argumentValues;
	}

	public Object getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}
	
}
