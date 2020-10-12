package cn.backflow.admin.dao;

import cn.backflow.admin.common.pagination.Page;
import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.dao.base.BaseMyBatisDao;
import cn.backflow.admin.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao extends BaseMyBatisDao<User, Integer> {

    @Override
    public int saveOrUpdate(User user) {
        return user.getId() == null ? save(user) : update(user);
    }

    public Page<User> findByPageRequest(PageRequest pageRequest) {
        return pageQuery("User.paging", pageRequest);
    }

    public int updatePass(String name, String pass) {
        Map<String, String> param = new HashMap<>(2);
        param.put("name", name);
        param.put("pass", pass);
        return getSqlSession().update("User.updatePass", param);
    }

    public Long count() {
        return getSqlSession().selectOne("User.count");
    }

    public List<User> search(PageRequest pr) {
        pr.getFilters().put("_offset_", pr.getPageOffset());
        pr.getFilters().put("_limit_", pr.getPageSize());
        pr.getFilters().put("_sort_", pr.getSortColumns());
        return getSqlSession().selectList("User.search", pr.getFilters());
    }

    public int updateSelective(User user) {
        return getSqlSession().update("User.updateSelective", user);
    }

    public Page<User> query(PageRequest pr) {
        return pageQuery("User.query", pr);
    }

    /**
     * 分离部门关联
     *
     * @param id 部门ID
     */
    public int separateDepartment(Integer id) {
        return getSqlSession().update("User.separateDepartment", id);
    }

    /**
     * 按唯一属性查找
     *
     * @param identity 唯一属性（id, name, email, phone）
     */
    public User getByIdentity(Object identity) {
        return getSqlSession().selectOne("User.getByIdentity", identity);
    }
}