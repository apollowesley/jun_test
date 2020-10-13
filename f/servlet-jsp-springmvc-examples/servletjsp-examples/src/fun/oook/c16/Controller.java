package fun.oook.c16;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/12 18:20
 */
public interface Controller {

    String handleRequest(HttpServletRequest req, HttpServletResponse resp);
}
