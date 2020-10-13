package fun.oook.c08;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * PerfStatListener
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 15:19
 */
@WebListener
public class PerfStatListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        final ServletRequest request = sre.getServletRequest();

        request.setAttribute("start", System.nanoTime());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        final ServletRequest request = sre.getServletRequest();

        final Long start = (Long) request.getAttribute("start");
        final long end = System.nanoTime();

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final String uri = httpServletRequest.getRequestURI();

        System.out.println("time taken to execute " + uri + ": " + ((end - start) / 1000) + " microseconds");
    }
}
