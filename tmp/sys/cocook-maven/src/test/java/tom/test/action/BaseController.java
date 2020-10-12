package tom.test.action;

import java.util.Map;

import tom.cocook.core.ControllerModel;
import tom.cocook.core.RequestContext;

public class BaseController extends ControllerModel{
	
	@Override
	public boolean before(RequestContext context, Map<String, String> map) {
		context.setAttribute("cxtpath", context.getBasePath());   //"http://127.0.0.1" 固定域名
		//EHCacheManager.set("default", "aa", "hello world!!!");
		return super.before(context, map);
	}

}
