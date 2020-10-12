package com.jfast.web.admin.controller;

import com.jfast.api.ApiService;
import com.jfast.common.base.BaseController;
import com.jfast.common.utils.ResultCode;
import com.jfast.web.admin.service.AdminApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/4/12 21:00
 */
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {

    @Autowired
    protected AdminApiService adminApiService;
    @Autowired
    protected ApiService apiService;

    /**
     * 公用mybatis拦截器分页接口
     * @param params
     * @return
     */
    @GetMapping("/pagination")
    public Map getPageData(@RequestParam Map params) {
        return adminApiService.paginateGetByInterceptor(params);
    }

    /**
     * 公用 分页sql语句 接口
     * @param params
     * @return
     */
    @GetMapping("findByDefaultPagination")
    public Map findByDefaultPagination(@RequestParam Map params) {
        return adminApiService.findByDefaultPagination(params);
    }


    @DeleteMapping("deleteById")
    public ResultCode deleteById(@RequestBody Map params) {
        return adminApiService.deleteById(params);
    }
}
