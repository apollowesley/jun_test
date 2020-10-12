package tom.kit.json;

public interface Jsonable {
	
	String serialize(Object obj) throws Exception;
	
	Object deserialize(String json) throws Exception;
	
	<T> T deserialize(String json, Class<T> clazz) throws Exception;

}
