package durcframework.test.api;

import org.durcframework.core.DurcException;
import org.durcframework.open.InterceptorAdapter;
import org.durcframework.open.RequestContext;

public class MyInterceptor extends InterceptorAdapter {

	@Override
	public void beforeService(RequestContext requestContext) {
		System.out.println("服务执行前....");
		// 这里抛出异常,直接返回错误信息,服务不执行
		//throw new DurcException("beforeService抛出的错误");
	}
	
	@Override
	public void afterService(RequestContext requestContext) {
		System.out.println("服务执行完毕....");
	}
	
	@Override
	public boolean isMatch(RequestContext apiRequestContext) {
		
		// 如果返回false就不执行beforeService和afterService
		return true;
	}
	
}
