package ivyy.taobao.com.domain.gson;

import ivyy.taobao.com.entity.Address;
import ivyy.taobao.com.utils.GlobalConstants;
import ivyy.taobao.com.utils.HttpRequestUtils;
import ivyy.taobao.com.utils.IoUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
@SuppressWarnings("all")
/**
 *@Date:2015-1-5
 *@Author:liangjilong
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@Description��
 */
public class JsonMap {
	private static Gson gson=new Gson();
	//jsonmap.json�ļ�
	public static void main(String[] args) throws Exception {
		//Ҳ���Դ�jsonmap.jsonͨ���ļ�����ȡ
		//setDataByGson();
		//getDataByGson("URL");
		getDataByGson("FILE");
		//getStrToJsonByGson();
		//getObjectToJsonByGson();
		//getArrayToJsonByGson();
		///////////////////////////////////
	}


	/**
	 * ByGson���ַ���ת����Json����
	 * @throws Exception
	 */
	private static void getStrToJsonByGson()throws Exception
	{
		//String jsonStr="{\"city\":\"������\",\"province\":\"�㶫ʡ\"}"; //�Ȿ����json����,Ҳ���ַ���!
		 String jsonStr="{city:������,province:�㶫ʡ}"; 
		 Address addr =gson.fromJson(jsonStr,Address.class);//���ַ���ת����Json
		 String json=gson.toJson(addr);//�Ѷ���ת����json
		 System.out.println(json);
		 System.out.println(addr.getProvince()+"\t"+addr.getCity());
	}

	/**
	 * Gson��Ϸ������,�Ѷ���ת����Json����
	 * @throws Exception
	 */
	private static void getObjectToJsonByGson()throws Exception
	{
		 Address addr = new Address(); 
		 addr.setProvince("�㶫ʡ");
		 addr.setCity("������");
		 String json=gson.toJson(addr);//�Ѷ���ת����Json
		 //����һ��JsonParser����
		 //JsonParser parser = new JsonParser();  
		 //����һ��JsonElement����
         //JsonElement jsonEl = parser.parse(json);  
		 Type type = new TypeToken<Address>(){}.getType();//ͨ������
		 Address add= gson.fromJson(json,type);
		 //Address add= gson.fromJson(jsonEl,type);
		 System.out.println(add.getProvince()+"\t"+add.getCity());
	}
	
	
	
	
	
	/**
	 * ͨ��Gson������ת����Json����
	 * @param url
	 * @throws Exception
	 */
	private static void getArrayToJsonByGson()throws Exception
	{
		List<Address> lists=new ArrayList<Address>();
		for (int i = 1; i <=12; i++) {
			Address addr = new Address(); 
			addr.setProvince("��"+i+"��ʡ��");
			addr.setCity("��"+i+"������");
			lists.add(addr);
		}
		String json=gson.toJson(lists);//���������ת����Json
		Type type = new TypeToken<List<Address>>(){}.getType();//ͨ������
		List<Address> adds= gson.fromJson(json,type);
		for (Address a:adds) {
			System.out.println(a.getCity());
		}
	}
	/**
	 * ͨ��Gson��װJson����
	 */
	private  static void setDataByGson(){
		///////////////////��װjson////////////////////
		 JsonObject jsonObject=new JsonObject();
		 Map<String, String> lists = new HashMap <String, String>(); 
		 
		 lists.put("country", "�й�");
		 lists.put("province", "�㶫ʡ");
		 lists.put("city", "�Ƹ���");
		 lists.put("district", "�Ƴ�����ݺ·��������");
		 lists.put("street", "��ݺ·");
			
         String json=gson.toJson(lists);//ת����json����
         System.out.println(json); //���json
         
         ///////////////////��ȡjson////////////////////
         JsonParser parser = new JsonParser();
		 JsonElement jsonEl = parser.parse(json);
         JsonObject jsonObjRoot = null;
         jsonObjRoot = jsonEl.getAsJsonObject();
         if(jsonObjRoot.isJsonObject()&&jsonObjRoot.get("district")!=null &&jsonObjRoot.get("street").getAsString()!=""){
        	 String district=jsonObjRoot.get("district").getAsString();
        	 System.out.println(district);
         }
	} 
	
	/**
	 * ��gsonȥ��������
	 * @param url��ֹ����
	 * @throws Exception
	 */
	private static void getDataByGson(String flg) throws Exception {
		//PostMethod method = new PostMethod(url);
		String responseData="";
		if(flg.equals("URL")){
			//�����еľ�γ��39.983424,116.322987
			String url=GlobalConstants.getBaiduMapUrl("���key", "39.983424,116.322987", "json");
			responseData = HttpRequestUtils.HttpURLConnRequest(url, "GET");
		}else if(flg.equals("FILE")){
			String jsonData="ivyy/taobao/com/domain/gson/jsonmap.json";
			String path = JsonMap.class.getClassLoader().getResource(jsonData).getPath();
			responseData=IoUtils.reader(path);
		}
		
		//����ҳ���json����
		int start = responseData.indexOf("(") + 1;
		responseData = responseData.substring(start, responseData.lastIndexOf(")"));
		
		// ����һ��JsonParser
		JsonParser parser = new JsonParser();
		JsonElement jsonEl = parser.parse(responseData);
		// ��JsonElement����ת����JsonObject
		JsonObject jsonObjRoot = null;
		if (jsonEl.isJsonObject()) {
			 jsonObjRoot = jsonEl.getAsJsonObject();
			 String status="",result="",lat="",lng="",formatted_address="",business="",city="",district="",province="",street="",street_number="",cityCode="";
			 //String status=gson.toJson(jsonObjRoot.get("status"));//����һ
			 status=jsonObjRoot.get("status").getAsString();//������
			 JsonElement resultEl=jsonObjRoot.get("result");//result�ڵ�
			 
			 JsonElement locationEl=resultEl.getAsJsonObject().get("location");//location�ڵ�
			 
			 lat=locationEl.getAsJsonObject().get("lat").getAsString();
			 lng=locationEl.getAsJsonObject().get("lng").getAsString();
			 	
			 
		     formatted_address=resultEl.getAsJsonObject().get("formatted_address").getAsString();//formatted_address�ڵ�
		     business=resultEl.getAsJsonObject().get("business").getAsString();//business�ڵ�
			 cityCode=resultEl.getAsJsonObject().get("cityCode").getAsString();//cityCode�ڵ�

			 JsonElement compEl=resultEl.getAsJsonObject().get("addressComponent");//addressComponent�ڵ�
			 
			 city=compEl.getAsJsonObject().get("city").getAsString();
			 district=compEl.getAsJsonObject().get("district").getAsString();
			 province=compEl.getAsJsonObject().get("province").getAsString();
			 street=compEl.getAsJsonObject().get("street").getAsString();
			 street_number=compEl.getAsJsonObject().get("street_number").getAsString();
			 
			 JsonElement poisEl=resultEl.getAsJsonObject().get("pois");//pois�ڵ�
			 boolean flag=poisEl.isJsonArray();//�Ƿ�ΪJson����
			 if(flag){
				// System.out.println(poisEl.getAsJsonArray());
				 JsonArray jsonArray= poisEl.getAsJsonArray();//תΪ����
				 String addr="",cp="",distance="",name="",poiType="",tel="",uid="",zip="",x="",y="";
				 //����һ
				 /*for(Iterator iter=jsonArray.iterator();iter.hasNext();){
					 JsonObject jop=(JsonObject) iter.next();
					 addr=jop.get("addr").getAsString();
					 x=jop.get("point").getAsJsonObject().get("x").getAsString();//x�ڵ�
					 System.out.println(jop.get("point").getAsJsonObject());
					 System.out.println("address"+addr+"\t\tx="+x);
				 }
				 */
				 //������
				 for (int i = 0; i < jsonArray.size(); i++) {
					 JsonObject jop=(JsonObject)jsonArray.get(i);
					 addr=jop.get("addr").getAsString();
					 cp=jop.get("cp").getAsString();
					 distance=jop.get("distance").getAsString();
					 name=jop.get("name").getAsString();
					 poiType=jop.get("poiType").getAsString();
					 tel=jop.get("tel").getAsString();
					 uid=jop.get("uid").getAsString();
					 zip=jop.get("zip").getAsString();
					
					 if(jop.get("point").getAsJsonObject().isJsonObject())
					 {
						// System.out.println(jop.get("point").getAsJsonObject());
						 x=jop.get("point").getAsJsonObject().get("x").getAsString();//x�ڵ�
						 y=jop.get("point").getAsJsonObject().get("y").getAsString();//y�ڵ�
						 System.out.println(x);
					 }
				 }
			 }
		}
	}
	
}