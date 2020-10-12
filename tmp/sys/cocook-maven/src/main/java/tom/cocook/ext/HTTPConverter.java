package tom.cocook.ext;

import java.lang.reflect.Method;
import java.util.HashMap;

import tom.cocook.core.RequestContext;
import tom.cocook.interceptor.ActionInvocation;

public final class HTTPConverter extends tom.kit.clazz.Converter{
	/**
	 * 非静态变量反射调用无参方法, 需初始化
	 */
	@SuppressWarnings("serial")
	public static final HashMap<Class<?>, Method> class2RequestContext = new HashMap<Class<?>, Method>() {
		{
			try {
				put(RequestContext.class, ActionInvocation.class.getMethod("getRequestContext"));
				put(javax.servlet.http.HttpServletRequest.class, ActionInvocation.class.getMethod("getRequest"));
				put(javax.servlet.http.HttpServletResponse.class, ActionInvocation.class.getMethod("getResponse"));
				put(javax.servlet.http.HttpSession.class, ActionInvocation.class.getMethod("getSession"));
				put(javax.servlet.ServletContext.class, ActionInvocation.class.getMethod("getServletContext"));

				put(java.util.Map.class, ActionInvocation.class.getMethod("getParaMap"));

			} catch (Exception e) {
				throw new Error(e);
			}
		}
	};
	
	public static boolean canConvertRequestContext(Class<?> clazz) {
		return class2RequestContext.containsKey(clazz);
	}

	/**
	 * 非静态变量反射调用无参方法, 需初始化
	 * @param <T>
	 * @param _class
	 * @param obj != null 实例
	 * @param params == null, 反射参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T coverterclass2RequestContext(Class<T> _class, Object obj, Object... params) {
		try {
			return (T) class2RequestContext.get(_class).invoke(obj, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
