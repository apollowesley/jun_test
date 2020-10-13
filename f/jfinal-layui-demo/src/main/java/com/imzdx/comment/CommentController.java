package com.imzdx.comment;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.imzdx.common.model.Comment;

/**
 * Comment 管理	
 * 描述：
 * 
 */
public class CommentController extends Controller {

	//private static final Log log = Log.getLog(CommentController.class);
	
	static CommentService srv = CommentService.me;
	
	/**
	 * 列表
	 * /imzdx/comment/list
	 */
	public void list() {
		setAttr("page", srv.paginate(getParaToInt("p", 1), 40));
		render("commentList.html");
	}
	
	/**
	 * 准备添加
	 * /imzdx/comment/add
	 */
	public void add() {
		render("commentAdd.html");
	}
	
	/**
	 * 保存
	 * /imzdx/comment/save
	 */
	@Before({CommentValidator.class})
	public void save() {
		srv.save(getModel(Comment.class));
		renderJson("isOk", true);
	}

	/**
	 * 准备更新
	 * /imzdx/comment/edit
	 */
	public void edit() {
		Comment comment = srv.findById(getParaToInt("id"));
		setAttr("comment", comment);
		render("commentEdit.html");
	}

	/**
	 * 更新
	 * /imzdx/comment/update
	 */
	@Before(CommentValidator.class)
	public void update() {
		srv.update(getModel(Comment.class));
		renderJson("isOk", true);
	}

	/**
	 * 查看
	 * /imzdx/comment/view
	 */
	public void view() {
		Comment comment = srv.findById(getParaToInt("id"));
		setAttr("comment", comment);
		render("commentView.html");
	}
	 
	/**
	 * 删除
	 * /imzdx/comment/delete
	 */
	public void delete() {
		srv.delete(getParaToInt("id"));
		renderJson("isOk", true);
	}
	
}