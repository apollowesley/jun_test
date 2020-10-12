package nivalsoul.kettle.plugins.common;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.stuxuhai.jpinyin.ChineseHelper;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.common.collect.Sets;
import org.apache.commons.io.FileUtils;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;

import java.io.File;
import java.util.*;

public class DataChangePlugin extends CommonStepRunBase {
	//字段
	private String[] fields;
	/*
	转换配置，其中转换类型changeType有：sc2tc(简体转繁体)、tc2sc(繁体转简体)、hz2py(汉字转拼音)
	[
	  {"inputField": "field1", "changeType": "sc2tc", "outputField": "field2"},
	  {"inputField": "field3", "changeType": "hz2py", "outputField": "field4"}
	]
	 */
	private JSONArray changeConfig;
	private List<String> ctList = Arrays.asList("sc2tc","tc2sc","hz2py",
			"base64Encode","base64Decode","MD5","SHA256");
	private int[] index;
	private boolean hasNewField = false;
	
	@Override
	protected Object[] disposeRow(Object[] row) throws Exception {
		if(hasNewField){
			row = RowDataUtil.createResizedCopy( row, data.outputRowMeta.size() );
		}
		for (int i = 0; i < changeConfig.size(); i++) {
			JSONObject jo = changeConfig.getJSONObject(i);
			String inField = jo.getString("inputField");
			String outField = jo.getString("outputField");
			String ct = jo.getString("changeType");
			boolean overwrite = jo.getBoolean("overwrite");
			int k = index[i]; //流中原始字段位置
			String nValue = getChangeData(row[k], ct);
			if(overwrite){
				row[k] = nValue;
			}else {
				row[getFieldIndex(outField)] = nValue;
			}
		}
		return row;
    }

	private String getChangeData(Object value, String ct) {
		String nValue = value.toString();
		switch (ct){
			case "sc2tc":
				nValue = ChineseHelper.convertToTraditionalChinese(nValue);
				break;
			case "tc2sc":
				nValue = ChineseHelper.convertToSimplifiedChinese(nValue);
				break;
			case "hz2py":
				try {
					nValue = PinyinHelper.convertToPinyinString(nValue, "", PinyinFormat.WITHOUT_TONE);
				} catch (PinyinException e) {
					;
				}
				break;
			case "base64Encode":
				nValue = Base64.encode(nValue);
				break;
			case "base64Decode":
				nValue = Base64.decodeStr(nValue);
				break;
			case "MD5":
				nValue = DigestUtil.md5Hex(nValue);
				break;
			case "SHA256":
				nValue = DigestUtil.sha256Hex(nValue);
				break;
			default:
				break;
		}
		return nValue;
	}

	@Override
	protected void init() throws Exception {
		super.init();
		RowMetaInterface rowMeta = commonStep.getInputRowMeta();
		fields = rowMeta.getFieldNames();
		if(configInfo.containsKey("changeConfig")) {
			changeConfig = JSON.parseArray(configInfo.getString("changeConfig"));
			index = new int[changeConfig.size()];
		}else{
			throw new Exception("转换配置没有指定！请检查配置信息。");
		}
		//检查配置
		for (int i = 0; i < changeConfig.size(); i++) {
			JSONObject jo = changeConfig.getJSONObject(i);
			String inField = jo.getString("inputField");
			String outField = jo.getString("outputField");
			for (int j = 0; j < fields.length; j++) {
				String field = fields[j];
				if(fields[j].equals(inField)){
					index[i] = j;
				}
			}
			if(!jo.containsKey("inputField") || !jo.containsKey("changeType")){
				throw new Exception("没有指定需要转换的字段inputField或转换类型changeType！");
			}
			String ct = jo.getString("changeType");
			if(!ctList.contains(ct)){
				throw new Exception("转换类型不在[sc2tc|tc2sc|hz2py]之中！");
			}
			if(outField!=null && !outField.equals(inField)){
				addField(data.outputRowMeta, outField, getFieldType(inField),
						ValueMeta.TRIM_TYPE_NONE, commonStep.getStepname(), "新增字段");
				jo.put("overwrite", false);
				hasNewField = true;
			}else{
				jo.put("overwrite", true);
			}
		}
	}

	
}
