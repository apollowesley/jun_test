package com.handy.controller.admin.setting;

import com.handy.common.vo.PageVO;
import com.handy.service.service.sys.ISysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/3 18:43
 */
@RestController
@RequestMapping("/api/admin/setting/menu")
@Api(value = "接口管理")
public class MenuApiController {
    @Autowired
    private ISysResourceService sysResourceService;

    @ApiOperation(value = "获取列表数据")
    @GetMapping("/list")
    public PageVO list() {
        val list = sysResourceService.list();
        return PageVO.pageVO(list, (long) list.size());
    }
}
