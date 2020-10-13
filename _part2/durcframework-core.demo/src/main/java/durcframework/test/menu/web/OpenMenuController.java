package durcframework.test.menu.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 菜单跳转
 * @author hc.tang
 * 2013年12月13日
 *
 */
@Controller
public class OpenMenuController {

	@RequestMapping("/openMenu.do")
	public void openMenu(@RequestParam("url") String url
		,HttpServletRequest request
		,HttpServletResponse response) {
		
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e2) {
			e2.printStackTrace();
			try {
				response.sendRedirect("/index.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}
}
