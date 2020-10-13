package fun.oook.c02.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * CookieInfoServlet
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 00:18
 */
@WebServlet(name = "CookieInfoServlet", urlPatterns = {"/cookieInfo"})
public class CookieInfoServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
            IOException {

        Cookie[] cookies = request.getCookies();
        StringBuilder styles = new StringBuilder();
        styles.append(".title {");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                if ("titleFontSize".equals(name)) {
                    styles.append("font-size:").append(value).append(";");
                } else if ("titleFontWeight".equals(name)) {
                    styles.append("font-weight:").append(value).append(";");
                } else if ("titleFontStyle".equals(name)) {
                    styles.append("font-style:").append(value).append(";");
                }
            }
        }
        styles.append("}");
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.print("<html><head>" + "<title>Cookie Info</title>"
                + "<style>" + styles.toString() + "</style>"
                + "</head><body>" + PreferenceServlet.MENU
                + "<div class='title'>"
                + "Session Management with Cookies:</div>");
        writer.print("<div>");

        // cookies will be null if there's no cookie
        if (cookies == null) {
            writer.print("No cookie in this HTTP response.");
        } else {
            writer.println("<br/>Cookies in this HTTP response:");
            for (Cookie cookie : cookies) {
                writer.println("<br/>" + cookie.getName() + ":"
                        + cookie.getValue());
            }
        }
        writer.print("</div>");
        writer.print("</body></html>");
    }
}
