package cn.jeeweb.tag.test.controller;

import cn.jeeweb.tag.test.entity.Table;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.generator.controller
 * @title:
 * @description: Cache工具类
 * @author: 王存见
 * @date: 2018/8/7 11:49
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@Controller
public class IndexController {

    /**
     * 测试beetl模板
     *
     * @return
     */
    @RequestMapping("/")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id", "apk2sf@163.com");
        List<Table> tableList = new ArrayList<>();
        Table table = new Table();
        table.setId("1");
        table.setTableName("1");
        Table table2 = new Table();
        table2.setId("2");
        table2.setTableName("王存见2");

        tableList.add(table);
        tableList.add(table2);
        modelAndView.addObject("tableList", tableList);
        modelAndView.addObject("tableName", "");
        modelAndView.addObject("table", table);
        modelAndView.setViewName("form");
        return modelAndView;
    }
}
