package cn.uncode.dal.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.uncode.dal.core.AbstractMongoDAL;
import cn.uncode.dal.criteria.Criterion;
import cn.uncode.dal.criteria.QueryCriteria;
import cn.uncode.dal.criteria.QueryCriteria.Criteria;
import cn.uncode.dal.descriptor.MapReduceOutputType;
import cn.uncode.dal.descriptor.Table;
import cn.uncode.dal.utils.JsonUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;

public class Mongo3DAL extends AbstractMongoDAL implements cn.uncode.dal.core.MongoDAL {
	
	private static final Logger LOG = LoggerFactory.getLogger(Mongo3DAL.class);
	
	private MongoDB database;
	DBObject query;
	@Override
	public List<Map<String, Object>> _selectByCriteria(Table table) {
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> coditon = new HashMap<String, Object>();
		try {
			QueryCriteria queryCriteria = table.getQueryCriteria();
			com.mongodb.client.MongoDatabase db = database.getMongoDB();
			for(Criteria criteria:queryCriteria.getOredCriteria()){
				for(Criterion criterion:criteria.getAllCriteria()){
					coditon = buildCriteria(criterion, coditon);
				}
			}
			FindIterable<Document> findIterable = db.getCollection(queryCriteria.getTable()).find(Document.parse((JSON.serialize(coditon))));
			LOG.debug("selectByCriteria->collection:"+queryCriteria.getTable()+",script:"+JSON.serialize(coditon));
		    if(StringUtils.isNotBlank(queryCriteria.getOrderByClause())){
		    	findIterable.sort(Document.parse(queryCriteria.getOrderByClause()));
		    }
		    if(queryCriteria.getSelectOne()){
		    	findIterable.skip(0);
		    	findIterable.limit(1);
		    }else{
		    	if(queryCriteria.getLimit()>0){
		    		findIterable.skip(queryCriteria.getRecordIndex());
			    	findIterable.limit(queryCriteria.getLimit());
		    	}else if(queryCriteria.getPageIndex() >= 0){
		        	int pageSize = 20, pageIndex = 1;
		        	if(queryCriteria.getPageSize() > 0){
		        		pageSize = queryCriteria.getPageSize();
		        	}
		        	if(queryCriteria.getPageIndex() > 1){
		        		pageIndex = queryCriteria.getPageIndex();
		        	}
		        	findIterable.skip((pageIndex - 1) * pageSize);
			    	findIterable.limit(pageSize);
		        }
		    }
		    StringBuffer sb = new StringBuffer();
		    if(null != table.getParams()){
		    	for(String fd : table.getParams().keySet()){
		        	sb.append(fd).append(",");
		        }
		    }
		    if(sb.length() > 0){
		    	findIterable.projection(Document.parse(JSON.serialize(table.getParams())));
		    }
		    if(findIterable != null){
		    	findIterable.forEach(new Block<Document>() {
					public void apply(Document document) {
						try {
							Map<String, Object> item = JsonUtils.fromJson(document.toJson(), Map.class);
							Map<String, String> idmap = (Map<String, String>) item.get("_id");
							item.put("id", idmap.get("$oid"));
							result.add(item);
						} catch (Exception e) {
							LOG.error(e.getMessage());
						}
					}
				});
		    }
		} catch (MongoException e) {
			LOG.error("mongo find error", e);
		}
		LOG.debug("selectByCriteria->result:"+result.toString());
		return result;
	}
	
	@Override
	public List<Map<String, Object>> _selectGroupByCriteria(Table table) {
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> coditon = new HashMap<String, Object>();
		try {
			QueryCriteria queryCriteria = table.getQueryCriteria();
			com.mongodb.client.MongoDatabase db = database.getMongoDB();
			for(Criteria criteria:queryCriteria.getOredCriteria()){
				for(Criterion criterion:criteria.getAllCriteria()){
					coditon = buildCriteria(criterion, coditon);
				}
			}
			MongoCollection<Document> md=db.getCollection(queryCriteria.getTable());
			List<Bson> list=new ArrayList<Bson>();
			list.add(Document.parse("{$match:"+JSON.serialize(coditon)+"}"));
			LOG.debug("selectGroupByCriteria->collection:"+queryCriteria.getTable()+",script:"+JSON.serialize(coditon));
		    if(StringUtils.isNotBlank(queryCriteria.getOrderByClause())){
		    	list.add(Document.parse("{$sort:"+queryCriteria.getOrderByClause()+"}"));
		    }
		    if(queryCriteria.getSelectOne()){
		  	    list.add(Document.parse("{$skip:0}"));
			    list.add(Document.parse("{$limit:1}"));
		    }else{
		    	if(queryCriteria.getPageIndex() >= 0){
		        	int pageSize = 20, pageIndex = 1;
		        	if(queryCriteria.getPageSize() > 0){
		        		pageSize = queryCriteria.getPageSize();
		        	}
		        	if(queryCriteria.getPageIndex() > 1){
		        		pageIndex = queryCriteria.getPageIndex();
		        	}
		        	int skip=(pageIndex - 1) * pageSize;
		        	list.add(Document.parse("{$skip:"+skip+"}"));
					list.add(Document.parse("{$limit:"+pageSize+"}"));
		        }
		    }
		    String groupBy=queryCriteria.getGroupBy();
		    list.add(Document.parse("{$group:{_id:'$"+groupBy+"'}}"));
			list.add(Document.parse("{$project:{"+groupBy+":'$_id',_id:0}}"));
		    AggregateIterable<Document> a= md.aggregate(list);
		    MongoCursor<Document> c=a.iterator();
		    while(c.hasNext()){
				Map<String, Object> item = JsonUtils.fromJson(c.next().toJson(), Map.class);
				Object object=item.get(groupBy);
				if(object instanceof Map){
					Map<String, String> map = (Map<String, String>)object;
					if(map.containsKey("$numberLong")){
						item.put(groupBy, map.get("$numberLong"));
					}
				}
				result.add(item);
		    }
		} catch (MongoException e) {
			LOG.error("mongo find error", e);
		}
		LOG.debug("selectGroupByCriteria->result:"+result.toString());
		return result;
	}

	@Override
	public int _countByCriteria(Table table) {
		int count = 0;
		Map<String, Object> coditon = new HashMap<String, Object>();
		try {
			QueryCriteria queryCriteria = table.getQueryCriteria();
			com.mongodb.client.MongoDatabase db = database.getMongoDB();
			for(Criteria criteria:queryCriteria.getOredCriteria()){
				for(Criterion criterion:criteria.getAllCriteria()){
					coditon = buildCriteria(criterion, coditon);
				}
			}
			long size = db.getCollection(queryCriteria.getTable()).count(Document.parse((JSON.serialize(coditon))));
			LOG.debug("countByCriteria->collection:"+queryCriteria.getTable()+",script:"+JSON.serialize(coditon));
			count = (int) size;
		} catch (MongoException e) {
			LOG.error("mongo find error", e);
		}
		LOG.debug("_countByCriteria->result:"+count);
		return count;
	}

	@Override
	public Map<String, Object> _selectByPrimaryKey(Table table) {
		final Map<String, Object> result = new HashMap<String, Object>();
		try {
			com.mongodb.client.MongoDatabase db = database.getMongoDB();
			
			FindIterable<Document> findIterable = null;
			if (table.getConditions().containsKey("_id")) {
                findIterable = db.getCollection(table.getTableName()).find(eq("_id", new ObjectId(String.valueOf(table.getConditions().get("_id")))));
                LOG.debug("selectByPrimaryKey->collection:"+table.getTableName()+",script:"+eq("_id", new ObjectId(String.valueOf(table.getConditions().get("_id")))).toString());
            } else {
            	findIterable = db.getCollection(table.getTableName()).find(Document.parse(JSON.serialize(table.getConditions())));
            	LOG.debug("selectByPrimaryKey->collection:"+table.getTableName()+",script:"+JSON.serialize(table.getConditions()));
            }
			if(findIterable != null){
		    	findIterable.forEach(new Block<Document>() {
					public void apply(Document document) {
						try {
							Map<String, Object> item = JsonUtils.fromJson(document.toJson(), Map.class);
							Map<String, String> idmap = (Map<String, String>) item.get("_id");
							item.put("id", idmap.get("$oid"));
							result.putAll(item);
						} catch (Exception e) {
							LOG.error(e.getMessage());
						}
					}
				});
		    }
		} catch (MongoException e) {
			LOG.error("mongo findOne error", e);
		}
		LOG.debug("selectByPrimaryKey->result:"+result.toString());
		return result;
	}

	@Override
	public int _insert(Table table) {
		try {
			com.mongodb.client.MongoDatabase db = database.getMongoDB();
			ObjectId newOid = ObjectId.get();
			table.getParams().put("_id", newOid);
			db.getCollection(table.getTableName()).insertOne(Document.parse(JSON.serialize(table.getParams())));
			table.getParams().put("id", newOid.toString());
			LOG.debug("insert->collection:"+table.getTableName()+",script:"+JSON.serialize(table.getParams()));
		} catch (MongoException e) {
			LOG.error("mongo insert error", e);
		}
		return 1;
	}

	@Override
	public int _updateByCriteria(Table table) {
		int count = 0;
		Map<String, Object> coditon = new HashMap<String, Object>();
		try {
	        QueryCriteria queryCriteria = table.getQueryCriteria();
	        com.mongodb.client.MongoDatabase db = database.getMongoDB();
			for(Criteria criteria:queryCriteria.getOredCriteria()){
				for(Criterion criterion:criteria.getAllCriteria()){
					coditon = buildCriteria(criterion, coditon);
				}
			}
			Map<String, Object> vaule = new HashMap<String, Object>();
			vaule.put("$set", table.getParams());
			UpdateResult result = db.getCollection(queryCriteria.getTable()).updateMany(Document.parse((JSON.serialize(coditon))), Document.parse(JSON.serialize(vaule)));
			if(result != null){
				count = (int) result.getModifiedCount();
			}
			LOG.debug("updateByCriteria->collection:"+table.getTableName()+",value:"+JSON.serialize(vaule)+",condition:"+JSON.serialize(coditon));
		} catch (MongoException e) {
			LOG.error("mongo update error", e);
		}
		return count;
	}

	@Override
	public int _updateByPrimaryKey(Table table) {
		try {
			if(table.getConditions().containsKey("_id")){
				com.mongodb.client.MongoDatabase db = database.getMongoDB();
				Object id = table.getConditions().get("_id");
				table.getParams().remove("id");//id被永久屏蔽
				Map<String, Object> vaule = new HashMap<String, Object>();
				vaule.put("$set", table.getParams());
				db.getCollection(table.getTableName()).updateOne(eq("_id", new ObjectId(String.valueOf(id))), Document.parse(JSON.serialize(vaule)));
				LOG.debug("updateByPrimaryKey->collection:"+table.getTableName()+",value:"+JSON.serialize(vaule)+",condition:"+eq("_id", new ObjectId(String.valueOf(id)).toString()));
				return 1;
			}
		} catch (MongoException e) {
			LOG.error("mongo update error", e);
		}
		return 0;
	}

	@Override
	public int _deleteByPrimaryKey(Table table) {
		try {
			com.mongodb.client.MongoDatabase db = database.getMongoDB();
			if(table.getConditions().containsKey("_id")){
				db.getCollection(table.getTableName()).deleteOne(eq("_id", new ObjectId(String.valueOf(table.getConditions().get("_id")))));
				LOG.debug("deleteByPrimaryKey->collection:"+table.getTableName()+",condition:"+eq("_id", new ObjectId(String.valueOf(table.getConditions().get("_id"))).toString()));
			}
		} catch (MongoException e) {
			LOG.error("mongo findOne error", e);
		}
		return 1;
	}

	@Override
	public int _deleteByCriteria(Table table) {
		Map<String, Object> coditon = new HashMap<String, Object>();
		int count = 0;
		try {
	        QueryCriteria queryCriteria = table.getQueryCriteria();
	        com.mongodb.client.MongoDatabase db = database.getMongoDB();
			for(Criteria criteria:queryCriteria.getOredCriteria()){
				for(Criterion criterion:criteria.getAllCriteria()){
					coditon = buildCriteria(criterion, coditon);
				}
			}
			DeleteResult result = db.getCollection(queryCriteria.getTable()).deleteMany(Document.parse((JSON.serialize(coditon))));
			if(result != null){
				count = (int) result.getDeletedCount();
			}
			LOG.debug("deleteByCriteria->collection:"+table.getTableName()+",condition:"+JSON.serialize(coditon));
		} catch (MongoException e) {
			LOG.error("mongo delete error", e);
		}
		return count;
	}

	public void setDatabase(MongoDB database) {
		this.database = database;
	}
	
	

	private Map<String, Object> buildCriteria(Criterion criterion, Map<String, Object> valueMap) {
		Object cdObj=valueMap.get(criterion.getColumn());
		Map<String, Object> cd = null;
		if(null!=cdObj){
			cd=(Map<String, Object>)cdObj;
		}else{
			cd=new HashMap<String, Object>();
		}
        if(Criterion.Condition.IS_NULL == criterion.getCondition()){
        	cd.put(MongoCondition.IS_NULL, false);
        	valueMap.put(criterion.getColumn(), cd);
        }else if(Criterion.Condition.IS_NOT_NULL == criterion.getCondition()){
        	cd.put(MongoCondition.IS_NOT_NULL, true);
        	valueMap.put(criterion.getColumn(), cd);
        }else if(Criterion.Condition.EQUAL == criterion.getCondition()){
        	valueMap.put(criterion.getColumn(), criterion.getValue());
        }else if(Criterion.Condition.NOT_EQUAL == criterion.getCondition()){
        	cd.put(MongoCondition.NOT_EQUAL, criterion.getValue());
        	valueMap.put(criterion.getColumn(), cd);
        }else if(Criterion.Condition.GREATER_THAN == criterion.getCondition()){
        	cd.put(MongoCondition.GREATER_THAN, criterion.getValue());
        	valueMap.put(criterion.getColumn(), cd);
        }else if(Criterion.Condition.GREATER_THAN_OR_EQUAL == criterion.getCondition()){
        	cd.put(MongoCondition.GREATER_THAN_OR_EQUAL, criterion.getValue());
        	valueMap.put(criterion.getColumn(), cd);
        }else if(Criterion.Condition.LESS_THAN == criterion.getCondition()){
        	cd.put(MongoCondition.LESS_THAN, criterion.getValue());
        	valueMap.put(criterion.getColumn(), cd);
        }else if(Criterion.Condition.LESS_THAN_OR_EQUAL == criterion.getCondition()){
        	cd.put(MongoCondition.LESS_THAN_OR_EQUAL, criterion.getValue());
        	valueMap.put(criterion.getColumn(), cd);
        }else if(Criterion.Condition.IN == criterion.getCondition()){
            List<Object> values = (List<Object>) criterion.getValue();
            if(values!= null && values.size() > 0){
            	cd.put(MongoCondition.IN, values);
            	valueMap.put(criterion.getColumn(), cd);
            }
        }else if(Criterion.Condition.NOT_IN == criterion.getCondition()){
        	List<Object> values = (List<Object>) criterion.getValue();
			if(values!= null && values.size() > 0){
            	cd.put(MongoCondition.NOT_IN, values);
            	valueMap.put(criterion.getColumn(), cd);
            }
        }else if(Criterion.Condition.BETWEEN == criterion.getCondition()){
        	cd.put(MongoCondition.GREATER_THAN_OR_EQUAL, criterion.getValue());
        	cd.put(MongoCondition.LESS_THAN_OR_EQUAL, criterion.getSecondValue());
        	valueMap.put(criterion.getColumn(), cd);
        }else if(Criterion.Condition.NOT_BETWEEN == criterion.getCondition()){
        	cd.put(MongoCondition.LESS_THAN_OR_EQUAL, criterion.getValue());
        	cd.put(MongoCondition.GREATER_THAN_OR_EQUAL, criterion.getSecondValue());
        	valueMap.put(criterion.getColumn(), cd);
        }else if(Criterion.Condition.LIKE == criterion.getCondition()){
        	cd.put(MongoCondition.LIKE, criterion.getValue());
        	valueMap.put(criterion.getColumn(), cd);
        }else if(Criterion.Condition.NOT_LIKE == criterion.getCondition()){
        	valueMap.put(criterion.getColumn(), criterion.getValue());
        }else{
        	if(StringUtils.isNotEmpty(criterion.getColumn()) && criterion.getValue() == null){
            	Map<String, Object> sqlMap = Document.parse(criterion.getColumn());
            	valueMap.putAll(sqlMap);
        	}
        }
        return valueMap;
	}
	
	public static class MongoCondition{
    	public static final String TAG= "$";
        public static final String IS_NULL = "$exists";
        public static final String IS_NOT_NULL = "$exists";
        public static final String EQUAL = "$eq";
        public static final String NOT_EQUAL = "$ne";
        public static final String GREATER_THAN = "$gt";
        public static final String GREATER_THAN_OR_EQUAL = "$gte";
        public static final String LESS_THAN = "$lt";
        public static final String LESS_THAN_OR_EQUAL = "$lte";
        public static final String LIKE = "$regex";
        public static final String NOT_LIKE = "$nlk";
        public static final String IN = "$in";
        public static final String NOT_IN = "$nin";
        public static final String BETWEEN = "$bt";
        public static final String NOT_BETWEEN = "$nbt";
       
    }


	@Override
	public Object getTemplate() {
		DB db = database.getDB();
		return new Jongo(db);
	}

	@Override
	public void runScript(String script) {
		try {
			com.mongodb.client.MongoDatabase db = database.getMongoDB();
			db.runCommand(buildCommand(script));
		} catch (MongoException e) {
			LOG.error("mongo findOne error", e);
		}
	}

    /**
     * 构建command命令
     *
     * @param script
     * @return
     */
    private Document buildCommand(String script) {
    	DBObject dbObject = BasicDBObjectBuilder.start()
					        .add("$eval", script)
					        .add("nolock", true)
					        .get();
    	
        return Document.parse(JSON.serialize(dbObject));
    }

	@Override
	public Object insertMany(String tableName, List<Object> list) {
		if(null==list || list.size()==0) return 0;
	       List<Document> documentList=new ArrayList<Document>();
		   try {
				com.mongodb.client.MongoDatabase db = database.getMongoDB();
				for(Object obj:list){
					ObjectId newOid = ObjectId.get();
					Map<String, Object> param = (Map<String, Object>) JsonUtils.objToMap(obj);
					param.put("_id", newOid);
					documentList.add(Document.parse(JSON.serialize(param)));
				}
				db.getCollection(tableName).insertMany(documentList);
				LOG.debug("insert->collection:"+tableName+",script:"+JSON.serialize(documentList));
			} catch (MongoException e) {
				LOG.error("mongo insert list error", e);
			}
		   return 1;
	}

	@Override
	public void mapReduce(String sourceTableName,String map, String reduce,String finalize, String outputTarget,MapReduceOutputType outputType,Map<String,Object> query) {
		DB db = database.getDB();
		DBCollection md = db.getCollection(sourceTableName);
		MapReduceCommand.OutputType mapReduceoutputType = null;
		switch (outputType) {
		case REPLACE:
			mapReduceoutputType = MapReduceCommand.OutputType.REPLACE;
			break;
		case MERGE:
			mapReduceoutputType = MapReduceCommand.OutputType.MERGE;
			break;
		case REDUCE:
			mapReduceoutputType = MapReduceCommand.OutputType.REDUCE;
			break;
		case INLINE:
			mapReduceoutputType = MapReduceCommand.OutputType.INLINE;
			break;
		default:
			return;
		}
		BasicDBObject bson = new BasicDBObject();
		bson.putAll(query);
		MapReduceCommand cmd = new MapReduceCommand(md , map, reduce,outputTarget, mapReduceoutputType, bson);
		if(StringUtils.isNotBlank(finalize)){
			cmd.setFinalize(finalize);
		}
		MapReduceOutput out = md.mapReduce(cmd);
	}

}
