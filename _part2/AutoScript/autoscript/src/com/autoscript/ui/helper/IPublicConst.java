/**
 * 
 */
package com.autoscript.ui.helper;

/**
 * 共用常量类
 * 
 * @author longsebo
 */
public interface IPublicConst {
	
	// Java包前缀
	public static final String PACKNAMEPREFIX = "com.abchina";
	// Java包路径前缀
	public static final String PACKPATHPREFIX = "com/abchina/";
	// Java源代码目录
	public static final String SRC = "src";
	// 工作流源代码目录
	public static final String WORKFLOW_SRC = "workflow";
	//Java测试代码目录
	public static final String TEST = "test";
	//数据库脚本目录
	public static final String DB = "db";
	//文档目录
	public static final String DOCS = "docs";
	//运行类库目录结构
	public static final String LIB = "WebContent/WEB-INF/lib";
	//文件分隔符
	public static final String DIV = "/";
	//等号
	public static final String EQUATE = "=";
	//Java分隔符
	public static final String DOT = ".";
	//Java分隔字符
	public static final char DOTCHAR = '.';
	//逗号
	public static final String COMMA = ",";
	//大于号
	public static final String GREATER_SIGN = ">";
	//小于号
	public static final String LESS_SIGN = "<";
	//左括号
	public static final String LEFT_PARENTHESES = "(";
	//右括号
	public static final String RIGHT_PARENTHESES = ")";
	//左大括号
	public static final String LEFT_BRACE = "{";
	//右大括号
	public static final String RIGHT_BRACE = "}";
	//井号
	public static final String HASH = "#";
	//and符号
	public static final String AND_CONNECTOR = "&";
	//空字符串
	public static final String EMPTY_STRING = "";
	//空格
	public static final String SPACE = " ";
	//双斜杠
	public static final String DOUBLE_SLASH="\\";
	// 模块下子包常量
	// web
	public static final String WEB = "web";
	// action
	public static final String ACTION = "action";
	// action
	public static final String ACTION_UP = "Action";
	// method
	public static final String METHOD = "method";
	//
	public static final String METHODTEXT = "_*";
	// vo
	public static final String VO =  "vo";
	// vo大写
	public static final String VO_CAPITAL =  "VO";
	//为了查询条件的vo
	public static final String QUERY_VO_CAPITAL = "SearchVO";
	//service
	public static final String SERVICE ="service";
	//I 接口
	public static final String I ="I";
	//service第一个字符大写
	public static final String SERVICE_UP ="Service";
	
	//application
	public static final String APPLICATION="application";
	//al类后缀名称
	public static final String AL_CAPITAL="AL";
	//al属性
	public static final String AL_POSTFIXLOWER="Al";
	//dao
	public static final String DAO="dao";
	//dao首字母大写
	public static final String DAO_CAPITAL="Dao";
	//domain
	public static final String DOMAIN="domain";
	//domain首字母大写
	public static final String DOMAIN_CAPITAL="Domain";
	//impl 
	public static final String IMPL ="impl";
	//Impl 
	public static final String IMPL_UP ="Impl";
	//web代码根目录
	public static final String WEBPATH="WebContent";
	//页面代码目录
	public static final String PAGEPATH="WEB-INF/pages";
	//配置目录
	public static final String CONF ="conf";
	//Spring xml 根配置文件名
	public static final String SPRINGROOTXML="applicationContext.xml";
	//数据源配置文件名
	public static final String SPRINGDATASOURCE="applicationContext-dataSource.xml";
	//sqlMap配置文件名
	public static final String SQLMAPSOURCE="sqlMap.xml";
	//Struts xml 根配置文件名
	public static final String STRUTSROOTXML="struts.xml";
	//向导页面,保存所有新建交易的连接
	public static final String INDEX_HTML="index.html";
	//Spring xml 文件名前缀
	public static final String SPRINGXMLPREFIX="application-";
	//Struts xml 文件名前缀
	public static final String STRUTSXMLPREFIX="struts-";
	//Struts dtd DTD文件名
	public static final String STRUTSDTD="struts-2.0.dtd";
	//Struts包
	public static final String STRUTSPACKAGE="/struts/package";
	//XML文件后缀  
	public static final String XMLFILE_POSTFIX = ".xml"; 
	//java文件名后缀
	public static final String JAVAFILE_POSTFIX = ".java";
	//jsp文件名后缀
	public static final String JSPFILE_POSTFIX = ".jsp";
	//sql文件后缀  
	public static final String SQLFILE_POSTFIX = ".sql"; 
	/*******类类型定义**********/
	//application
	public static final int ALCLASS =1;
	//service
	public static final int SERVICECLASS=2;
	//DAO
	public static final int DAOCLASS=3;
	//action
	public static final int ACTIONCLASS=4;
	//domain
	public static final int DOMAINCLASS=5;
	//vo 
	public static final int VOCLASS=6;
	//serviceInterface
	public static final int SERVICEINTERFACECLASS=7;
	
	//DAO父类
	public static final String DAOPARENTCLASS = "plantix.core.business.daosupport.AbstractPtxDaoSupport";
	//jndi父类名
	public static final String JNDIPARENTCLASS = "org.springframework.jndi.JndiObjectFactoryBean";
	//AL接口
	public static final String ALINTERFACE="plantix.core.application.Application";
	//Action父类
	public static final String ACTIONPARENTCLASS="plantix.web.core.action.PlantixActionSupport";
	//spring xml beans
	public static final String BEANS = "beans";
	//struts.xml root struts
	public static final String STRUTS = "struts";
	//sqlMap.xml root 
	public static final String SQLMAPCONFIG = "sqlMapConfig";
	//classpath
	public static final String CLASSPATH="classpath";
	//冒号
	public static final String COLON=":";
	//struct common package name
	public static final String COMMON="common";
	//spring 命名空间uri
	public static final String SPRING_NAMESPACEURI="http://www.springframework.org/schema/beans";
	//resource
	public static final String RESOURCE = "resource";
	//ref
	public static final String REF="ref";
	//property
	public static final String PROPERTY="property";
	//local
	public static final String LOCAL="local";
	//bean
	public static final String BEAN="bean";
	//IMPORT
	public static final String IMPORT="import";
	//include
	public static final String INCLUDE="include";
	//sqlMap
	public static final String SQLMAP = "sqlMap";
	//class
	public static final String CLASS="class";
	//parent
	public static final String PARENT="parent";
	//name
	public static final String NAME="name";
	//id
	public static final String  ID="id";
	//struts.xml package
	public static final String  PACKAGE="package";
	//struts.xml type
	public static final String  TYPE="type";
	//struts.xml result
	public static final String  RESULT="result";
	//default-interceptor-ref
	public static final String	INTERCEPTORREF = "default-interceptor-ref";
	//struts.xml method匹配的位置
	public static final String  METHODINDEX = "{1}";
	//path
	public static final String  PATH = "path";
	//界面的行间距
	public static final int VERTICALSPACE = 10;
	//PROTOTYPE
	public static final String PROTOTYPE ="prototype";
	//PROTOTYPE
	public static final String SCOPE ="scope";
	//复数s
	public static final String S ="s";
	
	//默认属性名称
	public static final String DEFAULT_ATTRIBUTE = "new_Attribute";
	//默认属性中文名称
//	public static final String DEFAULT_CNATTRIBUTE = MessageHelper.getString("wizard.NewSingle.columnJavaAttr");
	//java.lang.包前缀
	public static final String JAVA_LANG_PREFIX="java.lang.";
	//void
	public static final String VOID="void";
	//Date
	public static final String DATE = "Date";
	//java.util.Date
	public static final String FULLDATE="java.util.Date";
	//BigDecimal
	public static final String BIGDECIMAL="BigDecimal";
	//java.math.BigDecimal
	public static final String FULLBIGDECIMAL="java.math.BigDecimal";
	//java.util.List
	public static final String FULLLIST="java.util.List";
	//java.lange.Object
	public static final String ROOTOBJECTNAME="java.lang.Object";
	//java.lange.String
	public static final String FULLSTRING="java.lang.String";
	//JUnit 3测试父类
	public static final String JUNIT3ROOTCLASS="junit.framework.TestCase";
	//测试类后缀
	public static final String TESTCLASSSUFFIX = "Test";
	//*
	public static final String STAR="*";
	//测试框架
	//JUnit3框架
	public static final String JUNIT3="JUnit3";
	//JUnit4框架
	public static final String JUNIT4="JUnit4";
	//测试方法前缀
	public static final String TESTMETHODPREFIX = "test";
	//获取方法前缀
	public static final String GETMETHODPREFIX="get";
	//方法括号
	public static final String METHODBRACKET="()";
	//JUNIT3模板路径
	public static final String JUNIT3TEMPLATE = "autocode/templet/JUnit3.ftl";
	//JUNIT4模板路径
	public static final String JUNIT4TEMPLATE = "autocode/templet/JUnit4.ftl";
	//逗号字符
	public static final char COMMACHAR = ',';
	//单引号
	public static final String   INVERTEDCOMMA="'"; 
	//Assert包名
	public static final String ASSERTPACKNAME = "static org.junit.Assert.*";
	//AfterClass包名
	public static final String AFTERCLASSPACKNAME = "org.junit.AfterClass";
	//BeforeClass包名
	public static final String BEFORECLASSPACKNAME = "org.junit.BeforeClass";
	//Before包名
	public static final String BEFOREPACKNAME = "org.junit.Before";
	//After包名
	public static final String AFTERPACKNAME = "org.junit.After";
	//测试包名
	public static final String TESTPACKNAME = "org.junit.Test";
	//JUNIT3头模板路径
	public static final String JUNIT3HEADTEMPLATE = "autocode/templet/JUnit3Head.ftl";
	//JUNIT4头模板路径
	public static final String JUNIT4HEADTEMPLATE = "autocode/templet/JUnit4Head.ftl";
	//[]
	public static final String SQUAREBRACKETS="[]";
	//空参数值标识
	public static final String PARAMETERNULLVALUE = "'null'";
	//分号
	public static final char SEMICOLONCHAR=';';
	//---基本类型常量---
	//boolean
	public static final String BOOLEAN="boolean";
	//int
	public static final String INT="int";
	//long
	public static final String LONG="long";
	//char
	public static final String CHAR="char";
	//double
	public static final String DOUBLE="double";
	//float
	public static final String FLOAT="float";
	//byte
	public static final String BYTE="byte";
	//short
	public static final String SHORT="short";
	//extends
	public static final String EXTENDS = "extends";
	//super
	public static final String SUPER="super";
	
	public static final String JAVAX = "javax";
	public static final String SUN = "sun";
	public static final String JAVA = "java";
	//下划线
	public static final String UNDERLINE="_"; 
}
