package priv.mdc.index.dumper;

import java.util.List;

import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;

/**
 * 通知器接口
 * @author xuhuahai
 *
 */
public interface BinlogEventNotifier {

	/**
	 * 通知应用层binlog的 数据变更
	 * @param database
	 * @param table
	 * @param eventType
	 * @param columns
	 */
	void notify(String destination, String database, String table, EventType eventType, List<Column> columns);
	
}
