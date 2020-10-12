package org.nature.framework.ws;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.nature.framework.core.NatureMap;

public class WsDataConvertor {
	public static List<List<WsData>> convert(List<NatureMap> list){
		List<List<WsData>> wsDataList= null;
		if (list!=null) {
			wsDataList = new ArrayList<List<WsData>>();
			for (NatureMap natureMap : list) {
				List<WsData> wsDatas= new ArrayList<WsData>();
				Set<Entry<String, Object>> entrySet = natureMap.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					wsDatas.add(new WsData(entry.getKey(), entry.getValue()));
				}
				wsDataList.add(wsDatas);
			}
		}else{
			wsDataList = Collections.emptyList();
		}
		return wsDataList;
	}
}
