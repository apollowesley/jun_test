package tom.cocook.handler;

import java.io.IOException;

import javax.servlet.ServletException;

import tom.cocook.core.ControllerInvoke;
import tom.cocook.core.RequestContext;
import tom.cocook.interceptor.ActionInvocation;
import tom.cocook.view.View;
import tom.cocook.view.ViewFactory;

public final class DefaultHandlerController implements HandlerController {

	@Override
	public void enter(ActionInvocation actionInvocation) throws ServletException, IOException {
		RequestContext context = actionInvocation.getRequestContext();
		try {
			ControllerInvoke controllerInvoke = new ControllerInvoke(actionInvocation);
			controllerInvoke.paserFieldAnotation();
			Object obj = controllerInvoke.getRuturnType();
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
			String msg = "ControllerModel("+actionInvocation.getAppControl().getClass().getName()+")->IP:"+
							context.getRequest().getRemoteHost();
			logger.info(msg);
		} catch(Exception e){
			ErrorHandler.Handler(context, e);
		}finally {
			actionInvocation.recycle();
			actionInvocation = null;
		}
	}

}
