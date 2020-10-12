package com.cdh.dao;

import com.cdh.model.Student;
import com.cdh.model.StudentVO;
import com.cdh.util.DBUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.sql.Struct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends BaseDAO{

    public boolean save(Student student){
        String sql = "insert into student(stu_name,cla_id,user_id) values(?,?,?)";
        return this.add(sql,student);
    }

    public Integer pageCount(String stuName, String claName, Integer pageSize) {
        try {
            con = DBUtil.getConnection();
            String sql = " select count(1) pageCount from user join student on user.user_id = student.user_id " +
                    " join clazz on student.cla_id = clazz.cla_id where stu_name like ?  and cla_name like ? " +
                    " order by stu_id " ;

            ps = con.prepareStatement(sql);
            ps.setString(1,"%"+stuName+"%");
            ps.setString(2,"%"+claName+"%");
            rs = ps.executeQuery();
            if(rs.next()){
                int count = rs.getInt("pageCount");
                return (int)Math.ceil((double)count/(double)pageSize);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,ps,con);
        }
        return 0;
    }

    public List<StudentVO> page(String stuName, String claName, Integer pageIndex, Integer pageSize) {
        List<StudentVO> list = new ArrayList<>();
        try {
            con = DBUtil.getConnection();
            String sql = " select stu_id,stu_name,log_name,if(log_status=0,'禁用','启用') log_status,user_sex,cla_name,student.create_time " +
                    " from user join student on user.user_id = student.user_id  join clazz on student.cla_id = clazz.cla_id " +
                    " where stu_name like ?  and cla_name like ?  order by stu_id limit ?,? ";

            ps = con.prepareStatement(sql);
            ps.setString(1,"%"+stuName+"%");
            ps.setString(2,"%"+claName+"%");
            ps.setInt(3, (pageIndex - 1) * pageSize);
            ps.setInt(4,pageSize);
            rs = ps.executeQuery();
            StudentVO studentVO = null;
            while (rs.next()){
                Integer stuId = rs.getInt("stu_id");
                stuName = rs.getString("stu_name");
                String logName = rs.getString("log_name");
                String logStatus = rs.getString("log_status");
                String userSex = rs.getString("user_sex");
                claName = rs.getString("cla_name");
                LocalDateTime createTime = rs.getTimestamp("create_time").toLocalDateTime();
                studentVO = StudentVO.builder().stuId(stuId).stuName(stuName).logName(logName).logStatus(logStatus)
                        .userSex(userSex).claName(claName).createTime(createTime).build();
                list.add(studentVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,ps,con);
        }
        return list;
    }

    public Integer findUserIdById(Integer stuId) {
        con = DBUtil.getConnection();
        String sql = "select user_id from student where stu_id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1,stuId);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,ps,con);
        }
        return null;
    }

    public boolean delById(Integer stuId) {
        String sql = "delete from student where stu_id=?";
        return this.delById(sql,stuId);
    }

    public boolean update(Student student) {
        String sql = "update student set cla_id=? where stu_id=?";
        return this.update(sql,student);
    }
}
