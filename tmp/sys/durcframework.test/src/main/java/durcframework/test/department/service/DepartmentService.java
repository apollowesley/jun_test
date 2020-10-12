package durcframework.test.department.service;

import org.durcframework.service.CrudService;
import org.springframework.stereotype.Service;

import durcframework.test.department.dao.DepartmentDao;
import durcframework.test.department.entity.Department;

@Service
public class DepartmentService extends CrudService<Department, DepartmentDao> {

}
