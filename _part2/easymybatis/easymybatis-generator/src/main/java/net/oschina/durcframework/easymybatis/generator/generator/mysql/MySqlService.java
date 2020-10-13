package net.oschina.durcframework.easymybatis.generator.generator.mysql;

import net.oschina.durcframework.easymybatis.generator.entity.DataBaseConfig;
import net.oschina.durcframework.easymybatis.generator.generator.SQLService;
import net.oschina.durcframework.easymybatis.generator.generator.TableSelector;

public class MySqlService implements SQLService {

	@Override
	public TableSelector getTableSelector(DataBaseConfig dataBaseConfig) {
		return new MySqlTableSelector(new MySqlColumnSelector(dataBaseConfig), dataBaseConfig);
	}

}
