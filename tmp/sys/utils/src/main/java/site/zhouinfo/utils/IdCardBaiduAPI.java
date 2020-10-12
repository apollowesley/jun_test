package site.zhouinfo.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * utils
 * Author:      zhou
 * Create Date：2016-02-16 0:48
 */
public class IdCardBaiduAPI {
	//链接百度 aip
	//@RequestMapping(value = "idCard",method = RequestMethod.POST)
	//@ResponseBody
	public String idCard(String idCard) throws UnsupportedEncodingException {
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("apikey", "b69b91e97ddef02cab8333e57db92932");
		//ResponseContent responseContent = HttpHelper.getUrlRespContent("http://apis.baidu.com/apistore/idservice/id?id=" + idCard, headers);
		//return responseContent.getContent();
		return "";
	}
}
