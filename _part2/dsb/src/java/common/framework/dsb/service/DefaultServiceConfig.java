package common.framework.dsb.service;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXBException;

import common.framework.dsb.ServiceException;
import common.framework.dsb.annotation.DSBService;
import common.framework.dsb.scanner.AnnotationAware;

/**
 * This class search and maintain all service descriptor in the CLASSPATH,
 * 
 * @author David Yuan
 * 
 */
public class DefaultServiceConfig implements ServiceConfig, AnnotationAware {

	private Map<String, ServiceDescriptor> serviceList = new Hashtable<String, ServiceDescriptor>();
	private Locale locale = null;
	private String serviceDir = null;

	/**
	 * Constructor
	 * 
	 * @param classLoader
	 *            class loader
	 * @throws IOException
	 * @throws IOException
	 * @throws JAXBException
	 */
	public DefaultServiceConfig(Locale locale, String serviceDir) {
		this.locale = locale;
		this.serviceDir = serviceDir;
	}

	@Override
	public void start() throws Exception {

	}

	@Override
	public void close() throws Exception {
		serviceList.clear();
		System.out.println(new Date() + " ServiceConfig was closed.");
	}

	public String getServiceDir() {
		return serviceDir;
	}

	/*
	 * @see common.framework.dsb.server.ServiceConfig#getLocale()
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Get service descriptor by service name
	 * 
	 * @param serviceName
	 *            the name of service
	 * @return Service Descriptor
	 */
	public ServiceDescriptor getService(String serviceName) {
		return serviceList.get(serviceName);
	}

	/**
	 * List all service descriptor
	 * 
	 * @return
	 */
	public List<ServiceDescriptor> listService() {
		return new ArrayList<ServiceDescriptor>(serviceList.values());
	}

	/**
	 * Add one Service Descriptor
	 * 
	 * @param serviceDescriptor
	 * @throws ServiceException
	 */
	public synchronized void addService(ServiceDescriptor serviceDescriptor) {
		serviceList.put(serviceDescriptor.serviceName, serviceDescriptor);
	}

	@Override
	public void annotationAware(Class targetServiceClass, Annotation annotation) {
		if (annotation instanceof DSBService) {
			DSBService serviceAnnotation = (DSBService) annotation;
			ServiceDescriptor descriptor = new ServiceDescriptor();
			descriptor.serviceName = serviceAnnotation.name();
			descriptor.startup = serviceAnnotation.startup();
			Class[] interfaces = targetServiceClass.getInterfaces();
			if (interfaces != null && interfaces.length > 0) {
				// 选取第一interface作为被发布的interface
				Class interfaceClass = interfaces[0];
				descriptor.interfaceClassName = interfaceClass.getName();
				descriptor.serviceClassName = targetServiceClass.getName();
				addService(descriptor);
			}
		}
	}

}
