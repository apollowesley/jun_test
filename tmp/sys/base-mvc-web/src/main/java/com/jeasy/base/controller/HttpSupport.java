package com.jeasy.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * HttpSupport
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public abstract class HttpSupport {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    protected final int getPageNo() {
        int defaultPage = 1;
        int page = NumberUtils.toInt(request.getParameter("pageNo"), defaultPage);
        return page > 0 ? page : defaultPage;
    }

    protected final int getPageSize() {
        int defaultPageSize = 5;
        int pageSize = NumberUtils.toInt(request.getParameter("pageSize"), defaultPageSize);
        return pageSize > 0 ? pageSize : defaultPageSize;
    }

    protected final int getOffset () {
        return (getPageNo() - 1) * getPageSize();
    }

    /**
     * 处理响应信息
     * @param code
     * @param message
     * @return
     */
    protected final ModelResult responseMessage(int code, String message){
        ModelResult modelResult = new ModelResult(code);
        modelResult.setMessage(message);
        return modelResult;
    }

    /**
     * 处理响应单个实体
     * @param code
     * @param message
     * @param entity
     * @return
     */
    protected final ModelResult responseEntity(int code, String message, Object entity){
        ModelResult modelResult = new ModelResult(code);
        modelResult.setMessage(message);
        modelResult.setEntity(entity);
        return modelResult;
    }

    /**
     * 处理响应list
     * @param code
     * @param message
     * @param list
     * @return
     */
    protected final ModelResult responseList(int code, String message, List list){
        ModelResult modelResult = new ModelResult(code);
        modelResult.setMessage(message);
        modelResult.setList(list);
        return modelResult;
    }

    /**
     * 处理响应page
     * @param code
     * @param message
     * @param totalCount
     * @param items
     * @return
     */
    protected final ModelResult responsePage(int code, String message, int totalCount, List items){
        ModelResult modelResult = new ModelResult(code);
        modelResult.setMessage(message);
        modelResult.setResultPage(new ResultPage(totalCount, getPageSize(), getPageNo(), items));
        return modelResult;
    }

    protected final String responseRedirect(String url) {
        return "redirect:" + url;
    }
}
