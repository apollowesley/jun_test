package com.imzdx.common.model;

import com.imzdx.common.model.base.BaseBlog;
import com.jfinal.plugin.activerecord.Page;

/**
 * Blog model.
 * 数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId
 */
@SuppressWarnings("serial")
public class Blog extends BaseBlog<Blog> {

    public static final Blog dao = new Blog().dao();

    public Page<Blog> paginate(int pageNumber, int pageSize) {
        return paginate(pageNumber, pageSize, "SELECT * ", "FROM `blog` ORDER BY create_time DESC");
    }


}
