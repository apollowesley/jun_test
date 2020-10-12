package cn.itcast.caiwen.alisms;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class MainSMS{
	//选择短信验证码请求的URL方式
	private static String url = "http://gw.api.taobao.com/router/rest";
	//申请的项目appkey
	private static String appkey = "24571186";
	//申请的项目对应的 密钥
	private static String secret = "167fa542a0c8ae9212820b2d5a070f08";
	public static void main(String[] args) throws Exception {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456");
		req.setSmsType("normal");
		req.setSmsFreeSignName("传智bos项目");
		req.setSmsParamString("{\"smsCode\":\"5201314\"}");
		req.setRecNum("18672304510");
		req.setSmsTemplateCode("SMS_82180012");
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());
	}
}
