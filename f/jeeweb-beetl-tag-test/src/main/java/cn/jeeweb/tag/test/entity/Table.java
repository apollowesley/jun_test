package cn.jeeweb.tag.test.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: CodegenTableEntity
 * @Description:代码生成的字段信息
 * @author: 王存见
 * @date: 2017年2月27日 下午5:35:23
 * 
 * @Copyright: 2017 www.jeeweb Inc. All rights reserved.
 *
 */
public class Table implements java.io.Serializable {

	private String id;
	//数据源id
	private String sourceid;
	private String title;
	private String tableName;
	private String className;
	private String tableType;
	private String tablePKType;
	private Boolean syncDatabase;
	private String parentField;
	private String remarks;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getTablePKType() {
		return tablePKType;
	}

	public void setTablePKType(String tablePKType) {
		this.tablePKType = tablePKType;
	}

	public Boolean getSyncDatabase() {
		return syncDatabase;
	}

	public void setSyncDatabase(Boolean syncDatabase) {
		this.syncDatabase = syncDatabase;
	}

	public String getParentField() {
		return parentField;
	}

	public void setParentField(String parentField) {
		this.parentField = parentField;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
