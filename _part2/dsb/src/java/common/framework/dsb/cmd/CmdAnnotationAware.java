package common.framework.dsb.cmd;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import common.framework.command.handler.CommandHandler;
import common.framework.command.server.CommandServer;
import common.framework.command.server.DefaultCommandServer;
import common.framework.dsb.annotation.DSBCmd;
import common.framework.dsb.scanner.AnnotationAware;

public class CmdAnnotationAware implements AnnotationAware {

	private CommandServer cmdServer = null;
	private Map<String, CommandHandler> handlers = new HashMap<String, CommandHandler>();

	public CmdAnnotationAware(int cmdServerPort, int concurrentThreads, long cmdConnectionTimeout) {
		super();
		cmdServer = new DefaultCommandServer(cmdServerPort, concurrentThreads, cmdConnectionTimeout);
	}

	public void start() throws Exception {
		cmdServer.start(handlers);
	}

	public void close() {
		cmdServer.close();
	}

	@Override
	public void annotationAware(Class targetClass, Annotation annotation) {
		if (annotation instanceof DSBCmd) {
			DSBCmd cmd = (DSBCmd) annotation;
			String cmdName = cmd.value();
			try {
				CommandHandler handler = (CommandHandler) targetClass.newInstance();
				handlers.put(cmdName, handler);
			} catch (Throwable e) {
				e.printStackTrace(System.out);
			}
		}// if
	}
}
