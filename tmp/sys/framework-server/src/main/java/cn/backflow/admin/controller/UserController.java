package cn.backflow.admin.controller;

import cn.backflow.admin.common.Configuration;
import cn.backflow.admin.common.Constants;
import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.common.secure.Permissions;
import cn.backflow.admin.controller.base.BaseSpringController;
import cn.backflow.admin.entity.User;
import cn.backflow.admin.service.DepartmentService;
import cn.backflow.admin.service.RoleService;
import cn.backflow.admin.service.UserService;
import cn.backflow.lib.util.JsonMap;
import cn.backflow.lib.util.StringUtil;
import cn.backflow.lib.util.WebUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("user")
public class UserController extends BaseSpringController {
    private static final String DEFAULT_SORT_COLUMNS = "updated desc"; //默认多列排序,example: username desc,created asc

    private final DepartmentService departmentService;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(RoleService r, UserService u, DepartmentService d) {
        this.departmentService = d;
        this.roleService = r;
        this.userService = u;
    }

    /* 列表 */
    @RequestMapping
    @Permissions("user.view")
    public Object paging(HttpServletRequest request) throws Exception {
        PageRequest pr = pageRequest(request, DEFAULT_SORT_COLUMNS);
        return userService.findPage(pr);
    }

    /* 列表(关联了部门名称) */
    @RequestMapping("query")
    @Permissions("user.view")
    public Object query(HttpServletRequest request) throws Exception {
        PageRequest pr = pageRequest(request, "u.updated desc");
        return userService.query(pr);
    }

    /**
     * 搜索用户
     *
     * @param request        HttpServletRequest
     * @param keyword        关键字
     * @param include        需包含的用户
     * @param excludeCurrent 是否排除当前用户
     * @return 用户列表
     */
    @RequestMapping("search")
    public List<User> search(HttpServletRequest request, String keyword,
                             @RequestParam(value = "include[]", required = false) String[] include,
                             boolean excludeCurrent) throws Exception {
        PageRequest pr = pageRequest(request, DEFAULT_SORT_COLUMNS, 20);
        if (excludeCurrent) {
            pr.addFilter("current", getCurrentUser(request).getId());
        }
        if (include != null) {
            pr.addFilter("include", include);
        }
        pr.addFilter("keyword", keyword);
        return userService.search(pr);
    }

    @RequestMapping(value = "{id}")
    @Permissions("user.view")
    public Object get(@PathVariable Integer id, HttpServletRequest request) throws Exception {
        User user = userService.getById(id);
        user.setPass(null);
        return user;
    }

    @RequestMapping("roles")
    public Object roles(HttpServletRequest request, @RequestParam(value = "id", required = false) Integer id) throws Exception {
        JsonMap json = JsonMap.succeed();
        json.put("roles", roleService.findAll(null));
        /* 现在用户只能有一个角色
        User currentUser = getCurrentUser(request);
        if (id != null) {
            Map<String, Object> filter = Collections.singletonMap("userId", id);
            Set<Object> owns = roleService.findMap(filter, "id").keySet();
            json.put("owns", owns);
        }
        */
        return json;
    }

    /* 保存新增 */
    @Permissions("user.edit")
    @RequestMapping(method = RequestMethod.POST)
    public Object create(@Valid User user, BindingResult errors, HttpServletRequest request) throws Exception {
        JsonMap json = JsonMap.succeed();
        if (errors.hasErrors()) {
            filedErrors(errors, json);
        }
        user.setAvatar(Configuration.default_avatar);
        userService.save(user);
        return json;
    }

    /* 保存更新 */
    @Permissions("user.edit")
    @RequestMapping(method = RequestMethod.PUT)
    public Object update(@Valid User user, BindingResult errors, HttpServletRequest request) throws Exception {
        JsonMap json = JsonMap.succeed();
        User currentUser = getCurrentUser(request);
        if (!currentUser.isAdmin() && !user.getId().equals(currentUser.getId())) {
            return json.success(false).msg("非超级管理员只能更改自己的信息哟.");
        }
        if (errors.hasErrors()) {
            return filedErrors(errors, json);
        }
        if (StringUtil.isBlank(user.getPass())) {
            user.setPass(null);
        }
        try {
            userService.update(user);
        } catch (Exception e) {
            json.success(false).msg(ExceptionUtils.getRootCauseMessage(e));
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping("avatar")
    public Object avatar(@RequestParam("url") String url, HttpServletRequest request) {
        User user = getCurrentUser(request);
        if (user == null) {
            return JsonMap.fail("会话已过期, 请重新登录.");
        }
        int effected = userService.updateAvatar(user.getId(), "null".equals(url) ? null : url);
        request.getSession().setAttribute(Constants.SESSION_USER_KEY, user);
        return new JsonMap(effected > 0);
    }

    @RequestMapping("current")
    public Object current(HttpServletRequest request) {
        return userService.getById(getCurrentUser(request).getId());
    }

    @RequestMapping("profile")
    public Object profile(HttpServletRequest request) {
        JsonMap json = JsonMap.succeed();
        User user = userService.getById(getCurrentUser(request).getId());
        user.setPass(null);
        return json.put("user", user);
    }

    @RequestMapping(value = "profile", method = RequestMethod.PUT)
    public Object profile(@Valid User user, BindingResult errors, HttpServletRequest request, HttpSession session) throws Exception {
        JsonMap json = JsonMap.succeed();
        User current = getCurrentUser(request);
        user.setName(current.getName());
        if (!Objects.equals(current.getId(), user.getId())) {
            errors.reject("name", null, "请不要企图修改别人的用户信息...");
        }
        if (errors.hasErrors()) {
            return filedErrors(errors, json);
        }
        if (StringUtil.isBlank(user.getEmail())) {
            user.setEmail(null);
        }
        userService.updateProfile(user);
        session.setAttribute(Constants.SESSION_USER_KEY, userService.getById(user.getId()));
        return JsonMap.succeed();
    }

    @Permissions("user.edit")
    @RequestMapping(value = "role", method = RequestMethod.PUT)
    public Object role(@RequestParam("id") Integer id, @RequestParam("roleId") Integer roleId, HttpServletRequest request) throws Exception {
        JsonMap json = JsonMap.succeed();
        User current = getCurrentUser(request);
        User target = userService.getById(id);
        if (!current.isAdmin()) {
            if (target.isAdmin() || roleId == -1) {
                return json.success(false).msg("系统管理员用户只能由系统管理员操作.");
            }
        }
        int effected = userService.updateRoleId(id, roleId);
        return json.success(effected > 0);
    }

    @RequestMapping(value = "partial", method = RequestMethod.PUT)
    public Object partial(@RequestParam("id") Integer id, HttpServletRequest request) {
        JsonMap json = JsonMap.fail("用户信息更新失败");
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.putAll(WebUtil.getRequestAllParams(request));
        int effected = userService.updateSelective(params);
        return json.success(effected > 0);
    }

    @RequestMapping(value = "status", method = RequestMethod.PUT)
    public Object status(@RequestParam("id") Integer id, @RequestParam("status") Integer status) {
        JsonMap json = JsonMap.fail("用户状态更新失败");
        Map<String, Integer> params = new HashMap<>();
        params.put("status", status);
        params.put("id", id);
        int effected = userService.updateSelective(params);
        return json.success(effected > 0);
    }


    /* 删除 */
    @Permissions("user.del")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return JsonMap.succeed();
    }
}