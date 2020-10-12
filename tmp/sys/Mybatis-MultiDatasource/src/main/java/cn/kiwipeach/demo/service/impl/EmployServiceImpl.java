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
package cn.kiwipeach.demo.service.impl;

import cn.kiwipeach.demo.datasource.CustomMultipleDataSource;
import cn.kiwipeach.demo.domain.Employ;
import cn.kiwipeach.demo.mapper.EmployMapper;
import cn.kiwipeach.demo.mapper.MysqlEmployMapper;
import cn.kiwipeach.demo.mapper.OracleEmployMapper;
import cn.kiwipeach.demo.service.EmployService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create Date: 2018/01/08
 * Description: 员工服务实现类
 *
 * @author kiwipeach [1099501218@qq.com]
 */
@Service
public class EmployServiceImpl implements EmployService {


    @Autowired
    private EmployMapper employMapper;

    @Autowired
    private OracleEmployMapper oracleEmployMapper;

    @Autowired
    private MysqlEmployMapper mysqlEmployMapper;

    @Override
    public Employ queryEmploy(BigDecimal empno) {
        return employMapper.selectByPrimaryKey(empno);
    }

    @Override
    public Map<String, Employ> testMulityDatasource(String empno) {
        Map<String, Employ> employMap = new HashMap<String, Employ>();
        //默认数据源：mysql
        Employ defaultEmploy = employMapper.selectByPrimaryKey(new BigDecimal("7777"));
        System.out.println(JSON.toJSONString(defaultEmploy));

        //切换数据源：oracle
        //若使用手动注解则放开下面注释
        //CustomMultipleDataSource.setDataSourceKey("oracleDataSource");
        Employ oracleEmploy = oracleEmployMapper.selectByPrimaryKey(new BigDecimal("7777"));//将会根据@Datasource注解自动设置数据源信息
        System.out.println(JSON.toJSONString(oracleEmploy));

        //切换数据源：mysql
        //若使用手动注解则放开下面注释
        //CustomMultipleDataSource.setDataSourceKey("mysqlDataSource");
        Employ mysqlEmploy = mysqlEmployMapper.selectByPrimaryKey(new BigDecimal("7777"));//将会根据@Datasource注解自动设置数据源信息
        System.out.println(JSON.toJSONString(mysqlEmploy));
        employMap.put("defaultEmploy", defaultEmploy);
        employMap.put("oracleEmploy", oracleEmploy);
        employMap.put("mysqlEmploy", mysqlEmploy);
        return employMap;
    }

    @Override
    @Transactional
    public int testMulityDatasourceTransation(String empno, String name) {
        Employ updateEmploy = new Employ(new BigDecimal(empno), name);
        CustomMultipleDataSource.setDataSourceKey("oracleDataSource");
        int i1 = employMapper.updateByPrimaryKeySelective(updateEmploy);
        CustomMultipleDataSource.setDataSourceKey("mysqlDataSource");
        int i2 = employMapper.updateByPrimaryKeySelective(updateEmploy);
        if ("7777".equals(empno)) {
            throw new RuntimeException("有毒员工编号：" + empno);
        }
        return i1 + i2;
    }


}
