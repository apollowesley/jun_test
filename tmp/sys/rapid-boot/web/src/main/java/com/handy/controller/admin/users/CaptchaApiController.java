package com.handy.controller.admin.users;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.handy.common.vo.PageVO;
import com.handy.service.entity.msg.MsgRecord;
import com.handy.service.service.msg.IMsgRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/29 16:00
 */
@RestController
@RequestMapping("/api/admin/users/captcha")
@Api(value = "验证码查询")
public class CaptchaApiController {
    @Autowired
    private IMsgRecordService msgRecordService;

    @GetMapping("/list")
    @ApiOperation(value = "验证码列表查询")
    public PageVO list(@ApiParam(name = "page", value = "每页页数") Long page,
                       @ApiParam(name = "limit", value = "每页条数") Long limit) {
        val wrapper = new QueryWrapper<MsgRecord>();
        LambdaQueryWrapper<MsgRecord> queryWrapper = wrapper.lambda();
        queryWrapper.orderByDesc(MsgRecord::getSentTime);
        IPage<MsgRecord> msgRecordIPage = msgRecordService.page(new Page<>(page, limit), wrapper);
        return PageVO.pageVO(msgRecordIPage.getRecords(), msgRecordIPage.getTotal());
    }
}
