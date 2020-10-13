package durcframework.test.department.controller;

import org.durcframework.core.GridResult;
import org.durcframework.core.MessageResult;
import org.durcframework.core.controller.CrudController;
import org.durcframework.core.expression.ExpressionQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.common.SearchEasyUI;
import durcframework.test.department.entity.Department;
import durcframework.test.department.service.DepartmentService;

@Controller
public class DepartmentController extends
		CrudController<Department, DepartmentService> {

	@RequestMapping("/listAllDepartment.do")
	public 
	@ResponseBody
	GridResult listAllDepartment() {
		return this.queryAll(new ExpressionQuery());
	}

	@RequestMapping("/listDepartment.do")
	public @ResponseBody GridResult listDepartment(SearchEasyUI searchEntity) {
		return this.query(searchEntity);
	}

	@RequestMapping("/addDepartment.do")
	public @ResponseBody MessageResult addDepartment(Department department) {
		return this.save(department);
	}

	@RequestMapping("/updateDepartment.do")
	public @ResponseBody MessageResult updateDepartment(Department department) {
		return this.update(department);
	}

	@RequestMapping("/delDepartment.do")
	public @ResponseBody MessageResult delDepartment(Department department) {
		return this.delete(department);
	}
}
