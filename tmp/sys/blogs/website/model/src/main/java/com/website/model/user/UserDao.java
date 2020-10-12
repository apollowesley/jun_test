package com.website.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @Author: xiaokai
 * @Description:
 * @Date: 2019/5/18
 * @Version: 1.0
 */
@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserDao extends JpaRepository<User,Long>, PagingAndSortingRepository<User, Long> {

    /**
     * @author: Zhu Liangfu
     * @Description: 查询当前用户信息
     * @date:2019/5/5   16:02
     */
    @Query(value = "select * from user  WHERE user.username=?",nativeQuery = true)
    User findUser(String username);

    /**
     * @author: Zhu Liangfu
     * @Description: 保存注册用户
     * @date:2019/5/5   16:03
     */
    @Query(value = "insert into `user`(username,password,register_time) VALUES (#{username},#{password},#{registerTime})",nativeQuery = true)
    void userRegist(User user);

}
