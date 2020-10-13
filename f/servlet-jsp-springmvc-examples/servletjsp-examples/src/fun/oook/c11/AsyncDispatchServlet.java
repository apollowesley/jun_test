package fun.oook.c11;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AsyncDispatchServlet
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 18:32
 */
@WebServlet(name = "AsyncDispatchServlet",
        urlPatterns = {"/c11/asyncDispatch"},
        asyncSupported = true)
public class AsyncDispatchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("mainThread", Thread.currentThread().getName());

        final AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(5000);
        asyncContext.start(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 该任务不是在主线程执行，所以mainThread，workerThread将会不同
            req.setAttribute("workerThread", Thread.currentThread().getName());

            asyncContext.dispatch("/c11/threadNames.jsp");
        });
    }
}
