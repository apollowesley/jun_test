/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.smartboot.maven.plugin.mydalgen.MiddlegenException;
import org.smartboot.maven.plugin.mydalgen.TableDecorator;
import org.smartboot.maven.plugin.mydalgen.Util;
import org.smartboot.maven.plugin.mydalgen.javax.JavaPlugin;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletConfig;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletConfigException;

/**
 * @author Seer
 * @version IWalletPlugin.java, v 0.1 2015年7月25日 上午10:23:00 Seer Exp.
 */
public class IWalletPlugin extends JavaPlugin {

	/** full qualified class name for DataAccessException */
	public static final String DATA_ACCESS_EXCEPTION_CLASS = "org.springframework.dao.DataAccessException";
	public static final String BASE_DO_CLASS = "BaseDTO";
	public static final String BASE_DAO_CLASS = "BaseDAO";

	/**
	 * the time when the source code was generated, may be used to tag source
	 * code
	 */
	private Date genTime;

	/**
	 * Constructor for IWalletPlugin.
	 */
	public IWalletPlugin() {
		super();

		genTime = new Date();
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.Plugin#getColumnDecoratorClass()
	 */
	@Override
	public Class getColumnDecoratorClass() {
		return IWalletColumn.class;
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.Plugin#getTableDecoratorClass()
	 */
	@Override
	public Class getTableDecoratorClass() {
		return IWalletTable.class;
	}

	/**
	 * @return
	 */
	public Date getGenTime() {
		return genTime;
	}

	/**
	 * @throws org.smartboot.maven.plugin.mydalgen.MiddlegenException
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.Plugin#validate()
	 */
	@Override
	public void validate() throws MiddlegenException {
		super.validate();
	}

	/**
	 * perform all iwallet specific configurations.
	 *
	 * @throws IWalletConfigException
	 */
	public void configAll() throws IWalletConfigException {
		for (TableDecorator td : getTables()) {
			IWalletTable iwTable = (IWalletTable) td;

			iwTable.configResultMaps();
			iwTable.configOperations();
		}
	}

	/**
	 * @see org.smartboot.maven.plugin.mydalgen.Plugin#generate()
	 */
	@Override
	protected void generate() throws MiddlegenException {
		try {
			configAll();
		} catch (IWalletConfigException e) {
			throw new MiddlegenException(e.getMessage());
		}

		super.generate();
	}

	/**
	 * @return
	 */
	public String getSeqDAOPackage() {
		if (StringUtils.isNotBlank(IWalletTable.DAO_PACKAGE)) {
			return getPackage() + "." + IWalletTable.DAO_PACKAGE;
		} else {
			return getPackage();
		}
	}

	/**
	 * @return
	 */
	public String getQualifiedSeqDAOClassName() {
		return Util.getQualifiedClassName(getSeqDAOPackage(), getSeqDAOClassName());
	}

	/**
	 * @return
	 */
	public String getSeqDAOClassName() {
		return MessageFormat.format(IWalletTable.DAO_PATTERN, new Object[] { "Seq" });
	}

	/**
	 * @return
	 */
	public String getSeqDAOBeanName() {
		return Util.decapitalise(getSeqDAOClassName());
	}

	/**
	 * @return
	 */
	public String getSeqIbatisPackage() {
		if (StringUtils.isNotBlank(IWalletTable.IBATIS_PACKAGE)) {
			return getPackage() + "." + IWalletTable.IBATIS_PACKAGE;
		} else {
			return getPackage();
		}
	}

	/**
	 * @return
	 */
	public String getQualifiedSeqIbatisClassName() {
		return Util.getQualifiedClassName(getSeqIbatisPackage(), getSeqIbatisClassName());
	}

	/**
	 * @return
	 */
	public String getSeqIbatisClassName() {
		return MessageFormat.format(IWalletTable.IBATIS_PATTERN, new Object[] { "Seq" });
	}

	/**
	 * @return
	 */
	public String getBaseDOPackage() {
		if (StringUtils.isNotBlank(IWalletTable.DO_PACKAGE)) {
			return getPackage() + "." + IWalletTable.DO_PACKAGE;
		} else {
			return getPackage();
		}
	}

	/**
	 * @return
	 */
	public String getBaseDAOPackage() {
		if (StringUtils.isNotBlank(IWalletTable.DAO_PACKAGE)) {
			return getPackage() + "." + IWalletTable.DAO_PACKAGE;
		} else {
			return getPackage();
		}
	}

	/**
	 * @return
	 */
	public String getBaseDOClassName() {
		return BASE_DO_CLASS;
	}

	/**
	 * @return
	 */
	public String getQualifiedBaseDOClassName() {
		if (StringUtils.isEmpty(getBaseDOPackage())) {
			return getBaseDOClassName();
		} else {
			return getBaseDOPackage() + "." + getBaseDOClassName();
		}
	}

	/**
	 * ȡ�����������õı���
	 *
	 * @return
	 * @throws IWalletConfigException
	 */
	public List<String> getAllTableNames() throws IWalletConfigException {
		return IWalletConfig.getInstance().getAllTableNames();
	}

}
