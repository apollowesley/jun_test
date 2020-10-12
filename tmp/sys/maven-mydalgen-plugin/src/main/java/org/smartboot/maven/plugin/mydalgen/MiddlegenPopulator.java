/*
 * Copyright (c) 2001, Aslak Hellesøy, BEKK Consulting
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * - Neither the name of BEKK Consulting nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package org.smartboot.maven.plugin.mydalgen;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.LogUtils;

/**
 * @author <a href="mailto:aslak.hellesoy@bekk.no">Aslak Helles</a>
 */
 class MiddlegenPopulator {


	private Middlegen middlegen;

	/** dalgen配置 */
	private MyDalgenConfig config;

	private String _schema;


	private String _catalog;

	private String[] _types = null;

	private Connection getConnection() throws SQLException {
		try {
			Class.forName(config.getJdbcDriver());
		} catch (ClassNotFoundException e) {
			throw new SQLException(e);
		}
		return DriverManager.getConnection(config.getJdbcUrl(), config.getJdbcUser(), config.getJdbcPassword());
	}

	private DatabaseMetaData getMetaData() throws SQLException {
		return getConnection().getMetaData();
	}

	/**
	 * Describe what the SchemaFactory constructor does
	 */
	 MiddlegenPopulator(Middlegen middlegen, MyDalgenConfig config) throws MiddlegenException {
		this.middlegen = middlegen;
		this.config = config;

		try {
			tune();
		} catch (SQLException e) {
			throw new MiddlegenException("Couldn't tune database:" + e.getMessage());
		}
	}

	/**
	 * Adds regular tables to middlegen's list of tables to process.
	 */
	 void addRegularTableElements() throws MiddlegenException {
		ResultSet tableRs = null;
		try {
			tableRs = getMetaData().getTables(_catalog, _schema, null, _types);

			while (tableRs.next()) {
				String tableName = tableRs.getString("TABLE_NAME");
				String tableType = tableRs.getString("TABLE_TYPE");
				String schemaName = tableRs.getString("TABLE_SCHEM");
				String ownerSinonimo;
				if ("TABLE".equals(tableType) || "VIEW".equals(tableType)
						|| "SYNONYM".equals(tableType) && isOracle()) {
					// it's a regular table or a synonym
					LogUtils.log("schema:" + _schema + "," + schemaName);
					LogUtils.log("table:" + tableName);

					TableElement tableElement = new TableElement();
					tableElement.setName(tableName);
					if ("SYNONYM".equals(tableType) && isOracle()) {
						ownerSinonimo = getSynonymOwner(tableName);
						if (ownerSinonimo != null) {
							tableElement.setOwnerSynonymName(ownerSinonimo);
						}
					}
					middlegen.addTableElement(tableElement);
				} else {
					LogUtils.log("Ignoring table " + tableName + " of type " + tableType);
				}
			}
			if (middlegen.getTableElements().isEmpty()) {
				String databaseStructure = getDatabaseStructure();
				throw new MiddlegenException("Middlegen successfully connected to the database, but "
						+ "couldn't find any tables. Perhaps the specified schema or catalog is wrong? -Or maybe "
						+ "there aren't any tables in the database at all?" + databaseStructure);
			}
		} catch (SQLException e) {
			// schemaRs and catalogRs are only used for error reporting if we
			// get an exception
			String databaseStructure = getDatabaseStructure();
			LogUtils.error(e.getMessage(), e);
			throw new MiddlegenException(
					"Couldn't get list of tables from database. Probably a JDBC driver problem." + databaseStructure);
		} finally {
			try {
				if(tableRs!=null) {
					tableRs.close();
				}
			} catch (Exception ignore) {
			}
		}
	}

	/**
	 * <p>
	 * add by zhaoxu 20060918
	 * </p>
	 */
	 void populate(Map<String, TableElement> wantedTables) throws MiddlegenException {
		try {
			DatabaseMetaData databaseMetaData = getMetaData();
			addTables(databaseMetaData, wantedTables);

			for (DbTable table : middlegen.getTables()) {
				addColumns(databaseMetaData, table);
			}

//			markFksToUnwantedTables(databaseMetaData);

		} catch (SQLException e) {
			LogUtils.error(e.getMessage(), e);
			throw new MiddlegenException("Database problem:" + e.getMessage());
		}
	}

	/**
	 * Gets the DatabaseStructure attribute of the MiddlegenPopulator object
	 *
	 * @return The DatabaseStructure value
	 * @exception MiddlegenException
	 *                Describe the exception
	 */
	private String getDatabaseStructure() throws MiddlegenException {
		ResultSet schemaRs = null;

		String nl = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder(nl);
		// Let's give the user some feedback. The exception
		// is probably related to incorrect schema configuration.
		sb.append("Configured schema:").append(_schema).append(nl);
		sb.append("Configured catalog:").append(_catalog).append(nl);

		try {
			schemaRs = getMetaData().getSchemas();
			sb.append("Available schemas:").append(nl);
			while (schemaRs.next()) {
				sb.append("  ").append(schemaRs.getString("TABLE_SCHEM")).append(nl);
			}
		} catch (SQLException e2) {
			LogUtils.error("Couldn't get schemas", e2);
			sb.append("  ?? Couldn't get schemas ??").append(nl);
		} finally {
			try {
				if(schemaRs!=null) {
					schemaRs.close();
				}
			} catch (Exception ignore) {
			}
		}
		ResultSet catalogRs = null;
		try {
			catalogRs = getMetaData().getCatalogs();
			sb.append("Available catalogs:").append(nl);
			while (catalogRs.next()) {
				sb.append("  ").append(catalogRs.getString("TABLE_CAT")).append(nl);
			}
		} catch (SQLException e2) {
			LogUtils.error("Couldn't get catalogs", e2);
			sb.append("  ?? Couldn't get catalogs ??").append(nl);
		} finally {
			try {
				if(catalogRs!=null){
					catalogRs.close();
				}
			} catch (Exception ignore) {
				LogUtils.error(ignore.getLocalizedMessage(),ignore);
			}
		}
		return sb.toString();
	}

	/**
	 * @return a list of tables found in the database
	 * @throws MiddlegenException
	 *             Describe the exception
	 */
	private String getDatabaseTables() throws MiddlegenException {
		String nl = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder(nl);
		sb.append("Found the following tables:");
		sb.append(nl);

		ResultSet tableRs = null;
		try {
			tableRs = getMetaData().getTables(_catalog, _schema, null, _types);
			while (tableRs.next()) {
				String realTableName = tableRs.getString("TABLE_NAME");
				sb.append(realTableName);
				sb.append(" ");
			}
		} catch (SQLException e2) {
			LogUtils.error("Couldn't get schemas", e2);
			sb.append("  ?? Couldn't get schemas ??").append(nl);
		} finally {
			try {
				if(tableRs!=null) {
					tableRs.close();
				}
			} catch (Exception ignore) {
				// ignore
			}
		}

		sb.append(nl);
		sb.append("----");
		sb.append(nl);
		return sb.toString();
	}

	/**
	 * Returns if we are on Oracle
	 *
	 * @return <code>true</code> we are on Oracle, <code>false</code> otherwise
	 */
	private boolean isOracle() {
		boolean ret = false;
		try {
			ret = getMetaData().getDatabaseProductName().toLowerCase().indexOf("oracle") != -1;
		} catch (Exception ignore) {
			LogUtils.error(ignore.getLocalizedMessage(),ignore);
		}

		return ret;
	}

	/**
	 * Returns synonym owner for Oracle.
	 *
	 * @param synonymName
	 *            Syn name
	 * @return Synonym owner for Oracle
	 * @throws MiddlegenException
	 *             If something orrible happens
	 */
	private String getSynonymOwner(String synonymName) throws MiddlegenException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String ret;
		/*
		 * added by yangyanzhao 20100604
		 * ������ͬ��ʱ?������ԭ�������ʱ����?����޸���sql select table_owner from
		 * sys.all_synonyms where table_name=? and owner=?�ĳ� select table_owner
		 * from sys.all_synonyms where (table_name=? or synonym_name=?) and
		 * owner=?
		 */
		try {
			ps = getConnection().prepareStatement(
					"select table_owner from sys.all_synonyms where (table_name=? or synonym_name=?) and owner=?");
			ps.setString(1, synonymName);
			ps.setString(2, synonymName);
			ps.setString(3, _schema);
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = rs.getString(1);
			} else {
				String databaseStructure = getDatabaseStructure();
				throw new MiddlegenException(
						"Wow! Synonym " + synonymName + " not found. How can it happen? " + databaseStructure);
			}
		} catch (SQLException e) {
			String databaseStructure = getDatabaseStructure();
			LogUtils.error(e.getMessage(), e);
			throw new MiddlegenException("Exception in getting synonym owner " + databaseStructure);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					LogUtils.error(e.getLocalizedMessage(),e);
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
					LogUtils.error(e.getLocalizedMessage(),e);
				}
			}
		}
		return ret;
	}

	/**
	 * Marks the columns as foreign keys if they have a relationship to an
	 * unwanted table
	 * 判断被关注表中的字段是否为其他非关注表字段的外键，实际项目中很少使用外键，所以该方法暂时屏蔽 Edit by Seer
	 * @todo-javadoc Write javadocs for exception
	 * @throws MiddlegenException
	 *             Describe the exception
	 */
	private void markFksToUnwantedTables(DatabaseMetaData databaseMetaData) throws MiddlegenException {
		ResultSet tableRs = null;
		try {
			tableRs = databaseMetaData.getTables(_catalog, _schema, null, _types);
			while (tableRs.next()) {
				String tableName = tableRs.getString("TABLE_NAME");
				String tableType = tableRs.getString("TABLE_TYPE");
				// ignore the views, they don't have foreign key relationships
				if ("TABLE".equals(tableType) && !middlegen.containsTable(tableName)
						|| "SYNONYM".equals(tableType) && isOracle()) {
					String ownerSinonimo = null;
					if ("SYNONYM".equals(tableType) && isOracle()) {
						ownerSinonimo = getSynonymOwner(tableName);
					}
					ResultSet exportedKeyRs;
					if (ownerSinonimo != null) {
						exportedKeyRs = databaseMetaData.getExportedKeys(_catalog, ownerSinonimo, tableName);
					} else {
						exportedKeyRs = databaseMetaData.getExportedKeys(_catalog, _schema, tableName);
					}
					while (exportedKeyRs.next()) {
						String fkTableName = exportedKeyRs.getString("FKTABLE_NAME");
						String fkColumnName = exportedKeyRs.getString("FKCOLUMN_NAME");
						// Mark the fk field as an fk anyway. This will be
						// useful for column sorting for example
						if (middlegen.containsTable(fkTableName)) {
							DbTable fkTable = middlegen.getTable(fkTableName);
							DbColumn fkColumn = (DbColumn) fkTable.getColumn(fkColumnName);
							fkColumn.setFk(true);
						}
					}
				}
			}
		} catch (SQLException e) {
			// schemaRs and catalogRs are only used for error reporting if we
			// get an exception
			String databaseStructure = getDatabaseStructure();
			LogUtils.error(e.getMessage(), e);
			throw new MiddlegenException(
					"Couldn't get list of tables from database. Probably a JDBC driver problem." + databaseStructure);
		} finally {
			try {
				if(tableRs!=null) {
					tableRs.close();
				}
			} catch (Exception ignore) {
			}
		}
	}

	/**
	 * Adds columns to the table, and registers any relations.
	 */
	private void addColumns(DatabaseMetaData databaseMetaData, DbTable table) throws MiddlegenException, SQLException {
		LogUtils.log("-------setColumns(" + table.getSqlName() + ")");

		// get the primary keys
		List<String> primaryKeys = new LinkedList<String>();
		ResultSet primaryKeyRs;
		if (table.getTableElement().getOwnerSynonymName() != null) {
			primaryKeyRs = databaseMetaData.getPrimaryKeys(_catalog, table.getTableElement().getOwnerSynonymName(),
					table.getSqlName());
		} else {
			primaryKeyRs = databaseMetaData.getPrimaryKeys(_catalog, _schema, table.getSqlName());
		}
		while (primaryKeyRs.next()) {
			String columnName = primaryKeyRs.getString("COLUMN_NAME");
			LogUtils.log("primary key:" + columnName);
			primaryKeys.add(columnName);
		}
		primaryKeyRs.close();

		// get the indices and unique columns
		List<String> indices = new LinkedList<String>();
		// maps index names to a list of columns in the index
		Map<String, String> uniqueIndices = new HashMap<String, String>();
		// maps column names to the index name.
		Map<String, List<String>> uniqueColumns = new HashMap<String, List<String>>();

		ResultSet indexRs = null;
		try {
			if (table.getTableElement().getOwnerSynonymName() != null) {
				indexRs = databaseMetaData.getIndexInfo(_catalog, table.getTableElement().getOwnerSynonymName(),
						table.getSqlName(), false, true);
			} else {
				indexRs = databaseMetaData.getIndexInfo(_catalog, _schema, table.getSqlName(), false, true);
			}

			while (indexRs.next()) {
				String columnName = indexRs.getString("COLUMN_NAME");

				if (columnName != null) {
					LogUtils.log("index:" + columnName);
					indices.add(columnName);
				}

				// now look for unique columns
				String indexName = indexRs.getString("INDEX_NAME");
				boolean nonUnique = indexRs.getBoolean("NON_UNIQUE");

				if (!nonUnique && columnName != null && indexName != null) {
					List<String> l = uniqueColumns.get(indexName);

					if (l == null) {
						l = new ArrayList<String>();
						uniqueColumns.put(indexName, l);
					}
					l.add(columnName);

					uniqueIndices.put(columnName, indexName);
					LogUtils.log("unique:" + columnName + " (" + indexName + ")");
				}
			}
		} catch (Throwable t) {
			// Bug #604761 Oracle getIndexInfo() needs major grants
			// http://sourceforge.net/tracker/index.php?func=detail&aid=604761&group_id=36044&atid=415990
		} finally {
			if (indexRs != null) {
				indexRs.close();
			}
		}

		// get the columns
		List<Column> columns = new LinkedList<Column>();
		ResultSet columnRs;
		if (table.getTableElement().getOwnerSynonymName() != null) {
			columnRs = databaseMetaData.getColumns(_catalog, table.getTableElement().getOwnerSynonymName(),
					table.getSqlName(), null);
		} else {
			columnRs = databaseMetaData.getColumns(_catalog, _schema, table.getSqlName(), null);
		}

		while (columnRs.next()) {
			int sqlType = columnRs.getInt("DATA_TYPE");
			String sqlTypeName = columnRs.getString("TYPE_NAME");
			String columnName = columnRs.getString("COLUMN_NAME");
			String columnDefaultValue = columnRs.getString("COLUMN_DEF");

			String remark = columnRs.getString("REMARKS");
			if (StringUtils.isBlank(remark)) {
				remark = columnName;
			}

			// if columnNoNulls or columnNullableUnknown assume "not nullable"
			boolean isNullable = DatabaseMetaData.columnNullable == columnRs.getInt("NULLABLE");
			int size = columnRs.getInt("COLUMN_SIZE");
			int decimalDigits = columnRs.getInt("DECIMAL_DIGITS");

			boolean isPk;
			List<String> pkColumnsOverride = table.getTableElement().getPkColumnsOverrideCollection();
			if (pkColumnsOverride.size() > 0) {
				isPk = pkColumnsOverride.contains(columnName);
			} else {
				isPk = primaryKeys.contains(columnName);
			}
			boolean isIndexed = indices.contains(columnName);
			String uniqueIndex = uniqueIndices.get(columnName);
			List<String> columnsInUniqueIndex = null;
			if (uniqueIndex != null) {
				columnsInUniqueIndex = uniqueColumns.get(uniqueIndex);
			}

			boolean isUnique = columnsInUniqueIndex != null && columnsInUniqueIndex.size() == 1;
			if (isUnique) {
				LogUtils.log("unique column:" + columnName);
			}

			Column column = new DbColumn(table, sqlType, sqlTypeName, columnName, remark, size, decimalDigits, isPk,
					isNullable, isIndexed, isUnique, columnDefaultValue);
			columns.add(column);
		}
		columnRs.close();

		for (Column column : columns) {
			table.addColumn(column);
		}

		// In case none of the columns were primary keys, issue a warning.
		if (primaryKeys.size() == 0) {
			LogUtils.log("WARNING: The JDBC driver didn't report any primary key columns in " + table.getSqlName());
		}
	}

	/**
	 * Tunes the settings depending on database.
	 */
	private void tune() throws SQLException {
		DatabaseMetaData metaData = getMetaData();

		String databaseProductName = metaData.getDatabaseProductName();
		String databaseProductVersion = metaData.getDatabaseProductVersion();
		String driverName = metaData.getDriverName();
		String driverVersion = metaData.getDriverVersion();
		_catalog = metaData.getConnection().getCatalog();
		DatabaseInfo databaseInfo = new DatabaseInfo(databaseProductName, databaseProductVersion, driverName,
				driverVersion);

		middlegen.setDatabaseInfo(databaseInfo);

		LogUtils.log("databaseProductName= " + databaseProductName);
		LogUtils.log("databaseProductVersion= " + databaseProductVersion);
		LogUtils.log("driverName= " + driverName);
		LogUtils.log("driverVersion= " + driverVersion);
		LogUtils.log("schema= " + _schema);
		LogUtils.log("catalog= " + _catalog);

		// ORACLE TUNING
		if (isOracle()) {
			// capitalize catalogue
			if (_catalog != null) {
				_catalog = _catalog.toUpperCase();
			}

			// usually the access rights are set up so that you can only query
			// your schema
			// ie. schema = username
			if (_schema != null) {
				_schema = _schema.toUpperCase();
			}

			// null will also retrieve objects for which only synonyms exists,
			// but this objects will not
			// be successfully processed anyway - did not check why -probably
			// columns not retrieved
			_types = new String[] { "TABLE", "VIEW", "SYNONYM" };
		}

	}

	/**
	 * Describe the method
	 */
	private void addTables(DatabaseMetaData databaseMetaData, Map<String, TableElement> wantedTables)
			throws MiddlegenException, SQLException {
		// get the tables
		LogUtils.log("-- tables --");

		// We're keeping track of the table names so we can detect if a table
		// occurs in different schemas
		Map<String, String> tableSchemaMap = new HashMap<String, String>();

		for (TableElement tableElement : wantedTables.values()) {
			String tableName = tableElement.getName();
			String schemaName;
			// check that the table really exists
			ResultSet tableRs = null;
			try {
				tableRs = databaseMetaData.getTables(_catalog, _schema, tableName, _types);
				if (!tableRs.next()) {
					tableRs = databaseMetaData.getTables(_catalog, _schema, tableName.toLowerCase(), _types);
					if (!tableRs.next()) {
						tableRs = databaseMetaData.getTables(_catalog, _schema, tableName.toUpperCase(), _types);
						if (!tableRs.next()) {
							throw new MiddlegenException("The database doesn't have any table named " + tableName
									+ ".  Please make sure the table exists. Also note that some databases are case sensitive."
									+ getDatabaseTables());
						}
					}
				}
				// BUG [ 596044 ] Case in table names - relationships
				// Update the tableElement with the name reported by the
				// resultset.
				// The case might not be the same, and some drivers want correct
				// case
				// in getCrossReference/getExportedKeys which we'll call later.
				schemaName = Util.ensureNotNull(tableRs.getString("TABLE_SCHEM"));
				String realTableName = tableRs.getString("TABLE_NAME");
				String tableType = tableRs.getString("TABLE_TYPE");
				tableElement.setPhysicalName(realTableName);

				if ("SYNONYM".equals(tableType) && isOracle()) {
					tableElement.setOwnerSynonymName(getSynonymOwner(realTableName));
				}
				// do this for non-synonyms only
				// Test for tables in different schemas
				String alreadySchema = tableSchemaMap.get(realTableName);
				if (alreadySchema != null) {
					throw new MiddlegenException("The table named " + realTableName + " was found both in the schema "
							+ "named " + alreadySchema + " and in the schema named " + schemaName + ". "
							+ "You have to specify schema=\"something\" in the middlegen task.");
				}

				tableSchemaMap.put(realTableName, schemaName);

				// Some more schema sanity testing
				// do for non-synonyms only
				if (!"".equals(schemaName) && !"null".equals(schemaName) && !Util.equals(_schema, schemaName)
						&& !("SYNONYM".equals(tableType) && isOracle())) {
					LogUtils.log("The table named " + realTableName + " was found in the schema " + "named \""
							+ schemaName + "\". However, Middlegen was not configured "
							+ "to look for tables in a specific schema. You should consider specifying " + "schema=\""
							+ schemaName + "\" instead of schema=\"" + _schema + "\" in the middlegen task.");
				}
			} finally {
				try {
					if(tableRs!=null) {
						tableRs.close();
					}
				} catch (SQLException ignore) {
				} catch (NullPointerException ignore) {
				}
			}
			DbTable table = new DbTable(tableElement, schemaName);
			table.init();
			middlegen.addTable(table);
		}
	}

}
