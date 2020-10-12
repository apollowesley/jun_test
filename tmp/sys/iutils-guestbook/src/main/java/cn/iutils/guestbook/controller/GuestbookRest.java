package cn.iutils.guestbook.controller;

import cn.iutils.common.Page;
import cn.iutils.common.ResultVo;
import cn.iutils.common.controller.BaseController;
import cn.iutils.guestbook.entity.Guestbook;
import cn.iutils.guestbook.service.GuestbookService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 留言板 接口
 * @author iutils.cn
 * @version 1.0
 */
@RestController
@RequestMapping("${restPath}/guestbook")
public class GuestbookRest extends BaseController {

    @Autowired
    private GuestbookService guestbookService;

    /**
     * 获取留言板分页列表
     * @param page
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public @ResponseBody
    ResultVo page(Guestbook guestbook,Page<Guestbook> page) {
        ResultVo resultVo = null;
        try {
            page.setEntity(guestbook);
            page.setOrderBy("a.create_date desc");
            Page<Guestbook> rPage = page.setList(guestbookService.findPage(page));
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",rPage);
        }catch (Exception e){
            logger.error("获取留言板分页列表接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }

    /**
     * 提交留言
     * @param guestbook
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public @ResponseBody
    ResultVo submit(Guestbook guestbook,HttpServletRequest request) {
        ResultVo resultVo = null;
        try {
            guestbook.setIp(getRemoteAddr(request));
            guestbookService.save(guestbook);
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",guestbook);
        }catch (Exception e){
            logger.error("提交留言接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }

    /**
     * 举报留言
     * @param id
     * @return
     */
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public @ResponseBody
    ResultVo report(@RequestParam(value="id",required=true)String id) {
        ResultVo resultVo = null;
        try {
            guestbookService.report(id);
            Guestbook guestbook = guestbookService.get(id);
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",guestbook.getReport());
        }catch (Exception e){
            logger.error("回复留言接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }

}
