package tom.kit;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池
 */
public class ThreadPool {

	public static int cpuCoreCount = Runtime.getRuntime().availableProcessors();
	static Logger logger = LoggerFactory.getLogger(ThreadPool.class);
	
	private static Object lock = new Object();
	private static volatile ThreadPoolExecutor commPool ;
	private static volatile ThreadPoolExecutor aioPool;
	

	/**
	 * 同步阻塞线程池, 用做长任务的执行, 用来做aio长连接
	 * @param size
	 * @return
	 */
	public static ThreadPoolExecutor createAioPool() {  //SynchronousQueue LinkedBlockingQueue
		ThreadPoolExecutor threadPoolInstance = new ThreadPoolExecutor(cpuCoreCount, cpuCoreCount, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(cpuCoreCount*20), 
				new DefaultThreadFactory("cocook-aio-pool-"), new RejectedExecutionHandler() {

			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				try {
					// 阻塞 同步, 这样添加不进去. 每次一个线程执行
					executor.getQueue().put(r);
					//logger.warn("AioPool size AIO out {}", executor.getQueue().size());
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});
		// 设置allowCoreThreadTimeOut,允许回收超时的线程
		threadPoolInstance.allowCoreThreadTimeOut(true);
		return threadPoolInstance;
	}

	

	/**
	 * 多而快的 任务处理
	 * 
	 * @return
	 */
	private  static ThreadPoolExecutor createCommPool() { //ArrayBlockingQueue  
		ThreadPoolExecutor threadPoolInstance = new ThreadPoolExecutor(cpuCoreCount , cpuCoreCount , 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(Short.MAX_VALUE), 
				new DefaultThreadFactory(), new RejectedExecutionHandler() {

			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				try {
					// 阻塞 同步, 这样添加不进去. 每次一个线程执行
					executor.getQueue().put(r);
					//logger.warn("CommPool size out {}", executor.getQueue().size());
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});
		// 设置allowCoreThreadTimeOut,允许回收超时的线程
		threadPoolInstance.allowCoreThreadTimeOut(true);
		return threadPoolInstance;
	}

	/**
	 * 返回公用线程池
	 * 
	 * @return 公用线程池
	 */
	public static ThreadPoolExecutor getCommPool() {
		if (commPool == null || commPool.isShutdown()) {
			synchronized (lock) {
				if(commPool == null){
					commPool = createCommPool();
				}
			}
		}
		return commPool;
		
	}
	
	
	public static ThreadPoolExecutor getAioPool() {
		if (aioPool == null || aioPool.isShutdown()) {
			synchronized (lock) {
				if (aioPool == null) {
					aioPool = createAioPool();
				}
			}
		}
		return aioPool;
	}

	public static void exec(Runnable command) {
		getCommPool().execute(command);
	}
	
	static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        
        public DefaultThreadFactory() {
        	this("cocook-comm-pool-");
		}
        
        DefaultThreadFactory(String poolname) { 
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                                  Thread.currentThread().getThreadGroup();
            namePrefix = poolname +  poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
	
}
