package ivyy.taobao.com.domain.fackjson;

import ivyy.taobao.com.entity.Classz;
import ivyy.taobao.com.entity.Student;
import ivyy.taobao.com.utils.JsonMapper;
/**
 *@Date:2015-1-5
 *@Author:liangjilong
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@Description��
 */
public class JackJson4 {  
    public static void main(String[] args) {  
    	Classz student = getClassz();  
        Long beginTime = System.currentTimeMillis();  
        String json = JsonMapper.toLogJson(student);  
        
        System.out.println("����ת��Ϊjson��" + json);  
        System.out.println("ת����ʱ��" + (System.currentTimeMillis()-beginTime) + "ms");  
        //json2bean����Ҫע�⣺Student���Teacher�������һ���յĹ��췽��  
        beginTime = System.currentTimeMillis();  
        //JsonMapper�ṩ�˺ܶഴ��Mapper�ķ��������Ƿ�Ҫ��buildNonDefaultMapper������ԶԱȼ��ַ�����ת��ʱ�䣬��������  
        Student student2 = JsonMapper.buildNonDefaultMapper().fromJson(json, Student.class);  
        System.out.println("jsonת���ɶ���" + student2);  
        System.out.println("ת����ʱ��" + (System.currentTimeMillis()-beginTime) + "ms");  
    }  
  
    /** 
     * ��ʼ��һ��student 
     * @return 
     */  
    private static Classz getClassz() {  
    	Student stu1=new Student();
		stu1.setAge(22);
		stu1.setUserName("�|����ˇ");
		stu1.setSex("��");
		Classz claz1=new Classz();
		claz1.getStudents().add(stu1);
        return claz1;  
    }  
}  