/**
 * 
 */
package com.laycms.log;

import org.springframework.stereotype.Repository;

import com.laycms.base.HibernateBaseDao;
import com.laycms.log.entity.BizLog;

/**
 * @author <p>Innate Solitary 于 2012-8-25 下午3:47:36</p>
 *
 */
@Repository
public class LogDao extends HibernateBaseDao<BizLog, Integer> {

	@Override
    public Class<BizLog> getEntityClass() {
	    return BizLog.class;
    }

}
