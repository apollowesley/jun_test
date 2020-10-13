package com.leech.dao;

import java.util.List;
import java.util.Map;

import com.leech.model.DatatablesBean;

public interface DatatablesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DatatablesBean record);

    int insertSelective(DatatablesBean record);

    DatatablesBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DatatablesBean record);

    int updateByPrimaryKey(DatatablesBean record);
    
    List<DatatablesBean> findPaginated(Map<String, Object> params);

	long findPaginatedCount(Map<String, Object> params);
}