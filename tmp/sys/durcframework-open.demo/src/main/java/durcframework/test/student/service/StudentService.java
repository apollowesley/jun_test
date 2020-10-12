package durcframework.test.student.service;

import org.durcframework.core.service.CrudService;
import org.springframework.stereotype.Service;

import durcframework.test.student.dao.StudentDao;
import durcframework.test.student.entity.Student;


@Service
public class StudentService extends CrudService<Student, StudentDao> {
	
	public void updateStu(){
		Student stu = get(31);
		String name = stu.getName();
		if("Jim".equalsIgnoreCase(name)){
			stu.setName("Jack");
		}else{
			stu.setName("Jim");
		}
		update(stu);
		
		//int i = 1/0; // 模拟出错
	}
	
}
