package fun.oook.c08;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;

/**
 * AppListener
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 14:16
 */
@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext context = sce.getServletContext();

        final Map<String, String> countries = new HashMap<>();
        countries.put("ca", "Canada");
        countries.put("us", "United States");
        context.setAttribute("countries", countries);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
