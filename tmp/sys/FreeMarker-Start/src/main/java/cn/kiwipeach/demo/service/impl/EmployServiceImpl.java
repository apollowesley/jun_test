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

import cn.kiwipeach.demo.BusinessException;
import cn.kiwipeach.demo.domain.Employ;
import cn.kiwipeach.demo.global.enums.BusinessEnums;
import cn.kiwipeach.demo.mapper.EmployMapper;
import cn.kiwipeach.demo.response.PageResultResponse;
import cn.kiwipeach.demo.service.EmployService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    @Override
    public Employ queryEmploy(BigDecimal empno) {
        //三种常见异常：1.不传异常code 2.传异常code和message 3.传枚举入参
        if ("7777".equals(String.valueOf(empno))) {
            throw new BusinessException("员工编号有毒[只传message]:"+empno);
        }else if("6666".equals(String.valueOf(empno))){
            throw new BusinessException("-6666","员工编号有毒[传异常code和message]:"+empno);
        }else if ("5555".equals(String.valueOf(empno))){
            throw new BusinessException(BusinessEnums.EMP_BUS_TEST);
        }else if("4444".equals(String.valueOf(empno))){
            throw new NullPointerException("神奇的空指针异常");
        }
        return employMapper.selectByPrimaryKey(empno);
    }

    @Override
    public PageResultResponse<Employ> queryEmployInfo(String job,int pageNo,int pageSize) {
        //使用Mybatis分页插件
        Page<Employ> page = PageHelper.startPage(pageNo, pageSize);
        employMapper.selectByJob(job);
        PageResultResponse<Employ> pageResultResponse = new PageResultResponse<>(pageNo,pageSize);
        pageResultResponse.setPageData(page);
        pageResultResponse.setTotalNum(page.getTotal());
        return pageResultResponse;
    }

}
