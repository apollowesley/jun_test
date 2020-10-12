package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.*;
import service.ItemService;
import service.SiteService;
import service.TurnimgService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ViewController {
    //    @Autowired
//    private UserService userService;
    @Autowired
    private TurnimgService turnimgService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private ItemService itemService;


    @RequestMapping("/index")
    public String index(QueryVo vo, Model model) {
        System.out.println(vo);
        List<Turnimg> turnimg = turnimgService.getAllImg();//轮播
        model.addAttribute("turnimg", turnimg);
        model = must(model);
        ArrayList<ArrayList<Item>> itemList = itemService.getItemList(vo);//项目
        model.addAttribute("itemList", itemList);
        vo.setTotal(itemService.getItemSize(vo));//查询条件
        model.addAttribute("vo", vo);
        System.out.println(vo);
//        System.out.println(itemList);
        return "index";
    }

    @RequestMapping("/main")
    public String main(Integer id, Model model) {
        Item item = itemService.getItemById(id);
        model.addAttribute("item", item);
        if (item == null) {
            model.addAttribute("msg", "对不起,该页面已被删除");
            return "view/msg";
        }
        model = must(model);
        List<Detail> detail = itemService.getDetailByItemId(id);
        model.addAttribute("detail", detail);
        return "main";
    }


    @RequestMapping("/aboutUs")
    public String aboutUs(Model model) {
        List<Detail> left = itemService.getDetailByItemId(-1);
        model.addAttribute("left", left);
        List<Detail> right = itemService.getDetailByItemId(-2);
        model.addAttribute("right", right);
        model = must(model);
        return "about-us";
    }

    Model must(Model model) {

        Map<String, String> site = siteService.getAll();//网站信息
        model.addAttribute("site", site);
        List<Type> type = itemService.getAllType();//分类
        model.addAttribute("type", type);
        return model;
    }

}
