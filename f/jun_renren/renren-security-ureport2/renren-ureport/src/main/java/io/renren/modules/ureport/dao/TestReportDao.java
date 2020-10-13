package io.renren.modules.ureport.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestReportDao {

	public List<Map<String, Object>> queryList();
	
}
