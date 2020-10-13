package com.pflm.modules.wechat.controller;
import com.pflm.base.entity.AccessTokenEntity;
import com.pflm.modules.sys.controller.AbstractController;
import com.pflm.modules.wechat.service.AccessTokenService;
import com.pflm.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author qinxuewu
 * @version 1.00
 * @time 15/11/2018下午 6:10
 */
@RestController
@RequestMapping("/sys/wechattoken")
public class WechatTokenController extends AbstractController {




    @Autowired
    AccessTokenService accessTokenService;

    /**
     * 微信公众号配置
     */
    @RequestMapping("/get")
    @RequiresPermissions("sys:wechattoken:all")
    public R list() {
        AccessTokenEntity data=accessTokenService.getToken(1);
        return R.ok().put("data",data);
    }



}
