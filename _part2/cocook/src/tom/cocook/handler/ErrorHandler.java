package tom.cocook.handler;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;

import tom.cocook.core.CocookException;
import tom.cocook.core.RequestContext;
import tom.cocook.jdbc.DBException;
import tom.cocook.log.Logger;
import tom.cocook.log.LoggerFactory;

public class ErrorHandler {

	private static Logger logger = LoggerFactory.getLog(ErrorHandler.class);

	/**
	 * context 做 错误页面的扩展
	 * _e 做 Exception 的筛选
	 * @param context
	 * @param _e
	 */
	public static void Handler(RequestContext context, Throwable _e) throws ServletException, IOException{
		StringWriter writer = new StringWriter();
		do {
			if(_e instanceof CocookException){
				_e.printStackTrace(new PrintWriter(writer));
				logger.error(writer.toString());
				throw new ServletException(_e);
			}
			
			if(_e instanceof DBException){
				_e.printStackTrace(new PrintWriter(writer));
				logger.error(writer.toString());
				throw new ServletException(_e);
			}
			
			if(_e.getCause()==null){
				throw new ServletException(_e);
			}
		} while ((_e = _e.getCause()) != null);
	}
}
