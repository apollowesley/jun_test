package fun.oook.c02.hiddenfields;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * CustomerServlet
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/10 22:39
 */
@WebServlet(name = "CustomerServlet", urlPatterns = {
        "/customer", "/editCustomer", "/updateCustomer"
})
public class CustomerServlet extends HttpServlet {
    private List<Customer> customers = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        final Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setName("李世民");
        customer1.setCity("长安");

        customers.add(customer1);

        final Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setName("嬴政");
        customer2.setCity("咸阳");
        customers.add(customer2);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String uri = req.getRequestURI();
        if (uri.endsWith("/customer")) {
            sendCustomerList(resp);
        } else if (uri.endsWith("/editCustomer")) {
            sendEditCustomerForm(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int customerId = 0;
        try {
            customerId = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        final Customer customer = getCustomer(customerId);
        if (customer != null) {
            customer.setName(req.getParameter("name"));
            customer.setCity(req.getParameter("city"));
        }

//        req.getRequestDispatcher("/customer").forward(req, resp);
        sendCustomerList(resp);
    }

    private void sendEditCustomerForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        int customerId = 0;
        try {
            customerId =
                    Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Customer customer = getCustomer(customerId);

        if (customer != null) {
            writer.println("<html><head><meta charset='UTF-8'>"
                    + "<title>Edit Customer</title></head>"
                    + "<body><h2>Edit Customer</h2>"
                    + "<form method='post' "
                    + "action='updateCustomer'>");
            writer.println("<input type='hidden' name='id' value='"
                    + customerId + "'/>");
            writer.println("<table>");
            writer.println("<tr><td>Name:</td><td>"
                    + "<input name='name' value='" +
                    customer.getName().replaceAll("'", "&#39;")
                    + "'/></td></tr>");
            writer.println("<tr><td>City:</td><td>"
                    + "<input name='city' value='" +
                    customer.getCity().replaceAll("'", "&#39;")
                    + "'/></td></tr>");
            writer.println("<tr>"
                    + "<td colspan='2' style='text-align:right'>"
                    + "<input type='submit' value='Update'/></td>"
                    + "</tr>");
            writer.println("<tr><td colspan='2'>"
                    + "<a href='customer'>Customer List</a>"
                    + "</td></tr>");
            writer.println("</table>");
            writer.println("</form></body>");
        } else {
            writer.println("No customer found");
        }
    }

    private Customer getCustomer(int customerId) {
        for (Customer customer : customers) {
            if (customer.getId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    private void sendCustomerList(HttpServletResponse resp) throws IOException {
        System.out.println(customers.toString());
        final String contextPath = this.getServletContext().getContextPath();

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<html><head><meta charset='UTF-8'><title>Customers</title></head>"
                + "<body><a href='" + contextPath + "/customer'><h2>Customers</h2></a>");
        writer.println("<ul>");
        for (Customer customer : customers) {
            writer.println("<li>" + customer.getName()
                    + "(" + customer.getCity() + ") ("
                    + "<a href='editCustomer?id=" + customer.getId()
                    + "'>edit</a>)");
        }
        writer.println("</ul>");
        writer.println("</body></html>");
    }
}
