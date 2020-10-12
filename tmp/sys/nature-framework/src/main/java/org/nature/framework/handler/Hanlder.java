package org.nature.framework.handler;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nature.framework.annotation.Redirect;
import org.nature.framework.bean.CtrlBean;
import org.nature.framework.bean.FileData;
import org.nature.framework.bean.JsonData;
import org.nature.framework.helper.AttrabuteHelper;
import org.nature.framework.helper.ConfigHelper;
import org.nature.framework.helper.CtrlHelper;
import org.nature.framework.interceptor.Invocation;
import org.nature.framework.template.FreeMarkTemplate;
import org.nature.framework.util.CastUtil;
import org.nature.framework.util.FilterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hanlder {
	private static Logger LOGGER = LoggerFactory.getLogger(Hanlder.class);
	private static Map<String, CtrlBean> ctrlMap = CtrlHelper.ctrlMap;
	@SuppressWarnings("unchecked")
	public static void doHandler(String targetKey ,HttpServletRequest request,HttpServletResponse response){
		System.out.println("RequestUrl:"+targetKey);
		CtrlBean ctrlBean = ctrlMap.get(targetKey);
		if (ctrlBean==null) {
			throw new RuntimeException("your request method is not exist");
		}
		Invocation invocation = new Invocation(ctrlBean,request,response);
		invocation.invoke();
		Object returnObject = invocation.getReturnValue();
		if (returnObject!=null) {
			if(returnObject.getClass().isAssignableFrom(String.class)){
				String toUrl = CastUtil.castString(returnObject);
				if (!toUrl.startsWith("/")&&toUrl.indexOf(".")!=-1) {
					toUrl = ConfigHelper.getAppPagePath()+ctrlBean.getNamespace()+"/"+toUrl;
				}
				if (toUrl.matches(".*\\.ftl(\\?.*)?")) {
					if (!invocation.getTargetMethod().isAnnotationPresent(Redirect.class)) {
						Map<String, Object> rootMap = AttrabuteHelper.putInRootMap(request,invocation.getTargetObject(), invocation.getTargetClass());
						new FreeMarkTemplate().sendTemplate(request, response, toUrl, rootMap);
					}else {
						new FreeMarkTemplate().sendTemplate(request, response, toUrl, Collections.EMPTY_MAP);
					}
				}else {
					if (!invocation.getTargetMethod().isAnnotationPresent(Redirect.class)) {
						AttrabuteHelper.putInAttrabute(request, invocation.getTargetObject(),invocation.getTargetClass() );
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
		
	}
	
}
