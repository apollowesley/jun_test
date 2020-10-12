package ivyy.taobao.com.domain.gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.stream.JsonReader;

/**
 *@Date:2015-1-5
 *@Author:liangjilong
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@Description��
 */
public class GsonTreeReader {
	private static Gson gson = new Gson();
	public static void main(String[] args) throws Exception {
		/**
		  * ��װaddress���������
		  */
		 Map<String, String> address = new HashMap <String, String>(); 
		 
		 address.put("country", "�й�");
		 address.put("province", "�㶫ʡ");
		 address.put("city", "�Ƹ���");
		 address.put("district", "�Ƴ�����ݺ·��������");
		 address.put("street", "��ݺ·");
			
		 /**
		  * ��װusers���������
		  */
		 Map<String, String> users = new HashMap <String, String>(); 
		 users.put("username", "liangjilong");
		 users.put("age", "25");
		 users.put("tel", "12396878");
		 users.put("email", "jilongliang@sina.com"); 
		 
		 Map<Object, Object> listsObj = new HashMap <Object, Object>(); 
		 listsObj.put("address",address);//address�ڵ�
		 listsObj.put("users",users);//users�ڵ�
		 
		 Object obj=listsObj;//ת���ɶ���
		 
		 Map<Object, Object> info = new HashMap <Object, Object>(); 
		 info.put("info", obj);//json�ĸ��ڵ�
		 
		 
        String json=gson.toJson(info);//ת����json����
        System.out.println(json);//��ӡjson����
		
		readJsonData(json);
		
	}
	/**JsonReader
	 * JsonTreeReader���ļ���ȡjson����
	 * @param in
	 * @throws Exception
	 */
	public static void readJsonData(String json) throws Exception {
		//����JsonParser����
		JsonParser parser = new JsonParser();
		JsonElement jsonEl = parser.parse(json);
		//����һ��JsonTreeReader������JsonReader
		JsonReader reader = new JsonTreeReader(jsonEl);
		try {
			reader.beginObject();
			String tagName = reader.nextName();
			if (tagName.equals("info")) {
				readInfo(reader);
			}
			reader.endObject();
		} finally {
			reader.close();
		}
	}
	/**
	 * ��ȡjson�ĸ�(��,��һ��)�ڵ�
	 * @param reader
	 * @throws Exception
	 */
	public static void readInfo(JsonReader reader) throws Exception {
		reader.beginObject();
		while (reader.hasNext()) {
			String tagName = reader.nextName();
			if (tagName.equals("address")) {
				readAddress(reader);
			} else if (tagName.equals("users")) {
				readUsers(reader);
			} 
		}
		reader.endObject();
	}
	/**
	 * ��ȡ�û���Ϣֵ
	 * @param reader
	 * @throws IOException
	 */
	public static void readUsers(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String tag = reader.nextName();
			if (tag.equals("username")) {
				String username = reader.nextString();
				System.out.println("�û���:" + username);
			} else if (tag.equals("email")) {
				String email = reader.nextString();
				System.out.println("Email:" + email);
			} else if (tag.equals("tel")) {
				String tel = reader.nextString();
				System.out.println("�绰:" + tel);
			}else if (tag.equals("age")) {
				String age = reader.nextString();
				System.out.println("����:" + age);
			} else {
				reader.skipValue();//����ֵ/����break
			}
		}
		reader.endObject();
	}
	/**
	 * ��ȡ����ֵ
	 * @param reader
	 * @throws IOException
	 */
	public static void readAddress(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String tag = reader.nextName();
			if (tag.equals("province")) {
				String province = reader.nextString();
				System.out.println("ʡ��:" + province);
			} else if (tag.equals("city")) {
				String city = reader.nextString();
				System.out.println("�ֵ�:" + city);
			} else if (tag.equals("street")) {
				String street = reader.nextString();
				System.out.println("�ֵ�:" + street);
			}else if (tag.equals("district")) {
				String district = reader.nextString();
				System.out.println("��:" + district);
			} else {
				reader.skipValue();//����ֵ/����break
			}
		}
		reader.endObject();
	}

}
