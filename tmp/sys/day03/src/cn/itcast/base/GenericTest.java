package cn.itcast.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class GenericTest {
	
	public static void main(String[] args) {
		
		/*
		 * 格式：类型<类型[,类型...]>
		 * * 泛型使用必须前后相同
		 * * 泛型是在编译前进行类型约束，在字节码文件中不存在泛型信息
		 */
		
		List<String> list = new ArrayList<String>();
		list.add("abc");
//		list.add(123);
		
		
		Map map = new HashMap();
		map.put(123, 123);
		
		//没有泛型的map 的遍历
		Set s = map.entrySet();
		Iterator ite = s.iterator();
		while(ite.hasNext()){
			Map.Entry entry = (Entry) ite.next();
			Integer key = (Integer) entry.getKey();
			Integer value = (Integer) entry.getValue();
			System.out.println("*** " + key + ":" + value);
		}
		
		
		
		Map<String,Integer> map2 = new HashMap<String, Integer>();
		map2.put("123", 456);
		map2.put("abc", 789);
		
		
		//遍历map  [key,value][key,value]
//		map2.keySet()  获得所有的键值 key
		Set<Map.Entry<String,Integer>> set = map2.entrySet();  //获得每一组 Entry(key,value)
		//迭代器
		Iterator<Entry<String,Integer>> it = set.iterator();
		while(it.hasNext()){
			Map.Entry<String, Integer> entry = it.next();
			String key = entry.getKey();
			Integer value = entry.getValue();
			System.out.println(key + ":" + value);
		}
		
		
	}
	
/*	
 * 不能同时存在
 * public void demo(List<String> list){
		
	}
	

	public void demo(List<Integer> list){
		
	}*/

}
