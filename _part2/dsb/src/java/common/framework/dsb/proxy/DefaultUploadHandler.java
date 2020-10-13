package common.framework.dsb.proxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import common.framework.dsb.service.ServiceConfig;
import common.framework.dsb.service.ServiceDescriptor;
import common.framework.log.Logger;

public class DefaultUploadHandler implements UploadHandler {

	private Map<String, OutputStreamWrapper> sessionList = new HashMap<String, OutputStreamWrapper>();
	private long seed = 1000L;

	private ServiceProxy serviceProxy = null;
	private ServiceConfig serviceConfig = null;

	public DefaultUploadHandler(ServiceProxy serviceProxy, ServiceConfig serviceConfig) {
		super();
		this.serviceProxy = serviceProxy;
		this.serviceConfig = serviceConfig;
	}

	public String openSession(String jarName) throws Exception, RemoteException {
		synchronized (sessionList) {
			Iterator<OutputStreamWrapper> iter = sessionList.values().iterator();
			while (iter.hasNext()) {
				OutputStreamWrapper outputStreamWrapper = iter.next();
				if (outputStreamWrapper.fileName.equalsIgnoreCase(jarName)) {
					throw new Exception("File " + jarName + " already being used.");
				}
			}
		}
		String sessionID = nextID();
		String filePath = this.serviceConfig.getServiceDir() + File.separator + jarName;
		OutputStream outputStream = new FileOutputStream(filePath, false);
		OutputStreamWrapper outputStreamWrapper = new OutputStreamWrapper(jarName, outputStream);
		synchronized (sessionList) {
			sessionList.put(sessionID, outputStreamWrapper);
		}
		return sessionID;
	}

	public void upload(String sessionID, byte[] data, int length) throws Exception, RemoteException {
		OutputStreamWrapper outputStreamWrapper = sessionList.get(sessionID);
		outputStreamWrapper.outputStream.write(data, 0, length);
		outputStreamWrapper.outputStream.flush();
	}

	public void closeSession(String sessionID) throws Exception, RemoteException {
		OutputStreamWrapper outputStreamWrapper = sessionList.get(sessionID);
		if (outputStreamWrapper != null) {
			synchronized (sessionList) {
				sessionList.remove(sessionID);
			}
			outputStreamWrapper.outputStream.flush();
			outputStreamWrapper.outputStream.close();
			File dirFile = new File(this.serviceConfig.getServiceDir());
			File jarFile = new File(dirFile, outputStreamWrapper.fileName);

			Collection<ServiceDescriptor> serviceDescriptors = readDeploymentFile(jarFile);
			if (serviceDescriptors.size() > 0) {
				ServiceClassLoader classLoader = (ServiceClassLoader) this.getClass().getClassLoader();
				classLoader.addURL(jarFile.toURI().toURL());
			}
			for (ServiceDescriptor sd : serviceDescriptors) {
				serviceConfig.addService(sd);
				serviceProxy.startService(sd.serviceName.trim());
			}
			Logger.log(Logger.FUNCTION_LEVEL, "upload success: " + outputStreamWrapper.fileName);
		}
	}

	@Override
	public void close() {
		sessionList.clear();
	}

	private Collection<ServiceDescriptor> readDeploymentFile(File file) throws Exception {
		Collection<ServiceDescriptor> result = new ArrayList<ServiceDescriptor>();
		// not implement yet
		return result;
	}

	private synchronized String nextID() {
		long id = System.nanoTime();
		StringBuffer sb = new StringBuffer();
		sb.append(seed++);
		sb.append("-");
		sb.append(id);
		return sb.toString();
	}

	private class OutputStreamWrapper {
		String fileName;
		OutputStream outputStream;

		public OutputStreamWrapper(String fileName, OutputStream outputStream) {
			super();
			this.fileName = fileName;
			this.outputStream = outputStream;
		}
	}

}
