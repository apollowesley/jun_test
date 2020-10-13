package com.bodsite.common.datasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.bodsite.common.logger.Logger;
import com.bodsite.common.logger.LoggerFactory;

/**
 * @Description:动态数据源
 * @author bod
 * @date
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
	private DataSource master;
	private List<DataSource> slaves;
    private int slaveSize; //读数据源个数
	private Integer strategy;// 默认，0：轮询，1，随机
	private AtomicInteger counter = new AtomicInteger();
	private Map<Object, Object> targetDataSources = new HashMap<>();

	/******************* 1、加载数据源关系， 设置数据源名，会根据数据源名返回数据源 ************/
	/**
	 * 设置数据源名
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return getDataSourceName();
	}

	/**
	 * 设置数据源映射关系
	 */
	@Override
	public void afterPropertiesSet() {
		if (this.master == null) {
			throw new IllegalArgumentException("Property 'targetDataSources' is required");
		}
		setDefaultTargetDataSource(master);
		targetDataSources.put(DataSourceHandler.DYNAMIC_DATA_SOURCE.MASTER.name(), master);
		if (slaves != null && !slaves.isEmpty()) {
			this.slaveSize = slaves.size();
			for (int i = 0; i < slaveSize; i++) {
				targetDataSources.put(DataSourceHandler.DYNAMIC_DATA_SOURCE.SLAVE.name() + i, slaves.get(i));
			}
		}
		setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}

	/******************* 2、直接返回数据源 ***********************/
	/**
	 * 原方法：根据数据源名称返回数据源，自定义直接返回数据源
	 */
	/*@Override
	protected DataSource determineTargetDataSource() {
		return (DataSource) targetDataSources.get(getDataSourceName());
	}*/

	/**
	 * 获取数据源名称
	 * @author bod
	 */
	public String getDataSourceName() {
		String dataSourceName = null;
		if (DataSourceHandler.isMaster()) {
			dataSourceName = DataSourceHandler.DYNAMIC_DATA_SOURCE.MASTER.name();
		} else if (DataSourceHandler.isSlave() && slaves != null && !slaves.isEmpty()) {
			int index = 0;
			if (strategy == null || strategy == 0) {
				int count = counter.incrementAndGet();
				index = count%slaveSize;
			}else if(strategy == 1){
	            index = ThreadLocalRandom.current().nextInt(0, slaveSize);
			}
			dataSourceName = DataSourceHandler.DYNAMIC_DATA_SOURCE.SLAVE.name()+index;
		}else{
			dataSourceName = DataSourceHandler.DYNAMIC_DATA_SOURCE.MASTER.name();
		}
		logger.info("This data source name is "+dataSourceName);
		return dataSourceName;
	}
	public DataSource getMaster() {
		return master;
	}

	public void setMaster(DataSource master) {
		this.master = master;
	}

	public List<DataSource> getSlaves() {
		return slaves;
	}

	public void setSlaves(List<DataSource> slaves) {
		this.slaves = slaves;
	}

	public Integer getStrategy() {
		return strategy;
	}

	public void setStrategy(Integer strategy) {
		this.strategy = strategy;
	}

}
