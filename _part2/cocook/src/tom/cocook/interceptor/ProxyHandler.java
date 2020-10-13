package tom.cocook.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

/**
 * 动态代理 做拦截器
 * @author panmg
 */
public class ProxyHandler implements InvocationHandler {
	private Object obj;
	ArrayList<Interceptor> list = null;

	public Object bind(Object obj, ArrayList<Interceptor> interceptors) {
		this.obj = obj;
		list = interceptors;
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object o = null;
		ActionInvocation actionInvocation = ((ActionInvocation) args[0]); // 参数
		if (before(actionInvocation)) {        //执行代理的方法
			o = method.invoke(this.obj, args); //执行本来的方法
		}
		after(actionInvocation);
		return o;
	}

	private boolean before(ActionInvocation actionInvocation) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		boolean flag = true;
		for(Interceptor inter: list){
			if(!flag) break;
			flag = inter.before(actionInvocation);
		}
		return flag;
	}

	private void after(ActionInvocation actionInvocation) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		for(Interceptor inter: list){
			inter.after(actionInvocation);
		}
	}

}
