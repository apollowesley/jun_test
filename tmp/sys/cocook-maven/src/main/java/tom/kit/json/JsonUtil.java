package tom.kit.json;

public class JsonUtil {
	static Jsonable json = null;
	static {
		try {
			json = new FastJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String serialize(Object obj) throws Exception {
		return json.serialize(obj);
	};

	public static Object deserialize(String _json) throws Exception {
		return json.deserialize(_json);
	};
	
	public static <T> T deserialize(String _json, Class<T> clazz) throws Exception{
		return json.deserialize(_json, clazz);
	};
}
