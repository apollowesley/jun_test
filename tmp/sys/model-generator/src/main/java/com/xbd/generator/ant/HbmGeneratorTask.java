package com.xbd.generator.ant;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

import com.xbd.generator.database.Column;
import com.xbd.generator.database.Table;
import com.xbd.generator.util.DriverManagerDataSource;
import com.xbd.generator.util.StringUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.ExecTask;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbmGeneratorTask extends ExecTask {
	private Logger log = LoggerFactory.getLogger(HbmGeneratorTask.class);
	private String dest_hbm = "/generate/hbm/";
	private String packageSuffix = "po";
	private String packageName = "com/xbd/model/";
	private String source = "/generate/hbm/";
	private String dest_src = "/generate/src/";
	private String excludeTables;

	private String driverClassName;
	private String url;
	private String username;
	private String password;

	public void execute() throws BuildException {
		try {
			DriverManagerDataSource ds = new DriverManagerDataSource();
			ds.setDriverClassName(this.getDriverClassName());
			ds.setUrl(this.getUrl());

			ds.setUsername(this.getUsername());
			ds.setPassword(this.getPassword());

			log.info("driverClassName : " + this.getDriverClassName());
			log.info("url : " + this.getUrl());
			log.info("username : " + this.getUsername());
			log.info("password : " + this.getPassword());
			if (this.url.indexOf("oracle") > -1) {
				generate(ds, this.getUsername().toUpperCase());
			} else {
				generate(ds, this.getUsername());
			}
		} catch (Throwable e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}

	/**
	 * 生成指定用户表对应的hbm文件，当 tablename 为空时生成用户全部表对应的hbm
	 */
	private void generate(DriverManagerDataSource ds, String username) throws Exception {
		Connection conn = ds.getConnection();
		DatabaseMetaData databaseMetaData = conn.getMetaData();

		List<Table> tablesList = new ArrayList<Table>();

		// tables
		List<Map> tableMapsList = resultSet2List(databaseMetaData.getTables(conn.getCatalog(), username, null, new String[] { "TABLE" }));
		log.info("共计：" + tableMapsList.size() + "张表！");
		for (Map map : tableMapsList) {
			String dbTable = StringUtils.safeToString(map.get("TABLE_NAME"));
			String tab = dbTable.toUpperCase();
			if (containsTable(this.getExTableNames(), tab)) {
				continue;
			} else if (!tab.matches("[a-zA-Z][a-zA-Z1-9_]*")) {
				continue;
			}

			Table table = new Table(map);
			tablesList.add(table);

			// 字段
			List<Map> columnList = resultSet2List(databaseMetaData.getColumns(conn.getCatalog(), username, dbTable, null));
			for (Map columnMap : columnList) {
				try {
					table.addColumu(new Column(columnMap));
				} catch (RuntimeException e) {
					log.error("table[" + table.getName() + "].[" + columnMap.get("COLUMN_NAME") + "]");
					throw e;
				}
			}

			// 主键
			List<Map> pkList = resultSet2List(databaseMetaData.getPrimaryKeys(conn.getCatalog(), username, dbTable));
			if (pkList.size() == 0) {
				System.err.println("排除 : Table " + table.getName() + " 没有定义主键！");
			} else if (pkList.size() > 1) {
				System.err.println("排除 : Table " + table.getName() + " 含有多主键(" + pkList.size() + ")！");
				for (Map pkMap : pkList) {
					System.out.print("  " + (String) pkMap.get("COLUMN_NAME"));
				}
				System.out.println();
			} else {
				for (Map pkMap : pkList) {
					table.addPrimaryKeyColumn((String) pkMap.get("COLUMN_NAME"));
				}
			}
			if (table.getColumns().size() == 0) {
				System.err.println("排除 ：Table " + table.getName() + " 没有普通字段列！");
			}

		}

		// generate hbm
		log.info("generate hbm start..");
		Velocity.init("velocity.properties");
		Template template = Velocity.getTemplate("hbm.vm", "utf-8");
		VelocityContext context = new VelocityContext();
		int ex = 0;
		for (Table table : tablesList) {
			log.info("正在生成{}表的hbm文件，路径为{}", new Object[] { table.getName(), this.dest_hbm });
			try {
				if (table.getPrimaryKeyColumn() != null && table.getColumns().size() > 0) {
					context.put("table", table);
					Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + this.dest_hbm + "/" + table.getName() + ".hbm.xml"), "UTF-8"));
					template.merge(context, writer);
					writer.flush();
					writer.close();
					ex++;
				}
			} catch (RuntimeException e) {
				log.error("[" + table.getName() + "] 生成报错！");
			}
		}
		log.info("实际生成：" + ex + " 个PO对象！");
		log.info("generate hbm end..");

		// 生成po
		new File(System.getProperty("user.dir") + dest_src + packageName + packageSuffix).mkdirs();

		Velocity.init("velocity.properties");
		template = Velocity.getTemplate("po.vm", "utf-8");
		context = new VelocityContext();
		context.put("packageName", packageName.replaceAll("/", ".") + packageSuffix);
		context.put("stringUtils", new StringUtils());

		for (Table table : tablesList) {
			String table_name = StringUtils.toUpperCaseFirst(table.getName().toLowerCase());
			try {
				context.put("table", table);
				File poFile = new File(System.getProperty("user.dir") + dest_src + packageName + packageSuffix + "/" + table_name + ".java");
				Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(poFile), "UTF-8"));
				template.merge(context, writer);
				writer.flush();
				writer.close();
			} catch (Exception e) {
				log.error("生成[" + table_name + "] " + e.getMessage());
				throw e;
			}
		}

		conn.close();
	}

	private List<Map> resultSet2List(Object rs) throws Exception {
		List<Map> results = new ArrayList<Map>();
		ResultSet localResultSet = (ResultSet) rs;
		if (rs != null) {
			ResultSetMetaData rsmd = localResultSet.getMetaData();
			for (; localResultSet.next();) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					map.put(rsmd.getColumnName(i), localResultSet.getObject(i));
				}

				results.add(map);
			}
		}
		if (localResultSet.getStatement() != null) {
			localResultSet.getStatement().close();
		}
		if (localResultSet != null) {
			localResultSet.close();
		}
		return results;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
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

	public String getDest_hbm() {
		return this.dest_hbm;
	}

	public void setDest_hbm(String dest_hbm) {
		this.dest_hbm = dest_hbm;
	}

	public String getPackageSuffix() {
		return packageSuffix;
	}

	public void setPackageSuffix(String packageSuffix) {
		this.packageSuffix = packageSuffix;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDest_src() {
		return dest_src;
	}

	public void setDest_src(String dest_src) {
		this.dest_src = dest_src;
	}

	public String getExcludeTables() {
		return excludeTables;
	}

	public void setExcludeTables(String excludeTables) {
		this.excludeTables = excludeTables;
	}

	private String[] getExTableNames() {
		if (StringUtils.isNotEmpty(this.excludeTables)) {
			return this.excludeTables.replaceAll(" ", "").split(",");
		} else {
			return new String[] {};
		}
	}

	private boolean containsTable(String[] tableNames, String table) {
		for (String tab : tableNames) {
			if (tab.equalsIgnoreCase(table)) {
				return true;
			}
		}
		return false;
	}

}
