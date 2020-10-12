package com.xinhuo.demo.service.impl;

import com.xinhuo.demo.model.Student;
import com.xinhuo.demo.dao.StudentMapper;
import com.xinhuo.demo.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * 描述: 
 * author: 新林
 * date: 2019-06-16
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
