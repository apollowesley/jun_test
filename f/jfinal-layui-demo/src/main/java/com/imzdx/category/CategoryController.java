package com.imzdx.category;

import com.imzdx.common.model.Category;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * Category 管理
 * 描述：
 */
public class CategoryController extends Controller {

    //private static final Log log = Log.getLog(CategoryController.class);

    static CategoryService srv = CategoryService.me;

    /**
     * 列表
     * /imzdx/category/list
     */
    public void list() {
        setAttr("page", srv.paginate(getParaToInt("p", 1), 40));
        render("categoryList.html");
    }

    /**
     * 准备添加
     * /imzdx/category/add
     */
    public void add() {
        render("categoryAdd.html");
    }

    /**
     * 保存
     * /imzdx/category/save
     */
    @Before({CategoryValidator.class})
    public void save() {
        srv.save(getModel(Category.class));
        renderJson("isOk", true);
    }

    /**
     * 准备更新
     * /imzdx/category/edit
     */
    public void edit() {
        Category category = srv.findById(getParaToInt("id"));
        setAttr("category", category);
        render("categoryEdit.html");
    }

    /**
     * 更新
     * /imzdx/category/update
     */
    @Before(CategoryValidator.class)
    public void update() {
        srv.update(getModel(Category.class));
        renderJson("isOk", true);
    }

    /**
     * 查看
     * /imzdx/category/view
     */
    public void view() {
        Category category = srv.findById(getParaToInt("id"));
        setAttr("category", category);
        render("categoryView.html");
    }

    /**
     * 删除
     * /imzdx/category/delete
     */
    public void delete() {
        srv.delete(getParaToInt("id"));
        renderJson("isOk", true);
    }

}