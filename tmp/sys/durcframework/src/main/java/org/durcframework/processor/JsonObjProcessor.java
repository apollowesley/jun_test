package org.durcframework.processor;

import com.alibaba.fastjson.JSONObject;

/**
 * 处理JSON对象的接口
 * 
 * @author hc.tang
 * 
 */
public interface JsonObjProcessor<Entity> {
	void process(Entity entity, JSONObject jsonObject);
}
