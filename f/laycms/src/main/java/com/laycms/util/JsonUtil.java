package com.laycms.util;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/** 
* @author 作者 zbb: 
* @version 创建时间：2017年6月23日 上午10:10:12 
* 类说明 
*/

public class JsonUtil {

	private static SerializeConfig mapping = new SerializeConfig();  
    /** 
     * 默认的处理时间 
     *  
     * @param jsonText 
     * @return 
     */  
    public static String toJSONString(Object obj) {  
        return JSON.toJSONString(obj,SerializerFeature.WriteDateUseDateFormat);  
    }  
  
    /** 
     * 自定义时间格式 
     *  
     * @param jsonText 
     * @return 
     */  
    public static String toJSONString(Object obj,String dateFormat) {  
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));  
        return JSON.toJSONString(obj, mapping);  
    }  

}
