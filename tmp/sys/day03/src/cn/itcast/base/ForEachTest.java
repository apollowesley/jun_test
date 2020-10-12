package cn.itcast.base;

import java.util.ArrayList;
import java.util.List;

public class ForEachTest {
	
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<String>();
		list.add("abc");
		//list.add(123);
		
		//遍历list
		for(int i = 0 ; i < list.size(); i ++){
			String obj = list.get(i);
			System.out.println(obj);
		}
		
		for(String s:list){
			System.out.println("##" + s);
		}
		
		
		
		//没有泛型
		List l = new ArrayList();
		l.add("abc");
		l.add(123);
		
		for(Object o :l){
			System.out.println("---" + o);
		}
		
		
		String[] str = {"abc","edc"};
		for(String s:str){
			System.out.println("string " + s);
		}
		
		/* foreach
		 * for(类型   变量名 : 集合(Iterable)|数组){}
		 * 
		 *  * 需要实现Iterable接口的所有的集合。只要能够迭代的所有的集合
		 */
		
		
	}

}
