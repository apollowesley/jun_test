package opensdk;

import java.util.Locale;

import junit.framework.Assert;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import opensdk.service.MsgDemoService;

public class MsgDemoServiceTest extends TestBase {
	
	MsgDemoService service = new MsgDemoService(appId, secret, serverCtx);
	
	@Test
	public void testTest1() {
		MsgResult result = service.getMsg("张三",Locale.ENGLISH);
		Assert.assertNotNull(result);
		System.out.println(JSON.toJSONString(result));
	}
	
}
