package com.kiss;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.kiss.user.entity.Menu;

public class Test {
	public static JSONSerializer getMenuSerializer(SerializeWriter out ){
		PropertyFilter filter = new PropertyFilter() {
		    public boolean apply(Object source, String name, Object value) {
		    	if("childMenus".equals(name))return false;
		        return true;
		    }
		};
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.getPropertyFilters().add(filter);
		return serializer;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	/*	Object value = "10";
		System.out.println(value instanceof Integer);*/
	/*	JSONObject json = new JSONObject();
		List<Menu> menus = new ArrayList<Menu>();
		for(int i=0;i<10;i++){
			Menu _1 = new Menu();
			_1.setMenuId(i+"");
			_1.setMenuName("菜单名称"+i);
			menus.add(_1);
		}
		SerializeWriter out = new SerializeWriter();
		json.put("total",menus.size());
		json.put("rows",menus);
		getMenuSerializer(out).write(json);
		System.out.println(out.toString());*/
		List<Integer> _1list = new ArrayList<Integer>();
		List<Integer> _2list = new ArrayList<Integer>();
		for(int i=0;i<1000;i++){
			_1list.add(i);
			if(i<10){
				_2list.add(i);
			}
		}
		long start = System.currentTimeMillis();
		for(Integer i : _1list){
			for(Integer j :_2list){
				System.out.println(i==j);
			}
		}
		long end = System.currentTimeMillis();
		for(Integer i : _2list){
			for(Integer j :_1list){
				System.out.println(i==j);
			}
		}
		long ends = System.currentTimeMillis();
		System.out.println("---------------" + (end -start));
		System.out.println("---------------" + (ends -end));
	}

}
