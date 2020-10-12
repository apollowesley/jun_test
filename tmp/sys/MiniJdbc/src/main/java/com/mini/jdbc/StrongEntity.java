package com.mini.jdbc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.cglib.proxy.Enhancer;

import com.mini.jdbc.utils.MiniUtil;
import com.mini.jdbc.utils.StrKit;
/**
 * 强实体基类 
 * @author sxjun
 * @date 2014-7-7 下午3:47:25
 *
 */
public abstract class StrongEntity implements BaseEntity,Model{
	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 主键
	 */
	private String[] primaryKeys;
	
	/**
	 * 是否持久态
	 * 查询出来的数据都是持久态的
	 * 持久态的数据在更新的时候，只更新修改的字段
	 * 非持久态的数据在更新的时候回全部更新
	 */
	private boolean isPersistent = false;
	
	/**
	 * 修改记录标志
	 */
	private Set<String> modifyFlag =  new HashSet<String>();
	
	public static <T> T getInstance(Class clazz){
		Enhancer enhancer=new Enhancer();  
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new StrongEntityInterceptor());
		return (T) enhancer.create();
	}
	
	/**
	 * 获取主键
	 */
	public String[] getPrimaryKeys() {
		if(primaryKeys!=null&&primaryKeys.length>0)
			return primaryKeys;
		else{
			Class clazz = this.getClass();
			if(MiniUtil.isBaseEntity(clazz)){
				String[] pks = EntityMapping.me().getEntity(clazz).getPrimaryKey();
				if(pks!=null&&pks.length>0)
					return pks;
				else
					throw new MiniDaoException("主键不存在");
			}else
				throw new MiniDaoException("主键不存在");
		}
	}

	/**
	 * 获取表名
	 */
	public String getTableName(){
		if(StrKit.notBlank(tableName))
			return tableName;
		else{
			boolean findit = false;
			Class clazz = this.getClass();
			if(MiniUtil.isBaseEntity(clazz)){
				String tabName = EntityMapping.me().getEntity(MiniUtil.getStrongClazz(clazz).getClass()).getName();
				if(StrKit.notBlank(tabName))
					return MiniUtil.caps(tabName);
				else
					throw new MiniDaoException("表名不存在");
			}else
				throw new MiniDaoException("表名不存在");
		}
	}
	
	/**
	 * 没有加@Column注解，但在数据库中存在的字段
	 */
	private Map<String, Object> aliasFields = new HashMap<String, Object>();
	
	/**
	 * 设置值
	 * @param field
	 * @param value
	 */
	public void setAliasField(String field, Object value) {
		aliasFields.put(field, value);
	}
	
	/**
	 * 根据字段获取值
	 * @param field
	 * @return
	 */
	public Object getAliasFields(String field) {
		return aliasFields.get(field);
	}
	
	/**
	 * 清空修改记录
	 */
	public void clearModifyFlag() {
		modifyFlag.clear();
	}
	
	/**
	 * 获取修改记录标志
	 * @return
	 */
	public Set<String> getModifyFlag() {
		return modifyFlag;
	}

	/**
	 * 添加修改记录字段
	 * @param modifyFlag
	 */
	public void addModifyFlag(String field) {
		this.modifyFlag.add(field);
	}
	
	/**
	 * 获取是否持久态数据
	 * @return
	 */
	public boolean isPersistent() {
		return isPersistent;
	}

	/**
	 * 设置是否持久态数据
	 * @param isPersistent
	 */
	public void setPersistent(boolean isPersistent) {
		this.isPersistent = isPersistent;
	}
	
}
