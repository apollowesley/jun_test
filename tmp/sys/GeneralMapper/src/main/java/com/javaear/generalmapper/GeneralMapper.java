package com.javaear.generalmapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.javaear.generalmapper.configuration.GeneralMapperBootstrapConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aooer
 */
@Import(GeneralMapperBootstrapConfiguration.class)
public class GeneralMapper {

    private SqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        try {
            this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    public int insert(Object entity) {
        return sqlSessionTemplate.insert(getSqlStatement("insert", entity.getClass()), entity);
    }

    /**
     * <p>
     * 插入一条记录（选择字段， null 字段不插入）
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    public int insertSelective(Object entity) {
        return sqlSessionTemplate.insert(getSqlStatement("insertSelective", entity.getClass()), entity);
    }

    /**
     * <p>
     * 插入（批量）
     * </p>
     *
     * @param entityList 实体对象列表
     * @return int
     */
    public int insertBatch(List<?> entityList) {
        return sqlSessionTemplate.insert(getSqlStatement("insertBatch", entityList.get(0).getClass()), entityList);
    }


    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param id 主键ID
     * @param clazz 对象类型
     * @return int
     */
    public int deleteById(Object id, Class clazz) {
        return sqlSessionTemplate.delete(getSqlStatement("deleteById", clazz), id);
    }


    /**
     * <p>
     * 根据 columnMap 条件，删除记录
     * </p>
     *
     * @param columnMap 表字段 map 对象
     * @param clazz 对象类型
     * @return int
     */
    public int deleteByMap(Map<String, Object> columnMap, Class clazz) {
        return sqlSessionTemplate.delete(getSqlStatement("deleteByMap", clazz), asParam("cm", columnMap));
    }


    /**
     * <p>
     * 根据 entity 条件，删除记录
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    public int deleteSelective(Object entity) {
        return sqlSessionTemplate.delete(getSqlStatement("deleteSelective", entity.getClass()), asParam("ew", entity));
    }


    /**
     * <p>
     * 删除（根据ID 批量删除）
     * </p>
     *
     * @param idList 主键ID列表
     * @param clazz 对象类型
     * @return int
     */
    public int deleteBatchIds(List<?> idList, Class clazz) {
        return sqlSessionTemplate.delete(getSqlStatement("deleteBatchIds", clazz), idList);
    }


    /**
     * <p>
     * 根据 ID 修改
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    public int updateById(Object entity) {
        return sqlSessionTemplate.update(getSqlStatement("updateById", entity.getClass()), asParam("et", entity));
    }


    /**
     * <p>
     * 根据 ID 选择修改
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    public int updateSelectiveById(Object entity) {
        return sqlSessionTemplate.update(getSqlStatement("updateSelectiveById", entity.getClass()), asParam("et", entity));
    }


    /**
     * <p>
     * 根据 whereEntity 条件，更新记录
     * </p>
     *
     * @param entity 实体对象
     * @param whereEntity
     * 实体查询条件（可以为 null）
     * @return int
     */
    public int update(Object entity, Object whereEntity) {
        Map<String, Object> objectMap = asParam("et", entity);
        objectMap.putAll(asParam("ew", whereEntity));
        return sqlSessionTemplate.update(getSqlStatement("update", entity.getClass()), objectMap);
    }


    /**
     * <p>
     * 根据 whereEntity 条件，选择更新记录
     * </p>
     *
     * @param entity 实体对象
     * @param whereEntity（可以为 null）
     * 实体查询条件
     * @return int
     */
    public int updateSelective(Object entity, Object whereEntity) {
        Map<String, Object> objectMap = asParam("et", entity);
        objectMap.putAll(asParam("ew", whereEntity));
        return sqlSessionTemplate.update(getSqlStatement("updateSelective", entity.getClass()), objectMap);
    }


    /**
     * <p>
     * 根据ID 批量更新
     * </p>
     *
     * @param entityList 实体对象列表
     * @return int
     */
    public int updateBatchById(List<Object> entityList) {
        return sqlSessionTemplate.update(getSqlStatement("updateBatchById", entityList.get(0).getClass()), entityList);
    }


    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     * @param clazz 对象类型
     * @return T
     */
    public <T> T selectById(Object id, Class<T> clazz) {
        return sqlSessionTemplate.selectOne(getSqlStatement("selectById", clazz), id);
    }

    ;


    /**
     * <p>
     * 查询（根据ID 批量查询）
     * </p>
     *
     * @param idList 主键ID列表
     * @param clazz 对象类型
     * @return List
     */
    public <T> List<T> selectBatchIds(List<Object> idList, Class<T> clazz) {
        return sqlSessionTemplate.selectList(getSqlStatement("selectBatchIds", clazz), idList);
    }


    /**
     * <p>
     * 查询（根据 columnMap 条件）
     * </p>
     *
     * @param columnMap 表字段 map 对象
     * @param clazz 表字段 map 对象类型
     * @return List
     */
    public <T> List<T> selectByMap(Map<String, Object> columnMap, Class<T> clazz) {
        return sqlSessionTemplate.selectList(getSqlStatement("selectByMap", clazz), asParam("cm", columnMap));
    }


    /**
     * <p>
     * 根据 entity 条件，查询一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return object
     */
    public <T> T selectOne(T entity) {
        return sqlSessionTemplate.selectOne(getSqlStatement("selectOne", entity.getClass()), asParam("ew", entity));
    }


    /**
     * <p>
     * 根据 entity 条件，查询总记录数
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    public int selectCount(Object entity) {
        return sqlSessionTemplate.selectOne(getSqlStatement("selectCount", entity.getClass()), asParam("ew", entity));
    }

    /**
     * <p>
     * 根据 entity 条件，查询全部记录
     * </p>
     *
     * @param entityWrapper 实体对象封装操作类（可以为 null）
     * @return List
     */
    public <T> List<T> selectList(EntityWrapper<T> entityWrapper) {
        return sqlSessionTemplate.selectList(getSqlStatement("selectList", entityWrapper.getEntity().getClass()), asParam("ew", entityWrapper));
    }

    /**
     * <p>
     * 根据 entity 条件，查询全部记录（并翻页）
     * </p>
     *
     * @param page     分页查询条件（可以为 RowBounds.DEFAULT）
     * @param entityWrapper 实体对象封装操作类（可以为 null）
     * @return List
     */
    public <T> List<T> selectPage(Pagination page, EntityWrapper<T> entityWrapper) {
        return sqlSessionTemplate.selectList(getSqlStatement("selectPage", entityWrapper.getEntity().getClass()), asParam("ew", entityWrapper),page);
    }

    public Map<String, Object> asParam(final String paramName, final Object obj) {
        return new HashMap<String, Object>() {{
            put(paramName, obj);
        }};
    }

    private static String getSqlStatement(String statement, Class clazz) {
        return GeneralMapper.class.getName().concat("." + clazz.getSimpleName() + "." + statement);
    }

}
