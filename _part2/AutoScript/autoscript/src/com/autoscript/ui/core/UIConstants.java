/*    */package com.autoscript.ui.core;

import java.io.File;

public abstract interface UIConstants {
	public static final String HOME_PATH = System.getProperty("user.dir");

	public static final String CONF_PATH = HOME_PATH + "/conf";

	public static final String UICONF_PATH = CONF_PATH + "/ui.conf";
	public static final String AUTOSCRIPT_CONFFILE = CONF_PATH + File.separator
			+ "sys.xml";
	public static final String UIPROPERTY_PATH = "conf.ui";
	public static final String RESOURCE_PATH = HOME_PATH + "/resources";

	public static final String LAYOUT_PATH = HOME_PATH + "/layout";

	public static final String RELATIONNODELAYOUT_FILE = LAYOUT_PATH
			+ "/relationstructure.jgx";

	public static final String LAYERNODELAYOUT_FILE = LAYOUT_PATH
			+ "/layerstructure.jgx";
	/**
	 * 模板目录 相对于工作目录
	 */
	public static final String TEMPLDATE_PATH = "template";
	/**
	 * 中间结果目录 相对于工作目录
	 */
	public static final String INTERMEDIATE_PATH = "intermediate";
	/**
	 * xml源数据文件名
	 */
	public static final String SOURCE_XML_FILE = "source.xml";
	/**
	 * 模板文件名后缀
	 */
	public static final String TEMPLATE_SUFFIX = ".ftl";
	/**
	 * 中间结果文件后缀名
	 */
	public static final String INTERMEDIATE_SUFFIX = ".imd";
	/**
	 * UTF-8字符集
	 */
	public static final String UTF_8 = "UTF-8";
	// AutoScript脚本关键字
	/**
	 * 创建文本文件方法
	 */
	public static final String CREATE_TEXT_FILE_PREFIX = "@{createTextFile(";
	/**
	 * 函数后缀
	 */
	public static final String FUNCTION_SUFFIX = ")}";
	/**
	 * 关闭文件方法
	 */
	public static final String CLOSE_FILE = "@{closeFile()}";
	public static final String DEFAULT_UICACHE = "HASHMAP";
	public static final String ACTIVEMQCONNECTIONFACTORY = "ACTIVEMQ";
	public static final String SINGLE_CONNECTIONFACTORYMODE = "SINGLE";
	public static final String MULTIBUS_CONNECTIONFACTORYMODE = "MULTIBUS";
	public static final String RELATIONSHIP_TYPE = "RELATIONSHIP";
	public static final String LAYER_TYPE = "LAYER";
	public static final int SELECT_MODAL = 0;
	public static final int INSERT_MODAL = 1;
	public static final String ADMIN = "A";
	public static final String OPERATOR = "C";
	public static final String EDIT_FLAG = "editflag";
	public static final String EDIT_INSERT = "insert";
	public static final String EDIT_UPDATE = "update";
	public static final String EDIT_DELETE = "delete";
	public static final String NODE_FLAG = "flag";
	public static final String NODE_USE = "Y";
	public static final String NODE_NOUSE = "N";
	public static final String NODE_STATE_NOUSE = "nouse";
	public static final String SYSACTIVE = "A";
	public static final String SYSCLOSE = "N";
	public static final String SYSONLINE = "1";
	public static final String SYSOFFLINE = "2";
	public static final String STARTUSING = "1";
	public static final String STOPUSING = "0";
	public static final String NOBALANCE = "0";
	public static final String BALANCE = "1";
	public static final String SIGNOUT = "0";
	public static final String TEMPSIGNOUT = "2";
	public static final String APPLYSTATE = "1";
	public static final String AUDITPASS = "2";
	public static final String AUDITNOPASS = "3";
	public static final String DEALIN = "2";
	public static final String SysRunningModeValue_Daytime = "1";
	public static final String SysRunningModeValue_Date = "2";
	public static final String SysRunningModeValue_DateComplete = "3";
	public static final String SysRunningModeValue_DealWith = "4";
	public static final String SysRunningModeValue_BatchComplete = "5";
	//FreeMark模板关键字
	/**
	 * xml 根节点
	 */
	public static final String ROOT_NODE="rootnode";
	//所有
	public static final String ALL_KEY="所有";
}
