package com.kld.common.framework.controller;

import com.kld.common.framework.bizService.BaseBizService;
import com.kld.common.framework.dto.GridQueryPara;
import com.kld.common.framework.dto.GridResult;
import com.kld.common.framework.dto.QueryParaDto;
import com.kld.common.framework.result.OperationResult;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;




public abstract class BaseController<T> {
	
	public abstract BaseBizService<T> getBaseBizService();

	public abstract Logger getLogger();
	
	/**
     * 查询列表
     */
    @RequestMapping(value = "/list")
    public
    @ResponseBody
    OperationResult list(GridQueryPara page, QueryParaDto query) {
        GridResult<T> resultList = getBaseBizService().getPageList(page, query);
        return resultList.toOperationResult();
    }
	
	/**
     * 保存信息
     */
    @RequestMapping(value = "/add")
    public
    @ResponseBody
    OperationResult save(T t) {
        try {
        	getBaseBizService().insert(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OperationResult.buildSuccessResult();
    }

    /**
     * 修改信息
     */
    @RequestMapping(value = "/update")
    public
    @ResponseBody
    OperationResult updateCategoryInfo(T t) {
        try {
        	getBaseBizService().update(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OperationResult.buildSuccessResult();
    }


    /**
     * 删除指定信息
     */
    @RequestMapping(value = "/delete")
    public
    @ResponseBody
    OperationResult deleteCategoryInfo(@PathVariable Integer id) {
        try {
        	getBaseBizService().delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OperationResult.buildSuccessResult();
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/batchDelete")
    public
    @ResponseBody
    OperationResult batchDeleteCategoryInfo(Integer[] ids) {
        try {
            for (int i = 0; i < ids.length; i++) {
            	getBaseBizService().delete(ids[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OperationResult.buildSuccessResult();
    }
    
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public
    @ResponseBody
    OperationResult getCommodityInfo(Long id) {
        T details = null;
        try {
            details = getBaseBizService().get(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OperationResult.buildSuccessResult(details);
    }
}
