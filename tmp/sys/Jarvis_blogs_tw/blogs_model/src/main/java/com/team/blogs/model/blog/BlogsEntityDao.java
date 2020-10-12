package com.team.blogs.model.blog;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: xiaokai
 * @Description:
 * @Date: 2019/6/21
 * @Version: 1.0
 */
public interface BlogsEntityDao extends JpaRepository<BlogsEntity, Long> {
}
