package cn.iutils.comment.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import cn.iutils.comment.entity.Comment;
import org.apache.ibatis.annotations.Param;

/**
* 评论表 DAO接口
* @author iutils.cn
* @version 1.0
*/
@MyBatisDao
public interface CommentDao extends ICrudDao<Comment> {

    /**
     * 举报
     * @param id
     * @return
     */
    public int report(@Param("id")String id);

}
