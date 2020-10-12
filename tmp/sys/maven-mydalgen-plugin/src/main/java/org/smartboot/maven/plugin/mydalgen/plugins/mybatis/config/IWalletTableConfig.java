package org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Seer
 * @version IWalletTableConfig.java, v 0.1 2015年7月25日 上午10:25:58 Seer Exp.
 */
public class IWalletTableConfig {
	/** the table name in database */
	private String sqlName;

	/** the dataobject name */
	private String doName;

	/** the sub package name */
	private String subPackage;

	/** the sequence corresponding to the table */
	private String sequence;

	// add by yuanxiao
	private String confidentiality;
	private String integrity;

	private String encodekeyname;
	private String abstractkeyname;

	/** 虚拟主键 */
	private String dummyPk;

	/**mybatis自定义标签*/
	private String mybatis;

	/** a list of all configured operations */
	private List<IWalletOperationConfig> operations = new ArrayList<IWalletOperationConfig>();

	/** a map of all column configuration */
	private Map<String, IWalletColumnConfig> columns = new HashMap<String, IWalletColumnConfig>();

	/** a list of all result maps */
	private List<IWalletResultMapConfig> resultMaps = new ArrayList<IWalletResultMapConfig>();

	/**
	 * Constructor for IWalletTableConfig.
	 */
	public IWalletTableConfig() {
		super();
		bConfidentiality();
		bIntegrity();
	}

	/**
	 * @return
	 */
	public String getSqlName() {
		return sqlName;
	}

	/**
	 * @return
	 */
	public String getDoName() {
		return doName;
	}

	/**
	 * @return
	 */
	public String getSubPackage() {
		return subPackage;
	}

	/**
	 * @param string
	 */
	public void setSqlName(String string) {
		sqlName = string;
	}

	/**
	 * @param string
	 */
	public void setDoName(String string) {
		doName = string;
	}

	/**
	 * @param string
	 */
	public void setSubPackage(String string) {
		subPackage = string;
	}

	public String getMybatis() {
		return mybatis;
	}

	public void addMybatis(String mybatis) {
		this.mybatis = mybatis;
	}

	/**
	 * @return
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		String newLine = System.getProperty("line.separator");

		sb.append("[").append("sqlname=").append(sqlName).append(", doname=").append(doName).append(", subpackage=")
		.append(subPackage).append(", sequence=").append(sequence).append(", confidentiality=")
		.append(confidentiality).append(", integrity=").append(integrity).append(", encodekeyname=")
		.append(encodekeyname).append(", abstractkeyname=").append(abstractkeyname).append("]").append(newLine);

		for (int i = 1; i <= resultMaps.size(); i++) {
			sb.append("rm-").append(i).append(": ").append(resultMaps.get(i - 1)).append(newLine);
		}

		for (int i = 1; i <= operations.size(); i++) {
			sb.append("op-").append(i).append(": ").append(operations.get(i - 1)).append(newLine);
		}

		for (Iterator<String> i = columns.keySet().iterator(); i.hasNext();) {
			sb.append("column:").append(columns.get(i.next())).append(newLine);
		}

		return sb.toString();
	}

	// public List<IWalletSqlConfig> getSqls() {
	// return sqls;
	// }
	//
	// public void addSql(IWalletSqlConfig sql) {
	// sqls.add(sql);
	// }

	// public List<CopyConfig> getCopys() {
	// return copys;
	// }
	//
	// public void addCopy(CopyConfig copy) {
	// copys.add(copy);
	// }

	/**
	 * @return
	 */
	public List<IWalletOperationConfig> getOperations() {
		return operations;
	}

	/**
	 * Add an operation configuration to the operation list, and have the
	 * operation points to this table configuration.
	 *
	 * @param operationConfig
	 */
	public void addOperation(IWalletOperationConfig operationConfig) {
		operations.add(operationConfig);
		operationConfig.setTableConfig(this);
	}

	/**
	 * Get a column configuration by its name.
	 *
	 * @param name
	 * @return
	 */
	public IWalletColumnConfig getColumn(String name) {
		return columns.get(name.toLowerCase());
	}

	/**
	 * Add a column configuration.
	 *
	 * @param columnConfig
	 */
	public void addColumn(IWalletColumnConfig columnConfig) {
		if (columnConfig != null) {
			columns.put(columnConfig.getName().toLowerCase(), columnConfig);
		}
	}

	/**
	 * @return
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * @param string
	 */
	public void setSequence(String string) {
		sequence = string;
	}

	/**
	 * @return
	 */
	public List<IWalletResultMapConfig> getResultMaps() {
		return resultMaps;
	}

	/**
	 * @param operationConfig
	 */
	public void addResultMap(IWalletResultMapConfig resultMapConfig) {
		resultMaps.add(resultMapConfig);
		resultMapConfig.setTableConfig(this);
	}

	/**
	 * @return Returns the dummyPk.
	 */
	public String getDummyPk() {
		return dummyPk;
	}

	/**
	 * @param dummyPk
	 *            The dummyPk to set.
	 */
	public void setDummyPk(String dummyPk) {
		this.dummyPk = dummyPk;
	}

	public String getConfidentiality() {
		return confidentiality;
	}

	public void setConfidentiality(String confidentiality) {
		this.confidentiality = confidentiality;
	}

	public String getIntegrity() {
		return integrity;
	}

	public void setIntegrity(String integrity) {
		this.integrity = integrity;
	}

	public String getEncodekeyname() {
		return encodekeyname;
	}

	public void setEncodekeyname(String encodekeyname) {
		this.encodekeyname = encodekeyname;
	}

	public String getAbstractkeyname() {
		return abstractkeyname;
	}

	public void setAbstractkeyname(String abstractkeyname) {
		this.abstractkeyname = abstractkeyname;
	}

	public boolean bConfidentiality() {
		if (getConfidentiality() != null && getConfidentiality().length() != 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean bIntegrity() {
		if (getIntegrity() != null && getIntegrity().length() != 0) {
			return true;
		} else {
			return false;
		}
	}

}
