package com.site.dto;

import java.util.Date;

import com.site.entity.BusiLog;

public class BusiLogDto extends BusiLog {

    private static final long serialVersionUID = 8302826610813601560L;

    private Date beginTime;

    private Date endTime;

    private Integer currentPage;

    private Integer pageSize;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCurrentPage() {
        return currentPage == null ? 1 : currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
