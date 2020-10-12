package com.zhengjieblog.pocket.repo;

import com.zhengjieblog.pocket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 郑杰
 * @date 2018-7-16
 */
public interface UserRepo extends JpaRepository<User,Long>{

    /**
     * 根据openid 查询user信息
     * @param openid
     * @return
     */
    User findByOpenid(String openid);
}
