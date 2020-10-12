package com.handy.controller.entry;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.handy.controller.BaseController;
import com.handy.service.entity.msg.MsgMessage;
import com.handy.service.service.msg.IMsgMessageService;
import com.handy.service.service.sys.ISysAccountService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

/**
 * @author handy
 * @Description: {首页访问接口}
 * @date 2019/9/1 18:09
 */
@Controller
@RequestMapping("/entry/home")
@ApiIgnore()
public class HomeController extends BaseController {
    @Autowired
    private ISysAccountService sysAccountService;
    @Autowired
    private IMsgMessageService msgMessageService;

    @GetMapping("/view")
    public String getCount(HttpSession session, Model model) {
        // 用户数量
        int userCount = sysAccountService.count();
        model.addAttribute("userCount", userCount);
        // 消息通知
        val wrapper = new QueryWrapper<MsgMessage>();
        LambdaQueryWrapper<MsgMessage> queryWrapper = wrapper.lambda();
        queryWrapper.eq(MsgMessage::getAccountId, getLoginUser(session).getId());
        IPage<MsgMessage> msgMessageIPage = msgMessageService.page(new Page<>(1, 10), wrapper);
        model.addAttribute("msgList", msgMessageIPage.getRecords());
        return "entry/home";
    }
}
