package durcframework.test.department.controller;

import org.durcframework.controller.CrudController;
import org.durcframework.entity.SearchEntity;
import org.durcframework.expression.ExpressionQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import durcframework.test.common.SearchEasyUI;
import durcframework.test.department.entity.Department;
import durcframework.test.department.service.DepartmentService;

@Controller
public class DepartmentController extends
		CrudController<Department, DepartmentService> {

	@RequestMapping("/listAllDepartment.do")
	public ModelAndView listAllDepartment() {
		return this.queryAll(new ExpressionQuery());
	}

	@RequestMapping("/listDepartment.do")
	public ModelAndView listDepartment(SearchEasyUI searchEntity) {
		return this.queryByEntity(searchEntity);
	}

	@RequestMapping("/addDepartment.do")
	public ModelAndView addDepartment(Department department) {
		ModelAndView mav = this.save(department);
		return mav;
	}

	@RequestMapping("/updateDepartment.do")
	public ModelAndView updateDepartment(Department department) {
		return this.update(department);
	}

	@RequestMapping("/delDepartment.do")
	public ModelAndView delDepartment(Department department) {
		return this.delete(department);
	}
}
