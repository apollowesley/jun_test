package org.nature.framework.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nature.framework.bean.CtrlBean;
import org.nature.framework.cache.NatureContext;
import org.nature.framework.core.NatureCtrl;
import org.nature.framework.helper.FieldHelper;
import org.nature.framework.helper.IocHelper;
import org.nature.framework.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Invocation{
	private static Logger LOGGER = LoggerFactory.getLogger(Invocation.class);
	
	private NatureCtrl targetObject;
	private Method targetMethod;
	private Class<?> targetClass;
	private Object returnValue;
	private List<NatureInterceptor> interceptors;
	private int interceptorIndex=0;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public Invocation(CtrlBean ctrlBean, HttpServletRequest request,HttpServletResponse response) {
		this.targetClass = ctrlBean.getCls();
		this.targetMethod = ctrlBean.getMethod();
		this.interceptors = ctrlBean.getInterceptors();
		this.request = request;
		this.response = response;
	}

	public void invoke() {
		try {
			initNatureCtrl();
			if (interceptors!=null&&interceptorIndex!=interceptors.size()) {
				interceptors.get(interceptorIndex++).intercept(this);
				return ;
			}
			assemblyField();
			this.returnValue = targetMethod.invoke(targetObject);
		} catch (Exception e) {
			LOGGER.error("invoke error");
			throw new RuntimeException(e);
		}
	}
	 
	private void initNatureCtrl() {
		targetObject = (NatureCtrl) ReflectionUtil.newInstance(targetClass);
		NatureContext.setContext(targetObject,request,response);
		IocHelper.injectService(targetObject, targetClass);//IOC
	}

	private void assemblyField() {
		new FieldHelper().putInField(request, targetObject, targetClass);//Assembly field
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public Object getReturnValue() {
		return returnValue;
	}

	public List<NatureInterceptor> getInterceptors() {
		return interceptors;
	}

	public int getInterceptorIndex() {
		return interceptorIndex;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public NatureCtrl getTargetObject() {
		return targetObject;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setTargetObject(NatureCtrl targetObject) {
		this.targetObject = targetObject;
	}
	
	
}
