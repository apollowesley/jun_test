package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.User;
import service.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(HttpSession session) {
        /*假装登录*/
//        User u = new User();
//        u.setAdmin(1);
//        u.setUsername("administrator");
//        session.setAttribute("admin", u);
        /*假装登录*/
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "login";//登录
        } else {
            return "admin";
        }
    }

    @RequestMapping("/loginCheck")
    @ResponseBody//将即将返回的对象转成json字符串,再返回到浏览器
    public Map<String, Object> loginCheck(User user1, HttpSession session) {
        User user2 = userService.getUserByUsername(user1.getUsername());
        System.out.println(user1 + " " + user2);
        Map<String, Object> map = new HashMap();
        if (user2 != null) {
            if (user1.getPassword().equals(user2.getPassword())) {
                map.put("success", true);
                session.setAttribute("admin", user2);
                return map;
            }
        }
        map.put("success", false);
        return map;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("admin", null);
        return "redirect:index.action";
    }

    @RequestMapping("/crm")
    public String crm(Model model, HttpSession session) {
        if (adminPd(session)) {
            List<User> list = userService.getAllUser();
            model.addAttribute("list", list);
            return "view/crm";
        }
        model.addAttribute("msg", "对不起,您没有权限访问此页面");
        return "view/msg";
    }

    @RequestMapping("/userEdit")
    public String userEdit(Integer id, Model model, HttpSession session) {
        if (!adminPd(session)) {
            model.addAttribute("msg", "对不起,您没有权限访问此页面");
            return "view/msg";
        }
        if (id == null) {
            return "view/crm-edit";
        }
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "view/crm-edit";
    }

    public boolean adminPd(HttpSession session) {
        User user = (User) session.getAttribute("admin");
        if (user != null) {
            if (user.getAdmin() == 1) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping("/userWrite")
    @ResponseBody//将即将返回的对象转成json字符串,再返回到浏览器
    public Map<String, Object> userWrite(User user, HttpSession session) {
        System.out.println(user);
        Map<String, Object> map = new HashMap();
        if (adminPd(session)) {
            try {
                userService.editUser(user);
                map.put("success", true);
                map.put("msg", "操作成功");
            } catch (Exception e) {
                map.put("success", false);
                map.put("msg", "操作失败");
            }
            return map;
        }
        return map;
    }

    @RequestMapping("/userDel")
    @ResponseBody//将即将返回的对象转成json字符串,再返回到浏览器
    public void userDel(Integer id, HttpSession session) {
        System.out.println(id);
        if (adminPd(session)) {
            userService.userDel(id);
        }
    }
}
