
package codeGenerate.mybatis.vo;

import java.util.Date;
import java.util.UUID;

public class ConfigVo {

	private String driverName;

	private String url;

	private String username;

	private String password;

	private String dialect;

	private String tblName;

	private String beanName;

	private String beanDescription;

	private String pack;

	private String genPath;

	private String createUser;

	private Date createTime;

	private String copyright;

	private String dbType;

	private String dbName;

	/**
	 * 模版类名路径
	 */
	private String template;

	/**
	 * 模版目录，默认是类路径的template目录下
	 */
	private String templateDirectory;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getTblName() {
		return tblName;
	}

	public void setTblName(String tblName) {
		this.tblName = tblName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getBeanDescription() {
		return beanDescription;
	}

	public void setBeanDescription(String beanDescription) {
		this.beanDescription = beanDescription;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getGenPath() {
		return genPath;
	}

	public void setGenPath(String genPath) {
		this.genPath = genPath;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	/**
	 * @description: 生成序列号
	 * @creator: dw.fu
	 * @createDate: 2015年10月8日 
	 * @modifier:
	 * @modifiedDate:
	 * @return
	 */
	public String getMostSignificantBits() {
		return UUID.randomUUID().getMostSignificantBits() + "";
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getTemplateDirectory() {
		return templateDirectory;
	}

	public void setTemplateDirectory(String templateDirectory) {
		this.templateDirectory = templateDirectory;
	}

	@Override
	public String toString() {
		return "ConfigVo [driverName=" + driverName + ", url=" + url + ", username=" + username + ", password=" + password + ", dialect=" + dialect + ", tblName=" + tblName
				+ ", beanName=" + beanName + ", beanDescription=" + beanDescription + ", pack=" + pack + ", genPath=" + genPath + ", createUser=" + createUser + ", createTime="
				+ createTime + ", copyright=" + copyright + ", dbType=" + dbType + ", dbName=" + dbName + ", template=" + template + ", templateDirectory=" + templateDirectory
				+ "]";
	}

}
