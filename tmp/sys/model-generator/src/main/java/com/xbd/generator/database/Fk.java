package com.xbd.generator.database;

import com.xbd.generator.util.StringUtils;

import java.util.Map;

public class Fk {
	private String pktable_name;

	private String pkcolumn_name;

	private String fktable_name;

	private String fkcolumn_name;

	private String not_null;

	public String getFkcolumn_name() {
		return this.fkcolumn_name;
	}

	public String getFktable_name() {
		return this.fktable_name;
	}

	public String getPkcolumn_name() {
		return this.pkcolumn_name;
	}

	public String getPktable_name() {
		return this.pktable_name;
	}

	public Fk(Map fkMap) {
		this.pktable_name = StringUtils.safeToString(fkMap.get("PKTABLE_NAME"));
		this.pkcolumn_name = StringUtils.safeToString(fkMap.get("PKCOLUMN_NAME"));
		this.fktable_name = StringUtils.safeToString(fkMap.get("FKTABLE_NAME"));
		this.fkcolumn_name = StringUtils.safeToString(fkMap.get("FKCOLUMN_NAME"));
	}

	public String getNot_null() {
		return not_null;
	}

	public void setNot_null(String not_null) {
		this.not_null = not_null;
	}
}
