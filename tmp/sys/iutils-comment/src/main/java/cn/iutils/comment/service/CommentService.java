package cn.iutils.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import cn.iutils.comment.dao.CommentDao;
import cn.iutils.comment.entity.Comment;

/**
* 评论表 Service层
* @author iutils.cn
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class CommentService extends CrudService<CommentDao, Comment> {

    /**
     * 举报
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int report(String id){
        return dao.report(id);
    }

}
