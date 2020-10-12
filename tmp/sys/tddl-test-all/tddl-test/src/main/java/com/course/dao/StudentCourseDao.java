package com.course.dao;


import com.course.model.JoinResult;
import com.course.model.StudentCourse;

import java.util.List;
import java.util.Map;

public interface StudentCourseDao {

    public void insertStudentCourse(StudentCourse studentCourse);

    public StudentCourse getStudentCourseBySId(Long cId);

    public void updateStudentCourseById(StudentCourse studentCourse);

    public void deleteStudentCourse(Long[] cIds);

    public void deleteAll();

    public List<JoinResult> getStudentAndStudentCourseInfo(Long sId);

    public List<JoinResult> joinTest2(Long sId);

}
