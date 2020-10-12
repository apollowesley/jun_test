/**
 * 
 * @auther wuwang
 * @createTime 2015-1-30 下午10:09:07
 */
package com.weiwo.jpush;

import com.hibernate.voDao.JpushLog;
import com.hibernate.voDao.JpushLogDAO;

/**
 * 
 * 
 * @author peaches
 */
public class JpushLogUtil {
	private static JpushLogDAO jpushLogDao = new JpushLogDAO();
	private JpushLog jpushLog;

	public static void saveLog(JpushLog jpushLog) {
		jpushLogDao.save(jpushLog);
	}

}
