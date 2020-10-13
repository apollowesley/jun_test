package tom.cocook.interceptor;

import java.io.Serializable;

public abstract class AbstractInterceptor implements Interceptor {
	
	@Override
	public void after(Serializable actionInvocation) {
	}
	
	@Override
	public abstract boolean before(Serializable actionInvocation);


}
