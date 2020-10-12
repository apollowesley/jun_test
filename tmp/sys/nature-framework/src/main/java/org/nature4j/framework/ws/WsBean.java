package org.nature4j.framework.ws;

public class WsBean {
	Object wsObject;
	String address;
	String namespace;
	
	public WsBean(Object wsObject, String address, String namespace) {
		super();
		this.wsObject = wsObject;
		this.address = address;
		this.namespace = namespace;
	}
	public Object getWsObject() {
		return wsObject;
	}
	public void setWsObject(Object wsObject) {
		this.wsObject = wsObject;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	
}
