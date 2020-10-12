package site.zhouinfo.mysql.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Author:      zhou
 * Create Date：2016-02-09 20:05
 */
public class Main {

	private final static Logger logger = LoggerFactory.getLogger(Main.class);

	String like="";
	//CPU核心数量 对线程插入数据库的速度有影响
	int threadNumber = Runtime.getRuntime().availableProcessors()*2;

	public static void main(String[] args) {

		Main main = new Main();
		//main.ordinary();

		//多线程处理
		main.thread();
	}

	public void ordinary() {
		long startTime = System.currentTimeMillis();
		//单线程写入数据库
		OrdinaryWrite ordinaryWrite = new OrdinaryWrite();
		ordinaryWrite.cityWriteToArea(like);

		long spendTime = System.currentTimeMillis() - startTime;
		logger.debug("耗时：" + spendTime + " ms");
		logger.debug("笔记本CPU数:" + Runtime.getRuntime().availableProcessors());
	}

	public void thread(){
		ReadData readData = new ReadData();
		readData.readCityByMysql(like);
		readData.cityToArea();

		int threadWriteNumber = readData.getAreaListSize() / threadNumber;
		int threadWriteNumberMod = readData.getAreaListSize() % threadNumber;

		logger.debug(String.valueOf("需要插入的总数："+readData.getAreaListSize()));
		logger.debug(String.valueOf("开的线程数量："+threadNumber));
		logger.debug(String.valueOf("平均每个线程需要插入的数量："+threadWriteNumber));
		logger.debug(String.valueOf("平均线程相加的数量："+threadWriteNumber * threadNumber));
		logger.debug(String.valueOf("最后一个线程需要插入的数量："+(threadWriteNumberMod+threadWriteNumber)));
		logger.debug("笔记本CPU数:" + Runtime.getRuntime().availableProcessors());
		for (int i = 0; i < threadNumber; i++) {
			ThreadWrite threadWrite = new ThreadWrite();
			if (i == threadNumber-1) {
				threadWrite.setAreaList(readData.getAreaList(i * threadWriteNumber, readData.getAreaListSize()));
			} else {
				threadWrite.setAreaList(readData.getAreaList(i * threadWriteNumber, (i + 1) * threadWriteNumber));
			}
			threadWrite.start();
		}
	}
}
