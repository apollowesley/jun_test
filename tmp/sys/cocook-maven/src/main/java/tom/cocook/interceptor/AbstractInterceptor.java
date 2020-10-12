package tom.cocook.interceptor;


public abstract class AbstractInterceptor implements Interceptor {
	
	@Override
	public void after(ActionInvocation actionInvocation) {
	}
	
	@Override
	public abstract boolean before(ActionInvocation actionInvocation);


}
