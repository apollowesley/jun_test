package priv.mdc.index.dumper;

import java.net.InetSocketAddress;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import priv.mdc.index.dumper.ali.DtsProcesser;
import priv.mdc.index.dumper.ali.MyDtsClusterMessageNotifier;
import priv.mdc.index.dumper.blog.MyBinlogEventNotifier;
import priv.mdc.index.dumper.blog.SimpleCanalClient;
import priv.mdc.index.dumper.full.FullMainLogic;
import priv.mdc.index.dumper.full.IndexInit;
import priv.mdc.index.dumper.process.IndexDump;
import priv.mdc.index.dumper.process.ProcessChannelMgr;
import priv.mdc.index.dumper.util.ConfigInfo;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;

/**
 * 该类为执行入口 
 * 
 * Binlog增量模式：程序一直读取来自MySQL binlog的更新事件，将变化的数据更新入索引
 * 阿里DTS增量模式：通过订阅阿里云DTS的数据表变更消息，将变化的数据更新入索引
 * 全量模式：用于将平日遗漏(增量有部分执行失败的可能)的数据补充完整，定期执行，建议的周期是一周一次，或者只在上线时先执行一次，然后以后根据实际需要再决定是否执行
 * 
 * @author xuhuahai
 *
 */
public class Entrance {

	protected final static Logger logger = LoggerFactory
			.getLogger(Entrance.class);

	public static void main(String[] args) {
		// need only argument, values : increment | full
		if (args.length < 1) {
			System.out.println("Need param : increment | full");
			return;
		}
		String runMode = args[0];
		// String runMode ="full";
		if ("increment".equalsIgnoreCase(runMode)) {
			// increment mode
			logger.info("-----------increment mode-----------");
			IndexDump.init();
			ProcessChannelMgr.startupIncrementProcessChannel();
			String destinations = ConfigInfo
					.getString("increment.canal.destinations");
			String[] dest_array = destinations.split(",");
			if (dest_array != null && dest_array.length > 0) {
				for (int i = 0; i < dest_array.length; ++i) {
					String destination = dest_array[i];
					logger.info("startup for destination : {}", destination);
					CanalConnector connector = CanalConnectors
							.newSingleConnector(new InetSocketAddress(
									ConfigInfo.getString("canal.server.host"),
									ConfigInfo.getInt("canal.server.port")),
									destination, "", "");
					final SimpleCanalClient clientTest = new SimpleCanalClient(
							destination);
					clientTest.setNotifier(new MyBinlogEventNotifier());
					clientTest.setConnector(connector);
					clientTest.start();
					Runtime.getRuntime().addShutdownHook(
							new Thread(new Runnable() {
								public void run() {
									try {
										logger.info("## stop the canal client");
										clientTest.stop();
									} catch (Throwable e) {
										logger.warn(
												"##something goes wrong when stopping canal:\n{}",
												ExceptionUtils.getFullStackTrace(e));
									} finally {
										logger.info("## canal client is down.");
									}
								}
							}, destination + "-ShutdownHook"));
				}
			}
		} else if ("full".equalsIgnoreCase(runMode)) {
			// full mode
			logger.info("-----------full mode-----------");
			IndexDump.init();
			IndexInit.reinit();
			ProcessChannelMgr.startupFullProcessChannel();
			FullMainLogic.execute();
			ProcessChannelMgr.shutdownFullProcessChannel();
		} else if ("alincr".equalsIgnoreCase(runMode)) {
			// aliyun increment mode
			// 利用DTS订阅功能呢来获取变更的数据
			logger.info("-----------alincr mode-----------");
			IndexDump.init();
			MyDtsClusterMessageNotifier myDtsClusterMessageNotifier = new MyDtsClusterMessageNotifier();
			String dtsIds = ConfigInfo.getString("aliyun.dts_id");
			String[] dtsIds_array = dtsIds.split(",");
			if (dtsIds_array != null && dtsIds_array.length > 0) {
				for (int i = 0; i < dtsIds_array.length; ++i) {
					String dtsId = dtsIds_array[i];
					DtsProcesser dtsProcesser = new DtsProcesser(dtsId,myDtsClusterMessageNotifier);
					dtsProcesser.startup();
				}
			}
		} 
	}

}
