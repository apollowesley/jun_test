package fun.oook.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Joey
 * @version 1.0
 * @since 2020/4/10 10:21
 */
public class CharsetEncodingFilter implements Filter {

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        this.encoding = filterConfig.getInitParameter("encoding");
        System.out.println(this.getClass().getName() + "初始化 -- " + encoding);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println(this.getClass().getName() + " 结束!");
    }
}
