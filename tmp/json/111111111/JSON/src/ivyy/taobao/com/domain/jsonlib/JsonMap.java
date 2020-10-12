package ivyy.taobao.com.domain.jsonlib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ivyy.taobao.com.entity.Address;
import ivyy.taobao.com.utils.GlobalConstants;
import ivyy.taobao.com.utils.HttpRequestUtils;
import ivyy.taobao.com.utils.IoUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *@Date:2015-1-5
 *@Author:liangjilong
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@Description��
 */
public class JsonMap {
	public static void main(String[] args) throws Exception {
		//Ҳ���Դ�jsonmap.jsonͨ���ļ�����ȡ
			///////////////////////////////////
		//setDataByJsonLib();
		///////////////////////////////////
		//getDataByJsonLib("URL");
		getDataByJsonLib("FILE");
		///////////////////////////////////
		//getStrToJsonByJsonLib();
		//getObjectToJsonByJsonLib();
		//getArrayToJsonByJsonLib();
		//getArrayToJsonByJSONLib();
		///////////////////////////////////
	}
	/**
	 * ByJsonLib���ַ���ת����Json����
	 * @throws Exception
	 */
	private static void getStrToJsonByJsonLib()throws Exception
	{
		String jsonStr="{\"city\":\"������\",\"province\":\"�㶫ʡ\"}"; //�Ȿ����json����,Ҳ���ַ���!
		 //String jsonStr="{city:������,province:�㶫ʡ}"; //JsonLib��֧�������ʽ
		JSONObject jsonObject=JSONObject.fromObject(jsonStr);
		if(!jsonObject.isEmpty()){
			System.out.println(jsonObject.get("province")+"\t"+jsonObject.get("city"));
		}
	}
	
	
	/**
	 * ByJsonLib,�Ѷ���ת����Json����
	 * @throws Exception
	 */
	private static void getObjectToJsonByJsonLib()throws Exception
	{
		 Address addr = new Address(); 
		 addr.setProvince("�㶫ʡ");
		 addr.setCity("������");
		 JSONObject jsonObject=new JSONObject();
		 jsonObject=jsonObject.fromObject(addr);
		 Address addrs=  (Address) JSONObject.toBean(jsonObject, Address.class);
		 System.out.println(addrs.getProvince()+"\t"+addrs.getCity());
	}
	
	/**
	 * ͨ��ByJSONLib������ת����Json����
	 * @param url
	 * @throws Exception
	 */
	private static void getArrayToJsonByJsonLib()throws Exception
	{ 
		Address address=new Address();
		address.setDistrict("�����");
		address.setCity("����");
		address.setProvince("�㶫ʡ");
		JSONArray jsonArr = JSONArray.fromObject(address);  //�Ѷ���ת����Json����
		 //ע�͵���Ҳ����ִ��
		//Map<String, Class<Address>> map=new HashMap<String,Class<Address>>();
		//map.put("address", Address.class);
	    //List<Address> lists = JSONArray.toList(jsonArr, Address.class, map); //�Ѷ���Json����ת����Json����
		List<Address> lists = JSONArray.toList(jsonArr, Address.class); //�Ѷ���Json����ת����Json����
	    for (Address obj : lists) {  
	    	System.out.println(obj.getProvince());
	    	System.out.println(obj.getCity());
	    	System.out.println(obj.getDistrict());
	    }
	}
	
	/**
	 * ͨ��ByJSONLib������ת����Json����
	 * @param url
	 * @throws Exception
	 */
	private static void getArrayToJsonByJSONLib()throws Exception
	{ 
		List<Address> lists=new ArrayList<Address>();
		for (int i = 1; i <=12; i++) {
			Address addr = new Address(); 
			addr.setProvince("��"+i+"��ʡ��");
			addr.setCity("��"+i+"������");
			addr.setCityCode("130"+i);
			addr.setStreet("�ֵ�"+i);
			addr.setStreetNumber("�ֵ�����"+i);
			lists.add(addr);
		}
		JSONArray jsonArr = JSONArray.fromObject(lists);  //�Ѷ���ת����Json����
		System.out.println(jsonArr);
		
		Map<String, Class<Address>> map=new HashMap<String,Class<Address>>();
		//ƴ�ӳ�address[{"city":"��1������","cityCode":"1301","district":"","province":"��1��ʡ��","street":"�ֵ�1","streetNumber":"�ֵ�����1"},{"city":"��2������","cityCode":"1302","district":"","province":"��2��ʡ��","street":"�ֵ�2","streetNumber":"�ֵ�����2"},{"city":"��3������","cityCode":"1303","district":"","province":"��3��ʡ��","street":"�ֵ�3","streetNumber":"�ֵ�����3"},{"city":"��4������","cityCode":"1304","district":"","province":"��4��ʡ��","street":"�ֵ�4","streetNumber":"�ֵ�����4"},{"city":"��5������","cityCode":"1305","district":"","province":"��5��ʡ��","street":"�ֵ�5","streetNumber":"�ֵ�����5"},{"city":"��6������","cityCode":"1306","district":"","province":"��6��ʡ��","street":"�ֵ�6","streetNumber":"�ֵ�����6"},{"city":"��7������","cityCode":"1307","district":"","province":"��7��ʡ��","street":"�ֵ�7","streetNumber":"�ֵ�����7"},{"city":"��8������","cityCode":"1308","district":"","province":"��8��ʡ��","street":"�ֵ�8","streetNumber":"�ֵ�����8"},{"city":"��9������","cityCode":"1309","district":"","province":"��9��ʡ��","street":"�ֵ�9","streetNumber":"�ֵ�����9"},{"city":"��10������","cityCode":"13010","district":"","province":"��10��ʡ��","street":"�ֵ�10","streetNumber":"�ֵ�����10"},{"city":"��11������","cityCode":"13011","district":"","province":"��11��ʡ��","street":"�ֵ�11","streetNumber":"�ֵ�����11"},{"city":"��12������","cityCode":"13012","district":"","province":"��12��ʡ��","street":"�ֵ�12","streetNumber":"�ֵ�����12"}]��������
		map.put("address", Address.class);//
	    List<Address> listAdds = JSONArray.toList(jsonArr, Address.class, map); //�Ѷ���Json����ת����Json����
	
		for(Iterator iter=listAdds.iterator();iter.hasNext();)
		{
			Address add=(Address)iter.next();
			System.out.println(add.getProvince()+"\t"+add.getCity()+"\t"+add.getStreet()+"\t"+add.getCityCode());
		} 
	}
	
	/**
	 * ʹ��Json-lib��װ����
	 * @throws Exception
	 */
	private static void setDataByJsonLib()throws Exception{
		JSONObject jsonObject=new JSONObject();
		Map<String, String> lists=new HashMap<String, String>();
		lists.put("�㶫ʡ", "������");
		lists.put("������", "�����");
		lists.put("�����", "�Ķ�");
		jsonObject=jsonObject.fromObject(lists);//ת��Ϊjson����
		System.out.println(jsonObject);//��ӡjson����
		System.out.println(jsonObject.get("�����"));//ȡ��json����
	}
	
	/**
	 * ʹ��Json-lib��ȡjson����
	 * @throws Exception
	 */
	private static void getDataByJsonLib(String flag) throws Exception {
		String responseData="";
		if(flag.equals("URL")){
			//�����еľ�γ��39.983424,116.322987
			String url=GlobalConstants.getBaiduMapUrl("���key", "39.983424,116.322987", "json");
			responseData = HttpRequestUtils.HttpURLConnRequest(url, "GET");
		}else if(flag.equals("FILE")){
			String jsonData="ivyy/taobao/com/domain/jsonlib/jsonmap.json";
			String path = JsonMap.class.getClassLoader().getResource(jsonData).getPath();
			responseData=IoUtils.reader(path);
		}
		
		// ����ҳ���json����
		int start = responseData.indexOf("(") + 1;
		responseData = responseData.substring(start, responseData.lastIndexOf(")"));
		JSONObject jsonObject = JSONObject.fromObject(responseData);
		// ����
		String status = "", result = "", lat = "", lng = "", formatted_address = "", business = "", city = "", district = "", province = "", street = "", street_number = "";

		if (jsonObject.has("status") && jsonObject.get("status") != null && jsonObject.get("status") != "") {
			status = jsonObject.get("status").toString();// status
		}
		result = jsonObject.get("result").toString();// result
		JSONObject location = JSONObject.fromObject(JSONObject .fromObject(result).get("location").toString());

		lat = location.get("lat").toString();// lat
		lng = location.get("lng").toString();// lng

		JSONObject formatAddr = JSONObject.fromObject(result);
		formatted_address = formatAddr.get("formatted_address").toString();// formatted_address
		business = formatAddr.get("business").toString();// business

		// ����
		JSONObject compObj = JSONObject.fromObject(JSONObject.fromObject(result).get("addressComponent").toString());// addressComponent
		city = compObj.get("city").toString();
		district = compObj.get("district").toString();
		province = compObj.get("province").toString();
		street = compObj.get("street").toString();
		street_number = compObj.get("street_number").toString();

		JSONArray poisArr = JSONObject.fromObject(result).getJSONArray("pois");

		if (poisArr != null && poisArr.size() > 0) {

			for (int i = 0; i < poisArr.size(); i++) {
				// ����
				String addr = "", cp = "", distance = "", name = "", poiType = "", tel = "", uid = "", zip = "";
				JSONObject tempJson = JSONObject.fromObject(poisArr.get(i));
				addr = tempJson.get("addr").toString();
				cp = tempJson.get("cp").toString();
				distance = tempJson.get("distance").toString();
				name = tempJson.get("name").toString();
				poiType = tempJson.get("poiType").toString();
				tel = tempJson.get("tel").toString();
				uid = tempJson.get("uid").toString();
				zip = tempJson.get("zip").toString();

				JSONArray pointArr = poisArr.fromObject(tempJson.get("point"));// ��ȡponit
				// ����
				String x = "", y = "";
				for (int j = 0; j < pointArr.size(); j++) {
					JSONObject obj = JSONObject.fromObject(pointArr.get(j));
					x = obj.get("x").toString();
					y = obj.get("x").toString();
					
					System.out.println(x);
				}
			}
		}

	}
}
