package com.imzdx.blog;

import com.imzdx.common.model.Blog;
import com.imzdx.common.utils.poi.ExcelKit;
import com.imzdx.common.utils.poi.PoiRender;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

import java.util.List;
import java.util.Map;

/**
 * BlogService
 * 所有 sql 与业务逻辑写在 Service 中，不要放在 Model 中，更不
 * 要放在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class BlogService {

    public static final BlogService me = new BlogService();
    /**
     * 所有的 dao 对象也放在 Service 中
     */
    private static final Blog dao = new Blog().dao();

    public Page<Blog> paginate(int pageNumber, int pageSize) {
        return dao.paginate(pageNumber, pageSize, "select *", "FROM `blog` ORDER BY create_time DESC");
    }

    public Blog findById(int id) {
        return dao.findById(id);
    }

    public void deleteById(int id) {
        dao.deleteById(id);
    }


    public Blog findOne() {
        List<Blog> blogs = dao.find("select * from blog limit 1");
        if (blogs.size() == 0) {
            return new Blog();
        }
        return blogs.get(0);
    }

    public Map<String, Object> importData(UploadFile uplad) {
        String insertSql = "insert into blog (title,author_id,disc,content,category,uv,`like`) values(?,?,?,?,?,?,?)";
        String deleteSql = "delete from blog";
        int count = insertSql.split("\\?").length - 1;
        return ExcelKit.importData(uplad, insertSql, true, deleteSql, count);
    }

    public PoiRender exportData() {
        String[] header = {"id","title","author_id","disc","content","category","uv","like","create_time","update_time"};
        String[] columns = {"id","title","author_id","disc","content","category","uv","like","create_time","update_time"};
        String fileName = "blog";
        List<Blog> objs = Blog.dao.find("select * from blog");
        return PoiRender.me(objs).fileName(fileName + ".xls").headers(header).columns(columns).cellWidth(5000).headerRow(1);
    }
}
