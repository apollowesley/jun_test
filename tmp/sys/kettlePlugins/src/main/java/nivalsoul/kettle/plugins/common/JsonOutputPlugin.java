package nivalsoul.kettle.plugins.common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.pentaho.di.core.row.RowMetaInterface;

import com.alibaba.fastjson.JSON;

public class JsonOutputPlugin extends CommonStepRunBase {
	//字段
	private String[] fields; 
	//缓存数据行
	private StringBuffer rows = new StringBuffer();
	//输出json类型:array(按行输出到JsonArray)、object(所有行输出到JsonObject的字段)
	private String outputType = "array";
	//字段名，输出类型为object时有效
	private String fieldName = "data";
	//json文件名
	private String fileName = "";
	//输出批量大小
	int batchSize = 1000;
	
	boolean isFirst = true;
	int num = 0;

	@Override
	protected Object[] disposeRow(Object[] row) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();  
        for(int i = 0; i < fields.length; i++){
        	map.put(fields[i],row[i]);
        }
        if(!isFirst) {
        	rows.append(',').append(JSON.toJSONString(map));
        }else {
        	rows.append(JSON.toJSONString(map));
        	isFirst = false;
        }
        num++;
        if(num == batchSize) {
        	writeDataToFile();
        	rows = new StringBuffer();
        	num = 0;
        }
        return row;
    }
	
	@Override
	protected void init() throws Exception {
		super.init();
		RowMetaInterface rowMeta = commonStep.getInputRowMeta();
		fields = rowMeta.getFieldNames();
		if(configInfo.containsKey("fileName")) {
			fileName = commonStep.environmentSubstitute(configInfo.getString("fileName"));
			new File(fileName).delete();
		}else {
			throw new Exception("输出文件名没有指定！请检查配置信息。");
		}
		if(configInfo.containsKey("outputType")) {
			outputType = configInfo.getString("outputType");
		}
		if("object".equalsIgnoreCase(outputType)) {
			if(configInfo.containsKey("fieldName"))
			    fieldName = configInfo.getString("fieldName");
			//JsonObject格式开始
			rows.append("{\""+fieldName+"\": ["); 
		}else {
			//JsonArray格式开始
			rows.append("[");
		}
		if(configInfo.containsKey("batchSize")) {
			try {
				batchSize = configInfo.getIntValue("batchSize");
			} catch (Exception e) {;}
		}
	}
	
	@Override
	protected void end() throws Exception {
		if("object".equalsIgnoreCase(outputType)) {
			//JsonObject格式结束
			rows.append("]}"); 
		}else {
			//JsonArray格式结束
			rows.append("]");
		}
		writeDataToFile();
		super.end();
	}

	private void writeDataToFile() throws Exception {
		FileUtils.writeStringToFile(new File(fileName), rows.toString(), "utf-8", true);
	}
	
}
