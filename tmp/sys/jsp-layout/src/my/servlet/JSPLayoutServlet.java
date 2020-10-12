package my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 在 Tomcat 下实现 JSP 的布局效果
 * @author Winter Lau
 * @url http://www.oschina.net/
 */
public class JSPLayoutServlet extends HttpServlet {

	public final static String LAYOUT = "layout";
	public final static String SCREEN_CONTENT = "screen_content";
	
    private String g_template_path;
    private String g_layout_path;
    private String g_default_layout;

	@Override
	public void init() throws ServletException {
		super.init();
		g_template_path = this.getInitParameter("template_path");
		if(g_template_path.endsWith("/"))
			g_template_path = g_template_path.substring(0, g_template_path.length()-1);
		g_layout_path = this.getInitParameter("layout_path");
		if(!g_layout_path.endsWith("/"))
			g_layout_path = g_layout_path + "/";
		g_default_layout = this.getInitParameter("default_layout");
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

        String requestURI = (String)req.getAttribute("javax.servlet.include.servlet_path");
        if(requestURI == null){
        	requestURI = (String)req.getAttribute("javax.servlet.include.path_info");
        	if(requestURI == null)
        		requestURI = req.getRequestURI();
        }
		requestURI = requestURI.substring(0, requestURI.length()-1);
		
		String jsp_uri = g_template_path + requestURI;
		BufferedResponse my_res = new BufferedResponse(res);
		getServletContext().getRequestDispatcher(jsp_uri).include(req, my_res);
		String screenContent = my_res.getScreenContent();
		
		//获取layout页面
		String layout_page = (String)req.getAttribute(LAYOUT);
		if(layout_page == null)
			layout_page = g_default_layout;
		if(layout_page!=null && layout_page.trim().length()>0){
			//执行layout页面
			req.setAttribute(SCREEN_CONTENT, screenContent);
			String layout_uri = g_layout_path + layout_page;
			//合并结果
			getServletContext().getRequestDispatcher(layout_uri).include(req, res);
		}
		else
			res.getWriter().append(screenContent);
	}

	/**
	 * Response封装
	 * @author Winter Lau
	 *
	 */
	class BufferedResponse extends HttpServletResponseWrapper {

		private StringWriter sout;
		private PrintWriter pout;
		
		public BufferedResponse(HttpServletResponse res) {
			super(res);
			sout = new StringWriter();
			pout = new PrintWriter(sout);
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			return pout;
		}

		protected String getScreenContent() {
			return sout.toString();
		}
	}

}
