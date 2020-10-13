package common.framework.dsb.service;

import java.util.List;
import java.util.Locale;

/**
 * This class maintain all service bean descriptors
 * 
 * @author David Yuan
 * 
 */
public interface ServiceConfig {

	/**
	 * Get bean descriptor from the given name
	 * 
	 * @param serviceName
	 *            service bean name
	 * @return the desired bean descriptor
	 */
	ServiceDescriptor getService(String serviceName);

	/**
	 * Get the directory of service
	 * 
	 * @return dirctory
	 */
	String getServiceDir();

	/**
	 * Get locale
	 * 
	 * @return locale
	 */
	Locale getLocale();

	/**
	 * List all bean descriptor
	 * 
	 * @return all bean descriptor
	 */
	List<ServiceDescriptor> listService();

	/**
	 * Add bean descriptor
	 * 
	 * @param beanDescriptor
	 *            BeanDescriptor
	 */
	void addService(ServiceDescriptor serviceDescriptor);

	/**
	 * initialize
	 * 
	 * @throws Exception
	 */
	void start() throws Exception;

	/**
	 * Close
	 * 
	 * @throws Exception
	 */
	void close() throws Exception;
}
