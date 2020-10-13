package fun.oook.c11;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;

/**
 * MyAsyncListener
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 19:13
 */
public class MyAsyncListener implements AsyncListener {
    @Override
    public void onComplete(AsyncEvent event) {
        final HttpServletRequest req = (HttpServletRequest) event.getAsyncContext().getRequest();
        System.out.println("async event complete: " + req.getRequestURI());
    }

    @Override
    public void onTimeout(AsyncEvent event) {
        final HttpServletRequest req = (HttpServletRequest) event.getAsyncContext().getRequest();
        System.out.println("async event timeout: " + req.getRequestURI());
    }

    @Override
    public void onError(AsyncEvent event) {
        final HttpServletRequest req = (HttpServletRequest) event.getAsyncContext().getRequest();
        System.out.println("async event error: " + req.getRequestURI());
    }

    @Override
    public void onStartAsync(AsyncEvent event) {
        final HttpServletRequest req = (HttpServletRequest) event.getAsyncContext().getRequest();
        System.out.println("async event start: " + req.getRequestURI());
    }
}
