package org.nature4j.framework.handler;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nature4j.framework.annotation.Redirect;
import org.nature4j.framework.bean.CtrlBean;
import org.nature4j.framework.bean.FileData;
import org.nature4j.framework.bean.JsonData;
import org.nature4j.framework.bean.ValidatorFail;
import org.nature4j.framework.core.NatureMap;
import org.nature4j.framework.helper.AttributeHelper;
import org.nature4j.framework.helper.ConfigHelper;
import org.nature4j.framework.helper.CtrlHelper;
import org.nature4j.framework.interceptor.Invocation;
import org.nature4j.framework.restful.RestfulTransfer;
import org.nature4j.framework.template.FreeMarkTemplate;
import org.nature4j.framework.util.CastUtil;
import org.nature4j.framework.util.FilterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hanlder {
	private static Logger LOGGER = LoggerFactory.getLogger(Hanlder.class);
	@SuppressWarnings("unchecked")
	public static void doHandler(String targetKey ,HttpServletRequest request,HttpServletResponse response){
		CtrlBean ctrlBean = CtrlHelper.getCtrlBean(targetKey,request.getMethod());
		NatureMap requestParams = new NatureMap();
		if (ctrlBean==null) {
			targetKey = RestfulTransfer.restfulTransfer(targetKey,requestParams);
			ctrlBean = CtrlHelper.getCtrlBean(targetKey,request.getMethod());
			if (ctrlBean==null) {
				throw new RuntimeException("your request method is not exist");
			}
		}
		Invocation invocation = new Invocation(ctrlBean,requestParams ,request,response);
		invocation.invoke();
		Object returnObject = invocation.getReturnValue();
		if (returnObject!=null) {
			boolean isRedirect = false;
			if (returnObject instanceof ValidatorFail) {
				ValidatorFail vf = (ValidatorFail) returnObject;
				returnObject = vf.getResult();
			}else {
				boolean hasRedirect = invocation.getTargetMethod().isAnnotationPresent(Redirect.class);
				if (hasRedirect) {
					Redirect redirect = invocation.getTargetMethod().getAnnotation(Redirect.class);
					String[] values = redirect.value();
					if (values.length==1&&(values[0].equals("")||values[0].equals(returnObject))) {
						isRedirect = true;
					}else {
						for (String v : values) {
							if (v.equals(returnObject)) {
								isRedirect = true;
								break;
							}
						}
					}
				}
			}
			
			if(returnObject.getClass().isAssignableFrom(String.class)){
				String toUrl = CastUtil.castString(returnObject);
				if (!toUrl.startsWith("/")) {
					if (toUrl.matches("^[\\w\\-/]+\\.\\w+(\\?.*)?$")) {
						toUrl = ConfigHelper.getAppPagePath()+ctrlBean.getNamespace()+"/"+toUrl;
					}
				}else if (toUrl.startsWith("/")&&isRedirect) {
					toUrl = request.getServletContext().getContextPath()+toUrl;
				}
				if (toUrl.matches("^[\\w\\-/]+\\.ftl(\\?.*)?$")) {
					if (!isRedirect) {
						Map<String, Object> rootMap = AttributeHelper.putInRootMap(request,invocation.getResponseParams());
						new FreeMarkTemplate().sendTemplate(request, response, toUrl, rootMap);
					}else {
						new FreeMarkTemplate().sendTemplate(request, response, toUrl, Collections.EMPTY_MAP);
					}
				}else {
					if (!isRedirect) {
						AttributeHelper.putInAttrabute(request,invocation.getResponseParams());
						FilterUtil.requestDispatcher(toUrl, request, response);
					}else {
						FilterUtil.sendRedirect(response,toUrl);
					}
				}
			}else if (returnObject.getClass().isAssignableFrom(JsonData.class)) {
				JsonData jsonData = (JsonData) returnObject;
				FilterUtil.writeJson(response, jsonData.json);
			}else if (returnObject.getClass().isAssignableFrom(FileData.class)) {
				FileData fileData = (FileData) returnObject;
				FilterUtil.writeFile(response, fileData);
			}
		}else {
			LOGGER.error("the method ["+invocation.getTargetMethod().getName()+"] is void or return null");
		}
		invocation=null;
		ctrlBean=null;
	}
	
}
