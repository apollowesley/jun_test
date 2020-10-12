package tom.db.mongo;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.bson.types.ObjectId;

import tom.kit.clazz.ReflectUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

/**
 * http://api.mongodb.org/java/2.11.3/ mongodb 基本操作
 * 
 * @author panmg
 */
public class MongoDao {

	public static MongoDB db;
	
	private static MongoDao self;

	public static void init(File file) {
		db = new MongoDB(file);
	}
	
	public static void init(Properties pro) {
		db = new MongoDB();
		db.init(pro);
	}
	
	public static MongoDao getInstance(){
		if(self == null){
			self = new MongoDao();
		}
		return self;
	}

	public MongoDao() {
	}

	public void set_DB(String _db) {
		db.setDb(_db);
	}
	
	public DBCollection getConllection(String col) {
		return db.getDB().getCollection(col);
	}
	
	/**
	 * 新增
	 * 
	 * @param col
	 * @param data
	 * @return
	 */
	public String insert(String col, Map<String, ? extends Object> data) {
		DBCollection collection = getConllection(col);
		DBObject dbObj = new BasicDBObject(data);
		collection.insert(dbObj, WriteConcern.SAFE);
		return dbObj.get("_id").toString();
	}

	public String insertAsBean(String col, Object bean) {
		Map<String, Object> map = beanToMap(bean);
		return insert(col, map);
	}

	public WriteResult insertAsBeans(String col, List<Object> beans) {
		return insert(col, toDBList(beans));
	}

	public WriteResult insert(String col, List<DBObject> list) {
		DBCollection collection = getConllection(col);
		return collection.insert(list);
	}

	public WriteResult insert(String col, DBObject... obj) {
		return this.insert(col, Arrays.asList(obj));
	}

	public String save(String col, DBObject obj) {
		DBCollection collection = getConllection(col);
		collection.save(obj);
		return obj.get("_id").toString();
	}

	/** 修改 */
	public WriteResult update(String col, DBObject q, DBObject o, boolean upsert, boolean multi) {
		DBCollection collection = getConllection(col);
		return collection.update(q, o, upsert, multi);
	}

	/** 单条修改 */
	public WriteResult update(String col, DBObject q, DBObject o) {
		return this.update(col, q, o, false, false);
	}

	/** 单条单列,其他列如果没有赋值不修改 */
	public WriteResult updateSingle(String col, DBObject q, DBObject o) {
		return this.update(col, q, new BasicDBObject("$set", o), false, false);
	}

	/** 批量修改 */
	public WriteResult updateMulti(String col, DBObject q, DBObject o) {
		return this.update(col, q, o, false, true);
	}

	/** 批量修改 单列,其他列如果没有赋值不修改 */
	public WriteResult updateMultiSingle(String col, DBObject q, DBObject o) {
		return this.update(col, q, new BasicDBObject("$set", o), false, true);
	}

	/** 批量修改 单列,其他列如果没有赋值不修改 */
	public WriteResult updateMulti(String col, Map<String, ? extends Object> q, Map<String, ? extends Object> o) {
		return this.update(col, new BasicDBObject(q), new BasicDBObject("$set", o), false, true);
	}

	/** 按id修改, 只修改指定的列 */
	public WriteResult updateByidAsBean(String col, String id, Object bean) {
		DBObject _id = new BasicDBObject("_id", new ObjectId(id));
		Map<String, Object> map = beanToMap(bean);
		if (map.containsKey("_id")) {
			map.remove("_id");
		}
		return this.updateMultiSingle(col, _id, new BasicDBObject(map));
	}

	public WriteResult updateByid(String col, String id, DBObject o) {
		DBObject _id = new BasicDBObject("_id", new ObjectId(id));
		BasicDBObject ob = (BasicDBObject) o;
		if (ob.containsField("_id")) {
			ob.remove("_id");
		}
		return this.updateMultiSingle(col, _id, o);
	}

	/**
	 * 删除
	 * 
	 * @param col
	 * @param o
	 * @return
	 */
	public WriteResult remove(String col, DBObject o) {
		DBCollection collection = getConllection(col);
		return collection.remove(o);
	}

	public WriteResult deleteById(String col, String id) {
		return remove(col, new BasicDBObject("_id", new ObjectId(id)));
	}

	public WriteResult delete(String col, String query) {
		return remove(col, (DBObject) JSON.parse(query));
	}

	public WriteResult delete(String col, Map<String, ? extends Object> query) {
		return remove(col, new BasicDBObject(query));
	}

	/**
	 * 查找
	 * 
	 * @param col
	 * @param o
	 * @return
	 */
	public List<?> distinct(String col, String key, DBObject query) {
		DBCollection collection = getConllection(col);
		return collection.distinct(key, query);
	}

	public DBObject findOne(String col, DBObject o) {
		DBCollection collection = getConllection(col);
		return collection.findOne(o);
	}

	private DBCursor find(String col, DBObject o) {
		DBCollection collection = getConllection(col);
		return collection.find(o);
	}

	private DBCursor find(String col, DBObject query, DBObject keys) {
		DBCollection collection = getConllection(col);
		return collection.find(query, keys);
	}

	public DBObject findOne(String col, DBObject query, DBObject keys) {
		DBCollection collection = getConllection(col);
		List<DBObject> list = DBCursor2list(collection.find(query, keys));
		if (list.size() > 0) {
			return list.get(0);
		}
		return new BasicDBObject(0);
	}

	public List<DBObject> DBCursor2list(DBCursor cur) {
		if (cur != null) {
			ArrayList<DBObject> list = new ArrayList<DBObject>();
			while (cur.hasNext()) {
				DBObject dbo = cur.next();
				if (dbo.get("_id") != null) {
					dbo.put("_id", dbo.get("_id").toString());
				}
				list.add(dbo);
			}
			return list;
		}
		return Collections.emptyList();
	}

	public List<DBObject> findList(String col, DBObject query) {
		DBCursor cursor = find(col, query);
		return DBCursor2list(cursor);
	}

	public List<DBObject> findOneList(String col, DBObject query, DBObject key) {
		DBCursor cursor = find(col, query, key);
		return DBCursor2list(cursor);
	}

	public List<DBObject> findList(String col, DBObject query, DBObject sort) {
		DBCursor cursor = find(col, query);
		if (sort != null) {
			cursor.sort(sort);
		}
		return DBCursor2list(cursor);
	}

	public List<DBObject> findList(String col, DBObject query, DBObject sort, int start, int limit) {
		DBCursor cursor = find(col, query);
		if (sort != null) {
			cursor.sort(sort);
		}
		if (start == 0) {
			cursor.limit(limit);
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
		if (dbObject == null)
			return null;
		return mapToBean(dbObject, clazz);
	}

	public <T> T getBean(String col, DBObject query, Class<T> clazz) {
		BasicDBObject dbObject = (BasicDBObject) findOne(col, query);
		if (dbObject == null)
			return null;
		return mapToBean(dbObject, clazz);
	}

	public <T> List<T> getList(String col, DBObject query, Class<T> clazz) {
		List<DBObject> list = findList(col, query);
		return toBeans(list, clazz);
	}

	public <T> List<T> getList(String col, DBObject query, DBObject sort, Class<T> clazz) {
		List<DBObject> list = findList(col, query, sort);
		return toBeans(list, clazz);
	}

	public <T> List<T> getList(String col, DBObject query, DBObject sort, int start, int limit, Class<T> clazz) {
		List<DBObject> list = findList(col, query, sort, start, limit);
		return toBeans(list, clazz);
	}

	public long count(String col, DBObject query) {
		DBCollection collection = getConllection(col);
		return collection.count(query);
	}

	/* public DBObject group() */

	/* public MapReduceOutput mapReduce */

	/* public List distinct */

	public List<DBObject> toDBList(List<Object> beans) {
		List<DBObject> list = new ArrayList<DBObject>();
		for (Object bean : beans) {
			Map<String, Object> map = beanToMap(bean);
			list.add(new BasicDBObject(map));
		}
		return list;
	}

	public <T> List<T> toBeans(List<DBObject> list, Class<T> clazz) {
		List<T> relist = new ArrayList<T>();
		for (DBObject dbObj : list) {
			BasicDBObject b = (BasicDBObject) dbObj;
			T t = mapToBean(b, clazz);
			relist.add(t);
		}
		return relist;
	}
	
	public static Map<String, Object> beanToMap(Object bean) {
		BeanInfo info = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> _class = bean.getClass();
		try {
			info = Introspector.getBeanInfo(_class);
		} catch (IntrospectionException e) {
			e.printStackTrace();
			return null;
		}
		PropertyDescriptor[] pros = info.getPropertyDescriptors();
		for (PropertyDescriptor pro : pros) {

			String name = pro.getName();
			if (!"class".equals(name)) {
				Method method = pro.getReadMethod();
				try {
					tom.db.mongo.Annotation.ObjectId objectId = _class.getDeclaredField(name).getAnnotation(tom.db.mongo.Annotation.ObjectId.class);
					Object result = method.invoke(bean);
					if (objectId != null && result != null) {
						result = new ObjectId((String) result);
					}
					if (result != null) {
						map.put(name, result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	 
	 
	 public static <T> T mapToBean(Map<String, ?> map, Class<T> _class) {
			T t;
			try {
				t = _class.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			for (String key : map.keySet()) {
				Object value = map.get(key);
				String methodName = ReflectUtil.toSetMethod(key);
				Method method = ReflectUtil.getDeclaredMethod(_class, methodName);
				ReflectUtil.invokeMethodByConvert(t, method, value);
			}
			return t;
		}
}
