框架介绍：
	durcframework是一个基于SpringMVC + Mybatis的框架。其设计目的在于提高开发效率，避免做重复的工作。尤其是在做管理后台时，能减少许多代码量。
 
框架用到的技术点：
	1. 采用泛型设计，对数据库的增删改查做了适度的封装。只需少量代码就能完成一个模块的CRUD操作。
	2. 使用注解来生成查询条件，mybatis文件不需要额外配置，减少了mybatis的代码量。
	3. 使用Java代码动态生成查询条件，可以根据不同场景，不同业务来组装查询条件。
	4. 可以配合前台做Ajax开发，传输JSON格式数据，也可以使用传统SpringMVC到jsp页面。
	5. 后台自动验证功能，支持JSR-303。

使用本框架可以完成的事：
	1. 少量代码完成对一张表的增删改查。
	2. 数据导出
	3. 数据校验

==============================
Controller完成对学生表的增删改查

// 继承CrudController,表示该Controller具有增删改查功能
@Controller
public class StudentCrudController extends CrudController<Student, StudentService> {
	
	@RequestMapping("/addStudent.do")
	public ModelAndView addStudent(Student student) {
		ModelAndView mav = this.save(student);
		System.out.println("添加后的主键ID:"+ student.getId());
		return mav;
	}
	
	@RequestMapping("/listStudent.do")
	public ModelAndView listStudent(SearchStudentEntity searchStudentEntity) {
		return this.queryByEntity(searchStudentEntity);
	}
	
	@RequestMapping("/updateStudent.do")
	public ModelAndView updateStudent(Student student) {
		return this.update(student);
	}
	
	// 传一个id值即可,根据主键删除
	@RequestMapping("/delStudent.do")
	public ModelAndView delStudent(Student student) {
		// 通过主键查询某一条记录
		System.out.println(this.getService().get(student.getId()));
		return this.delete(student);
	}
	
}
// 只需简单继承无需其它代码
@Service
public class StudentService extends CrudService<Student, StudentDao> {}

// 只需简单继承无需其它代码
public interface StudentDao extends BaseDao<Student> {}