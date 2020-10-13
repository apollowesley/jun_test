package io.renren.modules.ureport.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.modules.ureport.dao.TestReportDao;

@Service("testReportService")
public class TestReportService {

	@Autowired
	private TestReportDao testReportDao;
	
	public List<Map<String,Object>> loadReportData(String dsName,String datasetName,Map<String,Object> parameters){
		List<Map<String,Object>> result = testReportDao.queryList();
        return result;
    }
	
    public List<Map<String,Object>> buildReport(String dsName,String datasetName,Map<String,Object> parameters){
        return null;
    }
	
}
