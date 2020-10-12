package ivyy.taobao.com.domain.gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 *@DEMO:napp
 *@Author:jilongliang
 *@Date:2013-7-20
 */
public class JsonRead {
	private static Gson gson=new Gson();
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		 getJsonData();
         
        //������
        //String pathname="src/config/doc/json";
 		//InputStream input = new FileInputStream(new File(pathname));
 		//readJsonData(input,null);
	}
	/**
	 * ��װJson����
	 * @throws Exception
	 */
	private static void getJsonData() throws Exception {
		/**
		  * ��װaddress���������
		  */
		 Map<String, String> address = new HashMap <String, String>(); 
		 address.put("province", "�㶫ʡ");
		 address.put("city", "�Ƹ���");
		 address.put("district", "�Ƴ���");
		 address.put("street", "�Ƴ�����ݺ·��������"); 
		 /**
		  * ��װusers���������
		  */
		 Map<String, String> users = new HashMap <String, String>(); 
		 users.put("username", "liangjilong");
		 users.put("age", "25");
		 users.put("tel", "1369711900");
		 users.put("email", "jilongliang@sina.com"); 
		 
		 Map<Object, Object> listsObj = new HashMap <Object, Object>(); 
		 listsObj.put("address",address);//address�ڵ�
		 listsObj.put("users",users);//users�ڵ�
		 
		 Object obj=listsObj;//ת���ɶ���
		 
		 Map<Object, Object> info = new HashMap <Object, Object>(); 
		 info.put("info", obj);//json�ĸ��ڵ�
		 
		 
         String json=gson.toJson(info);//ת����json����
         System.out.println(json);//��ӡjson����
         readJsonData(null,json);
	}
	/**
	 * ���ļ���ȡjson����
	 * @param in
	 * @throws Exception
	 */
	public static void readJsonData(InputStream in,String json) throws Exception {
		//JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		JsonReader reader = new JsonReader(new StringReader(json));
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

