package org.coody.czone.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.coody.czone.web.blog.domain.VisitInfo;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.core.logger.BaseLogger;
import org.coody.framework.core.util.DateUtils;
import org.coody.framework.core.util.PrintException;
import org.coody.framework.core.util.StringUtil;
import org.coody.framework.jdbc.JdbcHandle;
import org.coody.framework.task.annotation.CronTask;

@AutoBuild
public class VisitQueue {

	private static BaseLogger logger = BaseLogger.getLogger(VisitQueue.class);
	private static ConcurrentLinkedQueue<String> visitQueue = new ConcurrentLinkedQueue<String>();

	@AutoBuild
	JdbcHandle jdbcHandle;

	/**
	 * 该方法每秒钟执行一次
	 * 
	 * @throws InterruptedException
	 */
	@CronTask("0/1 * * * * ? ")
	public synchronized void buildVist() throws InterruptedException {
		String userId = visitQueue.poll();
		Map<String, Integer> visitMap = new HashMap<String, Integer>();
		while (!StringUtil.isNullOrEmpty(userId)) {
			try {
				Integer num = visitMap.get(userId);
				if (num == null) {
					num = 0;
				}
				visitMap.put(userId, num + 1);
				TimeUnit.MICROSECONDS.sleep(1);
			} catch (Exception e) {
				PrintException.printException(logger, e);
			} finally {
				userId = visitQueue.poll();
			}
		}
		for (String key : visitMap.keySet()) {
			VisitInfo visitInfo = new VisitInfo();
			visitInfo.setUserId(key);
			visitInfo.setDayCode(DateUtils.getDateString());
			visitInfo.setVisit(visitMap.get(key).longValue());
			jdbcHandle.saveOrUpdate(visitInfo, "visit");
		}
	}

	public void writeVisit(String userId) {
		try {
			visitQueue.add(userId);
		} catch (Exception e) {
			PrintException.printException(logger, e);
		}
	}

	public static void main(String[] args) {
	}

}
