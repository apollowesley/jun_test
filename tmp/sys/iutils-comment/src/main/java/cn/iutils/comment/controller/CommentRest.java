package cn.iutils.comment.controller;

import cn.iutils.comment.entity.Comment;
import cn.iutils.comment.service.CommentService;
import cn.iutils.common.Page;
import cn.iutils.common.ResultVo;
import cn.iutils.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评论表 接口
 * @author iutils.cn
 * @version 1.0
 */
@RestController
@RequestMapping("${restPath}/comment")
public class CommentRest extends BaseController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取评论分页列表
     * @param page
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public @ResponseBody
    ResultVo page(Comment comment,Page<Comment> page) {
        ResultVo resultVo = null;
        try {
            page.setEntity(comment);
            page.setOrderBy("a.create_date desc");
            Page<Comment> rPage = page.setList(commentService.findPage(page));
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",rPage);
        }catch (Exception e){
            logger.error("获取评论分页列表接口调用失败",e.getMessage());
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
            commentService.report(id);
            Comment comment = commentService.get(id);
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",comment.getReport());
        }catch (Exception e){
            logger.error("回复留言接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }

}
