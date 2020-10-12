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
import cn.kiwipeach.demo.service.EmployService;
import com.alibaba.fastjson.JSON;
import com.sun.org.apache.regexp.internal.RE;
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
import java.util.Map;

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
    public Employ queryDetailData(@RequestParam("empno") String empno, Model model) {
        logger.debug("DemoController控制器接受入参:{}", empno);
        Employ employ = employService.queryEmploy(new BigDecimal(empno));
        logger.debug("DemoController控制器返回出参:{}", JSON.toJSONString(employ));
        model.addAttribute("employ", employ);
        logger.debug("DemoController控制器跳转视图:{}", "emp_detail");
        return employ;
    }

    @RequestMapping(value = "testMulityDatasource", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String,Employ> testMulityDatasource(@RequestParam("empno") String empno) {
        return employService.testMulityDatasource(empno);
    }


    @RequestMapping(value = "testMulityDatasourceTransation", method = {RequestMethod.GET})
    @ResponseBody
    public int testMulityDatasourceTransation(
            @RequestParam("empno") String empno,
            @RequestParam("name") String name
    ) {
        return employService.testMulityDatasourceTransation(empno, name);
    }

}
