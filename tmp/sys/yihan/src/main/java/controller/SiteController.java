package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.Detail;
import pojo.Turnimg;
import service.ItemService;
import service.SiteService;
import service.TurnimgService;

import java.util.List;
import java.util.Map;

@Controller
public class SiteController {
    //    @Autowired
//    private UserService userService;
    @Autowired
    private SiteService Service;

    private ItemService itemService;

    @RequestMapping("/site")
    public String site(Model model) {
        Map<String, String> site = Service.getAll();
        model.addAttribute("site", site);
        return "view/site";
    }

    @RequestMapping("/siteEdit")
    public String siteWrite(@RequestParam Map<String, String> map, Model model) {
        Service.editSite(map);
        return "redirect:site.action";
    }



    @RequestMapping("/about")
    public String about(Model model) {
        return "view/about";
    }

}
