package com.imzdx.comment;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.imzdx.common.model.Comment;

/**
 * Comment 管理	
 * 描述：
 */
public class CommentService {

	//private static final Log log = Log.getLog(CommentService.class);
	
	public static final CommentService me = new CommentService();
	private final Comment dao = new Comment().dao();
	
	
	/**
	* 列表-分页
	*/
	public Page<Comment> paginate(int pageNumber, int pageSize) {
		return dao.paginate(pageNumber, pageSize, "SELECT * ", "FROM comment  ORDER BY create_time DESC");
	}
	
	/**
	* 保存
	*/
	public void save(Comment comment) {
		comment.save();
	}
	
	/**
	* 更新
	*/
	public void update(Comment comment) {
		comment.update();
	}
	
	/**
	* 查询
	*/
	public Comment findById(int commentId) {
		return dao.findFirst("select * from comment where id=?", commentId);
	}
	
	/**
	* 删除
	*/
	public void delete(int commentId) {
		Db.update("delete from comment where id=?", commentId);
	}
	
	
}