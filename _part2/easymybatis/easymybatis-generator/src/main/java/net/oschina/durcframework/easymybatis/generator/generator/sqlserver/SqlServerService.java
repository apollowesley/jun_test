package net.oschina.durcframework.easymybatis.generator.generator.sqlserver;

import net.oschina.durcframework.easymybatis.generator.entity.DataBaseConfig;
import net.oschina.durcframework.easymybatis.generator.generator.SQLService;
import net.oschina.durcframework.easymybatis.generator.generator.TableSelector;

public class SqlServerService implements SQLService {

	@Override
	public TableSelector getTableSelector(DataBaseConfig dataBaseConfig) {
		return new SqlServerTableSelector(new SqlServerColumnSelector(dataBaseConfig), dataBaseConfig);
	}

}
