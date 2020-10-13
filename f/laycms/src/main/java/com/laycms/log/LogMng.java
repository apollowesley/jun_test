/**
 * 
 */
package com.laycms.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laycms.log.entity.BizLog;
import com.laycms.log.entity.LogOperationEnum;
import com.laycms.log.entity.LogStatusEnum;
import com.laycms.member.entity.Member;

/**
 * @author <p>Innate Solitary 于 2012-8-25 下午3:39:07</p>
 *
 */
@Service
public class LogMng {
	
	@Autowired
	private LogDao logDao;
	
	public void save(Member user, String remoteIp, Integer entityId,LogOperationEnum operation, LogStatusEnum status, String description) {
		this.save(user.getId(), user.getUsername(), remoteIp, entityId, operation, status, description);
	}
	
	public void save(Integer userId, String loginName, String remoteIp, Integer entityId,LogOperationEnum operation, LogStatusEnum status, String description) {
				
		BizLog log = new BizLog(userId, loginName, remoteIp, entityId, description, operation, status);
		log.setCreateTime(new Date());
		logDao.addNew(log);
				
	}
	

	
}
