package com.king.base.controller;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.king.base.model.BaseModel;
import com.king.base.service.BaseService;

/**
 * Abstract BaseController
 * @param <T>
 *     ServiceImpl
 * @param <E>
 *     Model
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class BaseController<T extends BaseService<E>, E extends BaseModel> {

    protected Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected T service;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    protected ModelResult list(Map<String, Object> params) {
        int totalCount = service.count(params);
        List<E> items = service.page(params, getOffset(), getPageSize());
        return responsePage(ModelResult.CODE_200, ModelResult.SUCCESS, totalCount, items);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    protected ModelResult save(E ldOrderDetail) {
        int result = service.save(ldOrderDetail);
        return responseMessage(result == 1 ? ModelResult.CODE_200 : ModelResult.CODE_500, result == 1 ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @ResponseBody
    protected ModelResult edit(@RequestParam(value = "id", required = true) Long id) {
        E ldOrderDetail = service.findById(id);
        return responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, ldOrderDetail);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    protected ModelResult update(E ldOrderDetail) {
        int result = service.modify(ldOrderDetail);
        return responseMessage(result == 1 ? ModelResult.CODE_200 : ModelResult.CODE_500, result == 1 ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    protected ModelResult delete(@RequestParam(value = "id", required = true) Long id) {
        int result = service.remove(id);
        return responseMessage(result == 1 ? ModelResult.CODE_200 : ModelResult.CODE_500, result == 1 ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

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
    protected final ModelResult responsePage(int code, String message, int totalCount, List<E> items){
        ModelResult modelResult = new ModelResult(code);
        modelResult.setMessage(message);
        modelResult.setResultPage(new ResultPage(totalCount, getPageSize(), getPageNo(), items));
        return modelResult;
    }

    protected final String responseRedirect(String url) {
        return "redirect:" + url;
    }
}
