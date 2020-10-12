package club.lis.model;
/**
 * @Auther: lishun
 * @Date: 2019/4/1 11:18
 * @Description:
 */
public class PackageSetting {
	private String objectPackage;
	private String daoPackage;
	private String daoImplPackage;
	private String servicePackage;
	private String serviceImplPackage;
	private String entityPackage;
	private String mapperPackage;
	private String controllerPackage;
	private String viewPackage;
	private String jsPackage;
	private Boolean isDeleteTablePrefix;

	public String getObjectPackage() {
		return objectPackage;
	}

	public void setObjectPackage(String objectPackage) {
		this.objectPackage = objectPackage;
	}

	public String getDaoPackage() {
		return daoPackage;
	}
	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}
	public String getDaoImplPackage() {
		return daoImplPackage;
	}
	public void setDaoImplPackage(String daoImplPackage) {
		this.daoImplPackage = daoImplPackage;
	}
	public String getServicePackage() {
		return servicePackage;
	}
	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}
	public String getServiceImplPackage() {
		return serviceImplPackage;
	}
	public void setServiceImplPackage(String serviceImplPackage) {
		this.serviceImplPackage = serviceImplPackage;
	}
	public String getEntityPackage() {
		return entityPackage;
	}
	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}
	public String getMapperPackage() {
		return mapperPackage;
	}
	public void setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}
	public String getControllerPackage() {
		return controllerPackage;
	}
	public void setControllerPackage(String controllerPackage) {
		this.controllerPackage = controllerPackage;
	}
	public String getViewPackage() {
		return viewPackage;
	}
	public void setViewPackage(String viewPackage) {
		this.viewPackage = viewPackage;
	}

	public String getJsPackage() {
		return jsPackage;
	}

	public void setJsPackage(String jsPackage) {
		this.jsPackage = jsPackage;
	}

	public Boolean getIsDeleteTablePrefix() {
		return isDeleteTablePrefix;
	}
	public void setIsDeleteTablePrefix(Boolean isDeleteTablePrefix) {
		this.isDeleteTablePrefix = isDeleteTablePrefix;
	}
	 
}
