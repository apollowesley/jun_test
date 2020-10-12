package com.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class SetCharacterEncodingFilter implements Filter {

	protected String encoding; // 接收字符编码
	protected FilterConfig filterConfig; // 初始化配置

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 1. ServletRequest 转成 HttpServletRequest
		HttpServletRequest sRequest = (HttpServletRequest) request;
		HttpServletResponse sResponse = (HttpServletResponse) response;

		if (encoding == null) {
			encoding = "UTF-8";
		}

		sResponse.setCharacterEncoding(encoding);

		// 2. 如果是GET，就生成一个Wrapper对象，覆盖获取参数的方法进行转码
		if ("GET".equalsIgnoreCase(sRequest.getMethod())) {
			sRequest.setCharacterEncoding(encoding);
			// sRequest = new MyRequest(sRequest);
		}
		// 3. 如果是POST，request.setCharacterEncoding("UTF-8")
		else if ("POST".equalsIgnoreCase(sRequest.getMethod())) {
			sRequest.setCharacterEncoding(encoding);
			sRequest = new MyRequest(sRequest);
		}

		// 调用chain.doFilter继续向后执行
		chain.doFilter(sRequest, sResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 从web.xml文件中读取encoding的值
		encoding = filterConfig.getInitParameter("encoding");
	}

	/**
	 * 包装器，覆盖 getParameter()和 getParameterValues() 这两个方法来进行转码。
	 * 
	 * @author BeanSoft
	 * 
	 */
	class MyRequest extends HttpServletRequestWrapper {

		public MyRequest(HttpServletRequest request) {
			super(request);
		}

		/**
		 * 字符串转码。
		 * 
		 * @param input
		 *            输入字符串
		 * @param srcEncoding
		 *            源字符串的编码
		 * @param targetEncoding
		 *            目标编码
		 * @return 转换过的内容
		 */
		public String changeEncoding(String input, String srcEncoding, String targetEncoding) {
			try {
				// 1. 获取源编码的bytes[]
				byte[] data = input.getBytes(srcEncoding);
				// 2. 将bytes[]按照制定编码转换为String
				return new String(data, targetEncoding);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			return input;
		}

		@Override
		public String getParameter(String name) {
			if (encoding == null) {
				encoding = "UTF-8";
			}
			String value = super.getParameter(name);
			if ("post".equalsIgnoreCase(super.getMethod())) {
				if ("application/x-www-form-urlencoded;charset=UTF-8".equals(super.getContentType())) {
					if (value != null && !"".equals(value) && !isEncoded(value)) {
						try {
							value = URLDecoder.decode(value, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
						}
					}
				}
			} else {
				if (value != null && !"".equals(value) && !isEncoded(value)) { // 适用于
																				// resin
																				// 环境中
					value = changeEncoding(value, "ISO8859-1", encoding);
				}
				/*
				 * if(value != null && !"".equals(value)){ // 适用于 tomcat(前提是没有的
				 * server.xml 文件中设置编码) value = changeEncoding(value,
				 * "ISO8859-1", encoding); }
				 */
			}
			return value;
		}

		@Override
		public String[] getParameterValues(String name) {
			if (encoding == null) {
				encoding = "UTF-8";
			}
			String[] values = super.getParameterValues(name);
			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					if ("post".equalsIgnoreCase(super.getMethod())) {
						if ("application/x-www-form-urlencoded;charset=UTF-8".equals(super.getContentType())) {
							if (values[i] != null && !"".equals(values[i]) && !isEncoded(values[i])) {
								try {
									values[i] = URLDecoder.decode(values[i], "UTF-8");
								} catch (UnsupportedEncodingException e1) {
									e1.printStackTrace();
								}
							}
						}
					} else {
						if (values[i] != null && !"".equals(values[i]) && !isEncoded(values[i])) {
							values[i] = changeEncoding(values[i], "ISO8859-1", encoding);
						}
					}
				}
			}
			return values;
		}

		/**
		 * 判断编码是否转化过(判断是不是中文)
		 * 
		 * @return
		 * @throws Exception
		 */
		public boolean isEncoded(String str) {
			String strRE = "";
			try {
				strRE = new String(str.getBytes("iso8859-1"), encoding);
			} catch (Exception ex) {
				throw new RuntimeException("转换编码失败！ ");
			}

			if (strRE.equals(str)) {
				// 非汉字
				return false;
			} else {
				return true;
			}
		}
	}
}
