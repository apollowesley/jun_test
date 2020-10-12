package cn.iutils.guestbook.controller;

import cn.iutils.common.ResultVo;
import cn.iutils.common.controller.BaseController;
import cn.iutils.guestbook.entity.Guestbook;
import cn.iutils.guestbook.service.GuestbookService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 留言板 接口
 * @author iutils.cn
 * @version 1.0
 */
@RestController
@RequestMapping("${adminPath}/guestbook")
public class GuestbookController extends BaseController {

    @Autowired
    private GuestbookService guestbookService;

    /**
     * 回复留言
     * @param guestbook
     * @return
     */
    @RequiresPermissions("guestbook:reply")
    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public @ResponseBody
    ResultVo reply(Guestbook guestbook,HttpServletRequest request) {
        ResultVo resultVo = null;
        try {
            guestbook.preUpdate();
            guestbookService.reply(guestbook);
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",guestbook);
        }catch (Exception e){
            logger.error("回复留言接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }

    /**
     * 删除留言
     * @param id
     * @return
     */
    @RequiresPermissions("guestbook:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody
    ResultVo delete(@RequestParam(value="id",required=true)String id,HttpServletRequest request) {
        ResultVo resultVo = null;
        try {
            guestbookService.delete(id);
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",id);
        }catch (Exception e){
            logger.error("删除留言接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }

}
