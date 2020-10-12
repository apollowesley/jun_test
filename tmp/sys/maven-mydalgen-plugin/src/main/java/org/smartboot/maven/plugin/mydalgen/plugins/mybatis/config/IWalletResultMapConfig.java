package org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seer
 * @version IWalletResultMapConfig.java, v 0.1 2015年7月25日 上午10:26:22 Seer Exp.
 */
public class IWalletResultMapConfig {

	/** the name of the result map */
	protected String name;

	/** 类型-MyBatis3引入 */
	protected String type;

	/** a list of all column configurations */
	protected List<IWalletColumnConfig> columns = new ArrayList<IWalletColumnConfig>();

	/** belong to which table */
	protected IWalletTableConfig tableConfig;

	/**
	 * Constructor for IWalletResultMapConfig.
	 */
	public IWalletResultMapConfig() {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return
	 */
	public IWalletTableConfig getTableConfig() {
		return tableConfig;
	}

	/**
	 * @param config
	 */
	public void setTableConfig(IWalletTableConfig config) {
		tableConfig = config;
	}

	public List<IWalletColumnConfig> getColumns() {
		return columns;
	}

	/**
	 * Add a column configuration.
	 *
	 * @param columnConfig
	 */
	public void addColumn(IWalletColumnConfig columnConfig) {
		if (columnConfig != null) {
			columns.add(columnConfig);
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

		sb.append("[name=").append(name).append("]");

		return sb.toString();
	}
}
