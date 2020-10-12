/**
 * 
 * @auther wuwang
 * @createTime 2014-5-24 下午9:50:53
 */
package com.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weiwo.pear.Pear;

import com.api.sso.Login;
import com.globalInterface.RequestParameter2;
import com.staticClass.Const;
import com.staticClass.JsonUtils;

/**
 * 
 * 
 * @author peaches
 */
public class Portal extends RequestParameter2 {
	private static final Logger log = LoggerFactory.getLogger(Portal.class);

	/**
	 * Constructor of the object.
	 */
	public Portal() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.service(request, response);
		/**
		 * @Tag servlet不是线程安全的，所以这里先使用这种方法试用一下，如果真的非常非常浪费服务器资源，再修改。
		 * @Tag Spring不也是scope ="prototype"
		 */
		System.out.println("cookies:" + request.getSession().getId());
		System.out.println("cookies:" + request.getSession().getAttribute("UOID"));
		System.out.println("cookies:" + request.getSession().getAttribute("UName"));

		try {
			String a = getParameter("a");
			try {
				/** 兼容低版本 登录 **/
				if (Const.stringIsEmpty(a) && request.getRequestURI().equals("/Portal.api")) {
					execute("portalReal", "service", true);
				}

				/** 如果a能转换为数字，则去 PortalReal类，这是兼容之前的方法。否则通过名字调用 **/
				int action = Integer.valueOf(a);
				execute("portalReal", "service", true);
			} catch (NumberFormatException e) {
				String requestUrl = request.getRequestURI();
				/** package的概念后面再引入，现在不用，现在只用一层 **/
				System.out.println(requestUrl);
				String action = requestUrl.substring(requestUrl.lastIndexOf('/'));
				if (action.contains("_")) {
					action = action.replace("/", "").replace(".api", "");
				} else {
					action = String.valueOf(a);
				}

				String[] names = action.split("_");
				/** 验证用户登录 **/
				boolean hadLogin = new Login(request, response).sessionValide();
				if (!hadLogin) {
					return;
				}
				/** 验证用户登录 **/

				execute(names[0], names[1], false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsono = new JSONObject();
			PrintWriter out = response.getWriter();
			jsono.put("code", "400");
			jsono.put("status", "false");
			out.write(jsono.toJSONString());
			out.close();
			return;
		}
	}

	/**
	 * 调用 className 类的 methodName 方法。parameter为是否带request和response参数，默认为false
	 * 
	 * @param className
	 * @param methodName
	 * @throws Exception
	 * @createTime 2014-12-27 下午9:10:41
	 */
	private void execute(String className, String methodName, boolean parameter) throws Exception {
		Class<?> c = Class.forName(Pear.getInstance().getBean(className));// 获得对象
		Object ci = c.newInstance();// 获得对象实例
		Method method;
		Object result;
		if (parameter) {
			/** 主要是兼容旧方法，不推荐使用 **/
			Class<?>[] pars = new Class[2];// 获得方法时的参数类型列表
			Object[] args = new Object[2];// 调用方法时的参数列表
			pars[0] = HttpServletRequest.class;
			pars[1] = HttpServletResponse.class;
			args[0] = request;
			args[1] = response;
			method = c.getMethod(methodName, pars);// 执行调用方法
			result = method.invoke(ci, args);
		} else {
			/** 初始化 **/
			Class<?>[] pars = new Class[2];// 获得方法时的参数类型列表
			Object[] args = new Object[2];// 调用方法时的参数列表
			pars[0] = HttpServletRequest.class;
			pars[1] = HttpServletResponse.class;
			args[0] = request;
			args[1] = response;
			Method methodService = c.getMethod("service", pars);// 执行初始化方法
			methodService.invoke(ci, args);

			method = c.getMethod(methodName);// 执行调用方法
			result = method.invoke(ci);
		}

		if (result == null) {

		} else {
			PrintWriter out = response.getWriter();// 输出json到页面
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("code", "200");
			map.put("status", "success");
			map.put("result", "{\"type\":" + result + "}");
			out.append(String.valueOf(JsonUtils.parseObjectFromObject(map)));
			out.flush();
			out.close();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

	@Override
	public void initClass() {
		// TODO Auto-generated method stub

	}

}
