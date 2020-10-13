package com.zb.common.vo;

import java.util.List;

import com.zb.common.enums.Enums;




/**
 * 
 * 
 * 作者: zhoubang 日期：2015年3月26日 下午1:39:11
 * 
 * @param <T>
 */
public class PageVo<T> {

    // 分页号
    private int pageNo;
    // 单页记录数
    private int pageSize = Enums.Page.DEFAULT_PAGE_SIZE.getValue();
    // 记录总数
    private long totalSize;
    // 结果集
    private List<T> result;

    private int totalPage;

    public PageVo() {
    }

    /**
     * 构建分页
     * 
     * @param result
     *            结果集
     * @param totalSize
     *            总记录条数
     * @param pageNo
     *            分页号
     * @param pageSize
     *            分页总数
     */
    public PageVo(List<T> result, long totalSize, int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.result = result;
        this.totalSize = totalSize;
        this.totalPage = Long.valueOf(this.totalSize % this.pageSize == 0 ? this.totalSize/ this.pageSize : this.totalSize / this.pageSize + 1).intValue();
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 获取总页数
     * 
     * @return a int.
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 构建分页
     * 
     * @param result
     * @param clazz
     * @param pageNo
     * @param pageSize
     * @param totalSize
     * @return
     */
    public static final <T> PageVo<T> build(List<T> result, Class<T> clazz,
            int pageNo, int pageSize, long totalSize) {
        return new PageVo<T>(result, totalSize, pageNo, pageSize);
    }

}
