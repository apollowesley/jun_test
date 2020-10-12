package com.iotechn.iot.commons.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-15
 * Time: 下午8:12
 */
public class CommonsPage<T> implements IPage<T>,Serializable {
    private List<T> items;

    private int pageNo;

    private int pageSize;

    private int count;

    public CommonsPage(List<T> items, int pageNo, int pageSize, int count) {
        this.items = items;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.count = count;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCount() {
        return count;
    }

    public int getTotalPageNo() {
        return count/pageSize + (count%pageSize==0?0:1);
    }

    public List<T> getData() {
        return items;
    }

    public boolean hasNext() {
        return getPageNo() < getTotalPageNo();
    }

    public boolean hasPrevious() {
        return getPageNo() > 1;
    }

    public String getMsg() {
        return "第" + pageNo + "页,共" + count + "条";
    }

    public int getCode() {
        return 0;
    }
}
