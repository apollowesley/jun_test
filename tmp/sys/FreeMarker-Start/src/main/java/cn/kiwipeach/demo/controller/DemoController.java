/*
 * Copyright 2017 KiWiPeach.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.kiwipeach.demo.controller;

import cn.kiwipeach.demo.domain.Employ;
import cn.kiwipeach.demo.freemarker.function.NumberSortTemplate;
import cn.kiwipeach.demo.response.ResultResponse;
import cn.kiwipeach.demo.service.EmployService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * Create Date: 2018/01/08
 * Description: Demo控制器
 *
 * @author kiwipeach [1099501218@qq.com]
 */
@Controller
@RequestMapping("demo")
public class DemoController {

    private final Logger logger = LoggerFactory.getLogger(DemoController.class);


    @Autowired
    private EmployService employService;

    @RequestMapping(value = "toEmpDetail", method = RequestMethod.GET)
    public String toEmployDetailPage(@RequestParam("empno") String empno, Model model) {

        logger.debug("DemoController控制器接受入参:{}", empno);
        Employ employ = employService.queryEmploy(new BigDecimal(empno));
        logger.debug("DemoController控制器返回出参:{}", JSON.toJSONString(employ));
        model.addAttribute("employ", employ);
        logger.debug("DemoController控制器跳转视图:{}", "emp_detail");
        return "emp_detail";
    }

    @RequestMapping(value = "queryEmpDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResultResponse<Employ> queryDetailData(@RequestParam("empno") String empno, Model model) {
        logger.debug("DemoController控制器接受入参:{}", empno);
        Employ employ = employService.queryEmploy(new BigDecimal(empno));
        logger.debug("DemoController控制器返回出参:{}", JSON.toJSONString(employ));
        model.addAttribute("employ", employ);
        return ResultResponse.success(employ);
    }

    @RequestMapping(value = "freemarker", method = RequestMethod.GET)
    public String toFreemarkerPage(Model model) {
        model.addAttribute("freemarker", "kiwipeach-卡卡罗特");
        return "hello";
    }

    /**
     * 8中基本数据类型的取值
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "freemarker01", method = RequestMethod.GET)
    public String toDemo01Page(Model model) {
        byte byteVar = -127;/* -127~127 8位 */
        short shortVar = 32767;/* -32768 ~ 32767 16位 */
        int inVar = 2100000000;/* -2^31-1~2^31  32位 21亿 */
        float floatVar = 3.1415926f;/*单精度：float的精度是6位有效数字，取值范围是10的-38次方到10的38次方，float占用4字节空间*/
        double doubleVar = 3.1415926d;/*双精度：double的精度是15位有效数字，取值范围是10的-308次方到10的308次方,double占用8字节空间。*/
        long longVar = 2100000000000000000L;/* 64位 */
        boolean booleanVar = false;/* ture/false */
        char charVar = 'A';
        String stringVar = "kiwipeach";
        Date dateVar = new Date();

        model.addAttribute("byteVar", byteVar);
        model.addAttribute("shortVar", shortVar);
        model.addAttribute("intVar", inVar);
        model.addAttribute("floatVar", floatVar);
        model.addAttribute("doubleVar", doubleVar);
        model.addAttribute("longVar", longVar);
        model.addAttribute("stringVar", stringVar);
        model.addAttribute("charVar", charVar);
        model.addAttribute("booleanVar", booleanVar);
        model.addAttribute("dateVar", dateVar);
        return "ftl/demo-01";
    }

    /**
     * 自定义数据类型测试
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "freemarker02", method = RequestMethod.GET)
    public String toDemo02Page(Model model) {
        Employ employ = new Employ();
        employ.setEmpno(new BigDecimal(10086));
        employ.setJob("弼马温");
        employ.setEname("孙悟空");
        employ.setSal(8000.00);
        model.addAttribute("suwukong",employ);
        return "ftl/demo-02";
    }

    /**
     * Collection，Map遍历测试测试
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "freemarker03", method = RequestMethod.GET)
    public String toDemo03Page(Model model) {
        //构造Collection和构造Map
        Collection<Employ> employCollection = new ArrayList<Employ>();
        Map<String,Employ> employMap = new HashMap<String,Employ>();
        for (int i=0;i<3;i++){
            Employ employ = new Employ();
            employ.setEmpno(new BigDecimal(10086+i));
            employ.setJob("弼马温"+i);
            employ.setEname("孙悟空"+i);
            employ.setSal(8000.00+i);

            employCollection.add(employ);
            employMap.put(String.valueOf(employ.getEmpno()),employ);
        }

        model.addAttribute("employCollection",employCollection);
        model.addAttribute("employMap",employMap);
        return "ftl/demo-03";
    }

    /**
     *变量运算测试
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "freemarker04", method = RequestMethod.GET)
    public String toDemo04Page(Model model) {
        return "ftl/demo-04";
    }

    /**
     * if,switch测试
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "freemarker05", method = RequestMethod.GET)
    public String toDemo05Page(Model model) {
        return "ftl/demo-05";
    }

    /**
     * 字符串内嵌函数测试
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "freemarker06", method = RequestMethod.GET)
    public String toDemo06Page(Model model) {
        return "ftl/demo-06";
    }

    /**
     * 自定义函数测试
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "freemarker07", method = RequestMethod.GET)
    public String toDemo07Page(Model model) {
        //sort_int 为自定义函数名，在模板引擎中可直接引用
        model.addAttribute("sort_int",new NumberSortTemplate());
        return "ftl/demo-07";
    }

    /**
     * 自定义指令测试
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "freemarker08", method = RequestMethod.GET)
    public String toDemo08Page(Model model) {
        return "ftl/demo-08";
    }

    /**
     * 其他内建函数测试
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "freemarker09", method = RequestMethod.GET)
    public String toDemo09Page(Model model) {
        return "ftl/demo-09";
    }

    /**
     * macro、nested、function指令测试
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "freemarker10", method = RequestMethod.GET)
    public String toDemo10Page(Model model) {
        return "ftl/demo-10";
    }



}
