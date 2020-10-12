package priv.mdc.index.dumper.process;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import priv.mdc.index.dumper.model.IndexDocReq;
import priv.mdc.index.dumper.util.ConfigInfo;

/**
 * 逻辑处理器
 * @author Kevin.XU
 *
 */
public class IndexDocReqProcesser implements Runnable{

	final static private Logger logger = LoggerFactory.getLogger(IndexDocReqProcesser.class);
	
	private IndexDocReqQueue serverReqObjQueue;
	
	private boolean isRunning = false;
	private String prefix;
	private int index = -1;
	private Thread currentThread = null;
	
	public IndexDocReqProcesser(String prefix,int index,IndexDocReqQueue serverReqObjQueue)
	{
		this.prefix = prefix;
		this.index = index;
		this.serverReqObjQueue = serverReqObjQueue;
	}
	
	public void startup() 
	{
		currentThread = new Thread(this,prefix+"-IndexDocReqProcesser-"+index);
		currentThread.setPriority(Thread.NORM_PRIORITY);
		currentThread.start();
	}

	public void shutdown()
	{
		isRunning = false;
		currentThread.interrupt();
	}
	
	public void run() 
	{
		isRunning = true;
		logger.info( "startup" );
		while(isRunning)
		{
			int maxBatchNum = ConfigInfo.getInt("process.batch.num");
			int retrieveTimeout = ConfigInfo.getInt("process.batch.timeout");
			List<IndexDocReq> indexDocReqList = null;
			try{
				indexDocReqList = serverReqObjQueue.getReqs(maxBatchNum, retrieveTimeout);
				if(indexDocReqList!=null && !indexDocReqList.isEmpty()){
					IndexDump.dump(indexDocReqList);
				}else{
					logger.info("Not found valid index doc request!");
				}
			}catch(Exception ex){
				logger.error(ex.getMessage(), ex);
				logger.error("server::handle completed failed, request= {}",indexDocReqList);
			}
		}
		logger.info( "shutdown" );
	}
	
}
