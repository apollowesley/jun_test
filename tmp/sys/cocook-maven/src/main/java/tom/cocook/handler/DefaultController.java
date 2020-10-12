package tom.cocook.handler;

import java.io.IOException;

import javax.servlet.ServletException;

import tom.cocook.core.ControllerInvoke;
import tom.cocook.core.RequestContext;
import tom.cocook.interceptor.ActionInvocation;
import tom.cocook.view.View;
import tom.cocook.view.ViewFactory;

public final class DefaultController implements Controller {

	@Override
	public void enter(ActionInvocation actionInvocation) throws ServletException, IOException {
		long a = System.currentTimeMillis();
		String module = actionInvocation.getParaMap().get("module");
		RequestContext context = actionInvocation.getRequestContext();
		try {
			ControllerInvoke controllerInvoke = new ControllerInvoke(actionInvocation);
			controllerInvoke.paserFieldAnotation();
			Object obj = controllerInvoke.parserControllerMethod();
			String page = (String) obj;
			if (page != null && !page.isEmpty()) {
				 /*模板解析*/
				View view = ViewFactory.getView();
				if(view!=null){
					view.render(context.getRealPage(page));
				}else{
					context.forward(page, true);
				}
			}
			long last = (System.currentTimeMillis()-a);
			String msg = module+" - " +last +" - ControllerModel("+actionInvocation.getMethod().toString()+
							")->:"+	context.getRequest().getRemoteHost();
							
			logger.info(msg);
		} catch(Exception e){
			logger.error(e.getMessage(), e);
		}finally {
			actionInvocation.recycle();
			actionInvocation = null;
		}
	}

}
