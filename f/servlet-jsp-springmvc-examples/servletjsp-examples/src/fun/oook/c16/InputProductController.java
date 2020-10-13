package fun.oook.c16;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * InputProductController
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/12 18:21
 */
public class InputProductController implements Controller {
    @Override
    public String handleRequest(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println(req.getRequestURI() + " - " + this.getClass().getName());
        return "/WEB-INF/c16/productForm.jsp";
    }
}
