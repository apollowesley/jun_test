package org.coody.czone.task;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.core.logger.BaseLogger;
import org.coody.framework.core.util.PrintException;
import org.coody.framework.core.util.StringUtil;
import org.coody.framework.task.annotation.CronTask;

@AutoBuild
public class WallQueue {

	private static BaseLogger logger=BaseLogger.getLogger(WallQueue.class);
	
	private static ConcurrentLinkedQueue<String> ipQueue=new ConcurrentLinkedQueue<String>();
	
	private static Set<String> wallIps=new HashSet<String>();
	
	public static void writeWallIp(String ip){
		if(wallIps.contains(ip)){
			return;
		}
		try {
			wallIps.add(ip);
			ipQueue.add(ip);
		} catch (Exception e) {
			PrintException.printException(logger, e);
		}	
	}
	/**
	 * 该方法每秒钟执行一次
	 * @throws InterruptedException
	 */
	@CronTask("0/1 * * * * ? ")
	public synchronized void buildWall() throws InterruptedException{
		String ip=ipQueue.poll();
		while(!StringUtil.isNullOrEmpty(ip)){
			try {
				String shell=MessageFormat.format("iptables -I INPUT -s {0} -j DROP", ip);
				Runtime.getRuntime().exec(shell);
				shell="service iptables save";
				Runtime.getRuntime().exec(shell);
				logger.info("封杀IP："+ip);
				Thread.sleep(20);
			} catch (Exception e) {
				PrintException.printException(logger, e);
			}finally {
				ip=ipQueue.poll();
			}
		}
		
	}
	
	
	public static void main(String[] args) {
	}
	
}
