package com.jfast.web.admin.controller.system;


import com.jfast.common.utils.ResultCode;
import com.jfast.web.admin.controller.ApiController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 系统日志管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/4/2 19:26
 */
@RestController
@RequestMapping("/system/log")
public class SystemLogController extends ApiController {

    @GetMapping
    @RequiresPermissions("system:log:list")
    public Map list(@RequestParam Map params) {
        return getPageData(params);
    }

    @Override
    @DeleteMapping
    @RequiresPermissions("system:log:list")
    public ResultCode deleteById(@RequestBody Map params) {
        return super.deleteById(params);
    }
}
