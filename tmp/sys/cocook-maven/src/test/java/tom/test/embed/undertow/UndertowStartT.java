package tom.test.embed.undertow;

import javax.servlet.ServletException;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.servlet.api.ServletInfo;
import tom.cocook.UndertowStart;

public class UndertowStartT {
	
	public static void main(String[] args) throws ServletException {
		args = new String[2];
		args[0] = "-p";
		args[1] = "7070";
		
		ServletInfo statViewServlet = Servlets.servlet(StatViewServlet.class)
		.addMapping("/druid/*")
		.addInitParam("loginUsername", "admin")
		.addInitParam("loginPassword", "admin123")
		.addInitParam("resetEnable", "false");
		FilterInfo webStatFilter = new FilterInfo("WebStatFilter", WebStatFilter.class).addInitParam("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		
		new UndertowStart(UndertowStartT.class).init(args)
		.addCocookServlet() //添加cocookservlet
		.addServlet(statViewServlet) //添加其他 servlet
		.addFilter(webStatFilter) // 添加 filter
		//.addlistener(WebEmbedListener.class) // 添加listener
		.start();
	}

}
