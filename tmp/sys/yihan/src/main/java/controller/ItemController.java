package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Detail;
import pojo.Item;
import pojo.Type;
import service.ItemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Autowired
    private ItemService service;


    @RequestMapping("/type")
    public String type(Model model) {
        List<Type> type = service.getAllType();
        model.addAttribute("type", type);
        return "view/type";
    }

    @RequestMapping("/typeEdit")
    public String typeEdit(Integer id, Model model) {
        Type type = service.getTypeById(id);
        model.addAttribute("type", type);
        return "view/type-edit";
    }

    @ResponseBody
    @RequestMapping("/typeWrite")
    public Map<String, Object> typeWrite(Type type) {
        service.editType(type);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

    @ResponseBody
    @RequestMapping("/typeDel")
    public Map<String, Object> typeDel(Integer id) {
        service.delTypeById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

    @RequestMapping("/item")
    public String item(Model model, Integer typeId) {
        if (typeId == null) {
            List<Item> item = service.getAllItem();
            model.addAttribute("item", item);
        } else {
            List<Item> item = service.getItemByTypeId(typeId);
            model.addAttribute("item", item);
        }
        List<Type> type = service.getAllType();
        model.addAttribute("type", type);
        return "view/item";
    }

    @RequestMapping("/itemEdit")
    public String itemEdit(Integer id, Model model) {
        Item item = service.getItemById(id);
        model.addAttribute("item", item);
        List<Type> type = service.getAllType();
        model.addAttribute("type", type);
        return "view/item-edit";
    }

    @ResponseBody
    @RequestMapping("/itemWrite")
    public Map<String, Object> itemWrite(Item item) {
        System.out.println("asd" + item);
        Map<String, Object> map = new HashMap<>();
        try {
            service.itemWrite(item);
            map.put("success", true);
            map.put("msg", "操作成功");

        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "操作失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/itemDel")
    public Map<String, Object> itemDel(Integer id) {
        service.delItem(id);
        service.delDetailByItemId(id);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

    @RequestMapping("/detail")
    public String detail(Integer id, Model model) {
        List<Detail> detail = service.getDetailByItemId(id);
        model.addAttribute("detail", detail);
        model.addAttribute("itemid", id);

        System.out.println(detail);
        return "view/detail";
    }

    @RequestMapping("/detailEdit")
    public String detailEdit(Integer id, Integer itemid, Integer type, Model model) {
        if (id != null) {
            Detail detail = service.getDetailById(id);
            model.addAttribute("detail", detail);
            System.out.println(detail);
            type = detail.getType1();
        }
        model.addAttribute("type", type);
        model.addAttribute("itemid", itemid);
        return "view/detail-edit";
    }

    @ResponseBody
    @RequestMapping("/detailWrite")
    public Map<String, Object> detailWrite(Detail detail) {

        System.out.println(detail);
        Map<String, Object> map = new HashMap<>();
        try {
            service.editDetail(detail);
            map.put("success", true);
            map.put("msg", "操作成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "操作失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/detailDel")
    public Map<String, Object> detailDel(Integer id) {
        System.out.println(id);
        service.delDetail(id);

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }


}
