package priv.mdc.index.dumper.ali;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import priv.mdc.index.dumper.DtsClusterMessageNotifier;
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
import com.aliyun.drc.client.message.DataMessage.Record.Field;
import com.aliyun.drc.client.message.DataMessage.Record.Type;
import com.aliyun.drc.clusterclient.message.ClusterMessage;


public class MyDtsClusterMessageNotifier implements DtsClusterMessageNotifier{

	protected final static Logger logger = LoggerFactory.getLogger(MyDtsClusterMessageNotifier.class);
	
	public void notify(ClusterMessage message, String dtsId) {
		printMessage(message,dtsId);
		String table = message.getRecord().getTablename();
		Type optType = message.getRecord().getOpt();
		List<Field> fieldList = message.getRecord().getFieldList();
		try {
			FilterBean bean = FilterModel.queryFilterBean(table);
			if(bean!=null){
				ModelRecord record = ModelUtil.parseDts(bean, fieldList);
				UpdateType type = UpdateType.UPDATE;
                if (optType == Type.DELETE) {
                	type = UpdateType.DELETE;
                } else if (optType == Type.INSERT) {
                	type = UpdateType.CREATE;
                } else if (optType == Type.UPDATE) {
                	type = UpdateType.UPDATE;
                }
				IndexDocReq indexDocReq = ModelUtil.generate(bean, record, type);
				IndexDocReqQueue queue = ProcessChannelMgr.getIncrementProcessQueue(dtsId);
				if(queue!=null){
					queue.putReq(indexDocReq);
				}
			}else{
				//not found define, and skip it!
				logger.error("Not found define of filter bean, skip it : {}, {}",dtsId, table);
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
		} catch (FilterException e) {
			logger.error(e.getMessage(),e);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	private void printMessage(ClusterMessage message, String dtsId) {
		logger.info(">>>>>>>>>>>>>> dts : {}, table : {}, type : {}",dtsId,message.getRecord().getTablename(),message.getRecord().getOpt());
    	StringBuilder builder = new StringBuilder();
    	List<Field> fieldList = message.getRecord().getFieldList();
        for (Field field : fieldList) {
        	try {
				builder.append(field.getFieldname() + " : " + field.getValue().toString("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(),e);
			}
        	builder.append("    type=" + field.getType());
            if (field.isChangeValue()) {
                builder.append("    update=" + field.isChangeValue());
            }
            builder.append("\n");
        }
        logger.info(builder.toString());
	}
	
	
}
