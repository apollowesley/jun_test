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
package cn.kiwipeach.demo.multiple;

import cn.kiwipeach.demo.datasource.CustomMultipleDataSource;
import cn.kiwipeach.demo.domain.Employ;
import cn.kiwipeach.demo.mapper.MethodMulityEmployMapper;
import cn.kiwipeach.demo.mapper.MysqlEmployMapper;
import cn.kiwipeach.demo.mapper.OracleEmployMapper;
import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

/**
 * Create Date: 2018/01/14
 * Description: 测试多数据源
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class MulityDatasourceTestCase2 {

    private ApplicationContext applicationContext;

    @Before
    public void init() {
        applicationContext = new ClassPathXmlApplicationContext("spring-context.xml", "spring-mybatis.xml");
    }

    @Test
    public void testMulityDS1() {
        OracleEmployMapper oracleEmployMapper = applicationContext.getBean(OracleEmployMapper.class);
        MysqlEmployMapper mysqlEmployMapper = applicationContext.getBean(MysqlEmployMapper.class);
//        CustomMultipleDataSource.setDataSourceKey("mysqlDataSource"); 手动切换方式
        Employ mysqlEmploy = mysqlEmployMapper.selectByPrimaryKey(new BigDecimal("7777"));
        System.out.println(JSON.toJSONString(mysqlEmploy));
//        CustomMultipleDataSource.setDataSourceKey("oracleDataSource");//手动切换方式
        Employ oracleEmploy = oracleEmployMapper.selectByPrimaryKey(new BigDecimal("7777"));
        System.out.println(JSON.toJSONString(oracleEmploy));

    }

    @Test
    public void testMultiyDS2() {
        MethodMulityEmployMapper methodMulityEmployMapper = applicationContext.getBean(MethodMulityEmployMapper.class);
        Employ methodMulityEmploy1 = methodMulityEmployMapper.mysqlSelectByPrimaryKey(new BigDecimal("7777"));
        System.out.println(JSON.toJSONString(methodMulityEmploy1));
        Employ methodMulityEmploy2 = methodMulityEmployMapper.oracleSelectByPrimaryKey(new BigDecimal("7777"));
        System.out.println(JSON.toJSONString(methodMulityEmploy2));
    }
}
