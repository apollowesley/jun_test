package com.imzdx.category;

import com.imzdx.common.model.Category;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheInterceptor;

/**
 * Category 管理
 * 描述：
 */
public class CategoryService {

    //private static final Log log = Log.getLog(CategoryService.class);

    public static final CategoryService me = new CategoryService();
    private final Category dao = new Category().dao();


    /**
     * 列表-分页
     */
    @Before(CacheInterceptor.class)
    public Page<Category> paginate(int pageNumber, int pageSize) {
        return dao.paginate(pageNumber, pageSize, "SELECT * ", "FROM category  ORDER BY create_time DESC");
    }

    /**
     * 保存
     */
    public void save(Category category) {
        category.save();
    }

    /**
     * 更新
     */
    public void update(Category category) {
        category.update();
    }

    /**
     * 查询
     */
    public Category findById(int categoryId) {
        return dao.findFirst("select * from category where id=?", categoryId);
    }

    /**
     * 删除
     */
    public void delete(int categoryId) {
        Db.update("delete from category where id=?", categoryId);
    }


}