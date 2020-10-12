package priv.mdc.index.dumper.model;

import java.util.List;

import priv.mdc.index.dumper.filter.FilterBean;
import priv.mdc.index.dumper.filter.FilterBean.FieldBean;
import priv.mdc.index.dumper.model.UpdateType;

import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.drc.client.message.DataMessage.Record.Field;

public class ModelUtil {

	/**
	 * 解析来自Dts的数据
	 * @param filterBean
	 * @param fieldList
	 * @return
	 * @throws Exception
	 */
	public static ModelRecord parseDts(FilterBean filterBean, List<Field> fieldList) throws Exception{
		ModelRecord record = new ModelRecord();
		String idFieldName = filterBean.getIdFieldName();
        for (Field field : fieldList) {
        	if(field.getFieldname().equals(idFieldName)){
        		record.setId(field.getValue().toString("UTF-8"));
        		continue;
        	}
        	if(field.isChangeValue()){
        		record.putField(field.getFieldname(), field.getValue().toString("UTF-8"));
        	}
        }
		return record;
	}
	
	/**
	 * 解析来自Canal的数据
	 * @param filterBean
	 * @param columns
	 * @return
	 */
	public static ModelRecord parseCanal(FilterBean filterBean, List<Column> columns) throws Exception{
		ModelRecord record = new ModelRecord();
		String idFieldName = filterBean.getIdFieldName();
        for (Column column : columns) {
        	if(column.getName().equals(idFieldName)){
        		record.setId(column.getValue());
        		continue;
        	}
        	if(column.getUpdated()){
        		record.putField(column.getName(), column.getValue());
        	}
        }
		return record;
	}
	
	/**
	 * 产生用于灌入搜索引擎的文档内容，格式为
	 * @param filterBean
	 * @param record
	 * @param updateType
	 * @return
	 */
	public static IndexDocReq generate(FilterBean filterBean, ModelRecord record, UpdateType updateType){
		IndexDocReq req = new IndexDocReq();
		req.setIndex(filterBean.getIndexName());
		req.setType(filterBean.getDocName());
		req.setId(record.getId());
		switch (updateType){
		case CREATE:
			req.setAction("index");
			break;
		case UPDATE:
			req.setAction("update");
			break;
		case DELETE:
			req.setAction("delete");
			break;
		}
		JSONObject jsonObj = new JSONObject();
		List<FieldBean> dataFields = filterBean.getDataFields();
		for(int i=0; i<dataFields.size(); ++i){
			FieldBean field = dataFields.get(i);
			String value = record.getFieldValue(field.getFieldName());
			if(value==null){
				continue;
			}
			if(field.getFieldType().equals("text")){
				jsonObj.put( field.getDocFieldName(), value );
			}else if(field.getFieldType().equals("int")){
				jsonObj.put( field.getDocFieldName(), new Integer(value) );
			}else if(field.getFieldType().equals("double")){
				jsonObj.put( field.getDocFieldName(), new Double(value) );
			}else if(field.getFieldType().equals("a_double")){
				String[] array = value.split(",");
				Double[] array_double = new Double[array.length];
				for(int j = 0; j<array.length; ++j){
					array_double[j] = new Double(array[j]);
				}
				jsonObj.put( field.getDocFieldName(), array_double );
			}
		}
		req.setReqBody(JSONObject.toJSONString(jsonObj));
		return req;
	}
	
	
}
