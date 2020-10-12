/*
 * Copyright 2017 KiWiPeach.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.kiwipeach.demo.response;

import java.util.List;

/**
 * Create Date: 2018/01/16
 * Description: 统一分页返回实体
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class PageResultResponse<T> {
    /***
     * 当前页
     */
    private Integer curNo;
    /**
     * 页码大小
     */
    private Integer pageSize;
    /**
     * 总页数
     */
    private long totalNum;
    /**
     * 页面数据
     */
    private List<T> pageData;

    public PageResultResponse() {
    }

    public PageResultResponse(Integer curNo, Integer pageSize) {
        this.curNo = curNo;
        this.pageSize = pageSize;
    }

    public Integer getCurNo() {
        return curNo;
    }

    public void setCurNo(Integer curNo) {
        this.curNo = curNo;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }
}
