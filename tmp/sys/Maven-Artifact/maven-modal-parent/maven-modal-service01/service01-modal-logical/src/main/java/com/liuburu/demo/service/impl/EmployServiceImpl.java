package com.liuburu.demo.service.impl;

import com.liuburu.demo.domain.Employ;
import com.liuburu.demo.mapper.EmployMapper;
import com.liuburu.demo.service.EmployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuburu
 * @create 2017/09/24
 **/
@Service
public class EmployServiceImpl implements EmployService {

    @Autowired
    private EmployMapper employMapper;

    @Override
    public List<Employ> queryAllEmp() {
        return employMapper.selectAll();
    }
}
