package com.yonge.crud.core;

public class GenerateConfiguration {

	private Configuration dbConfiguration;

	private String srcBase;

	private String pojoPackageName;

	private String daoPackageName;

	private String servicePackageName;

	private String sqlmapPackageName;

	private String sqlmapConfigPackageName;

	private String springConfigPackageName;

	public Configuration getDbConfiguration() {
		return dbConfiguration;
	}

	public void setDbConfiguration(Configuration dbConfiguration) {
		this.dbConfiguration = dbConfiguration;
	}

	public String getSrcBase() {
		return srcBase;
	}

	public void setSrcBase(String srcBase) {
		this.srcBase = srcBase;
	}

	public String getPojoPackageName() {
		return pojoPackageName;
	}

	public void setPojoPackageName(String pojoPackageName) {
		this.pojoPackageName = pojoPackageName;
	}

	public String getDaoPackageName() {
		return daoPackageName;
	}

	public void setDaoPackageName(String daoPackageName) {
		this.daoPackageName = daoPackageName;
	}

	public String getServicePackageName() {
		return servicePackageName;
	}

	public void setServicePackageName(String servicePackageName) {
		this.servicePackageName = servicePackageName;
	}

	public String getSqlmapPackageName() {
		return sqlmapPackageName;
	}

	public void setSqlmapPackageName(String sqlmapPackageName) {
		this.sqlmapPackageName = sqlmapPackageName;
	}

	public String getSqlmapConfigPackageName() {
		return sqlmapConfigPackageName;
	}

	public void setSqlmapConfigPackageName(String sqlmapConfigPackageName) {
		this.sqlmapConfigPackageName = sqlmapConfigPackageName;
	}

	public String getSpringConfigPackageName() {
		return springConfigPackageName;
	}

	public void setSpringConfigPackageName(String springConfigPackageName) {
		this.springConfigPackageName = springConfigPackageName;
	}
}
