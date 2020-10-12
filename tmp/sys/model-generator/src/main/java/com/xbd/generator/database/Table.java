package com.xbd.generator.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xbd.generator.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Table {
	private String schem;

	private String name;

	private Column primaryKeyColumn;

	private List<Column> columns;

	private List<Column> properties;

	// one-to-many set
	private List<Fk> exportedKeysList;

	// many-to-one parentTable
	private List<Fk> importedKeysList;

	private String comment;

	private static final Log log = LogFactory.getLog(Table.class);

	public Table(Map tableMap) {
		this.columns = new ArrayList<Column>();
		this.properties = new ArrayList<>();
		this.exportedKeysList = new ArrayList<Fk>();
		this.importedKeysList = new ArrayList<Fk>();

		// table
		this.schem = StringUtils.safeToString(tableMap.get("TABLE_SCHEM"));
		this.name = StringUtils.safeToString(tableMap.get("TABLE_NAME")).toUpperCase();
		this.comment = StringUtils.safeToString(tableMap.get("REMARKS"));
	}

	public void addColumu(Column column) {
		this.columns.add(column);
		this.properties.add(column);
	}

	public void addPrimaryKeyColumn(String pk) {
		if (this.getPrimaryKeyColumn() != null) {
			System.err.println("Table[" + this.getName() + "] 含有重复主键");
		}

		this.primaryKeyColumn = this.deleteColumn(pk);

		deleteProperty(pk);
	}

	public void addExportedKey(Map map) {
		this.exportedKeysList.add(new Fk(map));
	}

	public void addImportedKey(Map map) {
		Fk fk = new Fk(map);
		Column column = this.deleteColumn(fk.getFkcolumn_name());
		if (column != null) {
			fk.setNot_null(column.getNot_null());
			this.importedKeysList.add(fk);
		} else {
			log.error("Table[" + this.getName() + "] " + " 主键和外键重复(" + fk.getFkcolumn_name() + ")！");
		}

	}

	public String getName() {
		return this.name;
	}

	public Column getPrimaryKeyColumn() {
		return this.primaryKeyColumn;
	}

	public String getSchem() {
		return this.schem;
	}

	public List<Column> getColumns() {
		return this.columns;
	}

	private Column deleteColumn(String comumnName) {
		for (int i = 0; i < this.columns.size(); i++) {
			if (this.columns.get(i).getName().equals(comumnName)) {
				return this.columns.remove(i);
			}
		}
		return null;
	}

	private void deleteProperty(String comumnName) {
		for (int i = 0; i < this.properties.size(); i++) {
			if (this.properties.get(i).getName().equals(comumnName)) {
				this.properties.remove(i);
			}
		}
	}

	public List<Column> getProperties() {
		return properties;
	}

	public void setProperties(List<Column> properties) {
		this.properties = properties;
	}

	public List<Fk> getExportedKeysList() {
		return this.exportedKeysList;
	}

	public List<Fk> getImportedKeysList() {
		return this.importedKeysList;
	}

	public String getComment() {
		return this.comment;
	}
}
