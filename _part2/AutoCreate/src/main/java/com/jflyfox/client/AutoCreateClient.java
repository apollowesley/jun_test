package com.jflyfox.client;

import java.sql.SQLException;
import java.util.Map;

import com.jflyfox.autocreate.template.CRUD;
import com.jflyfox.autocreate.util.AutoCreate;
import com.jflyfox.autocreate.util.DbUtils;
import com.jflyfox.util.Config;
import com.jflyfox.util.StrUtils;

public class AutoCreateClient {

	public static void main(String[] args) throws Exception {
		run();
	}

	protected static void run() throws SQLException, Exception {
		DbUtils.init();

		String selected = Config.getStr("template.selected");
		String tables = Config.getStr("template.tables");
		Map<String, CRUD> crudMap = null;
		if (StrUtils.isEmpty(tables) || "all".equalsIgnoreCase(tables)) {
			crudMap = DbUtils.getCRUDMap();
		} else {
			String[] tableArray = tables.split(",");
			crudMap = DbUtils.getCRUDMap(tableArray);
		}

		AutoCreate.PATH_PAGE_TEMPLATE = Config.getStr(selected);
		AutoCreate.crudMap = crudMap;
		AutoCreate.createCode();
	}

}