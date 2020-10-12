package org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config;

/**
 * @author Seer
 * @version IWalletColumnConfig.java, v 0.1 2015年7月25日 上午10:27:38 Seer Exp.
 */
public class IWalletColumnConfig {
	/** name of the column, it's the name in database */
	private String name;

	/**
	 * the java type of the column, used to override default mapping by
	 * middlegen
	 */
	private String javaType;

	/**
	 * Constructor for IWalletFieldConfig.
	 */
	public IWalletColumnConfig() {
		super();
	}

	/**
	 * @return
	 */
	public String getJavaType() {
		return javaType;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param string
	 */
	public void setJavaType(String string) {
		javaType = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		if (string != null) {
			name = string.toLowerCase();
		} else {
			name = null;
		}
	}

	/**
	 * @return
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("[name=").append(name).append(", javaType=").append(javaType).append("]");

		return sb.toString();
	}
}
