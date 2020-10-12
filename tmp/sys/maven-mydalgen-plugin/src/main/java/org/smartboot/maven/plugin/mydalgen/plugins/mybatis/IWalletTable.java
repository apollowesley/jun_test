/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.smartboot.maven.plugin.mydalgen.Column;
import org.smartboot.maven.plugin.mydalgen.Plugin;
import org.smartboot.maven.plugin.mydalgen.Table;
import org.smartboot.maven.plugin.mydalgen.Util;
import org.smartboot.maven.plugin.mydalgen.javax.JavaPlugin;
import org.smartboot.maven.plugin.mydalgen.javax.JavaTable;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletConfig;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletConfigException;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletOperationConfig;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletResultMapConfig;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletTableConfig;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.operation.IWalletDelete;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.operation.IWalletInsert;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.operation.IWalletSelect;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.operation.IWalletUnknown;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.operation.IWalletUpdate;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.DalUtil;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.LogUtils;

import Zql.ZDelete;
import Zql.ZInsert;
import Zql.ZQuery;
import Zql.ZUpdate;

/**
 * @author Seer
 * @version IWalletTable.java, v 0.1 2015年7月25日 上午10:22:34 Seer Exp.
 */
public class IWalletTable extends JavaTable {
	public static final String DO_PATTERN = "{0}DO";
	public static final String DAO_PATTERN = "{0}DAO";
	public static final String IBATIS_PATTERN = "{0}DaoImpl";
	public static final String DO_PACKAGE = "dataobject";
	public static final String DAO_PACKAGE = "dao";
	public static final String IBATIS_PACKAGE = "dao.impl";
	public static final String RESULT_MAP_PREFIX = "RM-";

	/** the table config corresponding to the table */
	private IWalletTableConfig tableConfig;

	/** a list of all result maps */
	private List<ResultMap> resultMaps = new ArrayList<ResultMap>();

	/** a map make look up result map by name quick */
	private Map<String, ResultMap> resultMapIndex = new HashMap<String, ResultMap>();

	/** a list of all operation decorators */
	private List<Operation> operations = new ArrayList<Operation>();

	/** a list of all dataobject imports */
	private Set<String> doImports = new HashSet<String>();

	/** a list of all dao imports */
	private Set<String> daoImports = new HashSet<String>();

	/** a list of all ibatis imports */
	private Set<String> ibatisImports = new HashSet<String>();

	/** a list of all ibatis imports */
	private Set<String> daoImplImports = new HashSet<String>();

	/**
	 * Constructor for IWalletTableDecorator.
	 */
	public IWalletTable(Table subject) {
		super(subject);
	}

	/**
	 * @param plugin
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.PreferenceAware#setPlugin(org.smartboot.maven.plugin.mydalgen.Plugin)
	 */
	@Override
	public void setPlugin(Plugin plugin) {
		if (!(plugin instanceof IWalletPlugin)) {
			throw new IllegalArgumentException("The plugin must be an instance of IWalletPlugin.");
		}

		super.setPlugin(plugin);
	}

	/**
	 * Get the sub package of this table.
	 *
	 * @return
	 */
	public String getSubPackage() {
		return tableConfig.getSubPackage();
	}

	/**
	 * The package for the table is the concatenation of the main package (for
	 * the project) and the sub package for the table.
	 *
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.javax.JavaTable#getPackage()
	 */
	@Override
	public String getPackage() {
		if (StringUtils.isBlank(getSubPackage())) {
			return super.getPackage();
		} else {
			return super.getPackage() + "." + getSubPackage();
		}
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.javax.JavaTable#getBaseClassName()
	 */
	@Override
	public String getBaseClassName() {
		if (StringUtils.isNotBlank(tableConfig.getDoName())) {
			return tableConfig.getDoName();
		} else {
			String theName = super.getBaseClassName();
			try {
				theName = DalUtil.removeTablePrefix(theName);
			} catch (IWalletConfigException e) {
				LogUtils.waring(e.getMessage());
			}

			return theName;
		}
	}

	/**
	 * Gets the variable name.
	 *
	 * <p>
	 * The parent class has intentionally hide this method. However, we need the
	 * method to compose method signatures.
	 *
	 * @return The VariableName value
	 */
	protected String getVariableName() {
		return Util.decapitalise(getDestinationClassName());
	}

	public String getBeanName() {
		return Util.decapitalise(getBaseClassName());
	}

	public String getDaoBeanName() {
		return Util.decapitalise(getDAOClassName());
	}

	/**
	 * Gets the SingularisedVariableName attribute of the JavaTable object
	 *
	 * <p>
	 * The parent class has intentionally hide this method. However, we need the
	 * method to compose method signatures.
	 *
	 * @return The SingularisedVariableName value
	 */
	public String getSingularisedVariableName() {
		if (getTableElement().getSingular() != null) {
			return getTableElement().getSingular();
		} else {
			//return Util.singularise(getVariableName());
			return getVariableName();
		}
	}

	// public List<IWalletSqlConfig> getSqls() {
	// return tableConfig.getSqls();
	// }

	// public List<CopyConfig> getCopys() {
	// return tableConfig.getCopys();
	// }

	/**
	 * Gets all operations
	 *
	 * @return
	 */
	public List<Operation> getOperations() {
		return operations;
	}

	/**
	 * @return
	 */
	public IWalletTableConfig getTableConfig() {
		return tableConfig;
	}

	/**
	 * @return
	 */
	public Set<String> getDoImports() {
		return doImports;
	}

	/**
	 * @param type
	 */
	public void addDoImport(String type) {
		addImport(doImports, type);
	}

	/**
	 * @param type
	 */
	public void addDoImports(List<String> list) {
		addImports(doImports, list);
	}

	/**
	 * @param type
	 */
	public void addDaoImports(List<String> list) {
		addImports(daoImports, list);
	}

	/**
	 * @return
	 */
	public Set<String> getDaoImports() {
		return daoImports;
	}

	/**
	 * @param type
	 */
	public void addIbatisImport(String type) {
		addImport(ibatisImports, type);
	}

	public void addIbatisImports(List<String> list) {
		addImports(ibatisImports, list);
	}

	public Set<String> getIbatisImports() {
		return ibatisImports;
	}

	public Set<String> getDaoImplImports() {
		daoImplImports.addAll(daoImports);
		daoImplImports.addAll(ibatisImports);

		return daoImplImports;
	}

	/**
	 * @param type
	 */
	public void addDaoImport(String type) {
		addImport(daoImports, type);
	}

	protected void addImport(Set<String> list, String type) {
		if (org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.DalUtil.isNeedImport(type)) {
			if (!list.contains(type)) {
				list.add(type);
			}
		}
	}

	protected void addImports(Set<String> list, List<String> typeList) {
		for (int i = 0; i < typeList.size(); i++) {
			addImport(list, typeList.get(i));
		}
	}

	/**
	 * @see org.smartboot.maven.plugin.mydalgen.PreferenceAware#init()
	 */
	@Override
	protected void init() {
		super.init();

		try {
			tableConfig = IWalletConfig.getInstance().getTableConfig(getSqlName());
		} catch (IWalletConfigException e) {
			LogUtils.waring(e.getMessage());
		}


		if (tableConfig == null) {
			LogUtils.waring("Can't get table configuration for table " + getSqlName() + ".");
		}
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.javax.JavaTable#getQualifiedBaseClassName()
	 */
	@Override
	public String getQualifiedDestinationClassName() {
		String pakkage = ((JavaPlugin) getPlugin()).getPackage();

		return Util.getQualifiedClassName(pakkage + ".dataobject", getDestinationClassName());
	}

	/**
	 * Configure all resultMaps.
	 */
	public void configResultMaps() {
		resultMaps = new ArrayList<ResultMap>();

		// the default resultmap
		resultMaps.add(new IWalletResultMap(this, null));

		// additional resultmaps
		Iterator<IWalletResultMapConfig> i = tableConfig.getResultMaps().iterator();

		while (i.hasNext()) {
			ResultMap resultMap = new IWalletResultMap(this, i.next());

			resultMaps.add(resultMap);
			resultMapIndex.put(resultMap.getIdAttr(), resultMap);
		}
	}

	/**
	 * Config all operations.
	 */
	public void configOperations() {
		operations = new ArrayList<Operation>();

		Iterator<IWalletOperationConfig> iop = tableConfig.getOperations().iterator();

		while (iop.hasNext()) {
			IWalletOperationConfig opConfig = iop.next();

			IWalletOperation op;

			if (opConfig.getZst() instanceof ZInsert) {
				op = new IWalletInsert(opConfig);
			} else if (opConfig.getZst() instanceof ZQuery) {
				op = new IWalletSelect(opConfig);
			} else if (opConfig.getZst() instanceof ZUpdate) {
				op = new IWalletUpdate(opConfig);
			} else if (opConfig.getZst() instanceof ZDelete) {
				op = new IWalletDelete(opConfig);
			} else {
				op = new IWalletUnknown(opConfig);
			}

			op.setPlugin(getPlugin());

			op.setTable(this);

			operations.add(op);
		}
	}

	/**
	 * Get the name of the result map corresponding to this table and
	 * dataobject.
	 *
	 * @return
	 */
	public String getResultMapId() {
		return RESULT_MAP_PREFIX
			+ org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.DalUtil.toUpperCaseWithDash(getBaseClassName());
	}

	/**
	 *
	 * @return
	 */
	public String getDOClassName() {
		return MessageFormat.format(DO_PATTERN, getBaseClassName());
	}

	/**
	 *
	 * @return
	 */
	public String getDAOClassName() {
		return MessageFormat.format(DAO_PATTERN, getBaseClassName());
	}

	/**
	 *
	 * @return
	 */
	public String getIbatisClassName() {
		return MessageFormat.format(IBATIS_PATTERN, getBaseClassName());
	}

	/**
	 *
	 * @return
	 */
	public String getDOPackage() {
		if (StringUtils.isNotBlank(DO_PACKAGE)) {
			return getPackage() + "." + DO_PACKAGE;
		} else {
			return getPackage();
		}
	}

	/**
	 *
	 * @return
	 */
	public String getDAOPackage() {
		if (StringUtils.isNotBlank(DAO_PACKAGE)) {
			return getPackage() + "." + DAO_PACKAGE;
		} else {
			return getPackage();
		}
	}

	/**
	 *
	 * @return
	 */
	public String getIbatisPackage() {
		if (StringUtils.isNotBlank(IBATIS_PACKAGE)) {
			return getPackage() + "." + IBATIS_PACKAGE;
		} else {
			return getPackage();
		}
	}

	/**
	 * @return
	 */
	public String getQualifiedDOClassName() {
		return Util.getQualifiedClassName(getDOPackage(), getDOClassName());
	}

	/**
	 * @return
	 */
	public String getQualifiedDAOClassName() {
		return Util.getQualifiedClassName(getDAOPackage(), getDAOClassName());
	}

	/**
	 * @return
	 */
	public String getQualifiedIbatisClassName() {
		return Util.getQualifiedClassName(getIbatisPackage(), getIbatisClassName());
	}

	/**
	 * @return
	 */
	public String getSequence() {
		return tableConfig.getSequence();
	}

	// add by yuanxiao
	public String getConfidentiality() {
		return tableConfig.getConfidentiality();
	}

	public String getIntegrity() {
		return tableConfig.getIntegrity();
	}

	public String getEncodekeyname() {
		return tableConfig.getEncodekeyname();
	}

	public String getAbstractkeyname() {
		return tableConfig.getAbstractkeyname();
	}

	/*
	 * public boolean isTicket() { return tableConfig.isTicket(); }
	 */

	/**
	 * TB-${table.baseClassName}-ID
	 */
	// public String getTicketName() {
	// String ticketName = tableConfig.getTicketName();
	// if (StringUtils.isBlank(ticketName)) {
	// ticketName = "TB-" + getBaseClassName() + "-ID";
	// }
	//
	// return ticketName;
	// }
	//
	// public boolean isFmtNo() {
	// return tableConfig.isFmtNo();
	// }
	//
	// public String getFmtNoName() {
	// String fmtNoName = tableConfig.getFmtNoName();
	// if (StringUtils.isBlank(fmtNoName)) {
	// fmtNoName = "com.github.obullxl.ticket.support.DefaultTicketEncode";
	// }
	//
	// return fmtNoName;
	// }
	//
	// public boolean isValve() {
	// return tableConfig.isValve();
	// }

	/**
	 * @return
	 */
	public boolean isHasSequence() {
		return StringUtils.isNotBlank(getSequence());
	}

	/**
	 * @return
	 */
	public List<ResultMap> getResultMaps() {
		return resultMaps;
	}

	/**
	 * @param id
	 * @return
	 */
	public ResultMap getResultMap(String id) {
		return resultMapIndex.get(id);
	}

	public Column getIwPkColumn() {
		Column pkColumn = getPkColumn();
		String dummyPk = tableConfig.getDummyPk();
		if (pkColumn == null && StringUtils.isNotBlank(dummyPk)) {
			pkColumn = getColumn(dummyPk);
		}
		return pkColumn;
	}

	/**
	 * Gets the SimplePk attribute of the Entity11DbTable object
	 *
	 * @return The SimplePk value
	 */
	@Override
	public boolean isSimplePk() {
		return getIwPkColumn() != null;
	}
}
