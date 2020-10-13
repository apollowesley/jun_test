package common.framework.dsb.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import common.framework.dsb.service.ServiceDescriptor;

/**
 * Remote interface for dynamic service interpreting.
 * 
 * @author David Yuan
 * 
 */
public interface ServiceProxy extends Remote {

	/**
	 * Start service proxy
	 * 
	 * @throws Exception
	 */
	void start(List<String> startupServices, List<String> disabledServies) throws Exception;

	/**
	 * Close service proxy
	 * 
	 * @throws Exception
	 */
	void close() throws Exception;

	/**
	 * Heart beat checking.
	 * 
	 * @throws Exception
	 */
	void ping() throws Exception;

	/**
	 * Invoke remote dynamic service.
	 * 
	 * @param serviceName
	 *            dynamic service name
	 * @param method
	 *            method name
	 * @param paramTypes
	 *            parameter types of this method
	 * @param args
	 *            arguments
	 * @return the returned object
	 * @throws Exception
	 */
	Object invoke(String serviceName, String method, Class[] paramTypes, Object[] args) throws Exception;

	/**
	 * Lookup dynamic service descriptor by service name.
	 * 
	 * @param serviceName
	 *            the name of dynamic service
	 * @return service LookupResult
	 * @throws RemoteException
	 */
	LookupResult lookup(String serviceName) throws RemoteException;

	/**
	 * List all available dynamic services
	 * 
	 * @return all available services
	 * @throws RemoteException
	 */
	List<ServiceDescriptor> listService() throws RemoteException;

	/**
	 * Get all started services
	 * 
	 * @return
	 * @throws RemoteException
	 */
	List<String> listStartedService() throws RemoteException;

	/**
	 * Start service with specified name: serviceName
	 * 
	 * @param serviceName
	 * @return
	 * @throws RemoteException
	 */
	boolean startService(String serviceName) throws Exception;

	/**
	 * Close service with specified name: servieName
	 * 
	 * @param serviceName
	 * @return
	 * @throws RemoteException
	 */
	boolean closeService(String serviceName) throws Exception;

	/**
	 * Refresh service with specified name: servieName
	 * 
	 * @param servieName
	 * @return
	 * @throws Exception
	 */
	boolean refreshService(String servieName) throws Exception;

	/**
	 * Open upload session.
	 * 
	 * @param jarName
	 *            the name of file to be uploaded to server
	 * @return session ID.
	 */
	String openSession(String jarName) throws Exception, RemoteException;

	/**
	 * Upload dynamic service jar file to server
	 * 
	 * @param sessionID
	 *            session ID
	 * @param data
	 *            file data
	 * @param length
	 *            the length of data
	 * @throws Exception
	 * @throws RemoteException
	 */
	void upload(String sessionID, byte[] data, int length) throws Exception, RemoteException;

	/**
	 * Close session when finished to transfer the whole file data to server.
	 * 
	 * @param sessionID
	 *            session ID
	 */
	void closeSession(String sessionID) throws Exception, RemoteException;

}