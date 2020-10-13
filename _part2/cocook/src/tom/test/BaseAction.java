package tom.test;

import java.util.Map;

import tom.cache.EHCacheManager;
import tom.cocook.core.ControllerModel;
import tom.cocook.core.RequestContext;

public class BaseAction extends ControllerModel{
	
	@Override
	public boolean before(RequestContext context, Map<String, String> map) {
		context.setAttribute("cxtpath", context.getBasePath());   //"http://127.0.0.1" 固定域名
		//EHCacheManager.set("default", "aa", "hello world!!!");
		System.out.println(EHCacheManager.get("default", "aa")); ;
		return super.before(context, map);
	}

}
