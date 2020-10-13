/*
 * Copyright 2017 the original author or authors.
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

package net.oschina.durcframework.easymybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.oschina.durcframework.easymybatis.util.EntityUtils;

public class PageSupport<Entity> implements PageResult<Entity> {
    private static final long serialVersionUID = 5931004082164727399L;
    
    private List<Entity> list;
    private long total = 0;
    private int start = 0;
    private int pageIndex = 1;
    private int pageSize = 10;
    private int pageCount = 0;

    public PageResult process(EntityProcessor<Entity> processor) {
        if (processor == null) {
            return this;
        }
        PageResult pageResult = this;
        if (processor != null) {
            List<Object> jsonObjList = processEntityToJSONObject(list, processor);
            pageResult.setList(jsonObjList);
        }
        return pageResult;
    }

    // 将list中的entity对象处理成JSONObject对象
    public static <Entity> List<Object> processEntityToJSONObject(List<Entity> list,
            EntityProcessor<Entity> processor) {
        List<Object> jsonObjList = new ArrayList<Object>(list.size());

        for (Entity entity : list) {
            Map<String, Object> jsonObject = EntityUtils.convertObj2Map(entity);
            processor.process(entity, jsonObject);
            jsonObjList.add(jsonObject);
        }

        return jsonObjList;
    }

    public int fatchCurrentPageIndex() {
        return pageIndex;
    }

    /**
     * 上一页
     * 
     * @return
     */
    public int fatchPrePageIndex() {
        return (pageIndex - 1 <= 0) ? 1 : pageIndex - 1;
    }

    /**
     * 下一页
     * 
     * @return
     */
    public int fatchNextPageIndex() {
        return (pageIndex + 1 > pageCount) ? pageCount : pageIndex + 1;
    }

    /**
     * 首页
     * 
     * @return
     */
    public int fatchFirstPageIndex() {
        return 1;
    }

    /**
     * 最后一页
     * 
     * @return
     */
    public int fatchLastPageIndex() {
        return pageCount;
    }

    /**
     * 结果集
     * 
     * @return
     */
    public List<Entity> fatchList() {
        return list;
    }

    /**
     * 总记录数
     * 
     * @return
     */
    public long fatchTotal() {
        return total;
    }

    /**
     * 当前页索引,等同于getCurrentPageIndex()
     * 
     * @return
     */
    public int fatchPageIndex() {
        return pageIndex;
    }

    /**
     * 每页显示几条记录
     * 
     * @return
     */
    public int fatchPageSize() {
        return pageSize;
    }

    public int fatchStart() {
        return start;
    }

    /**
     * 共几页
     * 
     * @return
     */
    public int fatchPageCount() {
        return pageCount;
    }

    @Override
    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public void setList(List<Entity> list) {
        this.list = list;
    }

}