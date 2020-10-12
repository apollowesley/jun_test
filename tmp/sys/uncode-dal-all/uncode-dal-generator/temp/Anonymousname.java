package cn.uncode.dal.dto;

import cn.uncode.dal.core.BaseDTO2;
/**
 * 数据库实体类,此类由Uncode自动生成
 * @author uncode
 * @date 2017-02-21
 */
public class Anonymousname extends BaseDTO2 {
	public final static String NAME = "name";
	public final static String USETIME = "usetime";

	/**
	 * 匿名名字
	 */
	private String name;
	/**
	 * 匿名使用时间
	 */
	private Long usetime;

	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public Long getUsetime(){
		return this.usetime;
	}
	public void setUsetime(Long usetime){
		this.usetime = usetime;
	}

}