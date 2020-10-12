package tom.db.jdbc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 存储过程执行返回对象
 */
public class ProceRecord {

    private Map<String, Object> retMap;

    public ProceRecord(Map<String, Object> retmap) {
        retMap = retmap;
    }

    public Map<String, Object> getResultMap() {
        return retMap;
    }

    /**
     * 得到相对位置的结果集
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getDataSet(int index) {
        return (List<Map<String, Object>>) retMap.get("DATASET_" + index);
    }

    /**
     * 取第1个返回结果集
     */
    public List<Map<String, Object>> getFirstDataSet() {
        return getDataSet(0);
    }

    public Object get(String key) {
        return retMap.get(key);
    }

    public String getString(String key) {
        return (String) get(key);
    }

    public BigDecimal getBigDeciaml(String key) {
        return (BigDecimal) get(key);
    }

    public Integer getInteger(String key) {
        return (Integer) get(key);
    }

    public Double getDouble(String key) {
        return (Double) get(key);
    }

    /**
     * 取返回参数cursor,序列化成List(Oracle 游标)
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getList(String key) {
        return (List<Map<String, Object>>) get(key);
    }
}
