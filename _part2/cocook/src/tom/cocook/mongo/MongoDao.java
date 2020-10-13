package tom.cocook.mongo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tom.kit.clazz.ReflectUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
/**
 * http://api.mongodb.org/java/2.11.3/
 * mongodb 基本操作
 * @author panmg
 */
public class MongoDao {

	public static MongoDB db;
	
	public static void init(File file){
		db = new MongoDB(file);
		db.init();
	}
	
	public MongoDao() {
	}

	/**
	 * 新增
	 * @param col
	 * @param data
	 * @return
	 */
	public String insert(String col, Map<String, ? extends Object> data) {
		DBCollection collection = db.getDB().getCollection(col);
		DBObject dbObj =  new BasicDBObject(data);
		collection.insert(dbObj);
		return dbObj.get("_id").toString();
	}
	
	public String insertAsBean(String col, Object bean){
		Map<String, Object> map = ReflectUtil.beanToMap(bean);
		return insert(col, map);
	}
	
	public WriteResult insertAsBeans(String col, List<Object> beans){
		return insert(col, toDBList(beans)) ;
	}

	public WriteResult insert(String col, List<DBObject> list) {
		DBCollection collection = db.getDB().getCollection(col);
		return collection.insert(list);
	}

	public WriteResult insert(String col, DBObject... obj) {
		return this.insert(col, Arrays.asList(obj));
	}
	
	 /** 修改*/
	public WriteResult update(String col, DBObject q, DBObject o, boolean upsert, boolean multi) {
		DBCollection collection = db.getDB().getCollection(col);
		return collection.update(q, o, upsert, multi);
	}
	 /** 单条修改*/
	public WriteResult update(String col, DBObject q, DBObject o) {
		return this.update(col, q, o, false, false);
	}
	 /** 单条单列,其他列如果没有赋值不修改*/
	public WriteResult updateSingle(String col, DBObject q, DBObject o) {
		return this.update(col, q, new BasicDBObject("$set",o), false, false);
	}
	 /** 批量修改*/
	public WriteResult updateMulti(String col, DBObject q, DBObject o){
		return this.update(col, q, o, false, true);
	}
	 /** 批量修改 单列,其他列如果没有赋值不修改*/
	public WriteResult updateMultiSingle(String col, DBObject q, DBObject o){
		return this.update(col, q, new BasicDBObject("$set",o), false, true);
	}
	 /** 批量修改 单列,其他列如果没有赋值不修改*/
	public WriteResult updateMulti(String col, Map<String, ? extends Object> q, Map<String, ? extends Object> o){
		return this.update(col, new BasicDBObject(q), new BasicDBObject("$set",o), false, true);
	}
	/** 按id修改, 只修改指定的列*/
	public WriteResult updateByidAsBean(String col, String id, Object bean){
		DBObject _id =new BasicDBObject("_id", new ObjectId(id));
		Map<String, Object> map = ReflectUtil.beanToMap(bean);
		if(map.containsKey("_id")){
			map.remove("_id");
		}
		return this.updateMultiSingle(col, _id, new BasicDBObject(map));
	}
	
	public WriteResult updateByid(String col, String id, DBObject o){
		DBObject _id =new BasicDBObject("_id", new ObjectId(id));
		BasicDBObject ob = (BasicDBObject)o;
		if(ob.containsField("_id")){
			ob.remove("_id");
		}
		return this.updateMultiSingle(col, _id, o);
	}
	
	
	/**
	 * 删除
	 * @param col
	 * @param o
	 * @return
	 */
	public WriteResult remove(String col, DBObject o){
		DBCollection collection = db.getDB().getCollection(col);
		return collection.remove(o);
	}
	public WriteResult deleteById(String col, String id) {
		return remove(col, new BasicDBObject("_id", new ObjectId(id)));
	}
	public WriteResult delete(String col, String query) {
		return remove(col, (DBObject)JSON.parse(query));
	}
	public WriteResult delete(String col, Map<String,? extends Object> query) {
		return remove(col, new BasicDBObject(query));
	}
	
	
	
	
	/**
	 * 查找
	 * @param col
	 * @param o
	 * @return
	 */
	public DBObject findOne(String col, DBObject o){
		DBCollection collection = db.getDB().getCollection(col);
		return collection.findOne(o);
	}
	
	public DBCursor find(String col, DBObject o){
		DBCollection collection = db.getDB().getCollection(col);
		return collection.find(o);
	}
	
	public DBCursor find(String col, DBObject ref, DBObject keys){
		DBCollection collection = db.getDB().getCollection(col);
		return collection.find(ref, keys);
	}
	
	public List<DBObject> DBCursor2list(DBCursor cur) {
		if (cur != null) {
			return  cur.toArray();
		}
		return Collections.emptyList();
	}
	
	public List<DBObject> findList(String col, DBObject query){
		DBCursor cursor = find(col, query);
		return DBCursor2list(cursor);
	}
	
	public List<DBObject> findList(String col, DBObject query, DBObject sort){
		DBCursor cursor = find(col, query);
		if(sort != null){
			cursor.sort(sort);
		}
		return DBCursor2list(cursor);
	}
	
	public List<DBObject> findList(String col, DBObject query, DBObject sort, int start, int limit){ 
		DBCursor cursor = find(col, query);
		if(sort != null){
			cursor.sort(sort);
		}
		if (start == 0) {
			cursor.batchSize(limit);
		} else {
			cursor.skip(start).limit(limit);
		}
		return DBCursor2list(cursor);
	}
	
	public DBObject queryById(String col, String id) {
		DBObject obj = new BasicDBObject();
		obj.put("_id", new ObjectId(id));
		return findOne(col, obj);
	}
	
	public <T> T getBeanById(String col, String id, Class<T> clazz) {
		BasicDBObject dbObject = (BasicDBObject) queryById(col, id);
		if(dbObject == null) return null;
		return ReflectUtil.mapToBean(dbObject, clazz);
	}
	
	public <T> T getBean(String col, DBObject query, Class<T> clazz) {
		BasicDBObject dbObject = (BasicDBObject) findOne(col, query);
		if(dbObject == null) return null;
		return ReflectUtil.mapToBean(dbObject, clazz);
	}

	public <T> List<T> getList(String col, DBObject query, Class<T> clazz) {
		List<DBObject> list =  findList(col, query);
		return toBeans(list, clazz);
	}
	
	public <T> List<T> getList(String col, DBObject query, DBObject sort,Class<T> clazz) {
		List<DBObject> list =  findList(col, query, sort);
		return toBeans(list, clazz);
	}
	
	public <T> List<T> getList(String col, DBObject query, DBObject sort,int start, int limit, Class<T> clazz) {
		List<DBObject> list =  findList(col, query, sort, start, limit);
		return toBeans(list, clazz);
	}

	public long count(String col, DBObject query){
		DBCollection collection = db.getDB().getCollection(col);
		return collection.count(query);
	}
	
	/*public DBObject group()*/
	
	/*public MapReduceOutput mapReduce*/
	
	/*public List distinct*/
	
	
	public List<DBObject> toDBList(List<Object> beans){
		List<DBObject> list = new ArrayList<DBObject>();
		for(Object bean : beans){
			Map<String, Object> map = ReflectUtil.beanToMap(bean);
			list.add(new BasicDBObject(map));
		}
		return list;
	}
	
	public <T> List<T> toBeans(List<DBObject> list, Class<T> clazz){
		List<T> relist = new ArrayList<T>();
		for(DBObject dbObj: list){
			BasicDBObject b = (BasicDBObject)dbObj;
			T t = ReflectUtil.mapToBean(b, clazz);
			relist.add(t);
		}
		return relist;
	}

}
