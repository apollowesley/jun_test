package cn.iutils.comment.controller;

import cn.iutils.common.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.controller.BaseController;
import cn.iutils.comment.entity.Comment;
import cn.iutils.comment.service.CommentService;

import javax.servlet.http.HttpServletRequest;

/**
* 评论表 控制器
* @author iutils.cn
* @version 1.0
*/
@RestController
@RequestMapping("${adminPath}/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    /**
     * 新增评论
     * @param comment
     * @param request
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    ResultVo create(Comment comment, HttpServletRequest request) {
        ResultVo resultVo = null;
        try{
            comment.setIp(getRemoteAddr(request));
            commentService.save(comment);
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",comment);
        }catch (Exception e){
            logger.error("评论接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }

    /**
     * 修改评论
     * @param comment
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    ResultVo update(Comment comment) {
        ResultVo resultVo = null;
        try{
            commentService.save(comment);
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",comment);
        }catch (Exception e){
            logger.error("评论接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody
    ResultVo delete(@RequestParam(value="id",required=true)String id) {
        ResultVo resultVo = null;
        try{
            commentService.delete(id);
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",id);
        }catch (Exception e){
            logger.error("评论接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }
}
