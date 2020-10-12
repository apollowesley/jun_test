package org.durcframework.core.controller;

import java.beans.PropertyEditor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.durcframework.core.DefaultMessageResult;
import org.durcframework.core.MessageResult;
import org.durcframework.core.util.RequestUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * 提供基础能力的Controller,如果一个Controller具备简单功能可以继承这个类
 * 
 * @author tanghc 2015-2-28
 */
public abstract class BaseController {
	private static final String DEF_ERROR_PAGE_NAME = "error";

	private Logger logger = Logger.getLogger(getClass());

	private ConcurrentMap<Class<?>,PropertyEditor> propertyEditorStore = new ConcurrentHashMap<Class<?>,PropertyEditor>();

	/**
	 * 获取httpRequest
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获取httpSession
	 * @return
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 获取客户端真实IP
	 * @return
	 */
	public String getClientIP() {
		return RequestUtil.getClientIP(getRequest());
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		this.initCustomEditor(binder);
	}

	protected void initCustomEditor(WebDataBinder binder) {
		Class<? extends BaseController> clazz = this.getClass();
		// 默认时间转换
		PropertyEditor propertyEditor = propertyEditorStore.get(clazz);
		if (propertyEditor == null) {
			DateFormat dateFormat = new SimpleDateFormat(getDateFormatPattern());
			propertyEditor = new CustomDateEditor(dateFormat, true);
			propertyEditorStore.put(clazz,propertyEditor);
		}

		binder.registerCustomEditor(Date.class, propertyEditor);
	}

	protected String getDateFormatPattern() {
		return "yyyy-MM-dd";
	}

	@ExceptionHandler
	protected Object exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
		logger.error(e.getMessage(), e);
		ModelAndView mav = this.newModelView(this.getErrorPageName());
		mav.addObject("error", e.getMessage());
		mav.addObject("e", e);
		return mav;
	}

	public Logger getLogger() {
		return logger;
	}

	/**
	 * 返回成功的视图
	 * 
	 * @return 默认返回DefaultMessageResult对象,可以重写getMessageResult()
	 *         方法返回自定义的MessageResult
	 */
	public MessageResult success() {
		MessageResult msgResult = getMessageResult();
		msgResult.setSuccess(true);
		return msgResult;
	}

	/**
	 * 返回成功
	 * 
	 * @param message 文本消息
	 * @return 默认返回DefaultMessageResult对象,可以重写getMessageResult()
	 *         方法返回自定义的MessageResult
	 */
	public MessageResult success(String message) {
		MessageResult msgResult = getMessageResult();
		msgResult.setSuccess(true);
		msgResult.setMessage(message);
		return msgResult;
	}
	
	/**
	 * 返回成功
	 * 
	 * @param message 文本消息
	 * @param attach 附加对象
	 * @return 默认返回DefaultMessageResult对象,可以重写getMessageResult()
	 *         方法返回自定义的MessageResult
	 */
	public MessageResult success(String message,Object attach) {
		MessageResult msgResult = getMessageResult();
		msgResult.setSuccess(true);
		msgResult.setMessage(message);
		msgResult.setAttach(attach);
		return msgResult;
	}

	/**
	 * 返回错误的视图
	 * 
	 * @param errorMsg
	 *            错误信息
	 * @return 默认返回DefaultMessageResult对象,可以重写getMessageResult()
	 *         方法返回自定义的MessageResult
	 */
	public MessageResult error(String errorMsg) {
		MessageResult msgResult = getMessageResult();
		msgResult.setSuccess(false);
		msgResult.setMessage(errorMsg);
		return msgResult;
	}
	
	/**
	 * 返回错误的视图
	 * 
	 * @param errorMsg
	 *            错误信息
	 * @param attach 附加对象
	 * @return 默认返回DefaultMessageResult对象,可以重写getMessageResult()
	 *         方法返回自定义的MessageResult
	 */
	public MessageResult error(String errorMsg,Object attach) {
		MessageResult msgResult = getMessageResult();
		msgResult.setSuccess(false);
		msgResult.setMessage(errorMsg);
		msgResult.setAttach(attach);
		return msgResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @param errorMsg
	 *            错误信息
	 * @param errorMsgs
	 *            更多错误信息
	 * @return 默认返回DefaultMessageResult对象,可以重写getMessageResult()
	 *         方法返回自定义的MessageResult
	 */
	public MessageResult error(String errorMsg, List<String> errorMsgs) {
		MessageResult msgResult = error(errorMsg);
		msgResult.setMessages(errorMsgs);
		return msgResult;
	}

	public ModelAndView newModelView(String viewName) {
		return new ModelAndView(viewName);
	}

	public ModelAndView newModelView(String viewName, Map<String, ?> model) {
		return new ModelAndView(viewName, model);
	}

	public ModelAndView newModelView(String viewName, String modelName, Object modelObject) {
		return new ModelAndView(viewName, modelName, modelObject);
	}

	public ModelMap newModel() {
		return new ModelMap();
	}
	
	/**
	 * 返回默认的错误页面,抛出的Exception将会跳转这个页面
	 * @return
	 */
	protected String getErrorPageName() {
		return DEF_ERROR_PAGE_NAME;
	}

	/**
	 * 返回默认的消息实现类,可覆盖此方法返回自定义的消息实现类
	 * 
	 * @return
	 */
	protected MessageResult getMessageResult() {
		return new DefaultMessageResult();
	}
}
