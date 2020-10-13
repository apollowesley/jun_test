package tom.cocook.core;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ControllerModel implements App  {

	public ControllerModel() {
	}

	/*控制器前后的方法*/
	public boolean before(RequestContext context, Map<String, String> map) {
		return true;
	}

    public String exec(RequestContext context, Map<String, String> map){
    	return null;
    }
    
	public String exec(HttpServletRequest req, Map<String, String> map) {
		return null;
	}
	
	public String exec(HttpServletResponse res, Map<String, String> map){
		return null;
	}

	@Override
	public Object extAnnnotation(Object obj, Method me, Map<String, String> map) {
		return obj;
	}
}
