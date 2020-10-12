package com.cnit1818.utils;

/**
 * Created with IntelliJ IDEA.
 * User: MaYong
 * Date: 12-12-19
 * Time: 下午3:58
 * To change this template use File | Settings | File Templates.
 */
public class Page<T> {
    //当前页
    private Integer page;
    //每页记录数量
    private Integer rows;
    //分页开始记录数
    private Integer start;
    //返回处理消息
    private String message;
    //查询条件
    private Object cond;
    //返回数据
    private T object;
    //排序条件
    private String orderBy;


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getStart() {
        if (this.page == null) {
            return null;
        }
        return (page - 1) * rows;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getCond() {
        return cond;
    }

    public void setCond(Object cond) {
        this.cond = cond;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
