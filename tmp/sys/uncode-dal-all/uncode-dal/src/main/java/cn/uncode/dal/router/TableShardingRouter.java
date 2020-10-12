package cn.uncode.dal.router;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import cn.uncode.dal.criteria.Criterion;
import cn.uncode.dal.criteria.Model;
import cn.uncode.dal.criteria.QueryCriteria;
import cn.uncode.dal.criteria.QueryCriteria.Criteria;
import cn.uncode.dal.utils.IDGenerateUtils;

public class TableShardingRouter {
	
	
    private static final ConcurrentMap<String, TableRouter> cache = new ConcurrentHashMap<String, TableRouter>();
    
    public static void setTableRouter(String table, TableRouter tableRouter){
    	cache.putIfAbsent(table, tableRouter);
    }
    
    public static boolean containsTable(String table){
    	return cache.containsKey(table);
    }
    
    public static String getShardingTableById(String table, long id){
    	TableRouter tableRouter = cache.get(table);
    	String tableName = null;
    	if(null != tableRouter){
    		for(Range range:tableRouter.getRanges()){
	    		if(SharingType.RANGE.equals(tableRouter.getSharingType())){
    				if(range.getStart()<=id && id <= range.getEnd()){
    					tableName = range.getTableName();
    					break;
    				}
	    		}else if(SharingType.HASH.equals(tableRouter.getSharingType())){
	    			long hash = (id % 10000000l) / 10000;
	    			if(range.getHashs().contains(String.valueOf(hash))){
	    				tableName = range.getTableName();
    					break;
	    			}
	    		}
    		}
    	}
    	return tableName;
    }
    
    public static List<Range> getShardingTables(QueryCriteria queryCriteria){
    	TableRouter tableRouter = cache.get(queryCriteria.getTable());
    	List<Range> ranges = null;
    	if(null != tableRouter){
    		if(SharingType.RANGE.equals(tableRouter.getSharingType())){
    			ranges = tableRouter.getRanges();
    		}else if(SharingType.HASH.equals(tableRouter.getSharingType())){
    			List<String> hashs = new ArrayList<String>();
    			if(queryCriteria.getOredCriteria() != null && queryCriteria.getOredCriteria().size() > 0){
    				for(Criteria criteria:queryCriteria.getOredCriteria()){
    					for(Criterion criterion:criteria.getCriteria()){
    						if(criterion.getColumn().equals(tableRouter.getFieldName())){
    							hashs.add(String.valueOf(criterion.getValue()));
    						}
    					}
    				}
    			}
    			ranges = new ArrayList<Range>();
    			if(hashs.size() > 0){
    				for(String hash:hashs){
    					for(Range range:tableRouter.getRanges()){
    						if(range.getHashs().contains(hash)){
    							ranges.add(range);
    						}
    					}
    				}
    			}else{
    				ranges = tableRouter.getRanges();
    			}
    		}
    	}
    	return ranges;
    }
    
    public static long generateShardingId(Model model){
    	TableRouter tableRouter = cache.get(model.getTableName());
    	if(null != tableRouter){
    		if(SharingType.RANGE.equals(tableRouter.getSharingType())){
    			return IDGenerateUtils.getId();
    		}else if(SharingType.HASH.equals(tableRouter.getSharingType())){
    			if(model.getContent() == null || !model.getContent().containsKey(tableRouter.getFieldName())){
    				throw new RuntimeException("散列依据的字段不能为空");
    			}
    			Object hashVal = model.getContent().get(tableRouter.getFieldName());
    			return IDGenerateUtils.getId(Integer.valueOf(String.valueOf(hashVal)));
    		}
    	}
    	return IDGenerateUtils.getId();
    }

}
