package tangjl.async;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WebLogServlet
 */
@WebServlet(urlPatterns = { "/WebLogServlet" }, asyncSupported = true)
public class WebLogServlet extends HttpServlet {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -260157400324419618L;

	/**
	 * 将客户端注册到监听 Logger 的消息队列中
	 */
	@Override
	protected void doGet(final HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html;charset=UTF-8");
		res.setHeader("Cache-Control", "private");
		res.setHeader("Pragma", "no-cache");
		req.setCharacterEncoding("UTF-8");
		final PrintWriter writer = res.getWriter();
		writer.println("异步之前输出的内容。");
		writer.flush();

		// 开始异步调用，获取对应的AsyncContext。
		final AsyncContext asyncContext = req.startAsync();
		// 设置超时时间，当超时之后程序会尝试重新执行异步任务，即我们新起的线程。
		asyncContext.setTimeout(10 * 60 * 1000L);
		// 新起线程开始异步调用，start方法不是阻塞式的，它会新起一个线程来启动Runnable接口，之后主程序会继续执行
		asyncContext.start(new Runnable() {

			@Override
			public void run() {
				try {
					String path = req.getServletContext().getRealPath("user");
					File file = new File(path);
					FileReader fis = new FileReader(file);
					BufferedReader br = new BufferedReader(fis);
					for (String s = br.readLine(); s != null; s = br.readLine()) {
						writer.println(s + "<br/>");
						writer.flush();
						Thread.sleep(50);
					}
					fis.close();
					br.close();
					// 异步调用完成，如果异步调用完成后不调用complete()方法的话，异步调用的结果需要等到设置的超时
					// 时间过了之后才能返回到客户端。
					asyncContext.complete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		writer.println("可能在异步调用前输出，也可能在异步调用之后输出，因为异步调用会新起一个线程。");
		writer.flush();
	}
}