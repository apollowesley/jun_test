package tom.test.action;

import java.lang.reflect.InvocationTargetException;

import tom.cocook.annotation.HttpMethod;
import tom.cocook.core.RequestContext;
import tom.cocook.handler.DefaultAnnotationHandler;
import tom.cocook.handler.DefaultNameHandler;
import tom.cocook.handler.HandlerInfo;
import tom.cocook.handler.RequestMapping;

public class HandlerTest {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		long a = System.currentTimeMillis();
		
		DefaultNameHandler defaultNameHandler = new DefaultNameHandler();
		defaultNameHandler.collectApps("/deemo/*", new Demo2Controller());
		RequestMapping re = new RequestMapping("/deemo/deemo", null);
		HandlerInfo handlerinfo =  defaultNameHandler.match(re, new RequestContext());
		System.out.println(handlerinfo.getMethods().get(re).invoke(handlerinfo.getController()));
		System.out.println();
	
		RequestMapping re34  = new RequestMapping("/deemo/dddd", null);
		handlerinfo = defaultNameHandler.match2(re34, new RequestContext());
		System.out.println(handlerinfo.getMethods().get(re34).invoke(handlerinfo.getController()));
		System.out.println();
		
		DefaultAnnotationHandler annotationHandler = new DefaultAnnotationHandler();
		annotationHandler.collectApps(null, new DemoController());
		
		RequestMapping re5  = new RequestMapping("/test", HttpMethod.GET);
		handlerinfo = annotationHandler.match(re5, new RequestContext());
		System.out.println(handlerinfo.getMethods().get(re5).invoke(handlerinfo.getController()));
		System.out.println();
		
		RequestMapping re6  = new RequestMapping("/test", HttpMethod.GET);
		handlerinfo = annotationHandler.match(re6, new RequestContext());
		System.out.println(handlerinfo.getMethods().get(re6).invoke(handlerinfo.getController()));
		System.out.println();
		
		RequestMapping re7  = new RequestMapping("/flt/123", HttpMethod.GET);
		RequestContext requestContext =  new RequestContext();
		handlerinfo = annotationHandler.match(re7, requestContext);
		System.out.println(handlerinfo.getMethods().get(re7).invoke(handlerinfo.getController()));
		System.out.println(requestContext.getUrlMap());
		
		RequestMapping re8  = new RequestMapping("/flt/456", HttpMethod.GET);
		handlerinfo = annotationHandler.match(re8, requestContext);
		System.out.println(handlerinfo.getMethods().get(re8).invoke(handlerinfo.getController()));
		System.out.println(requestContext.getUrlMap());
		
		System.out.println(System.currentTimeMillis()-a);
		
	}
}
