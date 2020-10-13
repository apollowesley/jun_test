package com.leech.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leech.dao.DatatablesMapper;
import com.leech.model.DatatablesBean;
import com.leech.pagemodel.base.datatables.DataSet;
import com.leech.pagemodel.base.datatables.PageCriterias;
import com.leech.service.DatatablesService;

@Service
public class DatatablesServiceImpl implements DatatablesService {
	@Autowired
	private DatatablesMapper datatablesMapper;

	@Override
	public List<DatatablesBean> findPaginated(Map<String, Object> params) {
		return datatablesMapper.findPaginated(params);
	}

	@Override
	public long findPaginatedCount(Map<String, Object> params) {
		return datatablesMapper.findPaginatedCount(params);
	}

	@Override
	public DataSet<DatatablesBean> findDatatablesWithPageCriterias(PageCriterias pcs) {
		List<DatatablesBean> rows = datatablesMapper.findPaginated(getParams(pcs));
		long count = datatablesMapper.findPaginatedCount(getParams(pcs));
		DataSet<DatatablesBean> dataSet = new DataSet<DatatablesBean>(rows, count, count);
		return dataSet;
	}

	private Map<String,Object> getParams(PageCriterias pcs){
		Map<String, Object> params = new HashMap<String, Object>();
		if(null != pcs) {
			params.put("offset", pcs.getStart());
			params.put("pagesize", pcs.getLength());
			params.put("search", pcs.getSearchValue());  //查询输入框的值
			
			//String column = pcs.getOrderColumn();
			String order = pcs.getOrderValue();
			String field = pcs.getOrderField();
			if("0".equals(field)) {
				params.put("sortName", " first_name "+order); //排序字段值
			}else if("1".equals(field)) {
				params.put("sortName", " last_name "+order);
			}else if("2".equals(field)) {
				params.put("sortName", " position "+order);
			}else if("3".equals(field)) {
				params.put("sortName", " office "+order);
			}else if("4".equals(field)) {
				params.put("sortName", " start_date "+order);
			}else if("5".equals(field)) {
				params.put("sortName", " age "+order);
			}else if("6".equals(field)) {
				params.put("sortName", " salary "+order);
			}else {
				params.put("sortName", " id "+order);
			}
		}
		return params;
	}
}	
