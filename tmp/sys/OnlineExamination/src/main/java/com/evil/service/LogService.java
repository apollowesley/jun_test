package com.evil.service;

import com.evil.pojo.system.Log;
import com.evil.util.Page;
import com.evil.util.PageResult;

public interface LogService extends BaseService<Log> {
	
	/**
	 * ��ѯϵͳ�����б���ҳ
	 * @param page  ��ҳ��Ϣ
	 * @param sortfield ������Ϣ
	 * @return
	 */
	PageResult findLogPage(Page page, String sortfield);

}
