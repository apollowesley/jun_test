package tom.test;

import java.io.Serializable;

import tom.cocook.annotation.Handler.Interceptor;
import tom.cocook.handler.DefaultNameHandler;
import tom.cocook.interceptor.AbstractInterceptor;
import tom.cocook.interceptor.ActionInvocation;


@Interceptor(handler={DefaultNameHandler.class}) //,DefaultAnnotationHandler.class 
public class Demointerceptor extends AbstractInterceptor{

	@Override
	public boolean before(Serializable actionInvocation) {
		System.out.println("------before 拦截器执行  begin");
		ActionInvocation invocation = (ActionInvocation)actionInvocation;
		System.out.println("params====="+ invocation.getParaMap());
		System.out.println("url   ====="+invocation.getMethodUrl());;
		System.out.println("urlmethod=="+invocation.getHandlerInfo().getMethods().get(invocation.getMethodUrl()));
		System.out.println("contor====="+invocation.getHandlerInfo().getController());
		System.out.println("urlpams===="+invocation.getHandlerInfo().getUrlParams());
		System.out.println("------before 拦截器执行  end ");
		return true;
	}
}
