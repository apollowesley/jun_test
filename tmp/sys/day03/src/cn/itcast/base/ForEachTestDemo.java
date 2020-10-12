package cn.itcast.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ForEachTestDemo {
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("abc");
		list.add("abcd");
		list.add("asf");
		list.add("ceg");
		list.add("daf");
		list.add("dfs");
		
		//迭代器
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			String str = it.next();
			if(str.contains("a")){
				//list.remove(str);  //java.util.ConcurrentModificationException
				it.remove();
			}
		}
		
		System.out.println(list);
		
		
		
	}
	
	public void demo3(){
		//java.util.Arrays.ArrayList  
		List<String> list = Arrays.asList("abc","bcd", "asf", "ceg", "daf", "dfs");  
		
		for(int i = 0 ; i < list.size() ; i ++){
			String str = list.get(i);
			if(str.contains("a")){
				//java.lang.UnsupportedOperationException
				list.remove(i);
			}
		}
		
		
		System.out.println(list);
	}
	
	public void demo2(){
		//不能删除
		List<String> list = new ArrayList<String>();
		list.add("abc");
		list.add("abcd");
		list.add("asf");
		list.add("ceg");
		list.add("daf");
		list.add("dfs");
		
		//java.util.ConcurrentModificationException
		for(String s:list){   //foreach不能删除
			if(s.contains("a")){
				list.remove(s);
			}
		}
		
		System.out.println(list);
	}
	
	
	public void demo(){
		
		List<String> list = new ArrayList<String>();
		list.add("abc");
		list.add("abcd");
		list.add("asf");
		list.add("ceg");
		list.add("daf");
		list.add("dfs");
		list.add("abc");
		
		for(int i = 0 ; i < list.size(); i++){
			String str = list.get(i);
			if(str.contains("a")){  //确定是否包含字符串a
				//移除
				list.remove(i);
//				list.remove(str);
				i --;
			}
			//System.out.println(str);
		}
		
		System.out.println(list);
		
	}

}
