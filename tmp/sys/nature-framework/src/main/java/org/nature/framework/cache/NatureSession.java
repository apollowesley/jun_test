package org.nature.framework.cache;

public interface NatureSession {
	public void init();
	public void set(String key,Object value);
	public Object get(String key);
	public void replace(String key,Object value);
	public void remove(String key);
	public String getLock();
	public String getId();
}
