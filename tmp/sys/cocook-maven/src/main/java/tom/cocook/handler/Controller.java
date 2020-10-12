package tom.cocook.handler;

import java.io.IOException;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tom.cocook.interceptor.ActionInvocation;

public interface Controller {

	public static Logger logger = LoggerFactory.getLogger("HandlerController");
	
	public abstract void enter(ActionInvocation actionInvocation) throws ServletException, IOException;
}
