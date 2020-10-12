package com.luoqy.speedy.core.generator.config;

/**
 * @author luoqy
 * @date 2019年7月7日
 *  代码生成配置
 */
public class CodeConfig{
	private boolean localhostFlag;//是否本地
	private String author;//作者
	private String dbName="aicheyunpei";
	private String tablename;//表名  speedy_table
	private String ignoreTabelPrefix;//忽略的表前缀 speedy
	private String parentMenuName;//父级目录 com.luoqy.speedy.common
	private String projectPackage;//项目包  com.luoqy.speedy
	private String bizName;//业务名称  表管理
	private String moduleName;//模板名称 table
    private boolean controllerSwitch = false;//是否生成控制器代码开关
    private boolean indexPageSwitch = false;//主页
    private boolean addPageSwitch = false;//添加页面
    private boolean editPageSwitch = false;//编辑页面
    private boolean jsSwitch = false;//主页的js
    private boolean infoJsSwitch = false;//详情页面js
    private boolean daoSwitch = false;// dao的开关
    private boolean serviceSwitch = false;//service
    private boolean entitySwitch = false;//生成实体的开关
    private boolean XmlSwich=false;//xml生成的开关
    private String requestPath;//请求路径前缀
	private String pageType;//生成页面类型 1=单页 2=列表
    
    public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getPageType(){return pageType;}
    public void setPageType(String pageType){this.pageType=pageType;}
    public String getRequestPath() {
		return requestPath;
	}
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	public boolean getLocalhostFlag() {
		return localhostFlag;
	}
	public void setLocalhostFlag(boolean localhostFlag) {
		this.localhostFlag = localhostFlag;
	}
	public void setEditPageSwitch(boolean editPageSwitch) {
		this.editPageSwitch = editPageSwitch;
	}
	public void setXmlSwich(boolean xmlSwich) {
		XmlSwich = xmlSwich;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getProjectPackage() {
		return projectPackage;
	}
	public void setProjectPackage(String projectPackage) {
		this.projectPackage = projectPackage;
	}
	public Boolean getXmlSwich() {
		return XmlSwich;
	}
	public void setXmlSwich(Boolean xmlSwich) {
		XmlSwich = xmlSwich;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getIgnoreTabelPrefix() {
		return ignoreTabelPrefix;
	}
	public void setIgnoreTabelPrefix(String ignoreTabelPrefix) {
		this.ignoreTabelPrefix = ignoreTabelPrefix;
	}
	public String getParentMenuName() {
		return parentMenuName;
	}
	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}
	public String getBizName() {
		return bizName;
	}
	public void setBizName(String bizName) {
		this.bizName = bizName;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public boolean getControllerSwitch() {
		return controllerSwitch;
	}
	public void setControllerSwitch(boolean controllerSwitch) {
		this.controllerSwitch = controllerSwitch;
	}
	public boolean getIndexPageSwitch() {
		return indexPageSwitch;
	}
	public void setIndexPageSwitch(boolean indexPageSwitch) {
		this.indexPageSwitch = indexPageSwitch;
	}
	public boolean getAddPageSwitch() {
		return addPageSwitch;
	}
	public void setAddPageSwitch(boolean addPageSwitch) {
		this.addPageSwitch = addPageSwitch;
	}
	public boolean getEditPageSwitch() {
		return editPageSwitch;
	}
	public boolean getJsSwitch() {
		return jsSwitch;
	}
	public void setJsSwitch(boolean jsSwitch) {
		this.jsSwitch = jsSwitch;
	}
	public boolean getInfoJsSwitch() {
		return infoJsSwitch;
	}
	public void setInfoJsSwitch(boolean infoJsSwitch) {
		this.infoJsSwitch = infoJsSwitch;
	}
	public boolean getDaoSwitch() {
		return daoSwitch;
	}
	public void setDaoSwitch(boolean daoSwitch) {
		this.daoSwitch = daoSwitch;
	}
	public boolean getServiceSwitch() {
		return serviceSwitch;
	}
	public void setServiceSwitch(boolean serviceSwitch) {
		this.serviceSwitch = serviceSwitch;
	}
	public boolean getEntitySwitch() {
		return entitySwitch;
	}
	public void setEntitySwitch(boolean entitySwitch) {
		this.entitySwitch = entitySwitch;
	}
}
