package fun.oook.c14;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;

/**
 * DynRegListener
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/12 11:48
 */
@WebListener
public class DynRegListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("监听器启动 " + this.getClass().getName());
        final ServletContext context = sce.getServletContext();

        Servlet firstServlet = null;
        try {
            firstServlet = context.createServlet(FirstServlet.class);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        if (firstServlet != null) {
            ((FirstServlet) firstServlet).setName("Dynamically registered servlet");
            System.out.println("动态加载servlet - " + firstServlet.getClass().getName());
        }

        final ServletRegistration.Dynamic dynamic = context.addServlet("firstServlet", firstServlet);
        dynamic.addMapping("/c14/dynamic");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
