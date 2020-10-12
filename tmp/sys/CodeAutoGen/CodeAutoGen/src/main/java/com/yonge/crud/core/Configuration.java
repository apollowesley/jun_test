package com.yonge.crud.core;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Configuration.class);

	private String driverClass;

	private String url;

	private String username;

	private String password;

	private String catalog;

	private String schema;

	public Configuration() {
		// TODO Auto-generated constructor stub
	}

	public Configuration(String driverClass, String url, String username,
			String password, String catalog, String schema) {
		if (StringUtils.isBlank(driverClass)) {
			LOGGER.warn("driverClass不能为空");
			return;
		}
		if (StringUtils.isBlank(url)) {
			LOGGER.warn("url不能为空");
			return;
		}
		if (StringUtils.isBlank(username)) {
			LOGGER.warn("username不能为空");
			return;
		}
		if (StringUtils.isBlank(password)) {
			LOGGER.warn("password不能为空");
			return;
		}
		if (StringUtils.isBlank(schema)) {
			LOGGER.warn("schema不能为空");
			return;
		}
		this.driverClass = driverClass;
		this.url = url;
		this.username = username;
		this.password = password;
		if (catalog != null) {
			this.catalog = catalog.toUpperCase();
		}
		this.schema = schema.toUpperCase();
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public CrudSessionFactory buildCrudSessionFactory() {
		return new CrudSessionFactory(this);
	}
}
