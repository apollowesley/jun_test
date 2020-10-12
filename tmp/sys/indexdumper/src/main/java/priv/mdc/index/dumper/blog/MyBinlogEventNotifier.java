package priv.mdc.index.dumper.blog;

import java.io.FileNotFoundException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import priv.mdc.index.dumper.BinlogEventNotifier;
import priv.mdc.index.dumper.filter.FilterBean;
import priv.mdc.index.dumper.filter.FilterException;
import priv.mdc.index.dumper.filter.FilterModel;
import priv.mdc.index.dumper.model.IndexDocReq;
import priv.mdc.index.dumper.model.ModelRecord;
import priv.mdc.index.dumper.model.ModelUtil;
import priv.mdc.index.dumper.model.UpdateType;
import priv.mdc.index.dumper.process.IndexDocReqQueue;
import priv.mdc.index.dumper.process.ProcessChannelMgr;

import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;

public class MyBinlogEventNotifier implements BinlogEventNotifier{

	protected final static Logger logger = LoggerFactory.getLogger(MyBinlogEventNotifier.class);
	
	public void notify(String destination, String database, String table, EventType eventType,
			List<Column> columns) {
		logger.info(">>>>>>>>>>>>>> database : {}, table : {}, type : {}",database,table,eventType);
		printColumn(columns);
		try {
			FilterBean bean = FilterModel.queryFilterBean(table);
			if(bean!=null){
				ModelRecord record = ModelUtil.parseCanal(bean, columns);
				UpdateType type = UpdateType.UPDATE;
                if (eventType == EventType.DELETE) {
                	type = UpdateType.DELETE;
                } else if (eventType == EventType.INSERT) {
                	type = UpdateType.CREATE;
                } else if (eventType == EventType.UPDATE) {
                	type = UpdateType.UPDATE;
                }
				IndexDocReq indexDocReq = ModelUtil.generate(bean, record, type);
				IndexDocReqQueue queue = ProcessChannelMgr.getIncrementProcessQueue(destination);
				if(queue!=null){
					queue.putReq(indexDocReq);
				}
			}else{
				//not found define, and skip it!
				logger.error("Not found define of filter bean, skip it : {}, {}",database, table);
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
		} catch (FilterException e) {
			logger.error(e.getMessage(),e);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
    private void printColumn(List<Column> columns) {
    	StringBuilder builder = new StringBuilder();
        for (Column column : columns) {
            builder.append(column.getName() + " : " + column.getValue());
            builder.append("    type=" + column.getMysqlType());
            if (column.getUpdated()) {
                builder.append("    update=" + column.getUpdated());
            }
            builder.append("\n");
        }
        logger.info(builder.toString());
    }

}
