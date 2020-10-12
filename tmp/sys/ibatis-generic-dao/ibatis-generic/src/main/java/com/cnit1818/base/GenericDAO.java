package com.cnit1818.base;

import com.cnit1818.utils.Page;
import com.ibatis.sqlmap.client.SqlMapClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MaYong
 * Date: 13-3-15
 * Time: 下午4:27
 * To change this template use File | Settings | File Templates.
 */

public class GenericDAO<T, PK extends Serializable> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    //根据ID查询实体
    public static final String POSTFIX_SELECTBYID = ".selectById";
    //根据条件查询ID列表
    public static final String POSTFIX_SELECTIDSBYCOND = ".selectIdsByCond";
    //根据条件查询实体
    public static final String POSTFIX_SELECTBYCOND = ".selectByCond";
    //根据条件查询记录数量
    public static final String POSTFIX_SELECTCOUNTBYCOND = ".selectCountByCond";
    //增加记录
    public static final String POSTFIX_INSERT = ".insert";
    //根据ID修改记录
    public static final String POSTFIX_UPDATEBYID = ".updateById";
    //根据ID删除记录
    public static final String POSTFIX_DELETEBYID = ".deleteById";

    protected Class<T> clazz;

    protected SqlMapClient dpClient;

    @SuppressWarnings("unchecked")
    public GenericDAO() {
        this.clazz = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    public GenericDAO(SqlMapClient sqlMapClient, Class<T> clazz) {
        this.clazz = clazz;
        this.dpClient = sqlMapClient;
    }

    /**
     * 通过id得到实体对象
     */
    @SuppressWarnings("unchecked")
    public T findById(PK id) throws SQLException {
        return (T) dpClient.queryForObject(clazz.getSimpleName() + POSTFIX_SELECTBYID, id);
    }

    /**
     * 通过条件得到实体对象
     */
    @SuppressWarnings("unchecked")
    public T findByCond(T t) throws SQLException {
        return (T) dpClient.queryForObject(clazz.getSimpleName() + POSTFIX_SELECTBYCOND, t);
    }

    /**
     * 通过条件得到实体对象列表
     */
    @SuppressWarnings("unchecked")
    public List<T> findListByCond(T t) throws SQLException {
        return dpClient.queryForList(clazz.getSimpleName() + POSTFIX_SELECTBYCOND, t);
    }

    /**
     * 分页查询出id列表
     *
     * @param page 分页存储数据类
     * @return List id列表
     */
    @SuppressWarnings("unchecked")
    public List<PK> pageQueryIdsByCond(Page page) throws SQLException {
        if (page.getStart() == null || page.getRows() == null) {
            return dpClient.queryForList(clazz.getSimpleName() + POSTFIX_SELECTIDSBYCOND, page.getCond());
        } else {
            return dpClient.queryForList(clazz.getSimpleName() + POSTFIX_SELECTIDSBYCOND, page.getCond(), page.getStart(), page.getRows());
        }
    }

    /**
     * 分页查询出实体列表
     *
     * @param page 分页存储数据类
     * @return List id列表
     */
    @SuppressWarnings("unchecked")
    public List<T> pageQueryByCond(Page page) throws SQLException {
        if (page.getStart() == null || page.getRows() == null) {
            return dpClient.queryForList(clazz.getSimpleName() + POSTFIX_SELECTBYCOND, page.getCond());
        } else {
            return dpClient.queryForList(clazz.getSimpleName() + POSTFIX_SELECTBYCOND, page.getCond(), page.getStart(), page.getRows());
        }
    }

    /**
     * 分页查询出实体列表
     *
     * @param cond  分页存储数据类
     * @param start 起始位置
     * @param skip  跳过数量
     * @return List 实体列表
     */
    @SuppressWarnings("unchecked")
    public List<T> pageQueryByCond(Object cond, int start, int skip) throws SQLException {
        return dpClient.queryForList(clazz.getSimpleName() + POSTFIX_SELECTBYCOND, cond, start, skip);
    }

    /**
     * 根据条件查询出记录总数
     *
     * @param page 分页存储数据类
     * @return int 数量
     */
    @SuppressWarnings("unchecked")
    public Integer pageQueryCountByCond(Page page) throws SQLException {
        return (Integer) dpClient.queryForObject(clazz.getSimpleName() + POSTFIX_SELECTCOUNTBYCOND, page.getCond());
    }

    /**
     * 根据条件查询出记录总数
     *
     * @param cond 查询数量
     * @return List 实体列表
     */
    @SuppressWarnings("unchecked")
    public Integer pageQueryCountByCond(Object cond) throws SQLException {
        return (Integer) dpClient.queryForObject(clazz.getSimpleName() + POSTFIX_SELECTCOUNTBYCOND, cond);
    }

    /**
     * 新增对象,返回主键
     */
    @SuppressWarnings("unchecked")
    public PK insert(Object o) throws SQLException {
        return (PK) dpClient.insert(clazz.getSimpleName() + POSTFIX_INSERT, o);
    }

    /**
     * 根据ID删除对象
     */
    public Integer deleteById(Serializable id) throws SQLException {
        return dpClient.delete(clazz.getSimpleName() + POSTFIX_DELETEBYID, id);
    }

    /**
     * 根据实体ID更新对象
     */
    public Integer updateById(T o) throws SQLException {
        Integer count = dpClient.update(clazz.getSimpleName() + POSTFIX_UPDATEBYID, o);
        return count;
    }

    /**
     * 根据自定义SqlMap中的条件语句查询出记录列表
     */
    @SuppressWarnings("unchecked")
    public List pageQueryByStatementCond(String statementPostfix, Page page) throws SQLException {
        if (page.getStart() == null || page.getRows() == null) {
            return dpClient.queryForList(clazz.getSimpleName() + "." + statementPostfix, page.getCond());
        } else {
            return dpClient.queryForList(clazz.getSimpleName() + "." + statementPostfix, page.getCond(), page.getStart(), page.getRows());
        }
    }

    /**
     * 根据自定义SqlMap中的条件语句查询出记录列表
     */
    @SuppressWarnings("unchecked")
    public List findListByStatementCond(String statementPostfix, Object cond) throws SQLException {
        return dpClient.queryForList(clazz.getSimpleName() + "." + statementPostfix, cond);
    }

    /**
     * 根据自定义SqlMap中的条件语句查询出记录列表
     */
    @SuppressWarnings("unchecked")
    public List pageQueryByStatementCond(String statementPostfix, Object cond, int start, int skip) throws SQLException {
        return dpClient.queryForList(clazz.getSimpleName() + "." + statementPostfix, cond, start, skip);
    }

    /**
     * 根据自定义SqlMap中的条件语句查询出记录数量
     */
    @SuppressWarnings("unchecked")
    public Integer pageQueryCountByStatementCond(String statementCountPostfix, Page page) throws SQLException {
        return (Integer) dpClient.queryForObject(clazz.getSimpleName() + "." + statementCountPostfix, page.getCond());
    }

    /**
     * 根据自定义SqlMap中的条件语句查询出记录数量
     */
    @SuppressWarnings("unchecked")
    public Integer pageQueryCountByStatementCond(String statementCountPostfix, Object cond) throws SQLException {
        return (Integer) dpClient.queryForObject(clazz.getSimpleName() + "." + statementCountPostfix, cond);
    }

}
