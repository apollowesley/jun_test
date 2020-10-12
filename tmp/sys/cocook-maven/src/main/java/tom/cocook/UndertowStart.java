package tom.cocook;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowLogger;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.servlet.api.ListenerInfo;
import io.undertow.servlet.api.ServletInfo;

import java.util.EventListener;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import tom.cocook.handler.CocookServlet;
import tom.net.Helper;

public class UndertowStart {
	private  Class<?> clazzLoader;
	private int port = 80;
	private String host = "localhost";
	private String ctxpath ="/";
	private DeploymentInfo servletBuilder;
	
	public UndertowStart(Class<?> clazz) {
		clazzLoader = clazz;
	}
	
	public UndertowStart(DeploymentInfo servletBuilder) {
		super();
		this.servletBuilder = servletBuilder;
	}

	public UndertowStart init(String[] args){
		port = Helper.option(args, "-p", 80);
		ctxpath = Helper.option(args, "-ctxpath", "/");
		host = Helper.option(args, "-h", "127.0.0.1");
		servletBuilder = Servlets.deployment()
				.setClassLoader(clazzLoader.getClassLoader())
				.setContextPath(ctxpath).setDeploymentName("");
		return this;
	}
	
	public UndertowStart addServlet(Class<? extends Servlet> clazz, String mapping, int loadOnStartup){
		ServletInfo servletInfo =  Servlets.servlet(clazz).addMapping(mapping).setLoadOnStartup(loadOnStartup);
		servletBuilder.addServlets(servletInfo);
		return this;
	}
	
	public UndertowStart addServlet(ServletInfo servletInfo){
		servletBuilder.addServlet(servletInfo);
		return this;
	}
	
	public UndertowStart addlistener(ListenerInfo listenerInfo){
		servletBuilder.addListener(listenerInfo); 
		return this;
	}
	
	public UndertowStart addCocookServlet(){
		ServletInfo servletInfo =  Servlets.servlet(CocookServlet.class).addMapping("/").setLoadOnStartup(1);
		servletBuilder.addServlets(servletInfo);
		return this;
	}
	
	public UndertowStart addlistener(Class<? extends EventListener> clazz){
		servletBuilder.addListener(new ListenerInfo(clazz));
		return this;
	}
	
	public UndertowStart addFilter(FilterInfo filterInfo){
		servletBuilder.addFilter(filterInfo);
		return this;
	}
	
	public UndertowStart addFilter(Class<? extends Filter> filterClass){
		servletBuilder.addFilter(new FilterInfo(filterClass.getName(), filterClass));
		return this;
	}
	
	public void start() throws ServletException{
		DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
		manager.deploy();
		HttpHandler servletHandler = manager.start();
		PathHandler path = Handlers.path(Handlers.redirect(ctxpath)).addPrefixPath(ctxpath, servletHandler);
		Undertow server = Undertow.builder().addHttpListener(port, host).setHandler(path).build();
		server.start();
		UndertowLogger.ROOT_LOGGER.infof("Configuring listener with protocol %s for interface %s and port %s", "http", host, port);
	}
	
}