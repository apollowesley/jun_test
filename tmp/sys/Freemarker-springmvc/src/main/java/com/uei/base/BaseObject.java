package com.uei.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("UEI_BaseObject")
public class BaseObject implements Serializable {
    private static final long serialVersionUID = 2324343203L;

    /**
     * 额外的参数
     */
    private Map<String, Object> params = new HashMap<String, Object>();

    /** 排序字段名称 */
    private String ordername;

    /** 排序方式 */
    private String ordersort = "DESC";

    /**
     * 当前页数,0为第一页,依次类推
     */
    private int pageNo = 0;

    /**
     * 每页显示的数据条数
     */
    private int pageSize = 0;

    /**
     * 第一页页码
     */
    private int firstPage = 0;

    /**
     * 要查询的数据条数
     */
    private int limit;

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public String getOrdersort() {
        return ordersort;
    }

    public void setOrdersort(String ordersort) {
        this.ordersort = ordersort;
    }

    /**
     * 开始位置
     *
     * @return
     */
    public int getBeginIndex() {
        return (this.pageNo - this.firstPage) * (this.pageSize);
    }

    /**
     * toString方法，返回属性名称及值
     *
     * @author afi
     * @return 属性名称及值，格式：[name]张三 [sex]男
     */
    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        try {
            Class<? extends Object> clazz = this.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("serialVersionUID".equals(fieldName)) {
                    continue;
                }
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1, fieldName.length());
                Method method = null;
                Object resultObj = null;
                method = clazz.getMethod(methodName);
                resultObj = method.invoke(this);
                if (resultObj != null && !"".equals(resultObj)) {
                    result.append("[").append(fieldName).append("]").append(resultObj).append(" ");
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     *
     * 对象转换成map
     *
     * @author zhangshaobin
     * @created 2014年11月28日 下午3:35:05
     *
     * @return
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<? extends Object> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                String fieldName = field.getName();
                if ("serialVersionUID".equals(fieldName)) {
                    continue;
                }
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                map.put(fieldName, clazz.getMethod(methodName).invoke(this));

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    /**
     * 获取分页limit语句
     *
     * @return
     */
    public String getLimitSql() {
        StringBuffer sb = new StringBuffer();
        if (this.pageSize != 0) {
            sb.append(" LIMIT ");
            sb.append(this.getBeginIndex());
            sb.append(" , ");
            sb.append(this.getPageSize());
        } else if (this.limit != 0) {
            sb.append(" LIMIT ");
            sb.append(this.limit);
        }
        return sb.toString();
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void put(String key, Object value) {
        this.params.put(key, value);
    }

}
