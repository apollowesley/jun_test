package cn.afterturn.dao.test.examples.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.afterturn.dao.test.examples.dao.EmployeeDao;


@Service
public class EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	public void sayHello(){
		employeeDao.getCount();
	}
}
