package cn.backflow.admin.service.base;

import cn.backflow.admin.common.pagination.Page;
import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.dao.base.BaseDao;
import cn.backflow.admin.entity.base.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


@Transactional(readOnly = true)
public abstract class BaseService<E extends BaseEntity, PK extends Serializable> {

    protected BaseDao<E, PK> repository;

    @Autowired
    public void setRepository(BaseDao<E, PK> repository) {
        this.repository = repository;
    }

    public Object select(String sql) throws DataAccessException {
        return repository.select(sql);
    }

    /**
     * 按主键获取对象
     * @param id 主建
     */
    public E getById(PK id) throws DataAccessException {
        return repository.getById(id);
    }

    /**
     * 查询所有记录
     * @param parameter 查询参数(实体或Map)
     */
    public List<E> findAll(Object parameter) throws DataAccessException {
        return repository.findAll(parameter);
    }

    /**
     * 查找mapKey与实体映射的 Map集合
     *
     * @param parameter 查询参数(实体或Map)
     * @param mapKey    要映射到Map的key, 应为实体的某个属性名
     */
    public Map<Comparable, E> findMap(Object parameter, String mapKey) {
        return repository.findMap(parameter, mapKey);
    }

    /**
     * 分页查询
     *
     * @param pr 分页查询请求
     */
    public Page<E> findPage(PageRequest pr) {
        return repository.findByPageRequest(pr);
    }

    /**
     * 检查某属性是否唯一
     *
     * @param uniqueProperty 要检查唯一的属性名
     */
    public boolean isUnique(String uniqueProperty) throws DataAccessException {
        return repository.isUnique(uniqueProperty);
    }

    /**
     * 插入数据
     */
    @Transactional
    public int save(E entity) throws DataAccessException {
        return repository.save(entity);
    }

    /**
     * 更新数据
     */
    @Transactional
    public int update(E entity) throws DataAccessException {
        return repository.update(entity);
    }

    @Transactional
    public int updateSelective(Object entity) {
        return repository.updateSelective(entity);
    }

    /**
     * 根据id检查是否插入或是更新数据
     */
    @Transactional
    public int saveOrUpdate(E entity) throws DataAccessException {
        return repository.saveOrUpdate(entity);
    }

    /**
     * 按主键删除
     *
     * @param id 主键
     */
    @Transactional
    public int deleteById(PK id) throws DataAccessException {
        return repository.deleteById(id);
    }

    /**
     * 按主键集合批量删除
     *
     * @param pks 主键集合
     */
    @Transactional
    public int deleteBatch(Collection<PK> pks) throws DataAccessException {
        return repository.deleteBatch(pks);
    }
}