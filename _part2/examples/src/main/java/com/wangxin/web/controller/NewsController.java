package com.wangxin.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

import com.github.pagehelper.PageInfo;
import com.wangxin.entity.simple.News;
import com.wangxin.service.simple.NewsService;

/** 
 * @Description 新闻示例
 * @author 王鑫 
 * @date Mar 16, 2017 3:58:01 PM  
 */
@Controller
public class NewsController {

    private static final Logger log = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    /*
     * 表单提交日期绑定
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/news/add", method = RequestMethod.GET)
    public String add() {
        log.info("# 进入发布新闻页面");
        return "news/add";
    }

    @RequestMapping(value = "/news/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> add(@ModelAttribute("newsForm") News news) {
        boolean flag = newsService.addNews(news);
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

    @RequestMapping(value = "/news/load/{id}", method = RequestMethod.GET)
    public String load(@PathVariable String id, ModelMap map) {
        log.info("# ajax加载新闻对象");
        News news = newsService.findNewsById(id);
        map.addAttribute("news", news);
        return "news/edit_form";
    }

    @RequestMapping(value = "/news/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> edit(@ModelAttribute("newsForm") News news) {
        boolean flag = newsService.editNews(news);
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

    @RequestMapping(value = "/news/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> delte(@PathVariable String id) {
        boolean flag = newsService.deleteNewsById(id);
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

    @RequestMapping(value = "/news/list", method = RequestMethod.GET)
    public String list(ModelMap map) {
        PageInfo<News> page = newsService.findNewsByPage(null, null);
        map.put("page", page);
        return "news/list";
    }

    @RequestMapping(value = "/news/list_page", method = RequestMethod.POST)
    public String list_page(@RequestParam(value = "keywords", required = false) String keywords, @RequestParam(value = "pageNum", required = false) Integer pageNum, ModelMap map) {
        log.info("#分页查询新闻 pageNum={} , keywords={}", pageNum, keywords);
        PageInfo<News> page = newsService.findNewsByPage(pageNum, keywords);
        map.put("page", page);
        map.put("keywords", keywords);
        return "news/list_page";
    }

}
