package priv.mdc.index.dumper.process;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import priv.mdc.index.dumper.model.IndexDocReq;


/**
 * 请求对象队列
 * @author Kevin.XU
 *
 */
public class IndexDocReqQueue {

	final static private Logger logger = LoggerFactory.getLogger(IndexDocReqQueue.class);
	
	private int max_size = 10000;         /**default value*/
	private BlockingQueue<IndexDocReq> queue = null;	
	
	public IndexDocReqQueue(int max_size)
	{
		if(max_size>0) this.max_size = max_size;
		this.queue = new LinkedBlockingQueue<IndexDocReq>(this.max_size);
	}
	
	/**
	 * 把请求对象放入队列
	 * @param taskObj
	 * @throws Exception
	 */
	public void putReq(IndexDocReq indexDocReq) throws Exception
	{
		boolean flag = false;
		try {
			int size = size();
			if((double)size/max_size>0.8)
			{
				logger.warn("NOTICE!!request queue size is {}",size);
			}else{
				logger.trace("request queue size is {}",size);
			}
			queue.put(indexDocReq);
			flag = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	/**
	 * 从队列中批量取出请求对象
	 * @param maxNum   最多取的对象个数
	 * @param timeout  超时时间，秒
	 * @return
	 * @throws Exception
	 */
	public List<IndexDocReq> getReqs(int maxNum, int timeout) throws Exception
	{
		List<IndexDocReq> result = new ArrayList<IndexDocReq>();
		while(maxNum-->0){
			try {
				IndexDocReq obj = queue.poll(timeout, TimeUnit.SECONDS);
				if(obj!=null){
					result.add(obj);
				}else{
					break;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw e;
			}
		}
		return result;
	}
	
	/**
	 * 返回请求对象的数目
	 * @return
	 */
	public int size()
	{
		return queue.size();
	}
	
	/**
	 * 清除对象
	 */
	public void clear()
	{
		queue.clear();
	}
	
}
