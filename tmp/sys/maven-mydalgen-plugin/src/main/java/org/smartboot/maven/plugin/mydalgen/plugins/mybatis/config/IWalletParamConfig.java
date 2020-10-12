package org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Seer
 * @version IWalletParamConfig.java, v 0.1 2015年7月25日 上午10:26:45 Seer Exp.
 */
public class IWalletParamConfig {

	/** 参数名 */
	private String name;

	/** 参数类型 */
	private String javaType;

	/** 泛型类型 */
	private String genericType;

	/**
	 * Constructor.
	 */
	public IWalletParamConfig() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getGenericType() {
		return genericType;
	}

	public void setGenericType(String genericType) {
		this.genericType = genericType;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	 public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
