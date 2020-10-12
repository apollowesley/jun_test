package org.smartboot.maven.plugin.mydalgen;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class MyDalgenConfig {
	private String configFile;
	private String mergedir;
	private String middlegenPackage;

	/** 数据库驱动 */
	private String jdbcDriver;

	/** 数据库URL */
	private String jdbcUrl;

	/** 数据库用户名 */
	private String jdbcUser;

	/** 数据库 密码 */
	private String jdbcPassword;

	public final String getConfigFile() {
		return configFile;
	}

	public final void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public final String getMergedir() {
		return mergedir;
	}

	public final void setMergedir(String mergedir) {
		this.mergedir = mergedir;
	}

	public final String getMiddlegenPackage() {
		return middlegenPackage;
	}

	public final void setMiddlegenPackage(String middlegenPackage) {
		this.middlegenPackage = middlegenPackage;
	}

	/**
	 * Getter method for property <tt>jdbcDriver</tt>.
	 *
	 * @return property value of jdbcDriver
	 */
	public final String getJdbcDriver() {
		return jdbcDriver;
	}

	/**
	 * Setter method for property <tt>jdbcDriver</tt>.
	 *
	 * @param jdbcDriver
	 *            value to be assigned to property jdbcDriver
	 */
	public final void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	/**
	 * Getter method for property <tt>jdbcUrl</tt>.
	 *
	 * @return property value of jdbcUrl
	 */
	public final String getJdbcUrl() {
		return jdbcUrl;
	}

	/**
	 * Setter method for property <tt>jdbcUrl</tt>.
	 *
	 * @param jdbcUrl
	 *            value to be assigned to property jdbcUrl
	 */
	public final void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	/**
	 * Getter method for property <tt>jdbcUser</tt>.
	 *
	 * @return property value of jdbcUser
	 */
	public final String getJdbcUser() {
		return jdbcUser;
	}

	/**
	 * Setter method for property <tt>jdbcUser</tt>.
	 *
	 * @param jdbcUser
	 *            value to be assigned to property jdbcUser
	 */
	public final void setJdbcUser(String jdbcUser) {
		this.jdbcUser = jdbcUser;
	}

	/**
	 * Getter method for property <tt>jdbcPassword</tt>.
	 *
	 * @return property value of jdbcPassword
	 */
	public final String getJdbcPassword() {
		return jdbcPassword;
	}

	/**
	 * Setter method for property <tt>jdbcPassword</tt>.
	 *
	 * @param jdbcPassword
	 *            value to be assigned to property jdbcPassword
	 */
	public final void setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

}
