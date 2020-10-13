package tom.kit.json.smd;


import java.util.Set;
import java.util.TreeSet;

public class SMD {
	public static final String DEFAULT_VERSION = ".1";
	public static final String DEFAULT_SERVICE_TYPE = "JSON-RPC";
	private String version = ".1";
	private String objectName;
	private String serviceType = "JSON-RPC";
	private String serviceUrl;
	private Set<SMDMethod> methods = new TreeSet();

	public void addSMDMethod(SMDMethod method) {
		this.methods.add(method);
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getObjectName() {
		return this.objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceUrl() {
		return this.serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public Set<SMDMethod> getMethods() {
		return this.methods;
	}
}
