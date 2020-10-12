package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdminDaoImpl;
import dao.CostDaoImpl;
import entity.Admin;
import entity.Cost;

public class MainServlet extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		获取网名
		String path = req.getServletPath();  
//		System.out.println(path); // 输出网名，供三少观察
		
//		根据网名调用相应的 service 方法
		if("/findCost.do".equals(path)) {
			findAll(req, resp);
		} else if("/toAddCost.do".equals(path)) {
			toAddCost(req, resp);
		} else if("/addCost.do".equals(path)) {
			addCost(req, resp);
		} else if("/toLogin.do".equals(path)) {
			toLogin(req, resp);
		} else if("/login.do".equals(path)) {
			login(req, resp);
		} else if("/toIndex.do".equals(path)) {
			toIndex(req, resp);
		} else {
			System.out.println("查无此页");
		}
	}
	
//	打开主页面
	private void toIndex(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/index.jsp").forward(req, resp);;
	}
	
//	对登录的信息进行逻辑判断
		 
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO 获取登录页面的信息，在数据库中查找这些信息是否存在，存在就重定向到主页，否则就重定向到登录页面
		String adminCode = req.getParameter("adminCode");
		String password = req.getParameter("password");
//		System.out.println(adminCode);
//		System.out.println(password);
//		System.out.println("到了login.do页面");
		
		Admin a = new AdminDaoImpl().findByAdminCode(adminCode);
		if(null == a) {
			// 用户名错误 -->> 给出错误提示并重定向到登录页面，也可是使用转发到主页面的jsp文件
			req.setAttribute("error", "用户名错误");
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, resp);
		} else if(!a.getPassword().equals(password)) {
			// 密码错误  -->>  给出错误提示并重定向到登录页面，也可以转发到主页面的jsp文件
			req.setAttribute("error", "密码错误");
//			resp.sendRedirect("toLogin.do");  // 虽然可是实现效果，但是重定向时就不能将request传过去
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, resp);
		} else {
			// 用户名和密码都正确  -->>  重定向到主页面
			resp.sendRedirect("toIndex.do");
		}
	}
	
//	打开登录页面
	private void toLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, resp);;
	}
	
//	资费增加
	private void addCost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		设置读取式时的编码格式
		req.setCharacterEncoding("utf-8");
//		从浏览器读取参数
		String name = req.getParameter("name");
		String costType = req.getParameter("costType");
		String baseDuration = req.getParameter("baseDuration");
		String baseCost = req.getParameter("baseCost");
		String unitCost = req.getParameter("unitCost");
		String descr = req.getParameter("descr");
		
//		打印参数
		System.out.println(name);
		System.out.println(costType);
		System.out.println(baseDuration);
		System.out.println(baseCost);
		System.out.println(unitCost);
		System.out.println(descr);
		
//		将读取到的参数保存到一个对象中
		Cost c = new Cost();
		c.setName(name);
		c.setCostType(costType);
		if(baseDuration != null && !baseDuration.equals("")) {
			c.setBaseDuration(new Double(baseDuration));
		} else {
			c.setBaseDuration(0.0);
		}
		if(baseCost != null && !baseCost.equals("")) {
			c.setBaseCost(Double.parseDouble(baseCost));
		} else {
			c.setBaseCost(0.0);
		}
		if(unitCost != null && !unitCost.equals("")) {
			c.setUnitCost(Double.parseDouble(unitCost));
		} else {
			c.setUnitCost(0.0);
		}
//		将读取到的数据存储到数据库中
		new CostDaoImpl().save(c);
//		数据保存后就立刻重定向到 资费查询页面
		resp.sendRedirect("findCost.do");
		
		
//		req.getRequestDispatcher("WEB-INF/cost/add.jsp").forward(req, resp);
	}
	
//	打开资费增加
	private void toAddCost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/cost/add.jsp").forward(req, resp);
	}

//	资费查询
	private void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		获取数据
		List<Cost> list = new CostDaoImpl().findAll();
//		将数据存储到request中
		req.setAttribute("costs", list);
//		获取转发器，并将request和response传到指定的jsp
		req.getRequestDispatcher("WEB-INF/cost/find.jsp").forward(req, resp);
	}
	
}
