package com.course.dao;


import com.course.model.Student;

import java.util.List;


public interface StudentDao {

    public void insertStudent(Student Student);

    public Student getStudentBySId(Long sId);

    public void updateStudentById(Student student);

    public void deleteStudent(Long[] sIds);

    public List<Student> orderTest();

    public void createTest();

    public void deleteAll();

}
