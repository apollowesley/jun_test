package ivyy.taobao.com.domain.gson;

import ivyy.taobao.com.entity.Address;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
/**
 *@Date:2015-1-5
 *@Author:liangjilong
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@Description��
 */
@SuppressWarnings("all")
public class GsonWriterReader {
	private static Gson gson = new Gson();
	public static void main(String[] args) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map map1 = new HashMap();
		map1.put("country", "�й�");
		map1.put("province", "�㶫ʡ");
		map1.put("city", "�Ƹ���");
		map1.put("district", "�Ƴ�����ݺ·��������");
		map1.put("street", "��ݺ·");
		
		Map map2 = new HashMap();
		map2.put("country", "�й�");
		map2.put("province", "�㶫ʡ");
		map2.put("city", "������");
		map2.put("district", "Խ����");
		map2.put("street", "������ߗ");
		
		list.add(map1);
		list.add(map2);
		
		String[] str = new String[2];
		str[0]="�й�";
		str[1]="����";
		 
		Address add = new Address(1, "�й�");
		
		List<Map> mapList = printlnJsonInfo(list, map1, str, add);
		
		/**д*/
		File file = writerJsonToFile(gson, mapList);
		/***��*/
		readJsonFromFile(gson, file);
	}

	/**
	 * ��ӡJson��Ϣ
	 * @param list
	 * @param map1
	 * @param str
	 * @param add
	 * @return
	 */
	private static List<Map> printlnJsonInfo(List<Map<String, String>> list, Map map1,String[] str, Address add) {
		String listsJson = gson.toJson(list);// ����תjson
		String mapJson = gson.toJson(map1);// mapתjson
		String arrayJson = gson.toJson(str);// ����תjson
		String ObjJson = gson.toJson(add);// ����תjson

		System.out.println("->********************Object to JSON********************->");
		System.out.println("list -> תJson: " + listsJson);
		System.out.println("Map���� -> תJson: " + mapJson);
		System.out.println("String���� -> תJson: " + arrayJson);
		System.out.println("Object -> תJson: " + ObjJson);
		System.out.println("->********************JSON to Object  ********************->");
		
		List<Address> listUser = new ArrayList<Address>();
		//��str��������˶������
		for (int i = 0; i<str.length; i++) {
			listUser.add(new Address(i,str[i]));
		}
		String listJson = gson.toJson(listUser);
		System.out.println("Json-> ת���ͼ���: " + listJson);
		List<Address> listAdd = gson.fromJson(listJson,new TypeToken<List<Address>>() {}.getType());
				
		for (Address addr : listAdd) {
			System.out.println("���ң�" + addr.getCountry());
		}
		System.out.println("Json -> ת���ͼ���List<Map<String,String>>: " + listsJson);
		List<Map> mapList = gson.fromJson(listsJson,new TypeToken<List<Map<String, String>>>() {}.getType());
				
		for (Map map : mapList) {
			System.out.println("����:" + map.get("street"));
		}
		System.out.println("Json -> תOject: " + listsJson);
		Address ad = gson.fromJson(ObjJson, Address.class);
		System.out.println("����:"+ad.getCountry());
		return mapList;
	}

	/**
	 * JsonWriter
	 * ��Json����д���ļ�����
	 * @param gson
	 * @param mapList
	 * @return
	 * @throws Exception
	 */
	private static File writerJsonToFile(Gson gson, List<Map> mapList)throws Exception {
		File file = new File("gson");// ��json������Ŀ��Ŀ¼���޺�׺��ʽ���ı�
		OutputStream out = new FileOutputStream(file);
		JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));//���ñ���
		gson.toJson(mapList, new TypeToken<List<Map<String, String>>>() {}.getType(), writer);//��ֵд��ȥ
		writer.flush();
		writer.close();
		return file;
	}

	/**
	 * JsonReader
	 * ��ȡ�ӱ����ļ���Json����
	 * @param gson
	 * @param file
	 * @throws Exception
	 */
	private static void readJsonFromFile(Gson gson, File file)throws Exception {
		InputStream input = new FileInputStream(file);
		JsonReader reader = new JsonReader(new InputStreamReader(input));
		List<Map> content = gson.fromJson(reader,new TypeToken<List<Map<String, String>>>() {}.getType());
		//List<Map> content = gson.fromJson(new InputStreamReader(input),new TypeToken<List<Map<String, String>>>() {}.getType());
		for (Map map : content) {
			System.out.println("����:" + map.get("street"));
		}
		reader.close();
	}

}