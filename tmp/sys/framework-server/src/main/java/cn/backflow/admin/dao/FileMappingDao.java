package cn.backflow.admin.dao;

import cn.backflow.admin.common.pagination.Page;
import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.dao.base.BaseMyBatisDao;
import cn.backflow.admin.entity.FileMapping;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class FileMappingDao extends BaseMyBatisDao<FileMapping, java.lang.Integer> {

    public Page<FileMapping> findByPageRequest(PageRequest pr) {
        return pageQuery("FileMapping.paging", pr);
    }

    public int save(List<FileMapping> mappings) {
        return getSqlSession().insert("FileMapping.insertBatch", mappings);
    }

    public List<FileMapping> findByKeys(List<String> keys) {
        return getSqlSession().selectList("FileMapping.findByKeys", keys);
    }

    public <T> Map<T, FileMapping> findMapByKeys(List<String> keys, String mapKey) {
        return getSqlSession().selectMap("FileMapping.findByKeys", keys, mapKey);
    }

    public int deleteByKeys(String[] keys) {
        return getSqlSession().delete("FileMapping.deleteByKeys", keys);
    }

    public List<String> findKeyByIds(Collection<Integer> ids) {
        return getSqlSession().selectList("FileMapping.findKeyByIds", ids);
    }

    public List<FileMapping> findByIds(List<Integer> ids) {
        return getSqlSession().selectList("FileMapping.findByIds", ids);
    }
}