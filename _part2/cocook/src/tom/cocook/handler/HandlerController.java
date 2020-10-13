package tom.cocook.handler;

import java.io.IOException;

import javax.servlet.ServletException;

import tom.cocook.interceptor.ActionInvocation;
import tom.cocook.log.Logger;
import tom.cocook.log.LoggerFactory;

public interface HandlerController {

	public static Logger logger = LoggerFactory.getLog(HandlerController.class);
	
	public abstract void enter(ActionInvocation actionInvocation) throws ServletException, IOException;
}
