package fun.oook.c11;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * AsyncCompleteServlet
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 18:56
 */
@WebServlet(name = "AsyncCompleteServlet",
        urlPatterns = {"/c11/asyncComplete"},
        asyncSupported = true)
public class AsyncCompleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        final PrintWriter writer = resp.getWriter();
        writer.println("<html><head><title>" +
                "Async Servlet</title></head>");
        writer.println("<body><div id='progress'></div>");

        final AsyncContext asyncContext = req.startAsync();
        asyncContext.addListener(new MyAsyncListener());

        asyncContext.setTimeout(20000);
        asyncContext.start(() -> {
            System.out.println("new thread:" + Thread.currentThread());

            for (int i = 0; i < 10; i++) {
                writer.println("<script>");
                writer.println("document.getElementById('progress').innerHTML = '" +
                        (i * 10) + "% complete'");
                writer.println("</script>");
                writer.flush();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            writer.println("<script>");
            writer.println("document.getElementById('progress').innerHTML = 'DONE'");
            writer.println("</script>");
            writer.println("</body></html>");

            asyncContext.complete();
        });
    }
}
