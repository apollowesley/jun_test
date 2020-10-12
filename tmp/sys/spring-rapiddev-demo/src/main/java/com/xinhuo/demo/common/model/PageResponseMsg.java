package com.xinhuo.demo.common.model;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * author 张新林
 * 时间 2019/6/16 16:12
 * 描述
 */
public class PageResponseMsg extends ResponseMsg{

    public PageResponseMsg(){

    }
    public PageResponseMsg(IPage page){
        setPage(page);
    }
    public PageResponseMsg(int code, String message, IPage page){
        this.code = code;
        this.message = message;
        setPage(page);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public void setPage(IPage page){
        JSONObject pageJO = new JSONObject();
        pageJO.put("list",page.getRecords());
        pageJO.put("_pageNum",page.getCurrent());
        pageJO.put("_totalPage",page.getPages());
        pageJO.put("_total",page.getTotal());
        pageJO.put("_size",page.getSize());
        this.data = pageJO;
    }
}
