package com.mkfree.java_reflect;

/**
 * @author oyhk
 * @date 2019-04-24 11:10
 **/
public class BaseDomain<T> {
    @HbaseColumn(name = "id")
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
