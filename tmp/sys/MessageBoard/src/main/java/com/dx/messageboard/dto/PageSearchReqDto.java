package com.dx.messageboard.dto;

/**
 * 分页查询
 * Create by zhoushiyu
 */
public class PageSearchReqDto {

    int currentPage;

    int pageSize;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
