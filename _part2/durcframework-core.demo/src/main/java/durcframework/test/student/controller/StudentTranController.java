package durcframework.test.student.controller;

import org.durcframework.core.DurcException;
import org.durcframework.core.MessageResult;
import org.durcframework.core.controller.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

// 事务处理
@Controller
public class StudentTranController extends CrudController<Student, StudentService>{

	@Autowired
	private TransactionTemplate transactionTemplate;
	
	// http://localhost/durcframeworkTest/updateTran.do
	@RequestMapping("/updateTran.do")
	public @ResponseBody MessageResult updateTran() {
		
		transactionTemplate.execute(new TransactionCallback<Student>() {

			@Override
			public Student doInTransaction(TransactionStatus arg0) {
				
				try{
					Student student = getService().get(1);
					student.setName("李四3");
					getService().update(student);
					int i = 1/0; // 模拟出错
				}catch(Exception e) {
					arg0.setRollbackOnly();
					// 这里抛出错误会在WebExceptionResolver中接受到
					throw new DurcException("修改出错,事务回滚");
				}
				
				return null;
			}
		});
		
		return success();
	}
	
	// http://localhost/durcframeworkTest/updateTran2.do
	@RequestMapping("/updateTran2.do")
	public @ResponseBody MessageResult updateTran2() {
		this.getService().updateStu();
		return success();
	}
	
}
