package priv.mdc.index.dumper.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import priv.mdc.index.dumper.util.ConfigInfo;


/**
 * 处理通道管理器
 * @author xuhuahai
 *
 */
public class ProcessChannelMgr {

	protected final static Logger logger = LoggerFactory
			.getLogger(ProcessChannelMgr.class);
	
	/**
	 * 增量处理通道
	 */
	private static Map<String,ProcessChannel> incrementProcessChannels = new HashMap<String,ProcessChannel>();
	
	/**
	 * 全量处理通道
	 */
	private static ProcessChannel fullProcessChannel = null;
	
	/**
	 * 启动增量处理通道
	 */
	public static void startupIncrementProcessChannel(){
		int queueSize = ConfigInfo.getInt("increment.process.queue.size");
		int threadNum = ConfigInfo.getInt("increment.process.thread.num");
		String destinations = ConfigInfo.getString("increment.canal.destinations");
		String[] dest_array = destinations.split(",");
		if (dest_array != null && dest_array.length > 0) {
			for (int i = 0; i < dest_array.length; ++i) {
				String destination = dest_array[i];
				ProcessChannel tempProcessChannel = new ProcessChannel(destination,queueSize,threadNum);
				incrementProcessChannels.put(destination, tempProcessChannel);
				tempProcessChannel.startup();
			}
		}
	}
	
	public static void startupIncrementProcessChannel(String destination){
		int queueSize = ConfigInfo.getInt("increment.process.queue.size");
		int threadNum = ConfigInfo.getInt("increment.process.thread.num");
		ProcessChannel tempProcessChannel = new ProcessChannel(destination,queueSize,threadNum);
		incrementProcessChannels.put(destination, tempProcessChannel);
		tempProcessChannel.startup();
	}
	
	/**
	 * 关闭增量处理通道
	 */
	public static void shutdownIncrementProcessChannel(){
		Iterator<ProcessChannel> processChannelIter = incrementProcessChannels.values().iterator();
		while(processChannelIter.hasNext()){
			processChannelIter.next().shutdown();
		}
	}
	
	/**
	 * 启动全量处理通道
	 */
	public static void startupFullProcessChannel(){
		int queueSize = ConfigInfo.getInt("full.process.queue.size");
		int threadNum = ConfigInfo.getInt("full.process.thread.num");
		fullProcessChannel = new ProcessChannel("FULL",queueSize,threadNum);
		fullProcessChannel.startup();
	}
	
	/**
	 * 关闭全量处理通道
	 */
	public static void shutdownFullProcessChannel(){
		fullProcessChannel.shutdown();
	}
	
	/**
	 * 返回全量队列
	 * @return
	 */
	public static IndexDocReqQueue getFullProcessQueue(){
		if(fullProcessChannel==null){
			return null;
		}
		return fullProcessChannel.getIndexDocReqQueue();
	}
	
	/**
	 * 根据目标返回增量队列
	 * @param destination
	 * @return
	 */
	public static IndexDocReqQueue getIncrementProcessQueue(String destination){
		ProcessChannel tempProcessChannel = incrementProcessChannels.get(destination);
		if(tempProcessChannel==null){
			return null;
		}
		return tempProcessChannel.getIndexDocReqQueue();
	}
	
	/**
	 * 处理通道
	 * @author xuhuahai
	 *
	 */
	public static class ProcessChannel{
		private String prefixName;
		private IndexDocReqQueue indexDocReqQueue = null;
		private List<IndexDocReqProcesser> reqProcessers = new ArrayList<IndexDocReqProcesser>();
		
		public ProcessChannel(String prefixName, int queueSize, int processThreadNum){
			this.prefixName = prefixName;
			indexDocReqQueue = new IndexDocReqQueue(queueSize);
			for(int i=0; i<processThreadNum; ++i){
				reqProcessers.add( new IndexDocReqProcesser(prefixName,i,indexDocReqQueue) );
			}
		}
		
		public void startup(){
			Iterator<IndexDocReqProcesser> reqProcessersIter = reqProcessers.iterator();
			while(reqProcessersIter.hasNext())
			{
				reqProcessersIter.next().startup();
			}
		}
		
		public void shutdown(){
			while(indexDocReqQueue.size()>0){
				logger.info("{} queue have {} unhandled elements, please wait 10s ...",prefixName,indexDocReqQueue.size());
				try {
					Thread.sleep(10*1000);
				} catch (InterruptedException e) {
				}
			}
			Iterator<IndexDocReqProcesser> reqProcessersIter = reqProcessers.iterator();
			while(reqProcessersIter.hasNext())
			{
				reqProcessersIter.next().shutdown();
			}
		}
		
		public IndexDocReqQueue getIndexDocReqQueue() {
			return indexDocReqQueue;
		}
		public void setIndexDocReqQueue(IndexDocReqQueue indexDocReqQueue) {
			this.indexDocReqQueue = indexDocReqQueue;
		}
		
	}
	
}
