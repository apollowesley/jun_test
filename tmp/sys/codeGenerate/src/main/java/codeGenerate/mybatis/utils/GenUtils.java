
package codeGenerate.mybatis.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import codeGenerate.mybatis.constants.DBType;
import codeGenerate.mybatis.generater.AbstractGenerater;
import codeGenerate.mybatis.generater.imp.CommonGenerater;
import codeGenerate.mybatis.generater.imp.MysqlGenerater;
import codeGenerate.mybatis.generater.imp.OracleGenerater;
import codeGenerate.mybatis.generater.imp.SqlServer2005Generater;
import codeGenerate.mybatis.vo.ConfigVo;

public class GenUtils {

	/**
	 * @description: 配置文件路径
	 * @creator: dw-fu1
	 * @createDate: 2018年2月26日 
	 * @modifier:
	 * @modifiedDate:
	 * @param configPath 配置路径
	 * @throws Exception
	 */
	public static void genCode(String configPath) throws Exception {
		File file = new File(configPath);
		if (!file.exists()) {
			System.out.println("文件不存在：" + configPath);
		}
		FileInputStream is = new FileInputStream(file);
		genCode(is);
	}

	/**
	 * @description: 流
	 * @creator: dw-fu1
	 * @createDate: 2018年2月26日 
	 * @modifier:
	 * @modifiedDate:
	 * @param is
	 * @throws Exception
	 */
	public static void genCode(InputStream is) throws Exception {
		List<ConfigVo> configList = readConfig(is);
		for (ConfigVo cf : configList) {
			AbstractGenerater generater = getByDbType(cf.getDbType());
			generater.setConfigVo(cf);
			generater.generateCode();
		}
	}

	public static List<ConfigVo> readConfig(InputStream is) throws ParserConfigurationException, SAXException, IOException {
		try {
			XmlParseHandler handler = new XmlParseHandler();
			// 1. 得到SAX解析工厂
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			// 2. 让工厂生产一个sax解析器
			SAXParser newSAXParser = saxParserFactory.newSAXParser();
			// 3. 传入输入流和handler，解析
			newSAXParser.parse(is, handler);

			List<ConfigVo> configVoList = handler.getConfigList();
			//验证
			for (ConfigVo configVo : configVoList) {
				if (StringUtils.isBlank(configVo.getDriverName())) {
					throw new RuntimeException("驱动名不能为空");
				}
				if (StringUtils.isBlank(configVo.getUrl())) {
					throw new RuntimeException("连接地址不能为空");
				}
				if (StringUtils.isBlank(configVo.getUsername())) {
					throw new RuntimeException("用户名称不能为空");
				}
				if (StringUtils.isBlank(configVo.getPassword())) {
					throw new RuntimeException("用户密码不能为空");
				}
				if (StringUtils.isBlank(configVo.getCreateUser())) {
					throw new RuntimeException("创建用户不能为空");
				}
				if (StringUtils.isBlank(configVo.getTemplate())) {
					throw new RuntimeException("生成模版不能为空");
				}
				if (StringUtils.isBlank(configVo.getGenPath())) {
					throw new RuntimeException("文件夹存放路径不能为空");
				}
				if (StringUtils.isBlank(configVo.getTblName())) {
					throw new RuntimeException("表名不能为空");
				}
				if (StringUtils.isBlank(configVo.getPack())) {
					throw new RuntimeException("包路径不能为空");
				}
				if (StringUtils.isBlank(configVo.getBeanDescription())) {
					throw new RuntimeException("bean描述不能为空");
				}
			}
			return configVoList;
		} finally {
			is.close();
		}

	}

	/**
	 * @description: 根据数据库类型获取相应的代码生成器
	 * @creator: dw-fu1
	 * @createDate: 2018年3月23日 
	 * @modifier:
	 * @modifiedDate:
	 * @param dbType
	 * @return
	 */
	private static AbstractGenerater getByDbType(String dbType) {
		if (DBType.MYSQL.equals(dbType)) {
			return new MysqlGenerater();
		}
		if (DBType.ORACLE.equals(dbType)) {
			return new OracleGenerater();
		}
		if (DBType.SQLSERVER2005.equals(dbType)) {
			return new SqlServer2005Generater();
		}
		if (DBType.SQLSERVER.equals(dbType)) {
			return new SqlServer2005Generater();
		}
		return new CommonGenerater();
	}
}

/**
 * TODO javadoc for codeGenerate.mybatis.XmlParseHandler
 * @Copyright: 
 * @author: dw-fu1
 * @since: 2018年2月24日
 */
class XmlParseHandler extends DefaultHandler {

	private String currentTag; // 记录当前解析到的节点名称

	private XMLConfig config;

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		currentTag = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		config = new XMLConfig();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		currentTag = qName; // 把当前标签记录下来
		//实体映射
		if ("bean".equals(qName)) { 
			XMLBean bean = new XMLBean();
			bean.setDesc(attributes.getValue("desc"));
			bean.setTableName(attributes.getValue("tableName"));
			String beanName = attributes.getValue("beanName");
			String path = attributes.getValue("path");
			if(StringUtils.isBlank(beanName)) {
				beanName=SystemUtils.getBeanNameByPath(path);
			}
			bean.setBeanName(beanName);
			bean.setPath(path);
			config.getBeanList().add(bean);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		String value = new String(ch, start, length); // 将当前TextNode转换为String
		if ("driverName".equals(currentTag)) {
			config.setDriverName(value);
		} else if ("url".equals(currentTag)) {
			config.setUrl(value);
		} else if ("dbName".equals(this.currentTag)) {
			config.setDbName(value);
		} else if ("userName".equals(currentTag)) {
			config.setUserName(value);
		} else if ("password".equals(currentTag)) {
			config.setPassword(value);
		} else if ("createUser".equals(currentTag)) {
			config.setCreateUser(value);
		} else if ("copyright".equals(currentTag)) {
			config.setCopyright(value);
		} else if ("template".equals(currentTag)) {
			config.setTemplate(value);
		} else if ("genPath".equals(currentTag)) {
			config.setGenPath(value);
		} else if ("templateDirectory".equals(currentTag)) {
			config.setTemplateDirectory(value);
		}
	}

	public List<ConfigVo> getConfigList() {
		List<ConfigVo> configVoList = new ArrayList<ConfigVo>();
		List<XMLBean> beanList = config.getBeanList();
		for (XMLBean xmlBean : beanList) {
			ConfigVo configVo = new ConfigVo();
			configVo.setDriverName(config.getDriverName());
			configVo.setUrl(config.getUrl());
			configVo.setDbName(config.getDbName());			
			configVo.setUsername(config.getUserName());
			configVo.setPassword(config.getPassword());
			configVo.setCreateUser(config.getCreateUser());
			configVo.setCopyright(config.getCopyright());
			configVo.setTemplate(config.getTemplate());
			configVo.setGenPath(config.getGenPath());
			//模版路径,默认路径：类路径/template
			String templateDirectory = config.getTemplateDirectory();
			if (templateDirectory == null || "".equals(templateDirectory.trim())) {
				templateDirectory = XmlParseHandler.class.getResource("/").getPath() + "template";
			}
			configVo.setTemplateDirectory(templateDirectory);

			configVo.setTblName(xmlBean.getTableName());
			configVo.setBeanName(xmlBean.getBeanName());
			configVo.setPack(xmlBean.getPath());
			configVo.setBeanDescription(xmlBean.getDesc());
			configVo.setCreateTime(new Date());
			//设置数据库类型
			configVo.setDbType(DBType.getByDriverName(configVo.getDriverName()));
			configVoList.add(configVo);
		}
		return configVoList;
	}
}

class XMLConfig {

	private String driverName;

	private String url;

	private String dbName;

	private String userName;

	private String password;

	private String createUser;

	private String copyright;

	private String template;

	private String genPath;

	private String templateDirectory;

	private List<XMLBean> beanList = new ArrayList<XMLBean>();

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getGenPath() {
		return genPath;
	}

	public void setGenPath(String genPath) {
		this.genPath = genPath;
	}

	public List<XMLBean> getBeanList() {
		return beanList;
	}

	public void setBeanList(List<XMLBean> beanList) {
		this.beanList = beanList;
	}

	public String getTemplateDirectory() {
		return templateDirectory;
	}

	public void setTemplateDirectory(String templateDirectory) {
		this.templateDirectory = templateDirectory;
	}

	@Override
	public String toString() {
		return "XMLConfig [driverName=" + driverName + ", url=" + url + ", dbName=" + dbName + ", userName=" + userName + ", password=" + password + ", createUser=" + createUser
				+ ", copyright=" + copyright + ", template=" + template + ", genPath=" + genPath + ", templateDirectory=" + templateDirectory + ", beanList=" + beanList + "]";
	}

}

class XMLBean {

	private String desc;

	private String tableName;

	private String beanName;

	private String path;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Bean [desc=" + desc + ", tableName=" + tableName + ", beanName=" + beanName + ", path=" + path + "]";
	}

}
