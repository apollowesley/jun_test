package priv.mdc.index.dumper;

import com.aliyun.drc.clusterclient.message.ClusterMessage;

public interface DtsClusterMessageNotifier {

	/**
	 * 通知
	 * @param message
	 */
	void notify(ClusterMessage message, String dtsId);
	
}
