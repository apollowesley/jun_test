package cn.uncode.dal.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import cn.uncode.dal.criteria.QueryCriteria;

public class ShardsUtils {
	
	public static List<Map<String, Object>> complieResult(List<Map<String, Object>> result, final QueryCriteria queryCriteria) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(queryCriteria.getOrderByClause())) {
			final String[] fds = queryCriteria.getOrderByClause().toLowerCase().split(",");
			LinkedHashMap<String, String> orderFd = new LinkedHashMap<String, String>();
			if(fds != null && fds.length > 0){
				for(String fd:fds){
					if(fd.indexOf("desc") != -1){
						String val = fd.replaceAll("desc", "");
						orderFd.put(val.trim(), "desc");
					}else{
						String val = fd.replaceAll("asc", "");
						orderFd.put(val.trim(), "asc");
					}
				}
			}
			Collections.sort(result, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(Map<String, Object> a, Map<String, Object> b) {
					int result = 0;
					for(Entry<String, String> item:orderFd.entrySet()){
						if(result == 0){
							Object va = a.get(item.getKey());
							Object vb = b.get(item.getKey());
							if("desc".equals(item.getValue())){
								result = (String.valueOf(va).compareTo(String.valueOf(vb))) * -1;
							}else{
								result = String.valueOf(va).compareTo(String.valueOf(vb));
							}
						}
					}
					return result;
				}
			});
		}
		if(queryCriteria.getSelectOne()){
			resultList.add(result.get(0));
		}else{
			resultList.addAll(result.subList(0, queryCriteria.getPageSize()));
		}
		return resultList;
	}

    
	public static void main(String[] args) {
		System.out.println("");

    }

}
