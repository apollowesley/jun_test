package cn.afterturn.dao.proxy;

import java.util.Map;

import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

/**
 * 参数类型
 * @author JueYue
 * 2014年4月2日 - 下午5:00:03
 */
@SuppressWarnings("rawtypes")
class ParamsModel {

    /**
     * freemark 使用 类型 Map<String,Object>
     */
    private Map<String, Object>       root;
    /**
     * ResultSetExtractor 类型
     */
    private ResultSetExtractor        resultSetExtractor;
    /**
     * RowMapper 类型
     */
    private RowMapper                 rowMapper;
    /**
     * CallableStatementCreator 类型
     */
    private CallableStatementCreator  callableStatementCreator;
    /**
     * CallableStatementCallback 类型
     */
    private CallableStatementCallback callableStatementCallback;
    /**
     * SQL数据
     */
    private String                    sql;

    public Map<String, Object> getRoot() {
        return root;
    }

    public void setRoot(Map<String, Object> root) {
        this.root = root;
    }

    public ResultSetExtractor getResultSetExtractor() {
        return resultSetExtractor;
    }

    public void setResultSetExtractor(ResultSetExtractor resultSetExtractor) {
        this.resultSetExtractor = resultSetExtractor;
    }

    public RowMapper getRowMapper() {
        return rowMapper;
    }

    public void setRowMapper(RowMapper rowMapper) {
        this.rowMapper = rowMapper;
    }

    public CallableStatementCreator getCallableStatementCreator() {
        return callableStatementCreator;
    }

    public void setCallableStatementCreator(CallableStatementCreator callableStatementCreator) {
        this.callableStatementCreator = callableStatementCreator;
    }

    public CallableStatementCallback getCallableStatementCallback() {
        return callableStatementCallback;
    }

    public void setCallableStatementCallback(CallableStatementCallback callableStatementCallback) {
        this.callableStatementCallback = callableStatementCallback;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

}
