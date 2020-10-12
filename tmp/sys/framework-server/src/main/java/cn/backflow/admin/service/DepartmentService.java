package cn.backflow.admin.service;

import cn.backflow.admin.dao.DepartmentDao;
import cn.backflow.admin.dao.UserDao;
import cn.backflow.admin.entity.Department;
import cn.backflow.admin.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DepartmentService extends BaseService<Department, Integer> {

    private final DepartmentDao departmentDao;
    private final UserDao userDao;

    @Autowired
    public DepartmentService(DepartmentDao departmentDao, UserDao userDao) {
        this.departmentDao = departmentDao;
        this.userDao = userDao;
    }

    public Map<Comparable, Department> findMap(Object filters) {
        return departmentDao.findMap(filters, "id");
    }

    /**
     * 更新部门排序
     *
     * @param id   部门ID
     * @param from 起始位置
     * @param to   结束位置
     * @return 修改影响记录数
     */
    @Transactional
    public int updatePriority(int id, int from, int to) {
        List<Department> siblings = findSiblings(id, false);
        for (int i = 0, len = siblings.size(); i < len; i++) {
            Department d = siblings.get(i);
            d.setPriority(i);
            if (i == from) {
                d.setPriority(to);
            }
            if (i == to) {
                d.setPriority(from);
            }
        }
        // TODO 优化: 通过UPDATE单个priority字段更新
        return departmentDao.updateBatchSelective(siblings);
    }

    /**
     * 查找兄弟部门
     *
     * @param id             部门ID
     * @param excludeCurrent 是否排除当前部门
     * @return 所有同层级的部门列表
     */
    public List<Department> findSiblings(Integer id, boolean excludeCurrent) {
        return departmentDao.findSiblings(id, excludeCurrent);
    }

    @Override
    @Transactional
    public int save(Department entity) throws DataAccessException {
        prepareForSaveOrUpdate(entity);
        return super.update(entity);
    }

    @Override
    @Transactional
    public int update(Department entity) throws DataAccessException {
        prepareForSaveOrUpdate(entity);
        return super.update(entity);
    }

    @Override
    @Transactional
    public int saveOrUpdate(Department entity) throws DataAccessException {
        prepareForSaveOrUpdate(entity);
        return super.saveOrUpdate(entity);
    }

    @Override
    @Transactional
    public int deleteById(Integer id) throws DataAccessException {
        userDao.separateDepartment(id);
        return super.deleteById(id);
    }

    /**
     * 设置部门层级与祖先路径
     */
    @Transactional
    private void prepareForSaveOrUpdate(Department department) {
        if (department.getId() == null)
            departmentDao.save(department);
        int level = 1;
        String ancestors = department.getId().toString();
        if (department.getParent() != null) {
            Department parent = getById(department.getParent());
            level = parent.getLevel() + 1;
            ancestors = parent.getAncestors() + "," + ancestors;
        }
        department.setLevel(level);
        department.setAncestors(ancestors);
    }
}