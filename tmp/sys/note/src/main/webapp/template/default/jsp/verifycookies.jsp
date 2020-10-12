<%@ page language="java" import="java.util.*,java.net.URLDecoder,com.javasrc.Login" pageEncoding="UTF-8"%>
<script type="text/javascript">
<%
String usernamecookies = "";
String passwordcookies = "";
String rememberId = "";
Cookie[] cookies = request.getCookies();
//System.out.println(cookies.length);
if (cookies != null) {
	for (int i = 0; i < cookies.length; i++) {
		if (cookies[i].getName().equals("cookie_user")&&!cookies[i].getValue().equals("-1")) {
			String value = cookies[i].getValue();
			if (value != null && !"".equals(value)) {
				usernamecookies = cookies[i].getValue().split("-")[0];
				if (cookies[i].getValue().split("-")[1] != null
						&& !cookies[i].getValue().split("-")[1]
								.equals("null")) {
					passwordcookies = cookies[i].getValue().split("-")[1];
				}
				rememberId = cookies[i].getValue().split("-")[2];
			}
			request.setAttribute("usernamecookies", URLDecoder.decode(usernamecookies, "UTF-8"));
			request.setAttribute("passwordcookies", passwordcookies);
			System.out.println(cookies[i].getMaxAge() + " "+ URLDecoder.decode(usernamecookies, "UTF-8") + " " + passwordcookies);
			out.println("var f = document.createElement(\"form\");");
			out.println("document.body.appendChild(f);");
			out.println("var i = document.createElement(\"input\");");
			out.println("i.type = \"hidden\";");
			out.println("i.value = \""+URLDecoder.decode(usernamecookies, "UTF-8")+"\";");
			out.println("i.name = \"username\";");
			out.println("var j = document.createElement(\"input\");");
			out.println("j.type = \"hidden\";");
			out.println("j.value = \""+passwordcookies+"\";");
			out.println("j.name = \"password\";");
			out.println("var r = document.createElement(\"input\");");
			out.println("r.type = \"hidden\";");
			out.println("r.value = \""+rememberId+"\";");
			out.println("r.name = \"rememberId\";");
			out.println("f.appendChild(i);");
			out.println("f.appendChild(j);");
			out.println("f.appendChild(r);");
			out.println("f.action = \"Login.yy\";");
		}
	}
}
%>
f.method="POST";
f.submit();
</script>
