package common.framework.dsb.proxy;

import java.io.Serializable;

import common.framework.dsb.service.ServiceDescriptor;

public class LookupResult implements Serializable {

	public static int BEAN_WAS_FOUND = 0;
	public static int BEAN_NOT_FOUND = 1;

	private int resultStatus = BEAN_NOT_FOUND;

	private ServiceDescriptor serviceDescriptor = null;

	public int getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(int resultStatus) {
		this.resultStatus = resultStatus;
	}

	public ServiceDescriptor getServiceDescriptor() {
		return serviceDescriptor;
	}

	public void setBeanDescriptor(ServiceDescriptor serviceDescriptor) {
		this.serviceDescriptor = serviceDescriptor;
	}
}
