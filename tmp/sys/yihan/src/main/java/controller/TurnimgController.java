package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Turnimg;
import service.TurnimgService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TurnimgController {
    @Autowired
    private TurnimgService service;

    @RequestMapping("/turnimg")
    public String logout(Model model) {
        List<Turnimg> list = service.getAllImg();
        model.addAttribute("list", list);
        System.out.println(list);
        return "view/turnimg";
    }

    @RequestMapping("/preview")
    public String preview(String src, Model model) {
        model.addAttribute("src", src);
        return "view/preview";
    }

    @RequestMapping("/turnimgEdit")
    public String turnimgEdit(Integer id, Model model) {
        if (id != null) {
            Turnimg turnimg = service.getTurnimgById(id);
            model.addAttribute("turnimg", turnimg);
        }
        return "view/turnimg-edit";
    }

    @ResponseBody
    @RequestMapping("/turnimgWrite")
    public Map<String, Object> turnimgWrite(Turnimg turnimg) {
        Map<String, Object> map = new HashMap();
        System.out.println(turnimg);
        try {
            service.editTurnimg(turnimg);
            map.put("success", true);
            map.put("msg", "操作成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "操作失败");
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("/turnimgDel")
    public String turnimgWrite(Integer id) {
        service.DelTurnimgById(id);
        return "success";
    }

}