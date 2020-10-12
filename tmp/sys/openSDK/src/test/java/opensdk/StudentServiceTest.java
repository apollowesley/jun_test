package opensdk;

import java.util.List;
import java.util.Locale;

import junit.framework.Assert;
import opensdk.entity.SearchStudentEntity;
import opensdk.entity.Student;
import opensdk.service.StudentService;

import org.junit.Test;

public class StudentServiceTest extends TestBase {
	
	StudentService service = new StudentService(appId, secret, serverCtx);

	@Test
	public void testList() {
		SearchStudentEntity searchStudentEntity = new SearchStudentEntity();
		searchStudentEntity.setSchName("Jim");
		
		GridResult<Student> gridResult = service.listStudent(searchStudentEntity,Locale.CHINESE);
		
		Assert.assertNotNull(gridResult);
		
		List<Student> rows = gridResult.getList(Student.class);
		
		for (Student stu : rows) {
			System.out.println(stu.getId() + "\t" + stu.getName());
		}
	}
	
}
