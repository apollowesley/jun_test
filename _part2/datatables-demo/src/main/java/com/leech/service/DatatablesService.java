package com.leech.service;

import java.util.List;
import java.util.Map;

import com.leech.model.DatatablesBean;
import com.leech.pagemodel.base.datatables.DataSet;
import com.leech.pagemodel.base.datatables.PageCriterias;

public interface DatatablesService {

	 /**
     * 分页查询
     * @param params
     * @return
     */
    public List<DatatablesBean> findPaginated(Map<String,Object> params);
    
	 /**
	  * 查询总记录数
	  * @param params
	  * @return
	  */
    public long findPaginatedCount(Map<String,Object> params);
    
    /**
     * 分页查询
     * @param pcs
     * @return
     */
	public DataSet<DatatablesBean> findDatatablesWithPageCriterias(PageCriterias pcs);
	
}
