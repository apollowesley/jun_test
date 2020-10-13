package org.apache.center.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.center.model.StatusCode;

import com.baomidou.framework.spring.SpringHelper;
import org.apache.playframework.mybatisplus.mapper.EntityWrapper;


public class StatusCodeUtils  {

	public static StatusCodeService statusCodeService;
	
	private static Map<String, List<StatusCode>> codeMap = new HashMap<String, List<StatusCode>>();
	
	static {
		statusCodeService = SpringHelper.getBean(StatusCodeService.class);
	}
	
    private static List<StatusCode> getData(String groupNum, String... itemNo) {
    	StringBuilder sb = new StringBuilder(groupNum);
    	if (itemNo != null && itemNo.length != 0) {
    		for (String string : itemNo) {
    		    sb.append(",");
    			sb.append(string);
    		}
    	} 
    	String key = sb.toString();
        if (codeMap.containsKey(key)) {
            return codeMap.get(key);
        } else {
        	EntityWrapper<StatusCode> ew = new EntityWrapper<StatusCode>();
        	ew.eq("group_num", groupNum);
        	if (itemNo != null && itemNo.length != 0) {
        		List<String> itemNos = new ArrayList<String>();
        		for (String string : itemNo) {
        			itemNos.add(string);
        		}
        		ew.in("node_num", itemNos);
        	}
        	return statusCodeService.selectList(ew);
        }
    }
	
	/**
     * 根据code编号获取所有其下的code子值
     * @param codeNo
     * @return
     */
    public static List<StatusCode> getCodes(String codeNo) {
        return getData(codeNo);
    }
    
    /**
     * 根据code编号获取所有其下的code子值
     * @param codeNo
     * @return
     */
    public static List<StatusCode> getCodes(String codeNo, String... itemNo) {
        return getData(codeNo,itemNo);
    }
    
    /**
     * 根据code编号 和 节点编号 itemNo 获取 健
     * @param codeNo code编号 
     * @param itemNo 节点编号
     * @return
     */
    public static String getNodeKeyByItemNo(String codeNo,String itemNo) {
        List<StatusCode> list = getData(codeNo);
        if (list != null && !list.isEmpty()) {
            for (StatusCode code : list) {
                if (code.getNodeNum().equals(itemNo)) {
                    return code.getNodeKey();
                }
            }
        }
        return "";
    }
    
    /**
     * 根据code编号 和 节点编号 itemNo 获取 值
     * @param codeNo code编号 
     * @param itemNo 节点编号
     * @return
     */
    public static String getNodeValueByItemNo(String codeNo,String itemNo) {
        List<StatusCode> list = getData(codeNo);
        if (list != null && !list.isEmpty()) {
            for (StatusCode code : list) {
                if (code.getNodeNum().equals(itemNo)) {
                    return code.getNodeValue();
                }
            }
        }
        return null;
    }
    
    /**
     * 根据code编号 和 节点编号 itemNo 获取 健
     * @param codeNo code编号 
     * @param itemNo 节点编号
     * @return
     */
    public static String getNodeKey(String codeNo,String itemValue) {
        List<StatusCode> list = getData(codeNo);
        if (list != null && !list.isEmpty()) {
            for (StatusCode code : list) {
                if (code.getNodeValue().equals(itemValue)) {
                    return code.getNodeKey();
                }
            }
        }
        return null;
    }
    
    /**
     * 根据code编号 和 节点编号 itemNo 获取  值
     * @param codeNo code编号 
     * @param itemNo 节点编号
     * @return
     */
    public static String getNodeValue(String codeNo,String itemKey) {
        List<StatusCode> list = getData(codeNo);
        if (list != null && !list.isEmpty()) {
            for (StatusCode code : list) {
                if (code.getNodeKey().equals(itemKey)) {
                    return code.getNodeValue();
                }
            }
        }
        return "";
    }
    
    /**
     * 判断 健 是否存在
     * @param codeNo
     * @param itemKey
     * @return
     */
    public static boolean isExistByItemKey(String codeNo,String itemKey) {
        List<StatusCode> list = getData(codeNo);
        if (list != null && !list.isEmpty()) {
            for (StatusCode code : list) {
                if (code.getNodeKey().equals(itemKey)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 判断 值 是否存在
     * @param codeNo
     * @param itemValue
     * @return
     */
    public static boolean isExistByItemValue(String codeNo,String itemValue) {
        List<StatusCode> list = getData(codeNo);
        if (list != null && !list.isEmpty()) {
            for (StatusCode code : list) {
                if (code.getNodeValue().equals(itemValue)) {
                    return true;
                }
            }
        }
        return false;
    }
}
