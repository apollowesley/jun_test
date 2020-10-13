package fun.oook.c09;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ImageProtectorFilter 避免图像文件被直接下载
 * <p>
 * 在浏览器中直接输入URL访问图片时， header中的referer是空的
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 16:25
 */
@WebFilter(filterName = "ImageProtectorFilter", urlPatterns = {
        "*.png", "*.jpg", "*.jpeg", "*.gif"
})
public class ImageProtectorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("Load filter: " + this.getClass().getName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final String referer = req.getHeader("referer");
        System.out.println("referer: " + referer);

        if (referer != null) {
            chain.doFilter(request, response);
        } else {
            final HttpServletResponse resp = (HttpServletResponse) response;
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {

    }
}
