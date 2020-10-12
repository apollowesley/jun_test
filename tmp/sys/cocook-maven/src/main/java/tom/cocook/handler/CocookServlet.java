package tom.cocook.handler;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import tom.cocook.annotation.HttpMethod;
import tom.cocook.config.Constants;
import tom.cocook.core.CocookException;
import tom.cocook.core.DefaultLogConfigure;
import tom.cocook.core.Initializers;
import tom.cocook.core.RequestContext;
import tom.cocook.interceptor.ActionInvocation;
import tom.cocook.view.ViewFactory;
import tom.db.jdbc.simple.DBUtil;
import tom.db.mongo.MongoDao;
import tom.kit.io.PropertiesUtil;
import tom.kit.io.Resource;

@WebServlet(name="CocookServlet", urlPatterns={"/"}, loadOnStartup=1 )
public final class CocookServlet extends HttpServlet {
	
	private static final long serialVersionUID = 2L;
	
	private static final String SystemConfigLocation = "SystemConfigLocation" ;
	
	private void initSystemConfig() throws IOException{
		String app = getInitParameter(SystemConfigLocation);
		if(app == null) app = "app.properties";
		Resource resource = new Resource(app);
		String webRoot =  this.getServletContext().getRealPath("/");
		resource.setWebRoot(webRoot);
		Properties props = PropertiesUtil.loadProperties(resource);
		props.put("webRoot", resource.getPhysicalwebapp());
		Constants.getSyscfg().putAll(PropertiesUtil.toMap(props));
		System.out.println(Constants.getSyscfg());
	}
	

	private void initLog(){
		new DefaultLogConfigure().config();
	}
	
	private void initDataSource() throws IOException{
		Resource resource = new Resource(Constants.getDBConfigLocation());
		resource.setWebRoot(Constants.getWebRoot());
		Properties props = PropertiesUtil.loadProperties(resource);
		if("SQL".equals(Constants.getDBType())){
			DBUtil.init(props);
			return;
		}
		MongoDao.init(props);
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
			initSystemConfig();
			
			initLog();
			
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
		DBUtil.closeDataSource();
		System.out.println("servlet destroy........");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(RequestContext.begin(getServletContext(), req, res), HttpMethod.GET);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(RequestContext.begin(getServletContext(), req, res), HttpMethod.POST);
	}

	private void process(RequestContext requestContext, HttpMethod method) throws ServletException, IOException {
		try{
			matchHandler(requestContext, method);
		}finally{
			if(requestContext!=null){
				requestContext.end();
				requestContext = null;
			}
		}
	}
	
	
	private void matchHandler(RequestContext context, HttpMethod method) throws ServletException, IOException{
		String uri = context.getRequest().getRequestURI();
        String contextPath = context.getRequest().getContextPath();
        String queryAction = uri.substring(contextPath.length());
		
		RequestMapping mapping = new RequestMapping();
		mapping.setMethod(method);
		mapping.setPath(queryAction);
		
		try{
			execHandler(mapping, context, method);
		}catch(IOException  e){
			throw e;
		}catch(ServletException ee){
			throw ee;
		}
	}
	
	private void execHandler(RequestMapping mapping, RequestContext context, HttpMethod method) throws ServletException, IOException{
		for(Handler handler: Handler.handlers.values()){
			HandlerInfo handlerInfo =  handler.match(mapping, context);
			if(handlerInfo == null) {
				continue;
			} 
			if(mapping.getMethod()!=null && mapping.getMethod()!= method) {
				context.error(HttpServletResponse.SC_FORBIDDEN, mapping.toString());
				return;
			}
			
			Controller controller = handler.proxyApp();
			ActionInvocation actionInvocation = new ActionInvocation(handlerInfo, mapping);
			if(actionInvocation.getMethod() == null){
				continue;
			}
			controller.enter(actionInvocation);
			controller = null;
			return;
		}
		String msg = mapping + " NOT FOUND ON " + this.getServletName();
		context.error(HttpServletResponse.SC_NOT_FOUND, msg);
	
	}

}
