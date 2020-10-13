package org.coody.czone.common.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.coody.framework.core.logger.BaseLogger;
import org.coody.framework.core.util.DateUtils;
import org.coody.framework.core.util.PrintException;
import org.coody.framework.core.util.StringUtil;
import org.coody.framework.web.container.HttpContainer;
import org.coody.framework.web.util.RequestUtil;

@SuppressWarnings("unchecked")
public abstract class BaseController{
	
	
	
	protected BaseLogger logger=BaseLogger.getLogger(this.getClass());

	protected void keepParas() {
		Enumeration<String> paras = HttpContainer.getRequest().getParameterNames();
		if (StringUtil.isNullOrEmpty(paras)) {
			return;
		}
		while (paras.hasMoreElements()) {
			try {
				String string = (String) paras.nextElement();
				if (StringUtil.isNullOrEmpty(string)) {
					continue;
				}
				HttpContainer.getRequest().setAttribute(string.replace(".", "_"),
						HttpContainer.getRequest().getParameter(string));
			} catch (Exception e) {
				PrintException.printException(logger, e);
			}

		}
	}
	protected String loadBasePath(HttpServletRequest request) {
		return RequestUtil.loadBasePath(request);
	}
	protected String getPara(String paraName) {
		return HttpContainer.getRequest().getParameter(paraName);
	}

	protected Integer getParaInteger(String paraName) {
		return StringUtil.toInteger(HttpContainer.getRequest().getParameter(paraName));
	}

	protected Integer[] getParaIntegers(String paraName) {
		String[] paras = HttpContainer.getRequest().getParameterValues(paraName);
		if (StringUtil.isNullOrEmpty(paras)) {
			return null;
		}
		Integer[] values = new Integer[paras.length];
		for (int i = 0; i < paras.length; i++) {
			try {
				values[i] = Integer.valueOf(paras[i]);
			} catch (Exception e) {
			}
		}
		return values;
	}

	protected Double getParaDouble(String paraName) {
		return StringUtil.toDouble(HttpContainer.getRequest().getParameter(paraName));
	}

	protected Float getParaFloat(String paraName) {
		return StringUtil.toFloat(HttpContainer.getRequest().getParameter(paraName));
	}

	protected Long getParaLong(String paraName) {
		return StringUtil.toLong(HttpContainer.getRequest().getParameter(paraName));
	}

	protected Date getParaDate(String paraName) {
		try {
			return DateUtils.toDate(HttpContainer.getRequest().getParameter(paraName));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	protected Date getParaDateTime(String paraName) {
		try {
			return DateUtils.toDate(HttpContainer.getRequest().getParameter(paraName));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	protected <T> T getBeanAll(String paraName,Object model) {
		return RequestUtil.getBeanAll(HttpContainer.getRequest(), paraName,model,true);
	}

	protected <T> T getBeanAccept(String paraName,Object model, String... paras) {
		return RequestUtil.getBeanAccept(HttpContainer.getRequest(), paraName,model,true, paras);
	}

	protected <T> T getBeanRemove(String paraName,Object model, String... paras) {
		return RequestUtil.getBeanRemove(HttpContainer.getRequest(), paraName,model,true, paras);
	}

	protected <T> T getBeanAll(Object model) {
		return RequestUtil.getBeanAll(HttpContainer.getRequest(), null,model,true);
	}

	protected <T> T getBeanAccept(Object model, String... paras) {
		return RequestUtil.getBeanAccept(HttpContainer.getRequest(), null,model,true, paras);
	}

	protected <T> T getBeanRemove(Object model, String... paras) {
		return RequestUtil.getBeanRemove(HttpContainer.getRequest(), null,model,true, paras);
	}

	
	protected <T> T getSessionPara(String paraName) {
		return (T) HttpContainer.getRequest().getSession().getAttribute(paraName);
	}

	protected void setSessionPara(String paraName, Object obj) {
		HttpContainer.getRequest().getSession().setAttribute(paraName, obj);
	}

	protected Object getAttribute(String paraName) {
		return HttpContainer.getRequest().getAttribute(paraName);
	}

	protected void setAttribute(String paraName, Object obj) {
		HttpContainer.getRequest().setAttribute(paraName, obj);
	}

	protected String getHeader(String paraName) {
		return HttpContainer.getRequest().getHeader(paraName);
	}

	protected Map<String, String> getParas() {
		Map<String, String[]> map=HttpContainer.getRequest().getParameterMap();
		if(StringUtil.isNullOrEmpty(map)){
			return null;
		}
		Map<String, String> paraMap=new HashMap<String, String>();
		for (String key:map.keySet()) {
			String[] values=map.get(key);
			if(StringUtil.isNullOrEmpty(values)){
				continue;
			}
			paraMap.put(key, values[0]);
		}
		return paraMap;
	}
}
