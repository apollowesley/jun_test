package com.imzdx.common.interceptor;

import com.imzdx.blog.BlogService;
import com.imzdx.category.CategoryService;
import com.imzdx.common.model.Blog;
import com.imzdx.common.model.Category;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * Created by imzdx on 2017/12/27 0027.
 */
public class ParameterInterceptor implements Interceptor {

    static CategoryService categoryService = CategoryService.me;
    static BlogService blogService = BlogService.me;

    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
//        controller.setAttr("path", controller.getRequest().getContextPath());
        // 菜单导航
        Page<Category> paginate = categoryService.paginate(1, 5);
        controller.setAttr("c",paginate.getList());
        // 标签云
        paginate = categoryService.paginate(1, 100);
        controller.setAttr("s",paginate.getList());
        // 近期文章
        Page<Blog> blogPage = blogService.paginate(1, 10);
        controller.setAttr("b",blogPage.getList());
        // 最热文章
        Page<Blog> hotPage = blogService.paginate(1, 10);
        controller.setAttr("h",hotPage.getList());
        inv.invoke();
    }
}
