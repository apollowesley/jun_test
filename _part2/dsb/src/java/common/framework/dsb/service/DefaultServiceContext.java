package common.framework.dsb.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import common.framework.dsb.DynamicService;
import common.framework.dsb.ResourceManager;
import common.framework.dsb.client.DefaultServiceFactory;
import common.framework.dsb.client.ServiceFactory;
import common.framework.dsb.proxy.ServiceProxy;

public class DefaultServiceContext implements ServiceContext {
	private ServiceDescriptor serviceDescriptor = null;
	private Properties prop = new Properties();
	private ServiceConfig serviceConfig = null;
	private ServiceFactory serviceFactory = null;

	public DefaultServiceContext(ServiceConfig serviceConfig, ServiceDescriptor beanDescriptor) {
		super();
		this.serviceConfig = serviceConfig;
		this.serviceDescriptor = beanDescriptor;
		this.serviceFactory = new DefaultServiceFactory();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DynamicService> T lookup(String serviceName) throws Exception {
		return serviceFactory.lookup(serviceName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DynamicService> T lookup(String serviceName, String host, int port) throws Exception {
		return serviceFactory.lookup(serviceName, host, port);
	}

	@Override
	public <T extends DynamicService> T lookup_ha(String serviceName, String haName) throws Exception {
		return serviceFactory.lookup_ha(serviceName, haName);
	}

	@Override
	public <T extends DynamicService> T lookup_ha(String serviceName) throws Exception {
		return serviceFactory.lookup_ha(serviceName);
	}

	@Override
	public ServiceProxy _lookup() throws Exception {
		return serviceFactory._lookup();
	}

	/*
	 * @see
	 * common.framework.dsb.server.ServiceContext#loadConfigProperties(java.
	 * lang.String)
	 */
	public void loadConfigProperties(String propertyFile) throws IOException {
		URL configFlie = getConfigFile(propertyFile);
		InputStream inputStream = configFlie.openStream();
		prop.load(inputStream);
		inputStream.close();
	}

	/*
	 * @see common.framework.dsb.server.ServiceContext#getProperties()
	 */
	public Properties getProperties() {
		return prop;
	}

	/*
	 * @see
	 * common.dynamic.service.server.ServiceContext#getProperty(java.lang.String
	 * )
	 */
	public String getProperty(String name) {
		return prop.getProperty(name);
	}

	/**
	 * @param name
	 * @param defaultVlaue
	 * @return
	 */
	public String getProperty(String name, String defaultVlaue) {
		return prop.getProperty(name, defaultVlaue);
	}

	/*
	 * @see
	 * common.dynamic.service.server.ServiceContext#setProperty(java.lang.String
	 * , java.lang.Object)
	 */
	public void setProperty(String name, String value) {
		prop.setProperty(name, value);
	}

	/*
	 * @see
	 * common.dynamic.service.server.ServiceContext#getConfigFile(java.lang.
	 * String)
	 */
	public URL getConfigFile(String fileName) throws IOException {
		return getFile("config", fileName);
	}

	/*
	 * @see
	 * common.dynamic.service.server.ServiceContext#getResourceFile(java.lang
	 * .String)
	 */
	public URL getResourceFile(String fileName) throws IOException {
		return getFile("resource", fileName);
	}

	/*
	 * @see
	 * common.dynamic.service.server.ServiceContext#getString(java.lang.String)
	 */
	public String getString(String key) {
		ResourceManager resourceManager = ResourceManager.getResourceManager(serviceDescriptor.serviceName.trim());
		return resourceManager.getString(key);
	}

	/*
	 * @see common.framework.dsb.server.ServiceContext#getServiceConfig()
	 */
	public ServiceConfig getServiceConfig() {
		return serviceConfig;
	}

	public ServiceDescriptor getServiceDescriptor() {
		return serviceDescriptor;
	}

	private URL getFile(String fileType, String fileName) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(fileType).append(File.separator);
		sb.append(getShortName(serviceDescriptor.serviceName.trim())).append(File.separator);
		sb.append(fileName);
		return new File(sb.toString()).toURI().toURL();
	}

	private String getShortName(String serviceName) {
		return serviceName.substring(serviceName.indexOf("/") + 1);
	}
}