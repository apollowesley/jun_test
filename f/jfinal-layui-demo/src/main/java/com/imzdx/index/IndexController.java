package com.imzdx.index;

import com.imzdx.blog.BlogService;
import com.imzdx.common.model.Blog;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * IndexController
 */
public class IndexController extends Controller {
    static BlogService srv = BlogService.me;

    public void index() {

        Integer page = getParaToInt(0, 1);
        Integer limit = getParaToInt("limit", 10);
        Page<Blog> paginate = srv.paginate(page, limit);
        setAttr("blogs", paginate);
        render("index.html");
    }

    public void admin() {
        render("admin.html");
    }

    public void detail() {
        Integer blogId = getParaToInt(0);
        Blog blog;
        if (blogId != null) {
            blog = srv.findById(blogId);
        } else {
            blog = srv.findOne();
        }
        setAttr("blog", blog);
        // 添加文章样式
        setAttr("type_class", "layui-article-detail");
        render("detail.html");
    }
}



