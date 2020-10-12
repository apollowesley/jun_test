package com.jeasy.base.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeasy.base.model.BaseModel;
import com.jeasy.base.service.BaseService;

import lombok.extern.log4j.Log4j;

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
@Log4j
public class BaseController<T extends BaseService<E>, E extends BaseModel> extends HttpSupport {

    @Autowired
    protected T service;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    protected ModelResult list(Map<String, Object> params) {
        List<E> items = service.find(params);
        return responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @RequestMapping(value = "page", method = RequestMethod.GET)
    @ResponseBody
    protected ModelResult page(Map<String, Object> params) {
        int totalCount = service.count(params);
        List<E> items = service.page(params, getOffset(), getPageSize());
        return responsePage(ModelResult.CODE_200, ModelResult.SUCCESS, totalCount, items);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    protected ModelResult add(E entity) {
        int result = service.save(entity);
        return responseMessage(result == 1 ? ModelResult.CODE_200 : ModelResult.CODE_500, result == 1 ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequestMapping(value = "show", method = RequestMethod.GET)
    @ResponseBody
    protected ModelResult show(@RequestParam(value = "id", required = true) Long id) {
        E entity = service.getById(id);
        return responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, entity);
    }

    @RequestMapping(value = "modify", method = RequestMethod.POST)
    @ResponseBody
    protected ModelResult modify(E entity) {
        int result = service.modify(entity);
        return responseMessage(result == 1 ? ModelResult.CODE_200 : ModelResult.CODE_500, result == 1 ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    @ResponseBody
    protected ModelResult remove(@RequestParam(value = "id", required = true) Long id) {
        int result = service.remove(id);
        return responseMessage(result == 1 ? ModelResult.CODE_200 : ModelResult.CODE_500, result == 1 ? ModelResult.SUCCESS : ModelResult.FAIL);
    }
}
