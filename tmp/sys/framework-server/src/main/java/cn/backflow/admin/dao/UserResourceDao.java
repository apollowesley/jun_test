package cn.backflow.admin.dao;

import cn.backflow.admin.common.pagination.Page;
import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.dao.base.BaseMyBatisDao;
import cn.backflow.admin.entity.UserResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserResourceDao extends BaseMyBatisDao<UserResource, Integer> {

    public Page<UserResource> findByPageRequest(PageRequest pr) {
        return pageQuery("UserResource.paging", pr);
    }

    public int insertBatch(List<UserResource> urs) {
        return getSqlSession().insert("UserResource.insertBatch", urs);
    }

    public int deleteByParameter(Object parameter) {
        return getSqlSession().delete("UserResource.delete", parameter);
    }

    public List<Map<String, Object>> findByParameter(Map<String, Object> parameter) {
        return getSqlSession().selectList("UserResource.findByParameter", parameter);
    }
}