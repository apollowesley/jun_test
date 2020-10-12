package org.nature4j.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nature4j.framework.bean.FileData;
import org.nature4j.framework.cache.NatureContext;
import org.nature4j.framework.helper.ConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterUtil {
	private static Logger LOGGER = LoggerFactory.getLogger(FilterUtil.class);
	
	private static Set<String> excludesPattern;
	
	public static void initExcludes(){
		String exclusions = ConfigHelper.getExcludesPattern();
		if (StringUtil.isNotBank(exclusions)) {
			excludesPattern = new HashSet<String>(Arrays.asList(exclusions.split("\\s*,\\s*")));
		}
	}
	
	public static boolean isExclusion(HttpServletRequest request) {
		String contextPath = request.getServletContext().getContextPath();
		String requestURI = request.getRequestURI();
        if (excludesPattern == null) {
            return false;
        }
        if (contextPath != null && requestURI.startsWith(contextPath)) {
            requestURI = requestURI.substring(contextPath.length());
            if (!requestURI.startsWith("/")) {
                requestURI = "/" + requestURI;
            }
        }
        for (String pattern : excludesPattern) {
            if (PathMatcherUtil.matches(pattern, requestURI)) {
                return true;
            }
        }
        return false;
    }
	
	public static void sendRedirect(HttpServletResponse response,String url) {
		try {
			if (url.equals("/")) {
				url = NatureContext.getRequest().getServletContext().getContextPath();
			}
			response.sendRedirect(url);
		} catch (IOException e) {
			LOGGER.error(e.toString());
		}
	}
	
	public static void requestDispatcher(String url,HttpServletRequest request,HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			request.getRequestDispatcher(url).forward(request, response);
		} catch (ServletException e) {
			LOGGER.error(e.toString());
		} catch (IOException e) {
			LOGGER.error(e.toString());
		}
	}
	
	public static void writeJson(HttpServletResponse response,String json){
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html;charset=UTF-8");
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			LOGGER.error(e.toString());
		}
	}
	
	public static void writeFile(HttpServletResponse response,FileData fileData ) {
		InputStream is =fileData.inputStream;
			if(is!=null){
				try {
					response.setHeader("Content-Type", fileData.contentType);
					response.setHeader("Content-Disposition",
							"attachment; filename=" + URLEncoder.encode(fileData.fileName, "UTF-8"));
					ServletOutputStream os = response.getOutputStream();
					int len = -1;
					byte[] buf = new byte[1024];
					while ((len=is.read(buf))!=-1) {
						os.write(buf, 0, len);
					}
				} catch (IOException e) {
					LOGGER.error(e.toString());
				}finally {
					try {
						is.close();
					} catch (IOException e) {
						LOGGER.error(e.toString());
					}
				}
			}else {
				throw new RuntimeException("inputstream is null .");
			}
		
	}
}
