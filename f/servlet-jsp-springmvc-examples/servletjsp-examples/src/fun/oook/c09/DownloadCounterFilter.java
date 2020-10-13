package fun.oook.c09;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * DownloadCounterFilter
 * <p>
 * urlPatterns = {"/*"} 设定为相关资源匹配的规则即可实现下载统计
 * 如 urlPatterns = {"/*.pdf", "/*.mp3"}
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 16:58
 */
@WebFilter(filterName = "DownloadCounterFilter", urlPatterns = {"/*"}, asyncSupported = true)
public class DownloadCounterFilter implements Filter {

    final ExecutorService es = Executors.newSingleThreadExecutor();

    Properties downloadLog;

    File logFile;

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("Load filter: " + this.getClass().getName());

        final String appPath = filterConfig.getServletContext().getRealPath("/");
        logFile = new File(appPath + File.separator + "logs", "downloadLog.txt");

        if (!logFile.exists()) {
            try {
                if (logFile.createNewFile()) {
                    System.out.println("create file failure - " + logFile.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        downloadLog = new Properties();
        try {
            downloadLog.load(new FileReader(logFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final String uri = req.getRequestURI();

        es.execute(() -> {
            final String property = downloadLog.getProperty(uri);
            if (property == null) {
                downloadLog.setProperty(uri, "1");
            } else {
                int count = 0;
                try {
                    count = Integer.parseInt(property);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                count++;
                downloadLog.setProperty(uri, Integer.toString(count));
            }

            try {
                downloadLog.store(new FileWriter(logFile), "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        es.shutdown();
    }
}
