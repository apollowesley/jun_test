package com.mini.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.mini.jdbc.dynamic.DynamicDialect;
import com.mini.jdbc.ehcache.EhCache;
import com.mini.jdbc.translate.TranslateFactory;
import com.mini.jdbc.utils.DbKit;
import com.mini.jdbc.utils.RecordKit;
import com.mini.jdbc.utils.StrKit;

/**
 * Db. Powerful database query and update tool box.
 */
@SuppressWarnings("rawtypes")
public class DbHelper {
	final Object[] NULL_PARA_ARRAY = new Object[0];
	
	private JdbcTemplate jdbcTemplate;
	private DynamicDialect dialect;
	private EhCache cache;
	private boolean exchange = false;//是否转义数据源方言
	
	/**
	 * 设置是否转义数据源方言
	 * @param exchange
	 */
	public void setExchange(boolean exchange) {
		this.exchange = exchange;
	}

	/**
	 * 数据库帮助类
	 */
	public DbHelper(){
		this.cache = new EhCache();
	}
	
	/**
	 * 数据库帮助类
	 */
	public DbHelper(EhCache cache){
		this.cache = cache;
	}
	
	/**
	 * 数据库帮助类
	 */
	public DbHelper(JdbcTemplate jdbcTemplate,DynamicDialect dialect){
		this.jdbcTemplate = jdbcTemplate;
		this.dialect = dialect;
	}
	
	/**
	 * 获取jdbcTemplate
	 * @return
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 设置jdbcTemplate
	 * @param jdbcTemplate
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 获取转义
	 * @return
	 */
	public DynamicDialect getDialect() {
		return dialect;
	}

	/**
	 * 设置转义
	 * @param dialect
	 */
	public void setDialect(DynamicDialect dialect) {
		this.dialect = dialect;
	}

	/**
	 * 查询
	 * @param sql
	 * @param paras
	 * @param rowMapper
	 * @return
	 */
	public <T> List<T> query(String sql, Object[] paras,RowMapper<T> rowMapper) {
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.query(sql, paras, rowMapper);
	}
	
	/**
	 * 查询
	 * @param sql
	 * @param rowMapper
	 * @return
	 */
	public <T> List<T> query(String sql, RowMapper<T> rowMapper) {
		return query(sql, NULL_PARA_ARRAY,rowMapper);
	}
	
	/**
	 * 查询list
	 * @param sql
	 * @param elementType
	 * @return
	 */
	public <T> List<T> queryForList(String sql,Class<T> elementType) {
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.queryForList(sql,elementType);
	}
	
	/**
	 * 查询list
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryForList(String sql) {
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 查询对象
	 * @param sql
	 * @param requiredType
	 * @param rowMapper
	 * @return
	 */
	public <T> T queryForObject(String sql,Class<T> requiredType,RowMapper<T> rowMapper){
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.queryForObject(sql, rowMapper);
	}
	
	/**
	 * 查询对象
	 * @param sql
	 * @param requiredType
	 * @param args
	 * @param rowMapper
	 * @return
	 */
	public <T> T queryForObject(String sql,Class<T> requiredType,Object[] args,RowMapper<T> rowMapper){
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.queryForObject(sql, args, rowMapper);
	}
	
	/**
	 * 查询对象
	 * @param sql
	 * @param requiredType
	 * @return
	 */
	public <T> T queryForObject(String sql,Class<T> requiredType){
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.queryForObject(sql, requiredType);
	}
	
	/**
	 * 查询对象放入缓存
	 * @param cacheName
	 * @param key
	 * @param sql
	 * @param requiredType
	 * @param args
	 * @return
	 */
	public <T> T queryForObjectByCache(String cacheName, Object key,String sql,Class<T> requiredType,Object[] args){
		T result = cache.get(cacheName, key);
		if (result == null) {
			result = queryForObject(sql, requiredType,args);
			cache.put(cacheName, key, result);
		}
		return result;
	}
	
	/**
	 * 查询对象放入缓存
	 * @param sql
	 * @param requiredType
	 * @param args
	 * @return
	 */
	public <T> T queryForObject(String sql,Class<T> requiredType,Object[] args){
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.queryForObject(sql, requiredType,args);
	}
	
	/**
	 * 查询并返回Map
	 * @param sql
	 * @return
	 */
	public Map<String,Object> queryForMap(String sql){
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.queryForMap(sql);
	}
	
	/**
	 * 查询并返回Map
	 * @param sql
	 * @param args
	 * @return
	 */
	public Map<String,Object> queryForMap(String sql, Object[] args){
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.queryForMap(sql,args);
	}
	
	/**
	 * 查询返回List
	 * @param sql
	 * @param elementType
	 * @param args
	 * @return
	 */
	public <T> List<T> queryForList(String sql,Class<T> elementType, Object[] args){
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.queryForList(sql, elementType,args);
	}
	
	/**
	 * 查询返回缓存List
	 * @param cacheName
	 * @param key
	 * @param sql
	 * @param elementType
	 * @param args
	 * @return
	 */
	public <T> List<T> queryForListByCache(String cacheName, Object key, String sql,Class<T> elementType, Object[] args){
		List<T> result = cache.get(cacheName, key);
		if (result == null) {
			result = queryForList(sql, elementType, args);
			cache.put(cacheName, key, result);
		}
		return result;
	}
	
	/**
	 * 插入或更新
	 * @param record
	 * @return
	 */
	public <T extends Model> int merge(T record) {
		Record _record = RecordKit.convert2Record(record);
		String[] primaryKeys = _record.getPrimaryKeys();
		Object[] ids = new Object[primaryKeys.length];
		for(int i = 0;i < primaryKeys.length;i++){
			ids[i] = ((Record)_record).get(primaryKeys[i]);
			if(ids[i]!=null&&StrKit.notBlank(ids[i].toString())){
				return update(record);
			}
		}
		return save(record);
	}
	
	/**
	 * 更新
	 * @param record
	 * @return
	 */
	public <T extends Model> int update(T record) {
		StringBuilder sql = new StringBuilder();
		List<Object> paras = new ArrayList<Object>();
		Map<String,Object> ingore = ModelDeal.dealUpdate(record);
		Record _record = RecordKit.convert2Record(record);
		String[] primaryKeys = _record.getPrimaryKeys();
		Object[] ids = new Object[primaryKeys.length];
		for(int i = 0;i < primaryKeys.length;i++){
			ids[i] = ((Record)_record).get(primaryKeys[i]);
		}
		dialect.getDialect().forDbUpdate(record.getTableName(), primaryKeys, ids, _record, sql, paras);
		ModelDeal.completeRecord(ingore, record);
		return jdbcTemplate.update(sql.toString(), paras.toArray());
	}
	
	/**
	 * 更新
	 * @param tableName
	 * @param primaryKeys
	 * @param record
	 * @return
	 */
	public int update(String tableName,String[] primaryKeys,Record record){
		Object[] ids = new Object[primaryKeys.length];
		for(int i = 0;i < primaryKeys.length;i++){
			ids[i] = record.get(primaryKeys[i]);
		}
		StringBuilder sql = new StringBuilder();
		List<Object> paras = new ArrayList<Object>();
		Map<String,Object> ingore = ModelDeal.dealUpdate(record);
		dialect.getDialect().forDbUpdate(tableName, primaryKeys, ids, record, sql, paras);
		ModelDeal.completeRecord(ingore, record);
		return jdbcTemplate.update(sql.toString(), paras.toArray());
	}
	
	/**
	 * 执行更新插入或删除
	 * @param sql
	 * @return
	 */
	public int update(String sql){
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.update(sql);
	}
	
	/**
	 * 执行更新插入或删除
	 * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
	 * @param paras the parameters of sql
	 * @return either the row count for <code>INSERT</code>, <code>UPDATE</code>,
     *         or <code>DELETE</code> statements, or 0 for SQL statements 
     *         that return nothing
	 */
	public int update(String sql, Object[] args,int[] argTypes) {
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.update(sql, args, argTypes);
	}
	
	/**
	 * 执行更新插入或删除
	 * @param sql
	 * @param args
	 */
	public int update(String sql,Object[] args) {
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.update(sql, args);
	}
	
	/**
	 * 查询
	 * @param sql
	 * @param paras
	 * @return
	 * @throws DataAccessException
	 */
	public List<Record> find(String sql, Object[] paras) throws DataAccessException{
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return (List<Record>) jdbcTemplate.query(sql, paras, BaseEntityMapper.getRowMapper(Record.class));
	}
	
	/**
	 * 查询实体List
	 * @param sql
	 * @param clazz
	 * @param paras
	 * @return
	 */
	public <T extends BaseEntity> List<T> findEntity(String sql, Class<? extends BaseEntity> clazz, Object[] paras) {
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return (List<T>) jdbcTemplate.query(sql, paras, BaseEntityMapper.getRowMapper(clazz));
	}
	
	/**
	 * 查询实体List并存入缓存
	 * @param cacheName
	 * @param key
	 * @param sql
	 * @param clazz
	 * @param paras
	 * @return
	 */
	public <T extends BaseEntity> List<T> findEntityByCache(String cacheName, Object key, String sql, Class<? extends BaseEntity> clazz, Object[] paras) {
		List<T> result = cache.get(cacheName, key);
		if (result == null) {
			result = findEntity(sql, clazz, paras);
			cache.put(cacheName, key, result);
		}
		return result;
	}
	
	
	/**
	 * 查询
	 * @param sql
	 * @return
	 */
	public List<Record> find(String sql) {
		return find(sql, NULL_PARA_ARRAY);
	}
	
	/**
	 * 查询实体
	 * @param sql
	 * @param clazz
	 * @return
	 */
	public <T extends BaseEntity> List<T> findEntity(String sql, Class<? extends BaseEntity> clazz) {
		return findEntity(sql, clazz, NULL_PARA_ARRAY);
	}
	
	/**
	 * Find first record. I recommend add "limit 1" in your sql.
	 * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
	 * @param paras the parameters of sql
	 * @return the Record object
	 */
	public Record findFirst(String sql, Object[] paras) {
		List<Record> result = find(sql, paras);
		return result.size() > 0 ? result.get(0) : null;
	}
	
	/**
	 * 查找第一条实体记录
	 * @param sql
	 */
	public Record findFirst(String sql) {
		List<Record> result = find(sql, NULL_PARA_ARRAY);
		return result.size() > 0 ? result.get(0) : null;
	}
	
	/**
	 * 查找第一条实体记录
	 * @param sql
	 * @param clazz
	 * @param paras
	 * @return
	 */
	public <T extends BaseEntity> T findFirst(String sql,Class<? extends BaseEntity> clazz, Object[] paras) {
		List<T> result = findEntity(sql,clazz,paras);
		return result.size() > 0 ? result.get(0) : null;
	}
	
	/**
	 * 查找第一条实体记录并缓存
	 * @param cacheName
	 * @param key
	 * @param sql
	 * @param clazz
	 * @param paras
	 * @return
	 */
	public Record findFirstByCache(String cacheName, Object key, String sql,Class<? extends BaseEntity> clazz, Object[] paras) {
		Record result = cache.get(cacheName, key);
		if (result == null) {
			result = findFirst(sql, clazz, paras);
			cache.put(cacheName, key, result);
		}
		return result;
	}
	
	/**
	 * 查找第一条实体记录
	 * @param sql
	 * @param clazz
	 * @return
	 */
	public <T extends BaseEntity> T findFirst(String sql,Class<? extends BaseEntity> clazz) {
		List<T> result = findEntity(sql,clazz,NULL_PARA_ARRAY);
		return result.size() > 0 ? result.get(0) : null;
	}
	/**
	 * Find record by id with default primary key.
	 * <pre>
	 * Example:
	 * Record user = Db.findById("user", 15);
	 * </pre>
	 * @param tableName the table name of the table
	 * @param idValue the id value of the record
	 */
	public <T extends BaseEntity> T findById(Class<? extends BaseEntity> clazz, Object idValue) {
		EntityInfo entityInfo = EntityMapping.me().getEntity(clazz);
		String sql = dialect.getDialect().forDbFindById(entityInfo.getName(),entityInfo.getPrimaryKey());
		List<T> list = (List<T>) jdbcTemplate.query(sql, new Object[]{idValue}, BaseEntityMapper.getRowMapper(clazz));
		if(list!=null && list.size()>0)
			return list.get(0);
		else
			return null;
	}
	
	/**
	 * Find record by id.
	 * <pre>
	 * Example:
	 * Record user = Db.findById("user", "user_id", 123);
	 * Record userRole = Db.findById("user_role", "user_id, role_id", 123, 456);
	 * </pre>
	 * @param tableName the table name of the table
	 * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
	 * @param idValue the id value of the record, it can be composite id values
	 */
	public Record findById(String tableName, String primaryKey, Object[] idValue) {
		String sql = dialect.getDialect().forDbFindById(tableName, new String[]{primaryKey});
		return  (Record) jdbcTemplate.query(sql, idValue, BaseEntityMapper.getRowMapper(Record.class));
	}
	
	/**
	 * Delete record by id with default primary key.
	 * <pre>
	 * Example:
	 * Db.deleteById("user", 15);
	 * </pre>
	 * @param tableName the table name of the table
	 * @param idValue the id value of the record
	 * @return true if delete succeed otherwise false
	 */
	public int deleteById(Class<? extends BaseEntity>  clazz, Object idValue) {
		EntityInfo entityInfo = EntityMapping.me().getEntity(clazz);
		String sql = dialect.getDialect().forDbDeleteById(entityInfo.getName(), entityInfo.getPrimaryKey());
		return jdbcTemplate.update(sql, new Object[]{idValue});
	}
	
	/**
	 * Delete record by id.
	 * <pre>
	 * Example:
	 * Db.deleteById("user", "user_id", 15);
	 * Db.deleteById("user_role", "user_id, role_id", 123, 456);
	 * </pre>
	 * @param tableName the table name of the table
	 * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
	 * @param idValue the id value of the record, it can be composite id values
	 * @return true if delete succeed otherwise false
	 */
	public int deleteById(String tableName, String primaryKey, Object[] idValue) {
		String sql = dialect.getDialect().forDbDeleteById(tableName, new String[]{primaryKey});
		return jdbcTemplate.update(sql, idValue);
	}
	
	/**
	 * Delete record.
	 * <pre>
	 * Example:
	 * boolean succeed = Db.delete("user", "id", user);
	 * </pre>
	 * @param tableName the table name of the table
	 * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
	 * @param record the record
	 * @return true if delete succeed otherwise false
	 */
	public int delete(String tableName, String primaryKey, Record record) {
		return deleteById(tableName, primaryKey, new Object[]{record.get(primaryKey)});
	}
	
	/**
	 * <pre>
	 * Example:
	 * boolean succeed = Db.delete("user", user);
	 * </pre>
	 * @see #delete(String, String, Record)
	 */
	public int delete(Model record) {
		Record _record = RecordKit.convert2Record(record);
		String[] primaryKeys = _record.getPrimaryKeys();
		Object[] ids = new Object[primaryKeys.length];
		for(int i = 0;i < primaryKeys.length;i++){
			ids[i] = ((Record)_record).get(primaryKeys[i]);
		}
		String sql = dialect.getDialect().forDbDeleteById(_record.getTableName(), _record.getPrimaryKeys());
		return jdbcTemplate.update(sql, ids);
	}
	
	public  PageResult<BaseEntity> paginate(String sql,int pageNumber, int pageSize,Object[] paras, Class<? extends BaseEntity> clazz) {
		try {
			if (pageNumber < 1 || pageSize < 1)
				throw new MiniDaoException("pageNumber and pageSize must be more than 0");
			
			int totalRow = 0;
			//try {
			//	totalRow = queryForObject("select count(*) " + DbKit.replaceFormatSqlFrom(DbKit.replaceFormatSqlOrderBy(sql)),Integer.class,paras);
			//} catch (Exception e) {
				totalRow = queryForObject("select count(*) from (" + sql+") table_alias_count",Integer.class,paras);
			//}
			if (totalRow == 0)
				return new PageResult<BaseEntity>(pageNumber, pageSize);
			
			if(paras==null)
				paras = NULL_PARA_ARRAY;
			
			StringBuilder sb = new StringBuilder();
			paras = dialect.getDialect().forPaginate(sb, pageNumber, pageSize, sql, paras);
			List list = jdbcTemplate.query(sb.toString(), paras, BaseEntityMapper.getRowMapper(clazz));
			return new PageResult<BaseEntity>(list, pageNumber, pageSize, totalRow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 分页
	 * @param sql
	 * @param pageNumber
	 * @param pageSize
	 * @param clazz
	 * @return
	 */
	public PageResult<BaseEntity> paginate(String sql,int pageNumber, int pageSize,Class<? extends BaseEntity> clazz) {
		return paginate(sql, pageNumber, pageSize,NULL_PARA_ARRAY, clazz);
	}
	
	/**
	 * 分页查询缓存
	 * @param cacheName
	 * @param key
	 * @param pageNumber
	 * @param pageSize
	 * @param sql
	 * @param clazz
	 * @param args
	 * @return
	 */
	public <T extends BaseEntity> PageResult<T> paginateByCache(String cacheName, Object key,int pageNumber, int pageSize, String sql,Class clazz, Object[] args) {
		PageResult<T> result = cache.get(cacheName, key);
		if (result == null) {
			result = paginate(pageNumber, pageSize, sql, args, BaseEntityMapper.getRowMapper(clazz));
			cache.put(cacheName, key, result);
		}
		return result;
	}
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param sql
	 * @param paras
	 * @param rowMapper
	 * @return
	 */
	public <T> PageResult<T> paginate(int pageNumber, int pageSize, String sql, Object[] paras,RowMapper rowMapper) {
		try {
			if (pageNumber < 1 || pageSize < 1)
				throw new MiniDaoException("pageNumber and pageSize must be more than 0");
			
			if (dialect.getDialect().isTakeOverDbPaginate())
				return dialect.getDialect().takeOverDbPaginate(pageNumber, pageSize, sql, paras);
			int totalRow = 0;
			//try {
			//	totalRow = queryForObject("select count(*) " + DbKit.replaceFormatSqlFrom(DbKit.replaceFormatSqlOrderBy(sql)),Integer.class,paras);
			//} catch (Exception e) {
				totalRow = queryForObject("select count(*) from (" +sql +") table_alias_count",Integer.class,paras);
			//}
			if (totalRow == 0)
				return new PageResult<T>(new ArrayList<T>(0), pageNumber, pageSize, 0);
			
			if(paras==null)
				paras = NULL_PARA_ARRAY;
			
			StringBuilder sb = new StringBuilder();
			paras = dialect.getDialect().forPaginate(sb, pageNumber, pageSize, sql, paras);
			List<T> list = jdbcTemplate.query(sb.toString(), paras, rowMapper);
			return new PageResult<T>(list, pageNumber, pageSize, totalRow);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param sql
	 * @param paras
	 * @return
	 */
	public List<Record> paginateRecord(int pageNumber, int pageSize, String sql, Object[] paras) {
		if (pageNumber < 1 || pageSize < 1)
			throw new MiniDaoException("pageNumber and pageSize must be more than 0");
		
		if(paras==null)
			paras = NULL_PARA_ARRAY;
		
		StringBuilder sb = new StringBuilder();
		paras = dialect.getDialect().forPaginate(sb, pageNumber, pageSize, sql, paras);
		List<Record> list = (List<Record>) jdbcTemplate.query(sb.toString(), paras, BaseEntityMapper.getRowMapper(Record.class));
		return list;
	}
	
	/**
	 * 分页查询并缓存
	 * @param cacheName
	 * @param key
	 * @param pageNumber
	 * @param pageSize
	 * @param sql
	 * @param paras
	 * @return
	 */
	public List<Record> paginateRecordByCache(String cacheName, Object key, int pageNumber, int pageSize, String sql, Object[] paras) {
		List<Record> result = cache.get(cacheName, key);
		if (result == null) {
			result = paginateRecord(pageNumber, pageSize, sql,paras);
			cache.put(cacheName, key, result);
		}
		return result;
	}
	
	/**
	 * 分页查询实体类
	 * @param pageNumber
	 * @param pageSize
	 * @param clazz
	 * @param sql
	 * @param paras
	 * @return
	 */
	public <T extends BaseEntity> List<T> paginateEntity(int pageNumber, int pageSize, Class<? extends BaseEntity> clazz, String sql, Object[] paras) {
		if (pageNumber < 1 || pageSize < 1)
			throw new MiniDaoException("pageNumber and pageSize must be more than 0");
		
		if(paras==null)
			paras = NULL_PARA_ARRAY;
		
		StringBuilder sb = new StringBuilder();
		paras = dialect.getDialect().forPaginate(sb, pageNumber, pageSize, sql, paras);
		List<T> list = (List<T>) jdbcTemplate.query(sb.toString(), paras, BaseEntityMapper.getRowMapper(clazz));
		return list;
	}
	
	/**
	 * 分页查询并缓存
	 * @param cacheName
	 * @param key
	 * @param pageNumber
	 * @param pageSize
	 * @param clazz
	 * @param sql
	 * @param paras
	 * @return
	 */
	public <T extends BaseEntity> List<T> paginateEntityByCache(String cacheName, Object key,int pageNumber, int pageSize, Class<? extends BaseEntity> clazz, String sql, Object[] paras) {
		List<T> result = cache.get(cacheName, key);
		if (result == null) {
			result = paginateEntity(pageNumber, pageSize, clazz, sql, paras);
			cache.put(cacheName, key, result);
		}
		return result;
	}
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param sql
	 * @return
	 */
	public PageResult<Record> paginate(int pageNumber, int pageSize, String sql) {
		return paginate(pageNumber, pageSize, sql,NULL_PARA_ARRAY, BaseEntityMapper.getRowMapper(Record.class));
	}
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param sql
	 * @param args
	 * @return
	 */
	public PageResult<Record> paginate(int pageNumber, int pageSize, String sql,Object[] args) {
		return paginate(pageNumber, pageSize, sql,args, BaseEntityMapper.getRowMapper(Record.class));
	}
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param sql
	 * @param clazz
	 * @return
	 */
	public <T extends BaseEntity> PageResult<T> paginate(int pageNumber, int pageSize, String sql,Class clazz) {
		return paginate(pageNumber, pageSize, sql,NULL_PARA_ARRAY, BaseEntityMapper.getRowMapper(clazz));
	}
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param sql
	 * @param clazz
	 * @param args
	 * @return
	 */
	public <T extends BaseEntity> PageResult<T> paginate(int pageNumber, int pageSize, String sql,Class clazz, Object[] args) {
		return paginate(pageNumber, pageSize, sql, args, BaseEntityMapper.getRowMapper(clazz));
	}
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param sql
	 * @return
	 */
	public List<Record> paginateRecord(int pageNumber, int pageSize, String sql) {
		return paginateRecord(pageNumber, pageSize, sql,NULL_PARA_ARRAY);
	}
	
	/**
	 * 分页查询并缓存
	 * @param cacheName
	 * @param key
	 * @param pageNumber
	 * @param pageSize
	 * @param sql
	 * @return
	 */
	public List<Record> paginateRecordByCache(String cacheName, Object key, int pageNumber, int pageSize, String sql) {
		List<Record> result = cache.get(cacheName, key);
		if (result == null) {
			result = paginateRecord(pageNumber, pageSize, sql);
			cache.put(cacheName, key, result);
		}
		return result;
	}
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param clazz
	 * @param sql
	 * @return
	 */
	public <T extends BaseEntity> List<T> paginateEntity(int pageNumber, int pageSize, Class<? extends BaseEntity> clazz, String sql) {
		return paginateEntity(pageNumber, pageSize, clazz, sql,NULL_PARA_ARRAY);
	}
	
	/**
	 * 分页查询并缓存
	 * @param cacheName
	 * @param key
	 * @param pageNumber
	 * @param pageSize
	 * @param clazz
	 * @param sql
	 * @return
	 */
	public <T extends BaseEntity> List<T> paginateEntityByCache(String cacheName, Object key,int pageNumber, int pageSize, Class<? extends BaseEntity> clazz, String sql) {
		List<T> result = cache.get(cacheName, key);
		if (result == null) {
			result = paginateEntity(pageNumber, pageSize, clazz, sql);
			cache.put(cacheName, key, result);
		}
		return result;
	}
	
	/**
	 * Save record.
	 * <pre>
	 * Example:
	 * Record userRole = new Record().set("user_id", 123).set("role_id", 456);
	 * Db.save("user_role", "user_id, role_id", userRole);
	 * </pre>
	 * @param tableName the table name of the table
	 * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
	 * @param record the record will be saved
	 * @param true if save succeed otherwise false
	 */
	public int save(String tableName, String[] primaryKey, Model record) {
		List<Object> paras = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		Map<String,Object> ingore = ModelDeal.dealInsert(record);
		Record _record = RecordKit.convert2Record(record);
		dialect.getDialect().forDbSave(sql, paras, tableName, primaryKey, _record);
		ModelDeal.completeRecord(ingore, record);
		return jdbcTemplate.update(sql.toString(),paras.toArray());
		
	}
	
	/**
	 * @see #save(BaseEntity)
	 */
	public int save(Model record) {
		return save(record.getTableName(),record.getPrimaryKeys(), record);
	}
	
	/**
	 * @see #save(String, String, Record)
	 */
	public int save(Record record, Class clazz) {
		EntityInfo entityInfo = EntityMapping.me().getEntity(clazz);
		return save(entityInfo.getName(),entityInfo.getPrimaryKey(), record);
	}
	
	/**
	 * @see #execute(String, ICallback)
	 */
	public void execute(String sql) {
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		jdbcTemplate.execute(sql);
	}
	
	/**
	 * Find Record by cache.
	 * @see #find(String, Object[])
	 * @param cacheName the cache name
	 * @param key the key used to get date from cache
	 * @return the list of Record
	 */
	public List<Record> findByCache(String cacheName, Object key, String sql, Object[] paras) {
		List<Record> result = cache.get(cacheName, key);
		if (result == null) {
			result = find(sql, paras);
			cache.put(cacheName, key, result);
		}
		return result;
	}
	
	/**
	 * @see #findByCache(String, Object, String, Object[])
	 */
	public List<Record> findByCache(String cacheName, Object key, String sql) {
		return findByCache(cacheName, key, sql, NULL_PARA_ARRAY);
	}
	
	/**
	 * Find first record by cache. I recommend add "limit 1" in your sql.
	 * @see #findFirst(String, Object[])
	 * @param cacheName the cache name
	 * @param key the key used to get date from cache
	 * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
	 * @param paras the parameters of sql
	 * @return the Record object
	 */
	public Record findFirstByCache(String cacheName, Object key, String sql, Object[] paras) {
		Record result = cache.get(cacheName, key);
		if (result == null) {
			result = findFirst(sql, paras);
			cache.put(cacheName, key, result);
		}
		return result;
	}
	
	/**
	 * @see #findFirstByCache(String, Object, String, Object[])
	 */
	public Record findFirstByCache(String cacheName, Object key, String sql) {
		return findFirstByCache(cacheName, key, sql, NULL_PARA_ARRAY);
	}
	
	/**
	 * Paginate by cache.
	 * @see #paginate(int, int, String, String, Object[])
	 * @return Page
	 */
	public PageResult<Record> paginateByCache(String cacheName, Object key, int pageNumber, int pageSize, String sql, Object[] paras) {
		PageResult<Record> result = cache.get(cacheName, key);
		if (result == null) {
			result = paginate(pageNumber, pageSize, sql, paras);
			cache.put(cacheName, key, result);
		}
		return result;
	}
	
	/**
	 * @see #paginateByCache(String, Object, int, int, String, String, Object[])
	 */
	public PageResult<Record> paginateByCache(String cacheName, Object key, int pageNumber, int pageSize, String sql) {
		PageResult<Record> result = cache.get(cacheName, key);
		if (result == null) {
			result = paginate(pageNumber, pageSize, sql);
			cache.put(cacheName, key, result);
		}
		return result;
	}
	
	/**
	 * @see #batch(String, BatchPreparedStatementSetter)
     */
    public int[] batch(String sql, BatchPreparedStatementSetter pss) {
    	if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
    	return jdbcTemplate.batchUpdate(sql, pss);
    }
	
	/**
	 * @see #batch(String, List<Object[]>)
     */
	public int[] batch(String sql, List<Object[]> batchArgs) {
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	/**
	 * @see #batch(String, List<Object[]>, int[] )
     */
	public int[] batch(String sql, List<Object[]> batchArgs,int[] argTypes) {
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.batchUpdate(sql, batchArgs, argTypes);
	}
	
	/**
	 * @see #batch(String, Collection<T>, int, ParameterizedPreparedStatementSetter<T>)
     */
	public int[][] batch(String sql,Collection<Object> batchArgs, int batchSize, ParameterizedPreparedStatementSetter<Object> pss) {
		if(exchange)
			TranslateFactory.translate(sql, dialect.getDialect());
		return jdbcTemplate.batchUpdate(sql, batchArgs, batchSize, pss);
	}
	
	
	/**
	 * @see #batch(String, List, int)
     */
    public int[] batch(String[] sql) {
    	for(String s : sql)
    		if(exchange)
    			TranslateFactory.translate(s, dialect.getDialect());
    	return jdbcTemplate.batchUpdate(sql);
    }
}



