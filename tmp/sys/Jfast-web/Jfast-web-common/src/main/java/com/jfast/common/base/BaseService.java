package com.jfast.common.base;

import com.jfast.common.model.ModelMap;
import com.jfast.common.pagination.Pagination;
import com.jfast.common.pagination.PagingBounds;
import com.jfast.common.utils.ResultCode;
import com.jfast.common.utils.ObjectUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/4/12 20:37
 */
public abstract class BaseService {

    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;
    private final Logger logger = LoggerFactory.getLogger(BaseService.class);

    /**
     * 返回插入成功的id
     * @param sqlId
     * @param params
     * @return
     */
    public Integer save(String sqlId, Map params) {
        ModelMap dataMap = new ModelMap(); // 由于使用HashMap可能会造成类型转换错误问题
        dataMap.put("params", params);
        int result = sqlSessionTemplate.insert(sqlId, dataMap);
        if (result > 0) {
            return dataMap.getInt("id");
        }
        return ResultCode.FAIL;
    }

    public int update(String sqlId, Map params) {
        Map dataMap = new HashMap<>();
        dataMap.put("params", params);
        return sqlSessionTemplate.update(sqlId, dataMap);
    }



    /**
     * 移除时间字段
     * @param params
     */
    protected void checkParams(Map params) {
        if (params != null) {
            params.remove("create_date");
            params.remove("last_login_time");
        }
    }


    public Map paginateGetByInterceptor(Map criteria) {
        Map result = new HashMap();
        String sqlId = (String) criteria.get("sqlId");
        Integer offset = PagingBounds.NO_ROW_OFFSET;
        Integer limit = PagingBounds.NO_ROW_LIMIT;
        if (ObjectUtils.isNotEmpty(criteria.get("offset"))) {
            offset = Integer.valueOf((String) criteria.get("offset"));
        }
        if (ObjectUtils.isNotEmpty(criteria.get("limit"))) {
            limit = Integer.valueOf((String) criteria.get("limit"));
        }
        Pagination<Map> retVal = new Pagination<>();
        PagingBounds pagingBounds = new PagingBounds(offset, limit);
        if (criteria.containsKey("sortColumns")) {
            pagingBounds.setSortColumns((String) criteria.get("sortColumns"));
        }
        List<Map> list = sqlSessionTemplate.selectList(sqlId, criteria, pagingBounds);
        for (Map entity: list) {
            retVal.add(entity);
        }
        retVal.setTotal(pagingBounds.getTotal());

        result.put("total", retVal.getTotal());
        result.put("data", retVal.getData());
        return result;
    }

    public Map findByDefaultPagination(Map params) {
        Map result = new HashMap();
        String sqlId = (String) params.get("sqlId");
        try {
            List<Map> list = sqlSessionTemplate.selectList(sqlId, params);
            Long count = getDataCount(sqlId, params);
            result.put("total", count);
            result.put("data", list);
            return result;
        } catch (Exception e) {
            logger.error("分页查询异常", e);
        }
        return null;
    }

    /**
     * 获取总条数
     */
    public Long getDataCount(String sqlId, Map params) throws Exception {
        Configuration configuration = sqlSessionTemplate.getConfiguration();
        MappedStatement mappedStatement = configuration.getMappedStatement(sqlId);
        params.remove("sqlId");
        BoundSql boundSql = mappedStatement.getBoundSql(params);
        String originalSql  = boundSql.getSql().trim();
        int offset = originalSql .lastIndexOf("limit");
        if (offset != -1) {
            originalSql  = originalSql .substring(0, offset);
        }
        String countSql = "select count(*) as count from( " + originalSql  + ") new_table";
        Object parameterObject = boundSql.getParameterObject();
        Connection connection =  configuration.getEnvironment().getDataSource().getConnection();
        BoundSql countBoundSql = newBoundSql(mappedStatement, boundSql, countSql);
        PreparedStatement countStmt = connection.prepareStatement(countSql);
        setParameters(countStmt, mappedStatement, countBoundSql, parameterObject);
        ResultSet resultSet = countStmt.executeQuery();
        Map countMap = this.findToMap(resultSet);
        return (Long)countMap.get("count");
    }

    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }


    private BoundSql newBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    public Map findToMap(ResultSet resultSet) throws Exception {
        Map<String, Object> map = new HashMap<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount]; //存储数据库字段列名
        for (int i = 0; i < columnCount; i++) {
            String columnName = metaData.getColumnLabel(i + 1);
            columnNames[i] = columnName;
        }
        while (resultSet.next()) {
            for (String name : columnNames) {
                Object value =  resultSet.getObject(name);
                map.put(name, value);
            }
        }
        return map;
    }
}
