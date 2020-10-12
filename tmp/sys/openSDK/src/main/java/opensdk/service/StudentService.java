package opensdk.service;

import java.util.Locale;

import opensdk.GridResult;
import opensdk.entity.SearchStudentEntity;
import opensdk.entity.Student;

public class StudentService extends BaseService {

	public StudentService(String appId, String secret, String serverCtx) {
		super(appId, secret, serverCtx);
	}
	
	public GridResult<Student> listStudent(SearchStudentEntity searchStudentEntity,Locale locale) {
		return this.getClient().postJsonByObj2Obj("listApiStudent.do", searchStudentEntity,GridResult.class,locale);
	}

}
