package common.framework.dsb.service;

import java.io.Serializable;

public class ServiceDescriptor implements Serializable {
	public String serviceName;
	public String interfaceClassName;
	public String serviceClassName;
	public boolean startup;
}
