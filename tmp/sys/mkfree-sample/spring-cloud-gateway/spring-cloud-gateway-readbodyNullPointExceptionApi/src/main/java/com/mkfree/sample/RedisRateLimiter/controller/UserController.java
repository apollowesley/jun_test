package com.mkfree.sample.RedisRateLimiter.controller;

import com.mkfree.sample.RedisRateLimiter.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private static Map<Integer, User> userMap = new HashMap<>();

    // 这里模拟存在用户
    static {
        userMap.put(1, new User(1, "oyhk"));
        userMap.put(2, new User(2, "路人甲"));
    }

    /**
     * 通过id获取用户
     * @param id
     * @return
     */
    @GetMapping(value = "/api/v1/user/findOne")
    public User findOne(int id) {
        return userMap.get(id);
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @PostMapping(value = "/api/v1/user/save")
    public User save(@RequestBody User user) {
        userMap.put(user.getId(), user);
        return userMap.get(user.getId());
    }

    /**
     * 保存用户2
     * @param user
     * @return
     */
    @PostMapping(value = "/api/v1/user/save2")
    public User save2(@RequestBody User user) {
        userMap.put(user.getId(), user);
        return userMap.get(user.getId());
    }


    /**
     * 更新
     * @param user
     * @return
     */
    @PutMapping(value = "/api/v1/user/update")
    public User update(@RequestBody User user) {
        userMap.put(user.getId(), user);
        return userMap.get(user.getId());
    }

    /**
     * 删除
     * @param user
     */
    @DeleteMapping(value = "/api/v1/user/delete")
    public void delete(@RequestBody User user) {
        userMap.remove(user.getId());
    }
}
