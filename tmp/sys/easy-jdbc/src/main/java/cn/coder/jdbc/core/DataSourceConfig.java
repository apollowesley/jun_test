package cn.coder.jdbc.core;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DataSourceConfig {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	private final String driverClassName;
	private final String url;
	private final String username;
	private final String password;
	private final Integer initialSize;
	private final String sourceName;
	private final int queryTimeout;

	private int loginTimeout;

	public DataSourceConfig(String sourceName, Properties p) {
		this.sourceName = sourceName;
		sourceName = "default".equals(sourceName) ? "" : ("." + sourceName);
		this.driverClassName = p.getProperty("jdbc.datasource" + sourceName + ".driverClassName");
		String tempUrl = p.getProperty("jdbc.datasource" + sourceName + ".url");
		if ("true".equals(p.getProperty("jdbc.multiQueries", "true").toLowerCase())) {
			this.url = tempUrl + (tempUrl.contains("?") ? "&" : "?") + "allowMultiQueries=true";
		} else {
			this.url = tempUrl;
		}
		this.queryTimeout = Integer.parseInt(p.getProperty("jdbc.queryTimeout", "5"));
		this.username = p.getProperty("jdbc.datasource" + sourceName + ".username");
		this.password = p.getProperty("jdbc.datasource" + sourceName + ".password");
		this.initialSize = Integer.parseInt(p.getProperty("jdbc.datasource" + sourceName + ".initialSize", "2"));

		printLog();
	}

	private void printLog() {
		if (logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("\n====================  dataSource  ====================");
			sb.append("\njdbc.sourceName                   ").append(this.sourceName);
			sb.append("\njdbc.driverClassName              ").append(this.driverClassName);
			sb.append("\njdbc.queryTimeout                 ").append(this.queryTimeout);
			sb.append("\njdbc.url                          ").append(this.url);
			sb.append("\njdbc.username                     ").append(this.username);
			sb.append("\njdbc.password                     ").append(this.password);
			sb.append("\njdbc.initialSize                  ").append(this.initialSize);
			logger.debug(sb.toString());
		}
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Integer getInitialSize() {
		return initialSize;
	}

	public String getSourceName() {
		return sourceName;
	}

	public int getQueryTimeout() {
		return queryTimeout;
	}

	public int getLoginTimeout() {
		return loginTimeout;
	}

	public void setLoginTimeout(int seconds) {
		this.loginTimeout = seconds;
	}

}
