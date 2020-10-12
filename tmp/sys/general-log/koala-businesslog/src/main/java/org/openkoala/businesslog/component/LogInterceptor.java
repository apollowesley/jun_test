package org.openkoala.businesslog.component;

import org.aspectj.lang.JoinPoint;

/**
 * 日志拦截器
 * @author xmfang
 *
 */
public class LogInterceptor {

	private static final String METHOD_NAME_SEPARATOR = ":";
	private static final String ARG_SEPARATOR = ",";
	
	//@Inject
	private LogManager logManager;
	
	public void logAfter(JoinPoint call) {
        System.out.println(call);
       // logManager.logging(generateMethodInfo(call));
	}
	
	private MethodInfo generateMethodInfo(JoinPoint call) {
		MethodInfo result = new MethodInfo();
		
		String className = call.getSignature().getDeclaringTypeName();
		String methodName = call.getSignature().getName();
		
		String argNames = "";
		for (Object arg : call.getArgs()) {
			argNames = argNames + arg.getClass().getSimpleName() + ARG_SEPARATOR;
		}
		argNames = argNames.substring(0, argNames.length() -1);
		
		String methodLocation = className + METHOD_NAME_SEPARATOR
				+ methodName + METHOD_NAME_SEPARATOR + argNames;
		
		result.setClassName(className);
		result.setMethodName(methodName);
		result.setMethodLocation(methodLocation);
		result.setArgumentValues(call.getArgs());
		
		return result;
	}
	
}
