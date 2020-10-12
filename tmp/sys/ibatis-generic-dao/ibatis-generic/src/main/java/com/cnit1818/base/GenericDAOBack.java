package com.cnit1818.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: MaYong
 * Date: 13-3-15
 * Time: 下午4:27
 * To change this template use File | Settings | File Templates.
 */
public abstract class GenericDAOBack<T, PK extends Serializable> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String POSTFIX_SELECTBYID = ".selectById";
    public static final String POSTFIX_SELECTBYIDS = ".selectByIds";
    public static final String POSTFIX_SELECTBYMAP = ".selectByMap";
    public static final String POSTFIX_SELECTIDSLIKEBYMAP = ".selectIdsLikeByMap";
    public static final String POSTFIX_PKSELECTMAP = ".pkSelectByMap";
    public static final String POSTFIX_COUNT = ".count";
    public static final String POSTFIX_COUNTLIKEBYMAP = ".countLikeByMap";
    public static final String POSTFIX_INSERT = ".insert";
    public static final String POSTFIX_DELETEBYID = ".deleteById";
    public static final String POSTFIX_DELETEBYIDS = ".deleteByIds";
    public static final String POSTFIX_DELETEBYIDSMAP = ".deleteByIdsMap";
    public static final String POSTFIX_DELETEBYMAP = ".deleteByMap";
    public static final String POSTFIX_UPDATE = ".update";
    public static final String POSTFIX_UPDATEBYMAP = ".updateByMap";
    public static final String POSTFIX_UPDATEBYIDSMAP = ".updateByIdsMap";

    protected Class<T> clazz;

    protected String clazzName;

    protected T t;

    // 定义主从数据库服务器，主数据库负责（Write op），从数据库负责（read op）
    @Autowired
    protected SqlMapClientTemplate masterSqlMapClientTemplate;

    @Autowired
    protected SqlMapClientTemplate slaveSqlMapClientTemplate;

    public GenericDAOBack() {
        // 通过范型反射，取得在子类中定义的class.
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        clazzName = clazz.getSimpleName();
    }

    public Integer count(String propertyName, Object propertyValue) {
        return count(new String[]{propertyName}, new Object[]{propertyValue});
    }

    public Integer count(String[] propertyNames, Object[] propertyValues) {

        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < propertyNames.length; i++) {
            map.put(propertyNames[i], propertyValues[i]);
        }
        return (Integer) slaveSqlMapClientTemplate.queryForObject(clazz.getName() + POSTFIX_COUNT, map);
    }

    //    @Override
    public Integer countLikeByMap(String[] propertyNames, Object[] propertyValues) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < propertyNames.length; i++) {
            map.put(propertyNames[i], propertyValues[i]);
        }
        return (Integer) slaveSqlMapClientTemplate.queryForObject(clazz.getName() + POSTFIX_COUNTLIKEBYMAP, map);
    }

    /**
     * 根据自定义SqlMap中的条件语句查询出记录数量
     */
    public Integer countByStatementPostfix(String statementPostfix, String[] properties, Object[] propertyValues) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        return (Integer) slaveSqlMapClientTemplate.queryForObject(clazz.getName() + statementPostfix, map);
    }

    /**
     * 通过id得到实体对象
     */
    public T findById(PK id) {
        return (T) slaveSqlMapClientTemplate.queryForObject(clazz.getName() + POSTFIX_SELECTBYID, id);

    }

    /**
     * 根据ids获取实体列表
     *
     * @param ids
     * @return
     */
    public List<T> findByIds(List<PK> ids) {
        return (List<T>) slaveSqlMapClientTemplate.queryForList(clazz.getName() + POSTFIX_SELECTBYIDS, ids);
    }

    /**
     * 直接从数据库查询出ids列表（包括符合条件的所有id）
     *
     * @param properties     查询条件字段名
     * @param propertyValues 字段取值
     * @return
     */
    public List<PK> findIdsBy(String[] properties, Object[] propertyValues, String orderBy, String order) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        if (orderBy != null) {
            map.put("orderBy", orderBy);
            map.put("order", order);
        }
        return (List<PK>) slaveSqlMapClientTemplate.queryForList(clazz.getName() + POSTFIX_PKSELECTMAP, map);
    }

    /**
     * 根据条件查询结果
     *
     * @param properties     查询条件名
     * @param propertyValues 查询条件值
     * @return
     */
    public List<T> findByMap(String[] properties, Object[] propertyValues, String orderBy, String order) {
//        return (List<T>) slaveSqlMapClientTemplate.queryForList(clazz.getName() + POSTFIX_SELECTBYMAP, map);
        return (List<T>) slaveSqlMapClientTemplate.queryForList(clazz.getName() + POSTFIX_SELECTBYMAP, null);
    }

    /**
     * 根据条件查询结果的id列表
     *
     * @param properties     查询条件名
     * @param propertyValues 查询条件值
     * @return
     */
    public List<PK> findIdsByMap(String[] properties, Object[] propertyValues, String orderBy, String order) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        if (orderBy != null) {
            map.put("orderBy", orderBy);
            map.put("order", order);
        }
        return (List<PK>) slaveSqlMapClientTemplate.queryForList(clazz.getName() + POSTFIX_PKSELECTMAP, map);
    }

    /**
     * 分页查询（未处理缓存）
     *
     * @param properties     查询条件字段名
     * @param propertyValues 字段取值
     * @return
     */
    public List<T> pageQueryBy(String[] properties, Object[] propertyValues, String orderBy, String order, int pageSize, int pageNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        if (orderBy != null) {
            map.put("orderBy", orderBy);
            map.put("order", order);
        }
        map.put("limit", true);
        map.put("start", (pageNo - 1) * pageSize);// limit 操作
        map.put("end", pageSize);
        return (List<T>) slaveSqlMapClientTemplate.queryForList(clazz.getName() + POSTFIX_SELECTBYMAP, map);
    }

    /**
     * 从数据库分页查询出ids列表的前200个id
     *
     * @param properties     查询条件字段名
     * @param propertyValues 字段取值
     * @return
     */
    protected List<PK> pageQueryIdsBy(String[] properties, Object[] propertyValues, String orderBy, String order) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        if (orderBy != null) {
            map.put("orderBy", orderBy);
            map.put("order", order);
        }
        map.put("limit", true);
        map.put("start", 0);//  操作
//        map.put("end", Config.getInstance().getPageCacheSize());
        return (List<PK>) slaveSqlMapClientTemplate.queryForList(clazz.getName() + POSTFIX_PKSELECTMAP, map);

    }

    /**
     * 分页查询出id列表（处理缓存）
     *
     * @param properties     查询条件字段名
     * @param propertyValues 字段取值
     * @return
     */
    public List<PK> pageQueryIdsByMap(String[] properties, Object[] propertyValues, String orderBy, String order, int pageSize, int pageNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        if (orderBy != null) {
            map.put("orderBy", orderBy);
            map.put("order", order);
        }
        map.put("limit", true);
        map.put("start", (pageNo - 1) * pageSize);// limit 操作
        map.put("end", pageSize);
        return (List<PK>) slaveSqlMapClientTemplate.queryForList(clazz.getName() + POSTFIX_PKSELECTMAP, map);
    }

    /**
     * like分页查询(不走列表缓存)
     */
    public List<T> pageLikeBy(String[] properties, Object[] propertyValues, String orderBy, String order, int pageSize, int pageNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        if (orderBy != null) {
            map.put("orderBy", orderBy);
            map.put("order", order);
        }
        map.put("limit", true);
        map.put("start", (pageNo - 1) * pageSize);// limit 操作
        map.put("end", pageSize);
        List<PK> ids = (List<PK>) slaveSqlMapClientTemplate.queryForList(clazz.getName() + POSTFIX_SELECTIDSLIKEBYMAP, map);
        return findByIds(ids);
    }

    /**
     * 新增对象
     */
    public Serializable insert(T o) throws Exception {
/*
        if (t instanceof QueryCachable) {
            // 清除受影响的缓存
            clearListCache(o);
        }
*/
        return (Serializable) masterSqlMapClientTemplate.insert(clazz.getName() + POSTFIX_INSERT, o);
    }


    /**
     * 更新对象
     */
    public T update(T o) throws Exception {
        masterSqlMapClientTemplate.update(clazz.getName() + POSTFIX_UPDATE, o);
        return o;
    }

    /**
     * 更新对象的部分属性
     */
    public int update(PK id, String[] properties, Object[] propertyValues) throws Exception {
        // 更新数据库
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        map.put("id", id);
        return masterSqlMapClientTemplate.update(clazz.getName() + POSTFIX_UPDATEBYMAP, map);
    }

    /**
     * 根据ID列表更新对象的部分属性
     */
    public int updateByIdsMap(List<PK> ids, String[] properties, Object[] propertyValues) throws Exception {
        // 更新数据库
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        map.put("ids", ids);
        return masterSqlMapClientTemplate.update(clazz.getName() + POSTFIX_UPDATEBYIDSMAP, map);
    }

    /**
     * 根据ID删除对象
     */
    public void deleteById(PK id) throws Exception {
        masterSqlMapClientTemplate.delete(clazz.getName() + POSTFIX_DELETEBYID, id);
    }

    /**
     * 根据ID删除对象
     */
    public void deleteByIds(List<PK> ids) throws Exception {
        masterSqlMapClientTemplate.delete(clazz.getName() + POSTFIX_DELETEBYIDS, ids);
    }

    /**
     * 根据ID及条件删除对象
     */
    public void deleteByIdsMap(List<PK> ids, String[] properties, Object[] propertyValues) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        map.put("ids", ids);
        masterSqlMapClientTemplate.delete(clazz.getName() + POSTFIX_DELETEBYIDSMAP, map);
    }

    /**
     * 根据条件删除对象
     */
    public int deleteByMap(String[] properties, Object[] propertyValues) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        return masterSqlMapClientTemplate.delete(clazz.getName() + POSTFIX_DELETEBYMAP, map);
    }

    /**
     * 根据自定义SqlMap中的条件语句查询出列表(注意：不处理缓存)
     */
    public List<T> findByStatementPostfix(String statementPostfix, String[] properties, Object[] propertyValues, String orderBy, String order) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        if (orderBy != null) {
            map.put("orderBy", orderBy);
            map.put("order", order);
        }
        return slaveSqlMapClientTemplate.queryForList(clazz.getName() + statementPostfix, map);
    }

    /**
     * 根据自定义SqlMap中的条件语句查询出列表(注意：不处理缓存)
     */
    public List<T> pageQueryByStatementPostfix(String statementPostfix, String[] properties, Object[] propertyValues, String orderBy, String order, int pageSize, int pageNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        if (orderBy != null) {
            map.put("orderBy", orderBy);
            map.put("order", order);
        }
        map.put("limit", true);
        map.put("start", (pageNo - 1) * pageSize);// limit 操作
        map.put("end", pageSize);
        List<PK> ids = (List<PK>) slaveSqlMapClientTemplate.queryForList(clazz.getName() + statementPostfix, map);
        return findByIds(ids);
    }

    /**
     * 根据自定义SqlMap中的条件语句更新数据（注意：不处理缓存）
     */
    public void updateByStatementPostfix(String statementPostfix, String[] properties, Object[] propertyValues) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        masterSqlMapClientTemplate.update(clazz.getName() + statementPostfix, map);
    }

    /**
     * 根据自定义SqlMap中的条件语句删除数据（注意：不处理缓存）
     */
    public void deleteByStatementPostfix(String statementPostfix, String[] properties, Object[] propertyValues) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        masterSqlMapClientTemplate.delete(clazz.getName() + statementPostfix, map);
    }

    /**
     * 根据自定义SqlMap中的条件语句插入数据（注意：不处理缓存）
     */
    public void insertByStatementPostfix(String statementPostfix, String[] properties, Object[] propertyValues) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            map.put(properties[i], propertyValues[i]);
        }
        masterSqlMapClientTemplate.insert(clazz.getName() + statementPostfix, map);
    }

}
