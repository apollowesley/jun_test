package common.framework.dsb.client;

import java.rmi.RemoteException;
import java.util.List;

import common.framework.dsb.DynamicService;
import common.framework.dsb.proxy.ServiceProxy;
import common.framework.dsb.service.ServiceDescriptor;

/**
 * @author David Yuan
 * 
 */
public interface ServiceFactory {

	String DSB_HA_SERVER = "dsb.ha.server";
	String DSB_SOCKET_TIMEOUT = "dsb.socket.timeout";

	int DSB_LOCAL_CALL = 1;
	int DSB_REMOTE_CALL = 2;
	int DSB_HA_CALL = 3;

	/**
	 * Lookup local dynamic service
	 * 
	 * @param serviceName
	 *            the name of local dynamic service
	 * @return the instance of local dynamic service
	 * @throws Exception
	 *             when failed to lookup dynamic service
	 */

	<T extends DynamicService> T lookup(String serviceName) throws Exception;

	/**
	 * Lookup remote dynamic service.
	 * 
	 * @param serviceName
	 *            the name of dynamic service
	 * @param host
	 *            the host address of dynamic service
	 * @param port
	 *            the port of dynamic service
	 * @return the instance of remote dynamic service
	 * @throws Exception
	 *             when failed to lookup dynamic service
	 */

	<T extends DynamicService> T lookup(String serviceName, String host, int port) throws Exception;

	/**
	 * Lookup remote service bean with high availability (HA) by using default
	 * HA name (dsb.ha.server), before calling this method, HA name
	 * (dsb.ha.server) must be configured into system properties. example:
	 * -Ddsb.ha.server=192.168.1.2:10000,192.168.1.3:10000
	 * 
	 * @param serviceName
	 * @return
	 * @throws Exception
	 */
	<T extends DynamicService> T lookup_ha(String serviceName) throws Exception;

	/**
	 * Lookup remote service bean with high availability (HA) from given haName.
	 * example: -Ddsb.ha.1=192.168.1.2:10000,192.168.1.3:10000
	 * 
	 * @param serviceName
	 * @return
	 * @throws Exception
	 */
	<T extends DynamicService> T lookup_ha(String serviceName, String haName) throws Exception;

	/**
	 * Lookup local service proxy.
	 * 
	 * @return
	 * @throws Exception
	 */
	ServiceProxy _lookup() throws Exception;

	/**
	 * Lookup remote service proxy.
	 * 
	 * @param host
	 * @param port
	 * @return
	 */
	ServiceProxy _lookup(String host, int port) throws Exception;

	/**
	 * Lookup remote service proxy with high availability (HA), before calling
	 * this method, dsb.ha.server must be configured into system properties.
	 * example: -Ddsb.ha.server=192.168.1.2:10000,192.168.1.3:10000
	 * 
	 * @return
	 * @throws Exception
	 */
	ServiceProxy _lookup_ha() throws Exception;

	/**
	 * Lookup remote service proxy with high availability (HA) by using given HA
	 * name, before calling this method, HA name must be configured into system
	 * properties. example: -Ddsb.ha.1=192.168.1.2:10000,192.168.1.3:10000
	 * 
	 * @param haName
	 * @return
	 * @throws Exception
	 */
	ServiceProxy _lookup_ha(String haName) throws Exception;

	/**
	 * List all available dynamic service in the specified server
	 * 
	 * @param host
	 *            the host address of server
	 * @param port
	 *            the port of server
	 * @return all available dynamic services
	 * @throws Exception
	 */
	List<ServiceDescriptor> list(String host, int port) throws Exception;

	/**
	 * List all available dynamic service in the local server.
	 * 
	 * @return all available dynamic services
	 * @throws Exception
	 */
	List<ServiceDescriptor> list() throws Exception;

	/**
	 * List all started services in the local server.
	 * 
	 * @return
	 * @throws RemoteException
	 */
	List<String> listStartedService() throws Exception;

	/**
	 * Start service with the specified name in the local server.
	 * 
	 * @param serviceName
	 * @return
	 * @throws RemoteException
	 */
	boolean startService(String serviceName) throws Exception;

	/**
	 * Close service with the specified name in the local server.
	 * 
	 * @param serviceName
	 * @return
	 * @throws RemoteException
	 */
	boolean closeService(String serviceName) throws Exception;

	/**
	 * Refresh service with the specified name in the local server.
	 * 
	 * @param servieName
	 * @return
	 * @throws Exception
	 */
	boolean refreshService(String servieName) throws Exception;

	/**
	 * Install dynamic service to remote server
	 * 
	 * @param jarPath
	 *            the path of local jar file
	 * @param host
	 *            the host address of remote server
	 * @param port
	 *            the port of remote server
	 * @throws Exception
	 */
	void installService(String jarPath, String host, int port) throws Exception;

}