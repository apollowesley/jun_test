package com.zhengjieblog.pocket.repo;

import com.zhengjieblog.pocket.entity.PocketDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 郑杰
 * @date 2018-7-20
 */
public interface PocketDetailRepo extends JpaRepository<PocketDetail,Long>,JpaSpecificationExecutor {
}
