package fun.oook.c09;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LoggingFilter
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 15:45
 */
@WebFilter(filterName = "LoggingFilter", urlPatterns = {"/*"},
        initParams = {
                @WebInitParam(name = "logFilename", value = "log.txt"),
                @WebInitParam(name = "prefix", value = "URI: ")
        }, asyncSupported = true)
public class LoggingFilter implements Filter {

    private PrintWriter logger;
    private String prefix;

    final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        prefix = filterConfig.getInitParameter("prefix");
        final String logFilename = filterConfig.getInitParameter("logFilename");
        final String appPath = filterConfig.getServletContext().getRealPath("/");
        // without path info in logFilename, the log file will created in $TOMCAT_HOME/bin

        final File logFile = new File(appPath + File.separator + "logs", logFilename);
        System.out.println("logFilename: " + logFile.getAbsolutePath());

        try {
            if (!logFile.exists()){
                final String parent = logFile.getParent();
                Files.createDirectories(Paths.get(parent));
            }

            logger = new PrintWriter(logFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("LoggingFilter.doFilter");
        final HttpServletRequest req = (HttpServletRequest) request;

        logger.println(LocalDateTime.now().format(format) + " " + prefix + req.getRequestURI());
        logger.flush();

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("destroying filter " + this.getClass().getSimpleName());
        if (logger != null) {
            logger.close();
        }
    }
}
