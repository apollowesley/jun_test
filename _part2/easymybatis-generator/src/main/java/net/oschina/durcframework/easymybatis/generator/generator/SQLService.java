package net.oschina.durcframework.easymybatis.generator.generator;

import net.oschina.durcframework.easymybatis.generator.entity.DataBaseConfig;

public interface SQLService {

	public TableSelector getTableSelector(DataBaseConfig dataBaseConfig);

}
