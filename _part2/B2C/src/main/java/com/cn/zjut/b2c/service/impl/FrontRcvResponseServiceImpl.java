package com.cn.zjut.b2c.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.zjut.b2c.dao.UserDao;
import com.cn.zjut.b2c.dao.ResponseMessageDao;
import com.cn.zjut.b2c.pojo.ResponseMessage;
import com.cn.zjut.b2c.sdk.AcpService;
import com.cn.zjut.b2c.sdk.LogUtil;
import com.cn.zjut.b2c.service.FrontRcvResponseServiceInter;

@Service("frontRcvResponseService")
public class FrontRcvResponseServiceImpl implements
		FrontRcvResponseServiceInter {
	
	@Resource
	private ResponseMessageDao responseMessageDao;
	
	@Override
	public void insertResponseMessage(ResponseMessage responseMessage){
		ResponseMessage responseMessageQuery = null;
		if((!responseMessage.getOrderid().equals("")) || responseMessage.getOrderid()!=null){
			responseMessageQuery = responseMessageDao.selectByOrderId(responseMessage.getOrderid());
		}
		if(responseMessageQuery==null){
			responseMessageDao.insertSelective(responseMessage);
		}
	}
	
	@Override
	public String getResultPageURL(String encoding) {
		
		LogUtil.writeLog("返回报文中encoding=[" + encoding + "]");
		String pageResult = "";
		if ("UTF-8".equalsIgnoreCase(encoding)) {
			pageResult = "utf8_result";
		} else {
			pageResult = "gbk_result";
		}
		return pageResult;
	}

	@Override
	public String getResultPageParam(Map<String, String> respParam,String encoding) throws UnsupportedEncodingException {

		// 打印请求报文
		LogUtil.printRequestLog(respParam);

		Map<String, String> valideData = null;
		StringBuffer page = new StringBuffer();
		if (null != respParam && !respParam.isEmpty()) {
			Iterator<Entry<String, String>> it = respParam.entrySet()
					.iterator();
			valideData = new HashMap<String, String>(respParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes("ISO-8859-1"), encoding);
				page.append("<tr><td width=\"30%\" align=\"right\">" + key
						+ "(" + key + ")</td><td>" + value + "</td></tr>");
				valideData.put(key, value);
			}
		}
		if (!AcpService.validate(valideData, encoding)) {
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
			LogUtil.writeLog("验证签名结果[失败].");
		} else {
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
			LogUtil.writeLog("验证签名结果[成功].");
			System.out.println(valideData.get("orderId")); //其他字段也可用类似方式获取
		}
		return page.toString();
	}

}
