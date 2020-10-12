package cn.backflow.admin.dao.base;

import cn.backflow.admin.common.pagination.Page;
import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.entity.base.BaseEntity;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class BaseMyBatisDao<E extends BaseEntity, PK extends Serializable> extends SqlSessionDaoSupport implements BaseDao<E, PK> {

    private Class<E> entityClass = getEntityClass();

    private SqlSessionFactory sqlSessionFactory;

    @SuppressWarnings("unchecked")
    private Class<E> getEntityClass() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<E>) (parameterizedType.getActualTypeArguments()[0]);
    }

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public Object select(String sql) throws DataAccessException {
        return getSqlSession().update(entityClass.getSimpleName() + ".select", sql);
    }

    @Override
    public void flush() { /* ignore */ }

    /**
     * 用于子类覆盖,在insert,update之前调用
     */
    protected void prepareForSaveOrUpdate(E o) {
    }

    public String getFindAllQuery() {
        return entityClass.getSimpleName() + ".findAll";
    }

    public String getPageQuery() {
        return entityClass.getSimpleName() + ".paging";
    }

    public String getFindByPrimaryKeyQuery() {
        return entityClass.getSimpleName() + ".getById";
    }

    public String getInsertQuery() {
        return entityClass.getSimpleName() + ".insert";
    }

    public String getInsertBatchQuery() {
        return entityClass.getSimpleName() + ".insertBatch";
    }

    public String getUpdateQuery() {
        return entityClass.getSimpleName() + ".update";
    }

    public String getUpdateSelectiveQuery() {
        return entityClass.getSimpleName() + ".updateSelective";
    }

    public String getDeleteQuery() {
        return entityClass.getSimpleName() + ".delete";
    }

    public String getBatchDeleteQuery() {
        return entityClass.getSimpleName() + ".deleteBatch";
    }

    public String getCountQuery(String statement) {
        return statement == null ? getPageQuery() : statement + "Count";
    }

    @Override
    public E getById(PK primaryKey) {
        return getSqlSession().selectOne(getFindByPrimaryKeyQuery(), primaryKey);
    }

    @Override
    public int deleteById(PK id) {
        return getSqlSession().delete(getDeleteQuery(), id);
    }

    @Override
    public int deleteBatch(Collection<PK> pks) {
        return getSqlSession().delete(getBatchDeleteQuery(), pks);
    }

    @Override
    public int save(E entity) {
        prepareForSaveOrUpdate(entity);
        return getSqlSession().insert(getInsertQuery(), entity);
    }

    public int insertBatch(List<E> entities) {
        return getSqlSession().insert(getInsertBatchQuery(), entities);
    }

    @Override
    public int update(E entity) {
        prepareForSaveOrUpdate(entity);
        return getSqlSession().update(getUpdateQuery(), entity);
    }

    @Override
    public int updateBatch(List<E> entities) throws DataAccessException {
        int i = 0;
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            for (E entity : entities) {
                i += sqlSession.update(getUpdateQuery(), entity);
            }
            sqlSession.commit();
            return i;
        }
    }

    @Override
    public <T> int updateBatchSelective(List<T> entities) throws DataAccessException {
        int i = 0;
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            for (T entity : entities) {
                i += sqlSession.update(getUpdateSelectiveQuery(), entity);
            }
            sqlSession.commit();
            return i;
        }
    }

    @Override
    public int updateSelective(Object entity) {
        return getSqlSession().update(getUpdateSelectiveQuery(), entity);
    }

    public int saveOrUpdate(E entity) {
        prepareForSaveOrUpdate(entity);
        return entity.getId() == null ? save(entity) : update(entity);
    }

    @Override
    public boolean isUnique(String uniquePropertyNames) {
        throw new UnsupportedOperationException();
    }

    public List<E> findAll(Object parameter) {
        return getSqlSession().selectList(getFindAllQuery(), parameter);
    }

    public <T> Map<T, E> findMap(Object parameter, String mapKey) {
        return getSqlSession().selectMap(getFindAllQuery(), parameter, mapKey);
    }

    public Page<E> findByPageRequest(PageRequest pageRequest) {
        return pageQuery(getPageQuery(), pageRequest);
    }

    protected Page<E> pageQuery(String statement, PageRequest pr) {
        Long count = getSqlSession().selectOne(getCountQuery(statement), pr.getFilters());
        Page<E> page = new Page<>(pr, count.intValue());
        if (count > 0) {
            pr.getFilters().put("_offset_", page.getFirstElementIndex());
            pr.getFilters().put("_limit_", page.getPageSize());
            pr.getFilters().put("_sort_", pr.getSortColumns());
            List<E> list = getSqlSession().selectList(statement, pr.getFilters());
            // List<E> list = getSqlSession().selectList(
            // statementName, pr.getFilters(),
            // new RowBounds(page.getFirstElementIndex(), page.getPageSize())
            // );
            page.setItems(list);
        }
        return page;
    }

}