package common.framework.dsb.service;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import common.framework.dsb.DynamicService;
import common.framework.dsb.proxy.ServiceProxy;

/**
 * Service context
 * 
 * @author David Yuan
 * 
 */
public interface ServiceContext {

	/**
	 * Lookup dynamic service from the given service name
	 * 
	 * @param serviceName
	 *            the name of service
	 * @return
	 * @throws Exception
	 */
	<T extends DynamicService> T lookup(String serviceName) throws Exception;

	/**
	 * Lookup remote dynamic service from the given host and port
	 * 
	 * @param serviceName
	 *            the name of the service
	 * @param host
	 *            the host address of the server
	 * @param port
	 *            the port of the server
	 * @return
	 * @return Dynamic service
	 * @throws Exception
	 */
	<T extends DynamicService> T lookup(String serviceName, String host, int port) throws Exception;

	/**
	 * Lookup remote service bean with high availability (HA), before calling
	 * this method, haName must be configured into system properties. example:
	 * dsb.ha1=192.168.1.2:10000,192.168.1.3:10000
	 * 
	 * @param serviceName
	 * @return
	 * @throws Exception
	 */
	<T extends DynamicService> T lookup_ha(String serviceName, String haName) throws Exception;

	/**
	 * Lookup remote service bean with high availability (HA), before calling
	 * this method, dsb.ha.server must be configured into system properties.
	 * example: -Ddsb.ha.server=192.168.1.2:10000,192.168.1.3:10000
	 * 
	 * @param serviceName
	 * @return
	 * @throws Exception
	 */
	<T extends DynamicService> T lookup_ha(String serviceName) throws Exception;

	/**
	 * Lookup local service proxy.
	 * 
	 * @return
	 * @throws Exception
	 */
	ServiceProxy _lookup() throws Exception;

	/**
	 * Load configuration properties file from the given properties file
	 * 
	 * @param propertyFile
	 *            properties file name
	 * @throws Exception
	 */
	void loadConfigProperties(String propertyFile) throws Exception;

	/**
	 * Get all properties of the current service
	 * 
	 * @return all properties
	 */
	Properties getProperties();

	/**
	 * Get object from service context
	 * 
	 * @param name
	 *            the name of the object
	 * @return the object
	 */
	String getProperty(String name);

	/**
	 * Get property value by the given name
	 * 
	 * @param name
	 * @param defaultVlaue
	 * @return
	 */
	String getProperty(String name, String defaultVlaue);

	/**
	 * add one property to property list.
	 * 
	 * @param name
	 *            the name the given property.
	 * @param value
	 *            the value of the given property
	 */
	void setProperty(String name, String value);

	/**
	 * Get all service bean deployment information
	 * 
	 * @return ServiceConfig
	 */
	ServiceConfig getServiceConfig();

	/**
	 * Gets a string for the given key from this resource bundle
	 * 
	 * @param key
	 *            the key for the desired string
	 * @return the string for the given key
	 */
	String getString(String key);

	/**
	 * Get configuration file
	 * 
	 * @param fileName
	 *            the name of file
	 * @return the URL of the desired file name
	 * @throws IOException
	 */
	URL getConfigFile(String fileName) throws IOException;

	/**
	 * Get resource file
	 * 
	 * @param fileName
	 *            the name of the desired file
	 * @return the URL of the desired file name
	 * @throws IOException
	 */
	URL getResourceFile(String fileName) throws IOException;

}