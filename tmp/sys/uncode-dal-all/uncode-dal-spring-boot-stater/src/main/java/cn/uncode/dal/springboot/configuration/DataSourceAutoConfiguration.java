package cn.uncode.dal.springboot.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.uncode.dal.datasource.DynamicDataSource;
import cn.uncode.dal.springboot.config.MasterDataSourceConfig;
import cn.uncode.dal.springboot.config.SlavesDataSourceConfig;
import cn.uncode.dal.springboot.config.StandbyDataSourceConfig;
import cn.uncode.dal.springboot.config.UncodeDALShardingConfig;

/**
 * Created by KevinBlandy on 2017/2/28 14:11
 */
@Configuration
@EnableConfigurationProperties({MasterDataSourceConfig.class, 
	StandbyDataSourceConfig.class, SlavesDataSourceConfig.class, UncodeDALShardingConfig.class})
public class DataSourceAutoConfiguration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceAutoConfiguration.class);
	
	@Autowired
	private MasterDataSourceConfig masterConfig;
	
	@Autowired
	private StandbyDataSourceConfig standbyConfig;
	
	@Autowired
	private SlavesDataSourceConfig slavesConfig;
	
	@Autowired
	private UncodeDALShardingConfig uncodeDALShardingConfig;
	
	@Bean
	public DataSource dataSource(){
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		//默认库为主库
		dynamicDataSource.setResolvedMasterDataSource(this.masterConfig.build());
		dynamicDataSource.setResolvedStandbyDataSource(standbyConfig.build());
		dynamicDataSource.setResolvedSlaveDataSources(slavesConfig.build());
		if(uncodeDALShardingConfig != null && uncodeDALShardingConfig.getTable() != null){
			Map<String, String> shardingTable = new HashMap<String, String>();
			int len = uncodeDALShardingConfig.getTable().size();
			for(int i=0; i<len; i++){
				String table = uncodeDALShardingConfig.getTable().get(i);
				String type = uncodeDALShardingConfig.getType().get(i);
				String field = uncodeDALShardingConfig.getField().get(i);
				String value = uncodeDALShardingConfig.getValue().get(i);
				//range:字段:表1@1-2,表2@3-4
				StringBuilder sb = new StringBuilder();
				sb.append(type).append(":").append(field).append(":").append(value);
				shardingTable.put(table, sb.toString());
			}
			dynamicDataSource.setTableShardingRules(shardingTable);
		}
		LOGGER.info("=====>DynamicDataSource inited..");
		return dynamicDataSource;
	}
}
