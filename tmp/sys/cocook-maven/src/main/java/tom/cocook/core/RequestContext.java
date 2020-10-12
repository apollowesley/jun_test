package tom.cocook.core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;


import tom.cocook.config.Constants;
import tom.kit.StringUtil;
import tom.kit.json.JsonUtil;

public class RequestContext {
	private ServletContext context;
	private HttpSession session;
	private HttpServletRequest req;
	private HttpServletResponse res;
	private static String encoding = Constants.getEncoding();
	private Map<String, Cookie> cookies = new HashMap<String, Cookie>();
	private Map<String, String> urlMap = new HashMap<String, String>();  // /user/{id} --> id:123
	private final static ThreadLocal<RequestContext> contexts = new ThreadLocal<RequestContext>();
	
	public ServletContext getServletContext() {
		return context;
	}

	public HttpSession getSession() {
		return session;
	}

	public HttpServletRequest getRequest() {
		return req;
	}

	public HttpServletResponse getResponse() {
		return res;
	}

	public Map<String, Cookie> getCookies() {
		return cookies;
	}
	
	public RequestContext() {
	}

	public RequestContext(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) {
		this.context = ctx;
		this.req = req;
		this.res = res;
		this.session = req.getSession(); // req.getSession(false) 默认不创建session
		Cookie[] cookies = req.getCookies();
		if (cookies != null)
			for (Cookie ck : cookies) {
				this.cookies.put(ck.getName(), ck);
			}
	}

	public static RequestContext begin(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) {
		RequestContext rc = new RequestContext(ctx, req, res);
		contexts.set(rc);
		return rc;
	}

	public static RequestContext get() {
		return contexts.get();
	}

	public void end() {
		this.context = null;
		this.req = null;
		this.res = null;
		this.session = null;
		this.cookies = null;
		contexts.remove();
	}

	public void forward(String url, boolean config) throws IOException, ServletException {
		if (config) {
			url = getRealPage(url);
		}
		req.getRequestDispatcher(url).forward(req, res);
	}
	
	public String getRealPage(String url){
		return Constants.getPageContext() + "/" + url + Constants.getPageSuffix() ;
	}

	public void redirect(String url) throws IOException {
		/* encodeURl自动对URL进行重写,避免禁止cookie的情况,同样适用getRequestDispatcher */
		res.sendRedirect(url);
	}
	
	public void error(int code, String...msg) throws IOException {
		if(msg.length>0)
			res.sendError(code, msg[0]);
		else
			res.sendError(code);
	}
	
	public void forbidden() throws IOException { 
		error(HttpServletResponse.SC_FORBIDDEN); 
	}

	public void notfound() throws IOException { 
		error(HttpServletResponse.SC_NOT_FOUND); 
	}

	public void printData(String contentType, Object data) throws IOException {
		res.setContentType(contentType); // "text/html; charset=utf-8"
		res.getWriter().print(data);
	}

	public void printHTML(Object data) throws IOException {
		printData("text/html; charset="+encoding, data);
	}

	public void printJSON(Object obj) throws IOException {
		try{
			String data = (obj instanceof String) ? (String)obj : JsonUtil.serialize(obj);
			printData("application/json;charset="+encoding, data);
		}catch(Exception e){
			throw new CocookException(e);
		}
	}

	public String[] getparams(String name) {
		return req.getParameterValues(name);
	}

	public String getParameterGBK(String name) throws UnsupportedEncodingException {
		String temp = req.getParameter(name);
		return !StringUtil.isEmpty(temp) ? new String(temp.getBytes("ISO-8859-1"), "GBK").trim() : "";
	}

	public String getParameterUTF(String name) throws UnsupportedEncodingException {
		String temp = req.getParameter(name);
		return !StringUtil.isEmpty(temp) ? new String(temp.getBytes("ISO-8859-1"), "UTF-8").trim() : "";
	}

	public String getParameter(String name) {
		return req.getParameter(name);
	}

	public void setHeader(String param1, String param2) {
		res.setHeader(param1, param2);
	}

	public void setContentType(String conType) {
		res.setContentType(conType);
	}

	public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {
		req.setCharacterEncoding(encoding);
	}

	public OutputStream getOutputStream() throws IOException {
		return res.getOutputStream();
	}

	public Object getAttribute(String name) {
		return req.getAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		req.setAttribute(name, value);
	}

	public Object getSessionAttribute(String name) {
		return req.getSession().getAttribute(name);
	}

	public void setSessionAttribute(String name, Object value) {
		req.getSession().setAttribute(name, value);
	}

	public void removeParam(String name) {
		req.removeAttribute(name);
	}

	public void removeSessionParam(String name) {
		req.getSession().removeAttribute(name);
	}
	
	public Map<String, Object> getAttributsMap(){
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		for (Enumeration<String> attrs=req.getAttributeNames(); attrs.hasMoreElements();) {
			String attrName = attrs.nextElement();
			parameterMap.put(attrName, req.getAttribute(attrName));
		}
		return parameterMap;
	}

	public Map<String, String> getParameterMap() {
		Map<String, String[]> hm = req.getParameterMap();
		Map<String, String> parameterMap = new HashMap<String, String>();
		Iterator<String> ite = hm.keySet().iterator();
		while (ite.hasNext()) {
			String key = ite.next();
			String[] values =  hm.get(key);
			String pamValues = "";
			for (String value : values) {
				if (pamValues.length() > 0)
					pamValues += ",";
				pamValues += value.trim();
				parameterMap.put(key, pamValues);
			}
		}
		return parameterMap;
	}

	public Map<String, String> getParameterMapGBK() throws UnsupportedEncodingException {
		Map<String, String[]> hm = req.getParameterMap();
		Map<String, String> parameterMap = new HashMap<String, String>();
		Iterator<String> ite = hm.keySet().iterator();
		while (ite.hasNext()) {
			String key = ite.next();
			String[] values = hm.get(key);
			String pamValues = "";
			for (String value : values) {
				/* value = URLDecoder.decode(value, "GBK"); */
				value = new String(value.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("GBK"));
				if (pamValues.length() > 0)
					pamValues += ",";
				pamValues += value.trim();
				parameterMap.put(key, pamValues);
			}
		}
		return parameterMap;
	}

	public Map<String, String> getParameterMapUTF() {
		Map<String, String[]> hm = req.getParameterMap();
		Map<String, String> parameterMap = new HashMap<String, String>();
		Iterator<String> ite = hm.keySet().iterator();
		while (ite.hasNext()) {
			String key = ite.next();
			String[] values = hm.get(key);
			String pamValues = "";
			for (String value : values) {
				/* value = URLDecoder.decode(value, "UTF-8"); */
				value = new String(value.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
				if (pamValues.length() > 0)
					pamValues += ",";
				pamValues += value.trim();
				parameterMap.put(key, pamValues);
			}
		}
		return parameterMap;
	}

	public Cookie getCookie(String name) {
		return cookies.get(name);
	}

	/**
	 * 设置COOKIE
	 * 
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public void setCookie(String name, String value, int maxAge) {
		setCookie(name, value, maxAge, true);
	}

	/**
	 * 设置COOKIE
	 * 
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public void setCookie(String name, String value, int maxAge, boolean all_sub_domain) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		if (all_sub_domain) {
			String serverName = req.getServerName();
			String domain = getDomainOfServerName(serverName);
			if (domain != null && domain.indexOf('.') != -1) {
				cookie.setDomain('.' + domain);
			}
		}
		cookie.setPath("/");
		res.addCookie(cookie);
	}

	public void deleteCookie(String name, boolean all_sub_domain) {
		setCookie(name, null, 0, all_sub_domain);
	}
	
	
	public static String getIpAddr(HttpServletRequest request){
		String ip = request.getHeader("X-REAL-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取用户访问URL中的根域名 例如: www.dlog.cn -> dlog.cn
	 * 
	 * @param req
	 * @return
	 */
	public String getDomainOfServerName(String host) {
		if (isIPAddr(host))
			return null;
		String[] names = host.split("\\.");
		int len = names.length;
		if (len == 1) {
			return null;
		}
		if (len == 3) {
			return makeup(names[len - 2], names[len - 1]);
		}
		if (len > 3) {
			String dp = names[len - 2];
			if (dp.equalsIgnoreCase("com") || dp.equalsIgnoreCase("gov") || dp.equalsIgnoreCase("net") || dp.equalsIgnoreCase("edu") || dp.equalsIgnoreCase("org"))
				return makeup(names[len - 3], names[len - 2], names[len - 1]);
			else
				return makeup(names[len - 2], names[len - 1]);
		}
		return host;
	}

	public boolean isIPAddr(String addr) {
		if (StringUtil.isEmpty(addr))
			return false;
		String[] ips = addr.split("\\.");
		if (ips.length != 4)
			return false;
		try {
			int ipa = Integer.parseInt(ips[0]);
			int ipb = Integer.parseInt(ips[1]);
			int ipc = Integer.parseInt(ips[2]);
			int ipd = Integer.parseInt(ips[3]);
			return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0 && ipc <= 255 && ipd >= 0 && ipd <= 255;
		} catch (Exception e) {
		}
		return false;
	}

	private String makeup(String... ps) {
		StringBuilder s = new StringBuilder();
		for (int idx = 0; idx < ps.length; idx++) {
			if (idx > 0) {
				s.append('.');
			}
			s.append(ps[idx]);
		}
		return s.toString();
	}
	
	
	public static String basePath; 
	public String getBasePath(){
		if(basePath == null){
			basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
		}
		return basePath;
	}

	public Map<String, String> getUrlMap() {
		return urlMap;
	}

	public void setUrlMap(Map<String, String> urlMap) {
		this.urlMap = urlMap;
	}
}
