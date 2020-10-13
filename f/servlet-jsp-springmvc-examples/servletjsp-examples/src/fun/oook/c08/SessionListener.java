package fun.oook.c08;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SessionListener
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 14:46
 */
@WebListener
public class SessionListener implements HttpSessionListener, ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext context = sce.getServletContext();

        context.setAttribute("userCounter", new AtomicInteger());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        final HttpSession session = se.getSession();
        final ServletContext context = session.getServletContext();

        final AtomicInteger userCounter = (AtomicInteger) context.getAttribute("userCounter");
        final int userCount = userCounter.incrementAndGet();

        System.out.println("-------------userCount incremented to: " + userCount);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        final HttpSession session = se.getSession();
        final ServletContext context = session.getServletContext();

        final AtomicInteger userCounter = (AtomicInteger) context.getAttribute("userCounter");
        final int userCount = userCounter.decrementAndGet();

        System.out.println("-------------userCount decremented to: " + userCount);
    }
}
