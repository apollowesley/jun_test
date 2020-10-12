package oc.tm.nio.http.support;

public interface ObjectFactory<T> {
	
	T createObject() throws Exception;
	
	void destroyObject(T obj);
	
	boolean validateObject(T obj);
}
