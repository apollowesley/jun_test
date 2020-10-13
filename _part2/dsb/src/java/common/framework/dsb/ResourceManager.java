package common.framework.dsb;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author David Yuan
 * 
 */
public class ResourceManager {
	public static Locale locale = null;
	private static final Map<String, ResourceManager> resourceManagers = new Hashtable<String, ResourceManager>();
	private String serviceName = null;
	private ResourceBundle resourceBundle = null;

	/**
	 * Get the resource manager by the given service name.
	 * 
	 * @param service
	 *            the name of the service
	 * @return resource manager
	 */
	public static synchronized ResourceManager getResourceManager(String service) {
		ResourceManager resourceManager = resourceManagers.get(service);
		if (resourceManager == null) {
			resourceManager = new ResourceManager(service);
		}
		return resourceManager;
	}

	/**
	 * Constructor of resource manager
	 * 
	 * @param service
	 *            the name of service
	 */
	public ResourceManager(String service) {
		super();
		this.serviceName = service;
		StringBuffer sb = new StringBuffer();
		sb.append("resource.");
		sb.append(getShortName(serviceName));
		sb.append(".i18n.");
		sb.append(getShortName(serviceName));
		String baseName = sb.toString();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (locale == null) {
			locale = Locale.getDefault();
		}
		this.resourceBundle = ResourceBundle.getBundle(baseName, locale, classLoader);
	}

	/**
	 * Get i18n string by the given key
	 * 
	 * @param key
	 *            the key of the desired resource
	 * @return string resouce
	 */
	public String getString(String key) {
		return resourceBundle.getString(key);
	}

	private String getShortName(String serviceName) {
		if (serviceName.startsWith("DSB/")) {
			return serviceName.substring(serviceName.indexOf("/") + 1);
		} else {
			return serviceName;
		}
	}
}
