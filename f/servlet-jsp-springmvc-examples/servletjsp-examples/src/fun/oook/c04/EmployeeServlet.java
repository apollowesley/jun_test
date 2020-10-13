package fun.oook.c04;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * EmployeeServlet
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 12:11
 */
@WebServlet(urlPatterns = {"/c04/employee"})
public class EmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Address address = new Address();
        address.setStreetName("坂田街道");
        address.setStreetNumber("黄金山一巷1号");
        address.setCity("深圳市");
        address.setState("广东省");
        address.setZipCode("SZ");
        address.setCity("中国");

        final Employee employee = new Employee();
        employee.setId(9527);
        employee.setName("刘明");
        employee.setAddress(address);

        req.setAttribute("employee", employee);

        final Map<String, String> capitals = new HashMap<>();
        capitals.put("China", "Beijing");
        capitals.put("Austria", "Vienna");
        capitals.put("Australia", "Canberra");
        capitals.put("Canada", "Ottawa");

        req.setAttribute("capitals", capitals);

        final RequestDispatcher dispatcher = req.getRequestDispatcher("/c04/employee.jsp");
        dispatcher.forward(req, resp);
    }
}
