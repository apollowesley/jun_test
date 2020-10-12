package org.openkoala.businesslog;

import org.aspectj.lang.JoinPoint;
import org.openkoala.businesslog.utils.ThreadLocalBusinessLogContext;

import javax.inject.Inject;
import java.util.Map;
import java.util.logging.Logger;

/**
 * User: zjzhai
 * Date: 11/28/13
 * Time: 11:38 AM
 */
public class BusinessLogInterceptor {

    private static Logger logger = Logger.getLogger(BusinessLogInterceptor.class.toString());

    /**
     * 业务方法返回值，在模板中使用的key
     */
    public final static String BUSINESS_METHOD_RETURN_VALUE_KEY = "methodReturn";

    @Inject
    private AbstractBusinessLogBuild businessLogBuild;

    @Inject
    private BusinessLogExporter businessLogExporter;


    public void logAfter(JoinPoint joinPoint, Object result) {


        System.out.println(joinPoint.getSignature());
        Map<String, Object> context = createDefaultContext(joinPoint, result);



        //在此实现多线程
        businessLogExporter.export(businessLogBuild.build());
    }

    private Map<String, Object> createDefaultContext(JoinPoint joinPoint, Object result) {

        Map<String, Object> context = ThreadLocalBusinessLogContext.get();

        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            context.put(i + "", args[i]);
        }

        context.put(BUSINESS_METHOD_RETURN_VALUE_KEY, result);
        return context;
    }


}
