package tom.cocook.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import tom.cocook.config.Constants;
import tom.cocook.core.CocookException;
import tom.cocook.core.Initializers;
import tom.cocook.core.RequestContext;
import tom.cocook.core.DefaultLoggerFactory.DefaultLogConfigure;
import tom.cocook.interceptor.ActionInvocation;
import tom.cocook.jdbc.simple.DBUtil;
import tom.cocook.mongo.MongoDao;
import tom.cocook.view.ViewFactory;

public final class CocookServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String SystemConfigLocation = "SystemConfigLocation" ;
	
	private void initPhysicalwebapp() throws IOException{
		String webRoot = null;
		webRoot = this.getServletContext().getRealPath("/");
		if (webRoot == null || webRoot.isEmpty()) {
			webRoot = this.getClass().getClassLoader().getResource("/").getFile();
			webRoot = new File(webRoot).getParentFile().getParentFile().getCanonicalPath();
		}
		Constants.setWebRoot(webRoot);
	}
	
	private void initSystemConfig() throws IOException{
		 InputStream in = getServletContext().getResourceAsStream(getInitParameter(SystemConfigLocation));
		 Properties properties = new Properties();
		 try {
			properties.load(in);
			for(Object obj: properties.keySet()){
				Constants.getSyscfg().put((String)obj, (String)properties.get(obj));
			}
		} finally{
			if(in!=null) in.close();
		}
	}
	
	private void initLogger(){
		new DefaultLogConfigure().configure();
	}
	
	private void initDataSource(){
		File file = new File(Constants.getWebRoot(), Constants.getDBConfigLocation());
		if("SQL".equals(Constants.getDBType())){
				DBUtil.init(file);
				return;
		}
		MongoDao.init(file);
	}
	
	private void initHandler() throws IOException, ClassNotFoundException{
		new Initializers().scan();
	}
	
	private void initView(){
		ViewFactory.initView();
	}
	
	@Override
	public void init() throws ServletException {
		try {
			initPhysicalwebapp();
			
			initSystemConfig();
			
			initLogger();
			
			initDataSource(); 
			
			initHandler();
			
			initView();
			
		} catch (Exception e) {
			throw new CocookException(e);
		} 
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(RequestContext.begin(getServletContext(), req, res), false);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(RequestContext.begin(getServletContext(), req, res), true);
	}

	private void process(RequestContext requestContext, boolean isPost) throws ServletException, IOException {
		try{
			matchHandler(requestContext, isPost);
		}finally{
			if(requestContext!=null){
				requestContext.end();
				requestContext = null;
			}
		}
	}
	
	
	private void matchHandler(RequestContext context, boolean isPost) throws ServletException, IOException{
		HandlerInfo handlerInfo = null;
		
		String uri = context.getRequest().getRequestURI();
        String contextPath = context.getRequest().getContextPath();
        String queryAction = uri.substring(contextPath.length());
		
		CurrentMethodUrl currentMethodUrl = new CurrentMethodUrl();
		for(Handler handler: Handler.handlers.values()){
			handlerInfo =  handler.match(queryAction, currentMethodUrl);
			if(handlerInfo == null) {
				continue;
			}
			HandlerController controller = handler.proxyApp();
			ActionInvocation actionInvocation = new ActionInvocation(handlerInfo, currentMethodUrl.getMethodUrl(),isPost);
			controller.enter(actionInvocation);
			controller = null;
			return;
		}
		String msg = queryAction + " NOT FOUND ON " + this.getServletName();
		context.error(HttpServletResponse.SC_NOT_FOUND, msg);
	}
	
	static class CurrentMethodUrl{
		private String methodUrl;

		public void setMethodUrl(String methodUrl) {
			this.methodUrl = methodUrl;
		}

		public String getMethodUrl() {
			return methodUrl;
		}
	}

}
