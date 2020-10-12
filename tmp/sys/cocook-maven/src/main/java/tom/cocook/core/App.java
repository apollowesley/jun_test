package tom.cocook.core;

import java.lang.reflect.Method;
import java.util.Map;


public interface App {
	 
	/*ext annotation passer*/ 
	public Object extAnnnotation(Object obj, Method me, Map<String,String> map);
}
