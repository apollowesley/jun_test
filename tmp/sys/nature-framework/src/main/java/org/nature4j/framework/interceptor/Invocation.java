package org.nature4j.framework.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nature4j.framework.bean.CtrlBean;
import org.nature4j.framework.cache.NatureContext;
import org.nature4j.framework.core.NatureCtrl;
import org.nature4j.framework.core.NatureMap;
import org.nature4j.framework.helper.AttributeHelper;
import org.nature4j.framework.helper.FieldHelper;
import org.nature4j.framework.helper.IocHelper;
import org.nature4j.framework.util.ReflectionUtil;
import org.nature4j.framework.validator.NatureValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Invocation{
	private static Logger LOGGER = LoggerFactory.getLogger(Invocation.class);
	
	private NatureCtrl targetObject;
	private Object returnValue;
	private int interceptorIndex=0;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private NatureMap requestParams = new NatureMap();
	private NatureMap responseParams = new NatureMap();
	private CtrlBean ctrlBean;
	
	public Invocation(CtrlBean ctrlBean,NatureMap requestParams, HttpServletRequest request,HttpServletResponse response) {
		this.ctrlBean = ctrlBean;
		this.request = request;
		this.response = response;
		this.requestParams = requestParams;
		initNatureCtrl();
		assemblyRequestParams();//组装请求的值
	}

	public void invoke() {
		try {
			if (this.returnValue ==null&&interceptorIndex!=ctrlBean.getInterceptors().size()) {
				ctrlBean.getInterceptors().get(interceptorIndex++).intercept(this);
				return ;
			}
			assemblyField();//赋值到Ctrl属性
			if (this.returnValue==null) {
				this.returnValue = ctrlBean.getMethod().invoke(targetObject);
			}
			assemblyResponseParams();//组装Ctrl的值
		} catch (Exception e) {
			LOGGER.error("invoke error");
			throw new RuntimeException(e);
		}
	}
	 
	private void initNatureCtrl() {
		targetObject = (NatureCtrl) ReflectionUtil.newInstance(ctrlBean.getCls());
		IocHelper.injectService(targetObject, ctrlBean.getCls());//IOC
		NatureContext.setContext(targetObject,request,response);
	}
	
	private void assemblyRequestParams() {
		FieldHelper.putInRequestParams(request,requestParams);
	}
	
	private void assemblyResponseParams() {
		AttributeHelper.putInRequestParams(responseParams,targetObject, ctrlBean.getCls());
	}

	private void assemblyField() {
		new FieldHelper().putInField(requestParams, targetObject, ctrlBean.getCls());//Assembly field
	}

	public Method getTargetMethod() {
		return ctrlBean.getMethod();
	}

	public Object getReturnValue() {
		return returnValue;
	}

	public List<NatureInterceptor> getInterceptors() {
		return ctrlBean.getInterceptors();
	}

	public List<NatureValidator> getValidators() {
		return ctrlBean.getValidators();
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
		return ctrlBean.getCls();
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

	/**
	 * 获取所有请求参数<br>
	 * 相对于request.getParameter(name)更方便实用；<br>
	 * 参数包含multipart/form-data表单数据
	 */
	public NatureMap getRequestParams() {
		return requestParams;
	}
	
	/**
	 * 获取所有响应参数<br>
	 * 相对于request.getAttribute(name)更方便实用；<br>
	 * 参数包含回显页面的数据
	 */
	public NatureMap getResponseParams() {
		return responseParams;
	}


	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

}
