package cn.gson.framework.controller;

import cn.gson.framework.common.JsonResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.framework.controller</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年04月27日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
public abstract class BaseController<T extends ServiceImpl, M> {

    @Autowired
    protected T service;

    @GetMapping
    public JsonResponse list(@RequestParam Map<String, String> params) {
        QueryWrapper qw = new QueryWrapper<>();
        JsonResponse response;
        if (params.containsKey("page")) {
            Page<Map<String, String>> pageable = new Page<>(Integer.parseInt(params.get("page")), Integer.parseInt(params.getOrDefault("size", "20")));
            params.remove("page");
            params.remove("size");
            qw.allEq(params);
            response = JsonResponse.success("", service.page(pageable, qw));
        } else {
            qw.allEq(params);
            response = JsonResponse.success("", service.list(qw));
        }
        return response;
    }

    @GetMapping("{id:\\d+}")
    public JsonResponse load(@PathVariable Long id) {
        Object dto = service.getById(id);
        return JsonResponse.success("", dto);
    }


    @PostMapping
    public JsonResponse save(M model) {
        service.save(model);
        return JsonResponse.success("", model);
    }

    @PutMapping
    public JsonResponse update(M model) {
        service.updateById(model);
        return JsonResponse.success("", model);
    }

    @DeleteMapping("{id:\\d+}")
    public JsonResponse delete(@PathVariable Long id) {
        service.removeById(id);
        return JsonResponse.success("");
    }
}
