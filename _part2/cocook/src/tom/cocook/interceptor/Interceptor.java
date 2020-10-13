package tom.cocook.interceptor;

import java.io.Serializable;


public interface Interceptor {
	public abstract boolean before (Serializable actionInvocation);
	public abstract void after (Serializable actionInvocation);
	
}
