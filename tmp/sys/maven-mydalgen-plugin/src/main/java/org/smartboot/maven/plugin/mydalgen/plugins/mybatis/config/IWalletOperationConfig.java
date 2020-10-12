package org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.LogUtils;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.SqlParser;

import Zql.ZStatement;

/**
 * @author Seer
 * @version IWalletOperationConfig.java, v 0.1 2015年7月25日 上午10:27:10 Seer Exp.
 */
public class IWalletOperationConfig {

	public static final String CDATA_START = "${cdata-start}";
	public static final String CDATA_END = "${cdata-end}";

	/** the name (method name) of this operation */
	private String name;

	/** how to pass parameters to dao: primitive/object */
	private String paramType;

	/** the multiplicity of results: one/many */
	private String multiplicity;

	private String parameterClass;

	/**
	 * the name of the resultMap, if null then use the table's default result
	 * map
	 */
	private String resultMap;

	/** the full qualified class name of the result class */
	private String resultType;

	/** the instance of the table configuration as container */
	private IWalletTableConfig tableConfig;

	/** the parsed sql */
	private SqlParser sqlParser;

	private String append;

	/** extra params */
	private List<IWalletParamConfig> extraParams = new ArrayList<IWalletParamConfig>();

	/**
	 * the sql statement of the operation.
	 *
	 * <p>
	 * Not all operations have explictly specified sql statement. The sql
	 * statement for operations of some types may be deducible from table meta
	 * data.
	 * */
	private String sql;

	/**
	 * the customized sqlmap mapped statement.
	 */
	private String sqlmap;

	// added by yangyanzhao 2010-02-08
	/** operation description */
	private String description;

	/**
	 * Constructor for IWalletOperationConfig.
	 */
	public IWalletOperationConfig() {
		super();
	}

	/**
	 * @return
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param string
	 */
	public void setSql(String string) {
		if (string == null) {
			sql = "";
		} else {
			sql = string;
		}

		if (sql.endsWith(";")) {
			sqlParser = new SqlParser(sql);
		} else {
			sqlParser = new SqlParser(sql + ";");
		}

		if (sqlParser == null) {
			LogUtils.waring("Can't parse sql: " + sql);
		}
	}

	/**
	 * @return
	 */
	public IWalletTableConfig getTableConfig() {
		return tableConfig;
	}

	/**
	 * @param config
	 */
	public void setTableConfig(IWalletTableConfig config) {
		tableConfig = config;
	}

	/**
	 * @return
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @return
	 */
	public ZStatement getZst() {
		if (sqlParser != null) {
			return sqlParser.getZst();
		} else {
			return null;
		}
	}

	/**
	 * @return
	 */
	public String getMultiplicity() {
		return multiplicity;
	}

	/**
	 * @return
	 */
	public String getParamType() {
		return paramType;
	}

	/**
	 * @param string
	 */
	public void setMultiplicity(String string) {
		multiplicity = string;
	}

	/**
	 * @param string
	 */
	public void setParamType(String string) {
		paramType = string;
	}

	/**
	 * @return
	 */
	public SqlParser getSqlParser() {
		return sqlParser;
	}

	/**
	 * @param string
	 */
	public void setSqlmap(String string) {
		if (StringUtils.isBlank(string)) {
			return;
		}

		String temp = StringUtils.replace(string, CDATA_START, "<![CDATA[");

		temp = StringUtils.replace(temp, CDATA_END, "]]>");

		sqlmap = temp;
	}

	/**
	 * @return
	 */
	public String getSqlmap(List<String> params) {
		if (params == null) {
			return sqlmap;
		}

		String temp = sqlmap;

		if (params.size() == 1) {
			temp = StringUtils.replace(temp, "${param1}", "value");
		} else {
			for (int i = 0; i < params.size(); i++) {
				temp = StringUtils.replace(temp, "${param" + (i + 1) + "}", params.get(i));
			}
		}

		return temp;
	}

	/**
	 * @return
	 */
	public boolean isHasSqlmap() {
		return StringUtils.isNotBlank(sqlmap);
	}

	/**
	 * @return
	 */
	public String getResultMap() {
		return resultMap;
	}

	/**
	 * @param string
	 */
	public void setResultMap(String string) {
		resultMap = string;
	}

	public final String getResultType() {
		return resultType;
	}

	public final void setResultType(String resultType) {
		this.resultType = resultType;
	}

	/**
	 * @return Returns the extraParams.
	 */
	public List<IWalletParamConfig> getExtraParams() {
		return extraParams;
	}

	/**
	 * Add one more extra params.
	 *
	 * @param paramConfig
	 */
	public void addExtraParam(IWalletParamConfig paramConfig) {
		extraParams.add(paramConfig);
	}

	/**
	 * @return Returns the append.
	 */
	public String getAppend() {
		return append;
	}

	/**
	 * @param append
	 *            The append to set.
	 */
	public void setAppend(String append) {
		this.append = append;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		if (description == null) {
			this.description = "";
		} else {
			this.description = description;
		}
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	public String getParameterClass() {
		return parameterClass;
	}

	public void setParameterClass(String parameterClass) {
		this.parameterClass = parameterClass;
	}
}
