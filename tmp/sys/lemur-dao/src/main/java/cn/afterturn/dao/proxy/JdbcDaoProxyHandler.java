package cn.afterturn.dao.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ognl.Ognl;
import ognl.OgnlException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.util.StringUtils;

import cn.afterturn.dao.annotation.IDaoParam;
import cn.afterturn.dao.annotation.IDaoResult;
import cn.afterturn.dao.annotation.IDaoSql;
import cn.afterturn.dao.format.SQLFormatter;
import cn.afterturn.dao.pager.IDaoPager;
import cn.afterturn.dao.resource.SQLResource;

/**
 * DAO代理实现类
 * 
 * @author JueYue
 * @date 2014年1月12日
 * @version 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class JdbcDaoProxyHandler implements InvocationHandler {

    private static final Logger        logger            = LoggerFactory
                                                             .getLogger(JdbcDaoProxyHandler.class);

    private static final String        INF_METHOD_ACTIVE = "insert,add,create,update,modify,store,delete,remove";

    private static final String        INF_METHOD_BATCH  = "batch";

    @Autowired
    private NamedParameterJdbcTemplate nameTemplate;

    @Autowired
    private JdbcTemplate               jdbcTemplate;

    private static final Pattern       NAMEPATTERN       = Pattern
                                                             .compile(":[ tnx0Bfr]*[0-9a-z.A-Z]+");

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // SQL模板
        String key = getKey(method);
        ParamsModel params = getSQL(key, method, args);
        // 返回结果
        Object returnObj = getValue(method, args, params);
        if (logger.isDebugEnabled()) {
            logger.debug("key:" + key + "\nsql:" + SQLFormatter.format(params.getSql().toString()));
        }
        return returnObj;
    }

    /**
     * 获取值
     * 
     * @date 2014年1月12日
     * @param method
     * @param args
     * @param templateSql
     * @return
     * @throws ClassNotFoundException 
     */
    private Object getValue(Method method, Object[] args, ParamsModel params)
                                                                             throws ClassNotFoundException {
        // ——————————处理复杂操作，传入执行及回调
        if (params.getCallableStatementCallback() != null) {
            return jdbcTemplate.execute(params.getCallableStatementCreator(),
                params.getCallableStatementCallback());
        }
        String sql = params.getSql();

        // ——————————处理查询操作
        String methodName = method.getName();
        if (checkActiveKey(methodName)) {
            return nameTemplate.update(sql, params.getRoot());
        }
        if (checkBatchKey(methodName)) {
            return batchUpdate(sql);
        }
        // ————处理基础类型返回
        Class<?> returnType = method.getReturnType();
        if (returnType.isPrimitive()) {
            Number number = nameTemplate.queryForObject(sql, params.getRoot(), BigDecimal.class);
            if ("int".equals(returnType.getSimpleName())) {
                return number.intValue();
            }
            if ("long".equals(returnType.getSimpleName())) {
                return number.longValue();
            }
            if ("double".equals(returnType.getSimpleName())) {
                return number.doubleValue();
            }
        }

        // 结果集解析
        if (params.getResultSetExtractor() != null) {
            return nameTemplate.query(sql, params.getRoot(), params.getResultSetExtractor());
        }

        // 行解析
        RowMapper<?> rowMapper = params.getRowMapper();

        if (returnType.isAssignableFrom(List.class)) {
            RowMapper<?> rm = getListRowMapper(rowMapper, method);
            return nameTemplate.query(sql, params.getRoot(), rm);
        }

        if (returnType.isAssignableFrom(Map.class)) {
            return nameTemplate.queryForObject(sql, params.getRoot(),
                rowMapper == null ? new ColumnMapRowMapper() : rowMapper);
        }

        if (isWrapClass(returnType)) {
            return nameTemplate.queryForObject(sql, params.getRoot(), returnType);
        }

        if (returnType.equals(IDaoPager.class)) {
            //return getPagerBySql();
        }

        RowMapper<?> rm = rowMapper == null ? ParameterizedBeanPropertyRowMapper
            .newInstance(returnType) : rowMapper;
        return nameTemplate.queryForObject(sql, params.getRoot(), rm);
    }

    private RowMapper<?> getListRowMapper(RowMapper<?> rowMapper, Method method)
                                                                                throws ClassNotFoundException {
        String genericReturnType = method.getGenericReturnType().toString();
        IDaoResult result = method.getAnnotation(IDaoResult.class);
        if (result != null && result.value() != Map.class) {
            return ParameterizedBeanPropertyRowMapper.newInstance(result.value());
        }
        String realType = genericReturnType.replace("java.util.List<", "").replace(">", "");
        if (!realType.contains("java.util.Map")) {
            return ParameterizedBeanPropertyRowMapper.newInstance(Class.forName(realType));
        }
        return new ColumnMapRowMapper();
    }

    /**
     * 批处理
     * 
     * @Author JueYue
     * @date 2013-11-17
     * @return
     */
    private int[] batchUpdate(String executeSql) {
        String[] sqls = executeSql.split(";");
        if (sqls.length < 100) {
            return jdbcTemplate.batchUpdate(sqls);
        }
        int[] result = new int[sqls.length];
        List<String> sqlList = new ArrayList<String>();
        for (int i = 0; i < sqls.length; i++) {
            sqlList.add(sqls[i]);
            if (i % 100 == 0) {
                addResulArray(result, i + 1,
                    jdbcTemplate.batchUpdate(sqlList.toArray(new String[0])));
                sqlList.clear();
            }
        }
        addResulArray(result, sqls.length, jdbcTemplate.batchUpdate(sqlList.toArray(new String[0])));
        return result;
    }

    /**
     * 把批量处理的结果拼接起来
     * 
     * @Author JueYue
     * @date 2013-11-17
     */
    private void addResulArray(int[] result, int index, int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            result[index - length + i] = arr[i];
        }
    }

    /**
     * 获得SQL
     * 
     * @date 2014年1月10日
     * @param method
     * @param args
     *            参数顺序 map,pager,rowmapper
     * @return
     */
    private ParamsModel getSQL(String key, Method method, Object[] args) {
        addSQLByAnnotation(key, method);
        ParamsModel paramsMap = parseParams(method, args);
        if (paramsMap.getCallableStatementCallback() == null) {
            paramsMap.setSql(SQLResource.get(key, paramsMap.getRoot()));
        }
        installPlaceholderSqlParam(paramsMap.getSql(), paramsMap.getRoot());
        return paramsMap;
    }

    /**
     * 组装占位符参数 -> Map
     * 
     * @param executeSql
     * @return
     * @throws OgnlException
     */
    private void installPlaceholderSqlParam(String executeSql, Map sqlParamsMap) {
        Matcher m = NAMEPATTERN.matcher(executeSql);
        while (m.find()) {
            logger.debug(" Match [" + m.group() + "] at positions " + m.start() + "-"
                         + (m.end() - 1));
            String ognl_key = m.group().replace(":", "").trim();
            try {
                sqlParamsMap.put(ognl_key, Ognl.getValue(ognl_key, sqlParamsMap));
            } catch (OgnlException e) {
                throw new RuntimeException(e.getMessage(), e.fillInStackTrace());
            }
        }
    }

    /**
     * 解析参数序列 2014年4月2日
     * 
     * @param method
     * @param args
     * @return
     */
    private ParamsModel parseParams(Method method, Object[] args) {
        ParamsModel result = new ParamsModel();
        IDaoParam[] params = getParamAnnotations(method.getParameterAnnotations());
        // 组装SQL参数
        if (params != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            ;
            for (int i = 0, alength = params.length; i < alength; i++) {
                if (params[i] != null && StringUtils.hasText(params[i].value()))
                    map.put(params[i].value(), args[i]);
            }
            result.setRoot(map);
        }
        // 识别特殊参数
        int le = (args == null) ? 0 : args.length;
        for (int i = 0; i < le; i++) {
            if (args[i] instanceof RowMapper) {
                result.setRowMapper((RowMapper) args[i]);
            } else if (args[i] instanceof CallableStatementCreator) {
                result.setCallableStatementCreator((CallableStatementCreator) args[i]);
            } else if (args[i] instanceof CallableStatementCallback) {
                result.setCallableStatementCallback((CallableStatementCallback) args[i]);
            } else if (args[i] instanceof ResultSetExtractor) {
                result.setResultSetExtractor((ResultSetExtractor) args[i]);
            }
        }
        return result;
    }

    /**
     * 获取注解参数 2014年4月2日
     * 
     * @param parameterAnnotations
     * @return
     */
    private IDaoParam[] getParamAnnotations(Annotation[][] parameterAnnotations) {
        if (parameterAnnotations.length == 0) {
            return null;
        }
        boolean ishave = false;
        for (int i = 0; i < parameterAnnotations[0].length; i++) {
            if (parameterAnnotations[0][i] instanceof IDaoParam) {
                ishave = true;
                break;

            }
        }
        if (ishave) {
            IDaoParam[] params = new IDaoParam[parameterAnnotations.length];
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (int j = 0; j < parameterAnnotations[i].length; j++) {
                    if (parameterAnnotations[i][j] instanceof IDaoParam) {
                        params[i] = (IDaoParam) parameterAnnotations[i][j];
                        break;

                    }
                }
            }
            return params;
        }
        return null;
    }

    /**
     * 获取SQl 2014年4月2日 注解的SQL无法启动加载,只能运行时加载
     * 
     * @param method
     * @param args
     * @return
     */
    private void addSQLByAnnotation(String key, Method method) {
        if (method.getAnnotation(IDaoSql.class) == null) {
            return;
        }
        if (SQLResource.containsKey(key)) {
            SQLResource.put(key, method.getAnnotation(IDaoSql.class).value());
        }
    }

    private String getKey(Method method) {
        return method.getDeclaringClass().getName() + "." + method.getName();
    }

    /**
     * 判斷是否是執行的方法（非查詢）
     * 
     * @param methodName
     * @return
     */
    private boolean checkActiveKey(String methodName) {
        String keys[] = INF_METHOD_ACTIVE.split(",");
        for (String s : keys) {
            if (methodName.startsWith(s))
                return true;
        }
        return false;
    }

    /**
     * 判斷是否批處理
     * 
     * @param methodName
     * @return
     */
    private boolean checkBatchKey(String methodName) {
        String keys[] = INF_METHOD_BATCH.split(",");
        for (String s : keys) {
            if (methodName.startsWith(s))
                return true;
        }
        return false;
    }

    /**
     * 判断传入参数是不是基本类型
     * 
     * @param clz
     * @return
     */
    public boolean isWrapClass(Class<?> clz) {
        try {
            return ((Class<?>) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return clz.getName().equals("java.lang.String");
        }
    }

}
