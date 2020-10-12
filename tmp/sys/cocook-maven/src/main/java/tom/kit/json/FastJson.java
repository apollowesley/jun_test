package tom.kit.json;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * fastson 
 * @author tomsun
 */
public class FastJson implements Jsonable{
	
	public FastJson() throws ClassNotFoundException {
		Class.forName("com.alibaba.fastjson.serializer.JSONSerializer");
	}
	
	@Override
	public String serialize(Object obj) throws Exception {
		return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteNullListAsEmpty);
	}

	@Override
	public Object deserialize(String json) throws Exception {
		return deserialize(json, HashMap.class);
	}

	@Override
	public <T> T deserialize(String json, Class<T> clazz) throws Exception {
		return JSON.parseObject(json, clazz);
	}
	
}
