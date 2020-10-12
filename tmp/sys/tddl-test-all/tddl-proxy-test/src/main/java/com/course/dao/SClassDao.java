package com.course.dao;


import com.course.model.SClass;

import java.util.List;

public interface SClassDao {

    public void insertSClass(SClass sClass);

    public SClass getSClassBySId(Long cId);

    public void updateSClassById(SClass sClass);

    public void deleteSClass(Long[] cIds);

    public List<SClass> getUnionResult();

    public void deleteAll();

}
