package net.oschina.durcframework.easymybatis.generator.generator.oracle;

import net.oschina.durcframework.easymybatis.generator.entity.DataBaseConfig;
import net.oschina.durcframework.easymybatis.generator.generator.SQLService;
import net.oschina.durcframework.easymybatis.generator.generator.TableSelector;

public class OracleService implements SQLService {

	@Override
	public TableSelector getTableSelector(DataBaseConfig dataBaseConfig) {
		return new OracleTableSelector(new OracleColumnSelector(dataBaseConfig), dataBaseConfig);
	}


}
