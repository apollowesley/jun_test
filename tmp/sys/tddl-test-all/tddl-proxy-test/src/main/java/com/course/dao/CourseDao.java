package com.course.dao;


import com.course.model.Course;

public interface CourseDao {

    public void insertCourse(Course course);

    public Course getCourseBySId(Long cId);

    public void updateCourseById(Course course);

    public void deleteCourse(Long[] cIds);

    public void deleteAll();
}
