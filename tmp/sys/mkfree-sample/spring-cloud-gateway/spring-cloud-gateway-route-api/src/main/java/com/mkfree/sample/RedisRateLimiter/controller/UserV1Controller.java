package com.mkfree.sample.RedisRateLimiter.controller;

import com.mkfree.sample.RedisRateLimiter.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserV1Controller {

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

    /**
     * 指定请求头规则
     * @param id
     * @return
     */
    @GetMapping(value = "/api/v1/user/findOneRouteHeader")
    public User findOneHeader(int id) {
        return userMap.get(id);
    }

    /**
     * 在指定时间前
     * @param id
     * @return
     */
    @GetMapping(value = "/api/v1/user/findOneRouteBefore")
    public User findOneRouteBefore(int id) {
        return userMap.get(id);
    }

    /**
     * 在指定时间后
     * @param id
     * @return
     */
    @GetMapping(value = "/api/v1/user/findOneRouteAfter")
    public User findOneRouteAfter(int id) {
        return userMap.get(id);
    }

    /**
     * 在指定时间区间
     * @param id
     * @return
     */
    @GetMapping(value = "/api/v1/user/findOneRouteBetween")
    public User findOneRouteBetween(int id) {
        return userMap.get(id);
    }

    /**
     * 在指定cookie
     * @param id
     * @return
     */
    @GetMapping(value = "/api/v1/user/findOneRouteCookie")
    public User findOneRouteCookie(int id) {
        return userMap.get(id);
    }

    /**
     * 在指定host
     * @param id
     * @return
     */
    @GetMapping(value = "/api/v1/user/findOneRouteHost")
    public User findOneRouteHost(int id) {
        return userMap.get(id);
    }

    /**
     * 在指定http method get
     * @param id
     * @return
     */
    @GetMapping(value = "/api/v1/user/findOneRouteMethodGet")
    public User findOneRouteMethodGet(int id) {
        return userMap.get(id);
    }

    /**
     * 在指定http method post
     * @param id
     * @return
     */
    @PostMapping(value = "/api/v1/user/findOneRouteMethodPost")
    public User findOneRouteMethodPost(int id) {
        return userMap.get(id);
    }

    @GetMapping(value = "/api/v1/user/findOneRoutePath/{id}")
    public User findOneRoutePath(@PathVariable int id) {
        return userMap.get(id);
    }


    @GetMapping(value = "/api/v1/user/findOneRouteQuery")
    public User findOneRouteQuery(int id) {
        return userMap.get(id);

    }

    @GetMapping(value = "/api/v1/user/findOneRouteRemoteAddr")
    public User findOneRouteRemoteAddr(int id) {
        return userMap.get(id);
    }

    @PostMapping(value = "/api/v1/user/findOneRouteReadBody")
    public User findOneRouteReadBody(@RequestBody User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    @PostMapping(value = "/api/v1/user/findOneRouteCondition")
    public User findOneRouteCondition(int id) {
        return userMap.get(id);
    }

    @PostMapping(value = "/api/v1/user/findOneRouteCondition2")
    public User findOneRouteCondition2(int id) {
        return userMap.get(id);
    }
}
