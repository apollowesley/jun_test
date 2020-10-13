package com.cn.zjut.b2c.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.cn.zjut.b2c.sdk.AcpService;
import com.cn.zjut.b2c.sdk.LogUtil;
import com.cn.zjut.b2c.service.BackRcvResponseServiceInter;

@Service("backRcvResponseService")
public class BackRcvResponseServiceImpl implements BackRcvResponseServiceInter {

	@Override
	public String BackRcvValidate(String encoding,Map<String, String> reqParam) throws Exception{
		// TODO Auto-generated method stub
		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes("ISO-8859-1"), encoding);
				valideData.put(key, value);
			}
		}

		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!AcpService.validate(valideData, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			//验签失败，需解决验签问题
			
		} else {
			LogUtil.writeLog("验证签名结果[成功].");
			//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
			
			String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
			String respCode =valideData.get("respCode"); //获取应答码，收到后台通知了respCode的值一般是00，可以不需要根据这个应答码判断。
			
		}
		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
		//返回给银联服务器http 200  状态码
		return "ok";
	}

}
