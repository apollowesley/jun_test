package com.cdh.service;

import com.cdh.dao.ClazzDAO;
import com.cdh.dao.StudentDAO;
import com.cdh.dao.UserDAO;
import com.cdh.model.Clazz;
import com.cdh.model.Student;
import com.cdh.model.StudentVO;
import com.cdh.model.User;
import com.cdh.util.MD5Util;
import com.cdh.util.PinYinUtil;
import com.cdh.util.ResponseData;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class StudentService {

    private StudentDAO studentDAO = new StudentDAO();
    private UserDAO userDAO = new UserDAO();
    private ClazzDAO clazzDAO = new ClazzDAO();

    public ResponseData save(String stuName, String stuClazz, String stuSex, String stuStatus) {
        //登陆名
        String logName = createLogName(stuName);
        //登陆密码
        String logPwd = MD5Util.getMD5("123");
        //登陆状态
        Integer logStatus = Objects.equals("启用",stuStatus)?1:0;
        User user = User.builder().logName(logName).logPwd(logPwd).logStatus(logStatus).userType("student").userSex(stuSex).build();
        boolean addUserFlg = userDAO.save(user);
        if(!addUserFlg){
            return ResponseData.fail("添加学生失败！");
        }
        Integer userId = userDAO.findUserByLogName(logName).getUserId();
        Integer classId = clazzDAO.findClassByName(stuClazz).getClaId();
        Student student = Student.builder().claId(classId).stuName(stuName).userId(userId).build();
        boolean addStuFlg = studentDAO.save(student);
        if(addStuFlg){
            return ResponseData.ok("添加学生成功！！");
        }
        return ResponseData.fail("添加学生失败！！");
    }

    private String createLogName(String stuName){
        String logName = PinYinUtil.getPingYin(stuName);
        String lastLoginName = userDAO.findLastLogName(logName);
        if(Objects.isNull(lastLoginName)){
            return logName;
        }
        String index = lastLoginName.substring(logName.length());
        if(StringUtils.isEmpty(index)){
            return logName+"1";
        }
        return logName+(Integer.parseInt(index)+1);
    }

    public ResponseData page(String stuName,String claName, Integer pageIndex, Integer pageSize) {
        if(Objects.equals(claName,"请选择")){
            claName="";
        }
        //查询总页数
        Integer pageCount = studentDAO.pageCount(stuName,claName,pageSize);
        //查询学生列表
        List<StudentVO> pageList = studentDAO.page(stuName,claName,pageIndex,pageSize);
        Object[][] pageData = new Object[pageList.size()][];
        for (int i = 0; i < pageList.size(); i++) {
            StudentVO stu = pageList.get(i);
            pageData[i] = new Object[]{stu.getStuId(),stu.getStuName(),stu.getLogName(),stu.getLogStatus(),
                    stu.getUserSex(),stu.getClaName(),stu.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))};
        }
        return ResponseData.okPage(pageData,pageCount);

    }

    public ResponseData delById(Integer stuId) {

        Integer userId = studentDAO.findUserIdById(stuId);
        if(Objects.isNull(userId)){
            return ResponseData.fail("要删除的学生不存在！!");
        }
        //删除学生信息
        boolean flg = studentDAO.delById(stuId);
        //删除用户信息
        flg = userDAO.del(userId);
        if(flg){
            return ResponseData.ok("删除学生成功！！");
        }
        return ResponseData.fail("删除学生失败！！");
    }

    public ResponseData update(StudentVO studentVO) {
        //修改登录状态
        Integer userId = studentDAO.findUserIdById(studentVO.getStuId());
        Integer logStatus = Objects.equals(studentVO.getLogStatus(),"启用")?1:0;
        User user = User.builder().userId(userId).logStatus(logStatus).build();
        userDAO.updateStatus(user);

        //修改学生所在班级
        Integer claId = clazzDAO.findClassByName(studentVO.getClaName()).getClaId();
        Student student = Student.builder().stuId(studentVO.getStuId()).claId(claId).build();
        boolean flg = studentDAO.update(student);
        if(flg){
            return ResponseData.ok("修改学生信息成功！！");
        }else{
            return ResponseData.fail("修改学生信息失败！！");
        }
    }
}
