package com.iotechn.iot.commons.entity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-15
 * Time: 下午8:07
 */
public interface IPage<T> {

    public int getPageNo();

    public int getPageSize();

    public int getCount();

    public int getTotalPageNo();

    public List<T> getData();

    public boolean hasNext();

    public boolean hasPrevious();

    public String getMsg();

    public int getCode();

}
