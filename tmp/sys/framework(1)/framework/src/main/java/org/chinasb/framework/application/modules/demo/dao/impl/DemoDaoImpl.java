package org.chinasb.framework.application.modules.demo.dao.impl;

import org.chinasb.framework.core.base.dao.BaseDAO;
import org.chinasb.framework.application.modules.demo.dao.DemoDao;
import org.chinasb.framework.application.modules.demo.model.Demo;
import org.springframework.stereotype.Repository;

/**
 * @author ethan
 */
@Repository
public class DemoDaoImpl extends BaseDAO<Demo, Integer> implements DemoDao {
    
}
