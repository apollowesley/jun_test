package tom.kit;


import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateFormatUtils;
/**
 * 简单任务调度 
 */
public abstract class ScheduledExecutor extends TimerTask {

	private static ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(3);

	private long initialDelay;
	private long period;
	private TimeUnit unit;
	
	public long getInitialDelay() {
		return initialDelay;
	}

	public void setInitialDelay(long initialDelay) {
		this.initialDelay = initialDelay;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

	@Override
	public void run() {
		exec();
	}

	public abstract void exec();

	public void shutdown() {
		if(SCHEDULED_EXECUTOR_SERVICE!=null && !SCHEDULED_EXECUTOR_SERVICE.isShutdown()){
			SCHEDULED_EXECUTOR_SERVICE.shutdown();
			SCHEDULED_EXECUTOR_SERVICE = null;
		}
	}

	private void init() {
		if(SCHEDULED_EXECUTOR_SERVICE ==null || SCHEDULED_EXECUTOR_SERVICE.isShutdown()){
			SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(3);
		}
	}

	public void start() {
		init();
		SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(this, initialDelay, period, unit);
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		ScheduledExecutor  test = new ScheduledExecutor() {
			
			@Override
			public void exec() {
				System.out.println("this=="+ DateFormatUtils.ISO_DATETIME_FORMAT.format(new Date()));
			}
		};
		test.setInitialDelay(0); //任务开始前停留时间
		test.setPeriod(1000); //时间间隔
		test.setUnit(TimeUnit.MILLISECONDS); //单位 毫秒
		test.start(); //开始
		
		Thread.sleep(5000);
		test.shutdown(); //停止
		test = new ScheduledExecutor() {
			
			@Override
			public void exec() {
				System.out.println("again=="+ DateFormatUtils.ISO_DATETIME_FORMAT.format(new Date()));
				
			}
		};
		test.setInitialDelay(0);
		test.setPeriod(1000);
		test.setUnit(TimeUnit.MILLISECONDS);
		test.start();
	}

}
