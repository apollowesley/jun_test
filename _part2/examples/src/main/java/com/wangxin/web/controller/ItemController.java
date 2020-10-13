package com.wangxin.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangxin.common.utils.SolrPageUtil;
import com.wangxin.entity.simple.Item;
import com.wangxin.service.simple.ItemService;
import com.wangxin.solr.ItemDocument;

/** 
 * @Description solrcloud操作
 * @author 王鑫 
 * @date Mar 16, 2017 3:58:01 PM  
 */
@Controller
public class ItemController {

    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    /*
     * 表单提交日期绑定
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/item/add", method = RequestMethod.GET)
    public String add() {
        log.info("# 进入发布新闻页面");
        return "item/add";
    }

    @RequestMapping(value = "/item/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> add(@ModelAttribute("itemForm") Item item) {
        boolean flag = itemService.addItem(item);
        Map<String, String> result = new HashMap<>();
        if (flag) {
            result.put("status", "1");
            result.put("msg", "发布成功");
        } else {
            result.put("status", "0");
            result.put("msg", "发布失败");
        }
        return result;
    }

    @RequestMapping(value = "/item/load/{id}", method = RequestMethod.GET)
    public String load(@PathVariable String id, ModelMap map) {
        log.info("# ajax加载新闻对象");
        ItemDocument item = itemService.findItemById(id);
        map.addAttribute("item", item);
        return "item/edit_form";
    }

    @RequestMapping(value = "/item/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> edit(@ModelAttribute("newsForm") Item item) {
        boolean flag = itemService.editItem(item);
        Map<String, String> result = new HashMap<>();
        if (flag) {
            result.put("status", "1");
            result.put("msg", "发布成功");
        } else {
            result.put("status", "0");
            result.put("msg", "发布失败");
        }
        return result;
    }

    @RequestMapping(value = "/item/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> delte(@PathVariable String id) {
        boolean flag = itemService.deleteItemById(id);
        Map<String, String> result = new HashMap<>();
        if (flag) {
            result.put("status", "1");
            result.put("msg", "删除成功");
        } else {
            result.put("status", "0");
            result.put("msg", "删除失败");
        }
        return result;
    }

    @RequestMapping(value = "/item/list", method = RequestMethod.GET)
    public String list(ModelMap map) {
        Page<ItemDocument> page = itemService.searchForPage(null, 0);
        map.put("page", page);
        map.put("navigatepageNums", SolrPageUtil.getNavigatepageNums(page));
        return "item/list";
    }

    @RequestMapping(value = "/item/list_page", method = RequestMethod.POST)
    public String list_page(@RequestParam(value = "keywords", required = false) String keywords, @RequestParam(value = "pageNum", required = false) Integer pageNum, ModelMap map) {
        log.info("#分页查询新闻 pageNum={} , keywords={}", pageNum, keywords);
        if (pageNum == null)
            pageNum = 0;
        Page<ItemDocument> page = itemService.searchForPage(keywords, pageNum);
        map.put("page", page);
        map.put("navigatepageNums", SolrPageUtil.getNavigatepageNums(page));
        map.put("keywords", keywords);
        return "item/list_page";
    }
    
    @RequestMapping(value = "/item/sync", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sysnc(@RequestParam String syncType) {
        boolean flag = itemService.syncItem(syncType);
        Map<String, String> result = new HashMap<>();
        if (flag) {
            result.put("status", "1");
            result.put("msg", "同步成功");
        } else {
            result.put("status", "0");
            result.put("msg", "同步失败");
        }
        return result;
    }

}
