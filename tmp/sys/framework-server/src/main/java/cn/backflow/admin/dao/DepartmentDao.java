package cn.backflow.admin.dao;

import cn.backflow.admin.common.pagination.Page;
import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.dao.base.BaseMyBatisDao;
import cn.backflow.admin.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DepartmentDao extends BaseMyBatisDao<Department, Integer> {


    @Override
    public int saveOrUpdate(Department department) {
        return department.getId() == null ? save(department) : update(department);
    }

    public Page<Department> findByPageRequest(PageRequest pageRequest) {
        return pageQuery("Department.paging", pageRequest);
    }

    public List<Department> findSiblings(Integer id, boolean excludeCurrent) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("excludeCurrent", excludeCurrent);
        parameter.put("id", id);
        return getSqlSession().selectList("Department.findSiblings", parameter);
    }
}