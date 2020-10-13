package common.framework.dsb.proxy;

import java.rmi.RemoteException;

public interface UploadHandler {

	void close();

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
