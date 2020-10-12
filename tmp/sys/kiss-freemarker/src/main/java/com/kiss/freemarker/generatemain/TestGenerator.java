package com.kiss.freemarker.generatemain;

import java.util.ArrayList;
import java.util.List;


/***
 * 
 * (生成入口)
 * @author yong_jiang 
 * 
 *	TestGenerator主要用来根据传入的 实体类的名称及他的上层包名
 *	 生成所有的Service，serviceImpl Logic，LogicImpl， Dao,DaoImpl,及serviceTest;
 *   用来快速开发项目，提升效率。
 */

public class TestGenerator {
	public static List<LabelValueBean> list = new ArrayList();
	static{
		
		/*LabelValueBean aChange = new LabelValueBean("PcAttribute","product");
		list.add(aChange);
		*/
	}
	private static void genSource(){
		for(LabelValueBean l : list){
			genSource(l.getLabel(),l.getValue());
		}
		
	}
	/**
	 * 
	 * @param entity  表示实体类名,packagename 表示包名(例如:广告模块就是 advert, 组织就是org);
	 */
	private static void genSource(String entity, String packagename){
		Generator gen = new AllGenerator();
		//实体名
		gen.setEntity(entity);
		//公司名
		gen.setOrgName("com.hrrm.kiss");
		//logic层和service层的公共包名
		gen.setPackageName(packagename);
		gen.setTargetDir("../hrrm-core");
		gen.setApplication("hrrm-core");
		gen.setSharedProject("hrrm-core");
		//指定dao层的公共包名
		if(gen.generate()){
			System.out.println("生成成功");
		}else{
			System.out.println("生成失败!!");
		}
		
	}
	
	public static void main(String[] args){
		genSource("RoleOperate", "user");
	}
}
