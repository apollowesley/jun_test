package cn.backflow.admin.dao.base;

import cn.backflow.admin.common.pagination.Page;
import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.entity.base.BaseEntity;
import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseDao<E extends BaseEntity, PK extends Serializable> {

    /**
     * 执行SQL查询语句
     */
    Object select(String sql) throws DataAccessException;

    /**
     * 按ID删除数据
     */
    int deleteById(PK id) throws DataAccessException;

    /**
     * 批量删除数据
     */
    int deleteBatch(Collection<PK> pks) throws DataAccessException;

    /**
     * 插入数据
     */
    int save(E entity) throws DataAccessException;

    /**
     * 批量插入
     */
    int insertBatch(List<E> entities) throws DataAccessException;

    /**
     * 更新数据
     */
    int update(E entity) throws DataAccessException;

    /**
     * 批量更新
     */
    int updateBatch(List<E> entities) throws DataAccessException;

    /**
     * 选择性更新
     */
    int updateSelective(Object entity);

    /**
     * 批量更新
     */
    <T> int updateBatchSelective(List<T> entities) throws DataAccessException;

    /**
     * 根据id检查是否插入或是更新数据
     */
    int saveOrUpdate(E entity) throws DataAccessException;

    /**
     * 检查某属性是否唯一
     */
    boolean isUnique(String uniqueProperty) throws DataAccessException;

    /**
     * 用于hibernate.flush() 有些dao实现不需要实现此类
     */
    void flush() throws DataAccessException;

    /**
     * 按主键获取对象
     */
    E getById(PK id) throws DataAccessException;

    /**
     * 查询返回所数据库中所有对象
     */
    List<E> findAll(Object parameter) throws DataAccessException;

    /**
     * 查询返回Map映射
     */
    <T> Map<T, E> findMap(Object parameter, String mapKey);

    /**
     * 分页查询
     */
    Page<E> findByPageRequest(PageRequest pr);
}