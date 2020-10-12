package nivalsoul.kettle.plugins.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.ValueMeta;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import nivalsoul.kettle.plugins.util.HttpTool;
import nivalsoul.kettle.plugins.util.excel.XLS2CSVmra;
import nivalsoul.kettle.plugins.util.excel.XLSX2CSV;

public class CustomInputPlugin extends CommonStepRunBase {

	@Override
	public boolean run() throws Exception {
		Object[] r = commonStep.getRow(); 
        if (commonStep.first) {
        	data.outputRowMeta = new RowMeta();
        	meta.getFields(data.outputRowMeta, commonStep.getStepname(), null, null, 
        			commonStep, commonStep.getRepository(), commonStep.getMetaStore());
            commonStep.first = false;
            init();
        }
        //创建输出记录
        String inputType = configInfo.getString("inputType");
        if("rest".equals(inputType)) {
        	getDataFromRest();
        }else if("excel".equals(inputType)) {
        	readFromExcel();
        }else {
        	return doNothing(r);
        }
        
        commonStep.setOutputDone();
        return false;
	}

	private void readFromExcel() throws FileNotFoundException, Exception, IOException, KettleStepException {
		String filename = commonStep.environmentSubstitute(configInfo.getString("filename"));
		FileInputStream is = new FileInputStream(filename);
		List<List<String>> lists = Lists.newArrayList();
		if(filename.endsWith(".xls")) {
			lists = XLS2CSVmra.readToList(is);
		} else if(filename.endsWith(".xlsx")) {
			lists = XLSX2CSV.readToList(is);
		} else {
			throw new Exception("文件名格式不正确，请确保是excel格式！");
		}
		is.close();
		
		int offsetLine = configInfo.getBooleanValue("header") ? 1 : 0; 
		JSONArray outputFields = meta.getOutputFields();
		if(outputFields==null || outputFields.size()==0) {
			outputFields = new JSONArray();
			//使用第一行数据或者自动字段名时，数据类型默认为string
			if(offsetLine == 0) {
				for(int i=0; i<lists.get(0).size();i++) {
					JSONObject field = new JSONObject();
					field.put("name", "field"+(i+1));
					field.put("type", "string");
					outputFields.add(field);
				}
			}else {
				List<String> headerLine = lists.get(0);
				for(int i=0; i<headerLine.size();i++) {
					JSONObject field = new JSONObject();
					field.put("name", headerLine.get(i));
					field.put("type", "string");
					outputFields.add(field);
				}
			}
			meta.setOutputFields(outputFields);
			meta.getFields(data.outputRowMeta, commonStep.getStepname(), null, null, 
					commonStep, commonStep.getRepository(), commonStep.getMetaStore());
		}

		for(int i=offsetLine; i<lists.size(); i++) {
			Object[] outputRowData = RowDataUtil.allocateRowData( data.outputRowMeta.size() );
			List<String> row = lists.get(i);
			for (int j=0;j<row.size();j++) {
				outputRowData[j] = row.get(j);
			}
			commonStep.putRow(data.outputRowMeta, outputRowData);
		}
	}

	private void getDataFromRest() throws KettleStepException {
		String url = configInfo.getString("url");
		String method = configInfo.getString("method");
		String result = null;
		if("get".equalsIgnoreCase(method)) {
			result = HttpTool.get(url);
		}else if("post".equalsIgnoreCase(method)) {
			JSONObject params = configInfo.getJSONObject("params");
			Map<String, String> map = Maps.newHashMap();
			for (String key : params.keySet()) {
				map.put(key, params.getString(key));
			}
			result = HttpTool.post(url, map);
		}
		String resultField = configInfo.getString("resultField");
		addField(data.outputRowMeta, resultField, ValueMeta.TYPE_STRING, 
				ValueMeta.TRIM_TYPE_NONE, commonStep.getStepname(), "结果字段");
		Object[] outputRowData = RowDataUtil.allocateRowData( data.outputRowMeta.size() );
		outputRowData[getFieldIndex(resultField)] = result;
		commonStep.putRow(data.outputRowMeta, outputRowData);
	}
	
	private boolean doNothing(Object[] r) throws Exception, KettleStepException {
		if (r == null) {
		    end();
		    commonStep.setOutputDone();
		    return false;
		}
		commonStep.putRow(data.outputRowMeta, r);
		return true;
	}
	
}
