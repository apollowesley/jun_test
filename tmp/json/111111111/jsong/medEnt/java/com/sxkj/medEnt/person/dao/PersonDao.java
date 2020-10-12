package com.sxkj.medEnt.person.dao;
import java.util.*;
import com.sxkj.frame.utils.*;
import com.sxkj.frame.utils.PageModel;
import com.sxkj.medEnt.person.bean.Person;
import com.sxkj.medEnt.productCondition.bean.ProductCondition;
import com.sxkj.medEnt.person.bean.Person;
public interface PersonDao {
public void save(Person person) throws Exception;
public void delete(String id) throws Exception;
public void update(Person person) throws Exception;
public OperateEnterprise search(String id) throws Exception;
public List<OperateEnterprise> searchList(Person person, PageModel pageModel) throws Exception;
}