package org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config;

/**
 * @author Seer
 * @version IWalletSeqConfig.java, v 0.1 2015年7月25日 上午10:26:08 Seer Exp.
 */
public class IWalletSeqConfig {
	/** name of the sequence */
	private String name;

	/**
	 * Constructor for IWalletSeqConfig.
	 */
	public IWalletSeqConfig() {
		super();
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
	public void setName(String string) {
		name = string;
	}
}
