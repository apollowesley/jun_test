package priv.mdc.index.dumper.ali;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import priv.mdc.index.dumper.DtsClusterMessageNotifier;
import priv.mdc.index.dumper.process.ProcessChannelMgr;
import priv.mdc.index.dumper.util.ConfigInfo;

import com.aliyun.drc.client.message.DataMessage.Record.Type;
import com.aliyun.drc.clusterclient.ClusterClient;
import com.aliyun.drc.clusterclient.ClusterListener;
import com.aliyun.drc.clusterclient.DefaultClusterClient;
import com.aliyun.drc.clusterclient.RegionContext;
import com.aliyun.drc.clusterclient.message.ClusterMessage;

/**
 * 一个阿里DTS订阅通道对应一个DtsProcesser
 * @author xuhuahai
 *
 */
public class DtsProcesser {

	protected final static Logger logger = LoggerFactory.getLogger(DtsProcesser.class);
	
	/**
	 * 订阅通道ID
	 */
	private String dtsId;
	
	/**
	 * 事件通知器
	 */
	private DtsClusterMessageNotifier dtsClusterMessageNotifier;
	
	public DtsProcesser(String dtsId,DtsClusterMessageNotifier dtsClusterMessageNotifier){
		this.dtsId = dtsId;
		this.dtsClusterMessageNotifier = dtsClusterMessageNotifier;
	}
	
	public void startup(){
		ProcessChannelMgr.startupIncrementProcessChannel(dtsId);
		// 创建一个context
		RegionContext context = new RegionContext();
		// 运行SDK的服务器是否使用公网IP连接DTS
		context.setUsePublicIp(true);
		// 用户accessKey secret
		context.setAccessKey(ConfigInfo.getString("aliyun.acccess_key"));
		context.setSecret(ConfigInfo.getString("aliyun.secret"));
		// 创建消费者
		final ClusterClient client = new DefaultClusterClient(context);
		// 创建订阅监听者listener
		ClusterListener listener = new ClusterListener() {
			@Override
			public void notify(List<ClusterMessage> messages)
					throws Exception {
				for (ClusterMessage message : messages) {
					if(message.getRecord().getOpt() == Type.HEARTBEAT){
						logger.debug("heartbeat");
					}else{
						if(message.getRecord().getOpt() == Type.INSERT
								|| message.getRecord().getOpt() == Type.UPDATE
								|| message.getRecord().getOpt() == Type.DELETE){
							dtsClusterMessageNotifier.notify(message,dtsId);
						}else{
							logger.debug("unknown msg {}",message.getRecord().getOpt());
						}
					}
					// 消费完数据后向DTS汇报ACK，必须调用
					message.ackAsConsumed();
				}
			}
			@Override
			public void noException(Exception e) {
				logger.error(e.getMessage(), e);
			}
		};
		// 添加监听者
		client.addConcurrentListener(listener);
		try {
			// 设置请求的订阅通道ID
			client.askForGUID(dtsId);
			// 启动后台线程， 注意这里不会阻塞， 主线程不能退出
			client.start();
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
	}
	
	public void shutdown(){
		
	}
	
}
