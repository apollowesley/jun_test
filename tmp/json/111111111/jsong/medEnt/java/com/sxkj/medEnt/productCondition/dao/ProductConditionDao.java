package com.sxkj.medEnt.productCondition.dao;
import java.util.*;
import com.sxkj.frame.utils.*;
import com.sxkj.frame.utils.PageModel;
import com.sxkj.medEnt.person.bean.Person;
import com.sxkj.medEnt.productCondition.bean.ProductCondition;
import com.sxkj.medEnt.person.bean.Person;
import com.sxkj.medEnt.productCondition.bean.ProductCondition;
public interface ProductConditionDao {
public void save(ProductCondition productCondition) throws Exception;
public void delete(String id) throws Exception;
public void update(ProductCondition productCondition) throws Exception;
public OperateEnterprise search(String id) throws Exception;
public List<OperateEnterprise> searchList(ProductCondition productCondition, PageModel pageModel) throws Exception;
}