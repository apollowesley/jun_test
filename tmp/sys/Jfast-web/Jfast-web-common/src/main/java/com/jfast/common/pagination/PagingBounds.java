package com.jfast.common.pagination;

import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * Created by guoyou on 2018/3/26.
 */
public class PagingBounds extends RowBounds {

    //总记录数
    private int total;
    //查询的起始位置
    private int offset;
    //查询多少行记录
    private int limit;

    private String sortColumns;

    private List<Map> dataList;

    public PagingBounds() {
        this.offset = NO_ROW_OFFSET;
        this.limit = NO_ROW_LIMIT;
    }

    public PagingBounds(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setMeToDefault() {
        this.offset = NO_ROW_OFFSET;
        this.limit = NO_ROW_LIMIT;
    }

    public int getSelectCount() {
        return limit + offset;
    }

    public void setSortColumns(String sortColumns) {
        this.sortColumns = sortColumns;
    }

    public String getSortColumns() {
        return sortColumns;
    }

    public void setDataList(List<Map> dataList) {
        this.dataList = dataList;
    }

    public List<Map> getDataList() {
        return dataList;
    }
}