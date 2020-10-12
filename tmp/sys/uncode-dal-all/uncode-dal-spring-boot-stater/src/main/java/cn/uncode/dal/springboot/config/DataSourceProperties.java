package cn.uncode.dal.springboot.config;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSourceProperties {
	
	protected String driverClassName;

	/** 初始化连接  */
	protected int initialSize = 5;
	/** 最大连接数量 */
	protected int maxTotal = 50;
	/** 最大空闲连接 */
	protected int maxIdle = 10;
	/** 最小空闲连接  */
	protected int minIdle = 3;
	/** 超时等待时间,(以毫秒为单位) */
	protected int maxWaitMillis = 3000;
	protected boolean defaultAutoCommit = true;
	/** 是否自动回收超时连接 */
	protected boolean removeAbandonedOnBorrow = true;
	/** 回收超时时间,(以秒为单位) */
	protected int removeAbandonedTimeout = 60;
	/** 指明是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个. */
	protected boolean testOnBorrow = true;
	/** 查询前验证链接是否有效，保证查询成功 */
	protected String validationQuery = "SELECT 1";
	/** 打开PSCache，并且指定每个连接上PSCache的大小 */
	protected int maxPoolPreparedStatementPerConnectionSize;
	protected boolean poolPreparedStatements = false;
	
	
	public void coinfig(BasicDataSource dataSource) {
        dataSource.setDriverClassName(driverClassName);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxTotal(maxTotal);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWaitMillis(maxWaitMillis);
        dataSource.setDefaultAutoCommit(defaultAutoCommit);
        dataSource.setRemoveAbandonedOnBorrow(removeAbandonedOnBorrow);
        dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setValidationQuery(validationQuery);
        // 打开PSCache，并且指定每个连接上PSCache的大小
        if(poolPreparedStatements){
        	dataSource.setPoolPreparedStatements(poolPreparedStatements);
            dataSource.setMaxOpenPreparedStatements(maxPoolPreparedStatementPerConnectionSize);
        }
    }
	
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}


	public int getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}


	public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}


	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}


	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}


	public int getMaxTotal() {
		return maxTotal;
	}
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public int getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	public int getMaxWaitMillis() {
		return maxWaitMillis;
	}
	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}
	public boolean isDefaultAutoCommit() {
		return defaultAutoCommit;
	}
	public void setDefaultAutoCommit(boolean defaultAutoCommit) {
		this.defaultAutoCommit = defaultAutoCommit;
	}
	public boolean isRemoveAbandonedOnBorrow() {
		return removeAbandonedOnBorrow;
	}
	public void setRemoveAbandonedOnBorrow(boolean removeAbandonedOnBorrow) {
		this.removeAbandonedOnBorrow = removeAbandonedOnBorrow;
	}
	public int getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}
	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}
	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	public String getValidationQuery() {
		return validationQuery;
	}
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}
	
	
	

}
