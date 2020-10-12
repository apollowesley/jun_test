package cn.kiwipeach.demo.service.impl;

import cn.kiwipeach.demo.SpringJunitBase;
import cn.kiwipeach.demo.domain.Employ;
import cn.kiwipeach.demo.response.PageResultResponse;
import cn.kiwipeach.demo.service.EmployService;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * ${DESCRIPTION}
 *
 * @author liuburu
 * @create 2018/01/08
 **/
public class EmployServiceImplTest extends SpringJunitBase{



    @Autowired
    private EmployService employService;

    @Test
    public void queryEmploy(){
        Employ employ = employService.queryEmploy(new BigDecimal("7369"));
        System.out.println(JSON.toJSONString(employ));
    }

    @Test
    public void queryEmployInfo(){
        PageResultResponse<Employ> pageResultResponse = employService.queryEmployInfo("SALESMAN", 3, 2);
        System.out.println(pageResultResponse.getTotalNum());
    }

}