package tom.cocook.interceptor;

import java.io.Serializable;


public interface Interceptor {
	public abstract boolean before (ActionInvocation actionInvocation);
	public abstract void after (ActionInvocation actionInvocation);
	
}
