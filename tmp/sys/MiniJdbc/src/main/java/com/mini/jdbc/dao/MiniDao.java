package com.mini.jdbc.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mini.jdbc.BaseEntity;
import com.mini.jdbc.MiniDaoException;
import com.mini.jdbc.Model;
import com.mini.jdbc.PageResult;
import com.mini.jdbc.Record;
import com.mini.jdbc.cfg.MiniConfig;
import com.mini.jdbc.dynamic.DynamicDataSource;
import com.mini.jdbc.utils.MiniUtil;

/**
 * 通用Dao对象
 * @author sxjun
 * 2016-1-15
 */
public class MiniDao implements IMiniDao
{
	
	/**
	 * mini jdbc 配置
	 */
	private MiniConfig miniConfig;
	
	/**
	 * miniDao 构造函数
	 * @param cacheManager
	 */
	public MiniDao() {
		miniConfig = new MiniConfig(); 
	}
	
	/**
	 * miniDao 构造函数
	 * @param cacheManager
	 */
	public MiniDao(DynamicDataSource dynamicDataSource) {
		miniConfig = new MiniConfig(); 
		miniConfig.setDynamicDataSource(dynamicDataSource);
	}
	
	/**
	 * miniDao 构造函数
	 * @param cacheManager
	 */
	public MiniDao(JdbcTemplate jdbcTemplate) {
		miniConfig = new MiniConfig(jdbcTemplate); 
	}
	
	/**
	 * 设置动态数据源
	 * @param dynamicDataSource
	 */
	public void setDynamicDataSource(DynamicDataSource dynamicDataSource) {
		miniConfig.setDynamicDataSource(dynamicDataSource);
	}
	
	/**
	 * 设置是否转义数据源方言
	 * @param exchange
	 */
	public void setExchange(boolean exchange) {
		miniConfig.setExchange(exchange);
	}

	/**
     * 插入数据
     * @param record BaseEntity或Record对象 <必须继承Record>
     * @return int
     */
    public <T extends Model> int insert(T record){
    	try{
	    	return miniConfig.getDbHelper().save(record);
	    } catch (Exception e) {
			throw new MiniDaoException("The function of insert is error",e);
		}
    }

    /**
     * 删除数据
     * @param record BaseEntity或Record对象 <必须继承Record>
     * @return int
     */
    public <T extends Model> int delete(T record){
    	try{
	    	return miniConfig.getDbHelper().delete(record);
	    } catch (Exception e) {
			throw new MiniDaoException("The function of delete is error",e);
		}
    }
    
    /**
     * 删除数据
     * @param clazz BaseEntity或Record对象 <必须继承Record>
     * @param primaryKey 主键
     * @return int
     */
    public int deleteById(Class<? extends BaseEntity> clazz, Object primaryKey){
    	try{
    		return miniConfig.getDbHelper().deleteById(clazz, primaryKey);
	    } catch (Exception e) {
			throw new MiniDaoException("The function of deleteById is error",e);
		}
    }
    
    /**
     * 更新数据
     * @param record BaseEntity或Record对象 <必须继承Record>
     * @return int
     */
    public <T extends Model> int update(T record){
    	try{
    		return miniConfig.getDbHelper().update(record);
    	} catch (Exception e) {
			throw new MiniDaoException("The function of update is error",e);
		}
    }
    
    /**
     * 插入或更新数据
     * @param record BaseEntity或Record对象 <必须继承Record>
     * @return int
     */
    public <T extends Model> int merge(T record){
    	try{
	    	return miniConfig.getDbHelper().merge(record);
	    } catch (Exception e) {
			throw new MiniDaoException("The function of insert is error",e);
		}
    }
    
    /**
     * 执行sql语句
     * @param sql 执行语句
     * @param paras 参数
     * @return int
     */
    public int execute(String sql, Object... paras){
    	try{
    		return miniConfig.getDbHelper().update(sql, paras);
    	} catch (Exception e) {
			throw new MiniDaoException("The function of execute is error",e);
		}
    }
    
    /**
     * 根据ID查找单个实体
     * @param clazz  类<必须继承BaseEntity>
     * @param primaryKey 主键
     * @return T extends BaseEntity
     */
    @SuppressWarnings("unchecked")
	public <T> T findById(Class<? extends BaseEntity> clazz, Object primaryKey){
    	try{
    		return (T) miniConfig.getDbHelper().findById(clazz, primaryKey);
    	} catch (Exception e) {
			throw new MiniDaoException("The function of findById is error",e);
		}
    }
    
    /**
     * 查找单条记录
     * @param sql 查询语句
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)，或基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]
     * @param args 参数值数组
     * @return T {String、Integer、Long、Record、BaseEntity等}
     */
    @SuppressWarnings("unchecked")
	public <T> T find(String sql, Class clazz, Object... args){
    	try {
			if(MiniUtil.isPrimitiveClass(clazz))
				return (T) miniConfig.getDbHelper().queryForObject(sql,clazz,args);
			else if(MiniUtil.isBaseEntity(clazz))
				return (T) miniConfig.getDbHelper().findFirst(sql,clazz,args);
			else if(MiniUtil.isRecord(clazz))
				return (T) miniConfig.getDbHelper().findFirst(sql,args);
			else
				throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
		} catch (Exception e) {
			throw new MiniDaoException("The function of find is error",e);
		}
    }
    
    /**
     * 查找单条记录
     * @param sql 查询语句
     * @param args 参数值数组
     * @return Record
     */
    @SuppressWarnings("unchecked")
   	public Record find(String sql, Object... args){
    	return this.find(sql,Record.class,args);
    }
    
    /**
     * 查找单条记录并缓存
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param sql 查询语句
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型);返回一个数组;String.class;Integer.class;Long.class]
     * @param args 参数值数组
     * @return T {String、Integer、Long、Record、BaseEntity等}
     */
    public <T> T cacheFind(String cacheName, Object key, String sql, Class clazz, Object... args){
    	try{
	    	if(MiniUtil.isPrimitiveClass(clazz))
	    		return (T) miniConfig.getDbHelper().queryForObjectByCache(cacheName, key, sql,clazz,args);
	    	else if(MiniUtil.isBaseEntity(clazz))
	    		return (T) miniConfig.getDbHelper().findFirstByCache(cacheName, key, sql,clazz,args);
	    	else if(MiniUtil.isRecord(clazz))
	    		return (T) miniConfig.getDbHelper().findFirstByCache(cacheName, key, sql,args);
	    	else
	    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
    	} catch (Exception e) {
			throw new MiniDaoException("The function of cacheFind is error",e);
		}
    }
    
    /**
     * 查找单条记录并缓存
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param sql 查询语句
     * @param args 参数值数组
     * @return Record
     */
    public Record cacheFind(String cacheName, Object key, String sql, Object... args){
    	return this.cacheFind(cacheName, key, sql, Record.class, args);
    }

    /**
     * 查找一个list
     * @param sql 查询语句
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)] 或 基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]
     * @param args 参数值数组
     * @return T extends BaseEntity
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> findList(String sql, Class clazz, Object... args){
    	try{
	    	if(MiniUtil.isPrimitiveClass(clazz))
	    		return miniConfig.getDbHelper().queryForList(sql, clazz, args);
	    	else if(MiniUtil.isBaseEntity(clazz))
	    		return miniConfig.getDbHelper().findEntity(sql,clazz,args);
	    	else  if(MiniUtil.isRecord(clazz))
	    		return (List<T>) miniConfig.getDbHelper().find(sql, args);
	    	else
	    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
	    } catch (Exception e) {
			throw new MiniDaoException("The function of findList is error",e);
		}
    }
    
    /**
     * 查找一个list
     * @param sql 查询语句
     * @param args 参数值数组
     * @return List<Record>
     */
	public List<Record> findList(String sql, Object... args){
    	return this.findList(sql, Record.class, args);
    }
    
    /**
     * 查找一个list并缓存
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param sql 查询语句
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)]
     * @param args 参数值数组
     * @return T extends BaseEntity
     */
    public <T> List<T> cacheFindList(String cacheName, Object key, String sql, Class clazz, Object... args){
    	try{
	    	if(MiniUtil.isPrimitiveClass(clazz))
	    		return miniConfig.getDbHelper().queryForListByCache(cacheName, key,sql, clazz, args);
	    	else if(MiniUtil.isBaseEntity(clazz))
	    		return miniConfig.getDbHelper().findEntityByCache(cacheName, key,sql,clazz,args);
	    	else  if(MiniUtil.isRecord(clazz))
	    		return (List<T>) miniConfig.getDbHelper().findByCache(cacheName, key,sql, args);
	    	else
	    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
	    } catch (Exception e) {
			throw new MiniDaoException("The function of cacheFindList is error",e);
		}
    }
    
    /**
     * 查找一个list并缓存
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param sql 查询语句
     * @param args 参数值数组
     * @return List<Record>
     */
    public List<Record> cacheFindList(String cacheName, Object key, String sql, Object... args){
    	return this.cacheFindList(cacheName, key, sql,Record.class, args);
    }
    
    /**
     * 分页查找一个list
     * @param sql 查询语句
     * @param pageNumber 记录行的偏移量
     * @param pageSize 记录行的最大数目
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)]，或基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]
     * @param args 参数值数组
     * @return T extends BaseEntity
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> paginate(String sql,int pageNumber, int pageSize, Class clazz, Object... args){
    	try{
	    	if(MiniUtil.isPrimitiveClass(clazz)){
	    		List<T> list = new ArrayList<T>();
	    		List<Record> records = miniConfig.getDbHelper().paginateRecord(pageNumber, pageSize, sql, args);
	    		for(Record record : records){
	    			Object[] o = record.getColumnValues();
	    			list.add((T)o[0]);
	    			break;
	    		}
	    		return list;
	    	}else if(MiniUtil.isBaseEntity(clazz))
	    		return miniConfig.getDbHelper().paginateEntity(pageNumber, pageSize, clazz, sql, args);
	    	else  if(MiniUtil.isRecord(clazz))
	    		return (List<T>) miniConfig.getDbHelper().paginateRecord(pageNumber, pageSize, sql, args);
	    	else
	    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
	    } catch (Exception e) {
			throw new MiniDaoException("The function of paginate is error",e);
		}
    }
    
    /**
     * 分页查找一个list
     * @param sql 查询语句
     * @param pageNumber 记录行的偏移量
     * @param pageSize 记录行的最大数目
     * @param args 参数值数组
     * @return List<Record>
     */
	public List<Record> paginate(String sql,int pageNumber, int pageSize, Object... args){
    	return this.paginate(sql, pageNumber, pageSize, Record.class, args);
    }
    
    /**
     * 分页查找一个list并缓存
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param sql 查询语句
     * @param pageNumber 记录行的偏移量
     * @param pageSize 记录行的最大数目
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)]
     * @param args 参数值数组
     * @return T extends BaseEntity
     */
    public <T> List<T> cachePaginate(String cacheName, Object key, String sql,int pageNumber, int pageSize, Class clazz, Object... args){
    	try{
	    	if(MiniUtil.isPrimitiveClass(clazz)){
	    		List<T> list = new ArrayList<T>();
	    		List<Record> records = miniConfig.getDbHelper().paginateRecordByCache(cacheName, key, pageNumber, pageSize, sql, args);
	    		for(Record record : records){
	    			Object[] o = record.getColumnValues();
	    			list.add((T)o[0]);
	    			break;
	    		}
	    		return list;
	    	}else if(MiniUtil.isBaseEntity(clazz))
	    		return miniConfig.getDbHelper().paginateEntityByCache(cacheName, key, pageNumber, pageSize, clazz, sql, args);
	    	else  if(MiniUtil.isRecord(clazz))
	    		return (List<T>) miniConfig.getDbHelper().paginateRecordByCache(cacheName, key, pageNumber, pageSize, sql, args);
	    	else
	    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
	    } catch (Exception e) {
			throw new MiniDaoException("The function of cachePaginate is error",e);
		}
    }
    
    /**
     * 分页查找一个list并缓存
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param sql 查询语句
     * @param pageNumber 记录行的偏移量
     * @param pageSize 记录行的最大数目
     * @param args 参数值数组
     * @return List<Record>
     */
    public List<Record> cachePaginate(String cacheName, Object key, String sql,int pageNumber, int pageSize, Object... args){
    	return this.cachePaginate(cacheName, key, sql, pageNumber, pageSize, Record.class, args);
    }
    
    /**
     * 获取分页Record数据
     * @param pageNumber 记录行的偏移量
     * @param pageSize 记录行的最大数目
     * @param sql 执行语句
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)]，或基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]
     * @param args 参数
     * @return PageResult<T>
     */
    @SuppressWarnings("unchecked")
	public <T> PageResult<T> paginateResult(String sql, int pageNumber, int pageSize, Class clazz, Object... args){
    	try{
	    	if(MiniUtil.isPrimitiveClass(clazz)){
	    		PageResult<Record> records = miniConfig.getDbHelper().paginate(pageNumber, pageSize, sql, args);
	    		List<T> list = new ArrayList<T>();
	    		for(Record record : records.getResults()){
	    			Object[] o = record.getColumnValues();
	    			list.add((T)o[0]);
	    			break;
	    		}
	    		PageResult<T> reds = new PageResult<T>(list, pageNumber, pageSize, Integer.parseInt(String.valueOf(records.getResultCount())));
	    		return reds;
	    	}else if(MiniUtil.isBaseEntity(clazz))
	    		return miniConfig.getDbHelper().paginate(pageNumber, pageSize, sql, clazz, args);
	    	else  if(MiniUtil.isRecord(clazz))
	    		return (PageResult<T>) miniConfig.getDbHelper().paginate(pageNumber, pageSize, sql, args);
	    	else
	    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
	    } catch (Exception e) {
			throw new MiniDaoException("The function of paginateResult is error",e);
		}
    }
    
    /**
     * 获取分页Record数据
     * @param pageNumber 记录行的偏移量
     * @param pageSize 记录行的最大数目
     * @param sql 执行语句
     * @param args 参数
     * @return PageResult<Record>
     */
	public PageResult<Record> paginateResult(String sql, int pageNumber, int pageSize, Object... args){
    	return this.paginateResult(sql, pageNumber, pageSize, Record.class,args);
    }
    
    /**
     * 获取分页Record数据并缓存
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param pageNumber 记录行的偏移量
     * @param pageSize 记录行的最大数目
     * @param sql 执行语句
     * @param args 参数
     * @return PageResult<T>
     */
    public <T> PageResult<T> cachePaginateResult(String cacheName, Object key, String sql, int pageNumber, int pageSize, Class clazz, Object... args){
    	try{
	    	if(MiniUtil.isPrimitiveClass(clazz)){
	    		PageResult<Record> records = miniConfig.getDbHelper().paginateByCache(cacheName, key, pageNumber, pageSize, sql, args);
	    		List<T> list = new ArrayList<T>();
	    		for(Record record : records.getResults()){
	    			Object[] o = record.getColumnValues();
	    			list.add((T)o[0]);
	    			break;
	    		}
	    		PageResult<T> reds = new PageResult<T>(list, pageNumber, pageSize, Integer.parseInt(String.valueOf(records.getResultCount())));
	    		return reds;
	    	}else if(MiniUtil.isBaseEntity(clazz))
	    		return miniConfig.getDbHelper().paginateByCache(cacheName, key, pageNumber, pageSize, sql, clazz, args);
	    	else  if(MiniUtil.isRecord(clazz))
	    		return (PageResult<T>) miniConfig.getDbHelper().paginateByCache(cacheName, key, pageNumber, pageSize, sql, args);
	    	else
	    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
	    } catch (Exception e) {
			throw new MiniDaoException("The function of cachePaginateResult is error",e);
		}
    }
    
    /**
     * 获取分页Record数据并缓存
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param pageNumber 记录行的偏移量
     * @param pageSize 记录行的最大数目
     * @param sql 执行语句
     * @param args 参数
     * @return PageResult<Record>
     */
    public PageResult<Record> cachePaginateResult(String cacheName, Object key, String sql, int pageNumber, int pageSize,Object... args){
    	return this.cachePaginateResult(cacheName, key, sql, pageNumber, pageSize, Record.class, args);
    }
}
