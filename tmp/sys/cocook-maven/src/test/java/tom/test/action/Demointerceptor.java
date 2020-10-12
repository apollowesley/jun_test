package tom.test.action;


import tom.cocook.annotation.Handler.Interceptor;
import tom.cocook.handler.DefaultNameHandler;
import tom.cocook.interceptor.AbstractInterceptor;
import tom.cocook.interceptor.ActionInvocation;


@Interceptor(DefaultNameHandler.class) //,DefaultAnnotationHandler.class 
public class Demointerceptor extends AbstractInterceptor{

	@Override
	public boolean before(ActionInvocation actionInvocation) {
		System.out.println("------before 拦截器执行  begin");
		ActionInvocation invocation = (ActionInvocation)actionInvocation;
		System.out.println("params====="+ invocation.getParaMap());
		System.out.println("url   ====="+invocation.getMapping());;
		System.out.println("contor====="+invocation.getHandlerInfo().getController());
		System.out.println("------before 拦截器执行  end ");
		return true;
	}
}
