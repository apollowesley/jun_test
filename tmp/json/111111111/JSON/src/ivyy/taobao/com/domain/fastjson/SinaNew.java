package ivyy.taobao.com.domain.fastjson;

import ivyy.taobao.com.utils.GlobalConstants;
import ivyy.taobao.com.utils.HttpRequestUtils;

import java.util.Iterator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 *@Author:liangjilong
 *@Date:2015-1-4
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@Description�����ͨ��fastjson�����
 */
public class SinaNew {
	
	
	public static void main(String[] args) throws Exception {

		String requestURL = GlobalConstants.getSinaNewUrl(2, "json");

		String jsonText = HttpRequestUtils.HttpURLConnRequest(requestURL, "GET");
		//System.out.println(jsonText);
		
		// ����ҳ���json����
		int start = jsonText.indexOf("(") + 1;
		jsonText = jsonText.substring(start, jsonText.lastIndexOf(")"));

		String result = "";
		JSONObject jsonObject = JSONObject.parseObject(jsonText);
		result = jsonObject.get("result").toString();
		
		JSONObject resObj = JSONObject.parseObject(result);
		// String encoding=resObj.get("encoding").toString();//��ȡ������

		String dataStr = resObj.get("data").toString();

		JSONArray dataArr = JSONArray.parseArray(dataStr);
		String title = "", url = "", keywords = "", img = "", media_name = "";
		int i=0;
		 
		for (Iterator iterator = dataArr.iterator(); iterator.hasNext();) {
			JSONObject object = (JSONObject) iterator.next();

			title = object.get("title").toString();// title
			url = object.get("url").toString();// url
			keywords = object.get("keywords").toString();// keywords
			img = object.get("img").toString();// img
			media_name = object.get("media_name").toString();// media_name

			
			String newsText=GlobalConstants.getNewsContent(url);//������������
			
			//System.out.println("==================��"+i+"ƪ=================="+newsText);
	    	i++;
	    	
			System.out.println(title + "\n" + url + "\n" + keywords + "\n"+ url + "\n" + media_name);

		}
	}
}
