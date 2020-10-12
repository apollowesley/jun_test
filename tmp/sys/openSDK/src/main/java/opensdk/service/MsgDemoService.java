package opensdk.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import opensdk.MsgResult;

public class MsgDemoService extends BaseService {

	public MsgDemoService(String appId, String secret, String serverCtx) {
		super(appId, secret, serverCtx);
	}
	
	public MsgResult getMsg(String name,Locale locale) {
		Map<String, String> params = new HashMap<String, String>(1);
		params.put("schName", name);
		return this.getClient().postJsonByMap2Obj("test1.do", params, MsgResult.class,locale);
	}

}
