package com.mkfree.sample.RedisRateLimiterApi.controller;

import com.mkfree.sample.RedisRateLimiterApi.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

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

    @GetMapping(value = "/api/v1/user/findOneRouteAddRequestHeader")
    public User findOneRouteAddRequestHeader(int id, @RequestHeader("headerName1") String headerValue1) {
        log.info("findOneRouteAddRequestHeader , headerName1 value : {}", headerValue1);
        return userMap.get(id);
    }

    @GetMapping(value = "/api/v1/user/findOneRouteAddRequestParameter")
    public User findOneRouteAddRequestParameter(int id, @RequestParam("parameterName1") String parameterValue1) {
        log.info("findOneRouteAddRequestParameter , parameterName1 value : {}", parameterValue1);
        return userMap.get(id);
    }

    @GetMapping(value = "/api/v1/user/findOneRouteAddResponseHeader")
    public User findOneRouteAddResponseHeader(int id) {
        return userMap.get(id);
    }

    @GetMapping(value = "/api/api/v1/user/findOneRoutePrefixPath")
    public User findOneRoutePrefixPath(int id) {
        return userMap.get(id);
    }

    @GetMapping(value = "/api/api/v1/user/findOneRoutePreserveHostHeader")
    public User findOneRoutePreserveHostHeader(int id) {
        return userMap.get(id);
    }
}
