package cn.uncode.dal.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.uncode.dal.core.BaseDAL;
import cn.uncode.dal.criteria.QueryCriteria;
import cn.uncode.dal.criteria.QueryCriteria.Criteria;
import cn.uncode.dal.descriptor.QueryResult;
import cn.uncode.dal.utils.JsonUtils;

public class MybatisDALTest {
	
	static ClassPathXmlApplicationContext context;
    
    private static BaseDAL baseDAL;
    
    @Test
    public void testSelectByCriteria(){
		startService();
        QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setTable(User.class);
//        Criteria critera = queryCriteria.createCriteria();
//        critera.andColumnGreaterThan(User.ID, 0);
//        critera.andColumnLessThanOrEqualTo(User.ID, 10000);
        QueryResult result =  baseDAL.selectByCriteria(queryCriteria);
        System.out.println(result.asList(User.class));
    }
    
    @Test
    public void testSelectShardingPageByCriteria(){
		startService();
        QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setTable("express");
        Criteria critera = queryCriteria.createCriteria();
        critera.append("expressnumber", "622001236501");
        QueryResult result =  baseDAL.selectPageByCriteria(queryCriteria);
        System.out.println("结果："+result.getList());
    }
    
    @Test
    public void testSelectByPrimaryKey1(){
    	startService();
		baseDAL = (BaseDAL) context.getBean("baseDAL");
        User user = new User();
        user.setId(1);
        QueryResult result =  baseDAL.selectByPrimaryKey(user);
        user = result.as(User.class);
        System.out.println(user.toString());
    }
    @Test
    public void testSelectByPrimaryKey2(){
    	startService();
		baseDAL = (BaseDAL) context.getBean("baseDAL");
    	QueryResult result =  baseDAL.selectByPrimaryKey("user", 1);
        System.out.println(result.get());
    }
    @Test
    public void testSelectByPrimaryKey3(){
    	startService();
        QueryResult result =  baseDAL.selectByPrimaryKey(User.class, 1);
        System.out.println(result.get());
    }
    
    @Test
    public void testSelectShardingByPrimaryKey(){
    	startService();
        QueryResult result =  baseDAL.selectByPrimaryKey("express", 337857832736773L);
        System.out.println(result.get());
    }
    
    @Test
    public void testInsert1(){
    	startService();
        User user = new User();
        user.setUserName("test001236501");
        Object result = baseDAL.insert(user);
        Long id = (Long)result;
        System.out.println(result);
    }
    
    @Test
    public void testInsert2(){
    	startService();
    	Map<String, Object> content = new HashMap<String, Object>();
    	content.put("nickname", "test001236501");
    	Object result = baseDAL.insert("user", content);
        System.out.println(result);
    }
    
   
    
   
    
    @Test
    public void testDeleteByPrimaryKey1(){
    	startService();
        User user = new User();
        user.setId(165);
        int result = baseDAL.deleteByPrimaryKey(user);
        System.out.println(result);
    }
    
    @Test
    public void testDeleteByPrimaryKey2(){
    	startService();
        int result = baseDAL.deleteByPrimaryKey(User.class, 1);
        System.out.println(result);
    }
    
    @Test
    public void testDeleteByPrimaryKey3(){
    	startService();
        int result = baseDAL.deleteByPrimaryKey("user", 1);
        System.out.println(result);
    }
    
    @Test
    public void testDeleteByPrimaryKey4(){
    	startService();
        int result = baseDAL.deleteByPrimaryKey("express", 165);
        System.out.println(result);
    }
    
    
    @Test
    public void testDeleteShardingByPrimaryKey(){
    	startService();
        int result = baseDAL.deleteByPrimaryKey("express", 337844626686167L);
        System.out.println(result);
    }
    
    @Test
    public void testDeleteByCriteria(){
    	startService();
        QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setTable(User.class);
        Criteria critera = queryCriteria.createCriteria();
        critera.andColumnEqualTo(User.USER_NAME, "test001236501");
        int result = baseDAL.deleteByCriteria(queryCriteria);
        System.out.println(result);
    }
    
    @Test
    public void testUpdateByCriteria(){
    	startService();
        User user = new User();
        user.setUserName("test6@xiaocong.tv");
        QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setTable(User.class);
        Criteria critera = queryCriteria.createCriteria();
        critera.andColumnEqualTo(User.USER_NAME, "test001236501");
        int result = baseDAL.updateByCriteria(user, queryCriteria);
        System.out.println(result);
    }
    
    @Test
    public void testUpdateByPrimaryKey(){
    	startService();
        User user = new User();
        user.setUserName("test@xiaocong.tv");
        user.setId(1);
        int result = baseDAL.updateByPrimaryKey(user);
        System.out.println(result);
    }
    
    @Test
    public void testUpdateByPrimaryKey2(){
    	startService();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 163);
        map.put("user_name", "test@xiaocong.tv");
        int result = baseDAL.updateByPrimaryKey("tb_user",map);
        System.out.println(result);
    }
    
    @Test
    public void testUpdateShardingByPrimaryKey(){
    	startService();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 337847163399839L);
        map.put("sender", "test@xiaocong.tv");
        int result = baseDAL.updateByPrimaryKey("express",map);
        System.out.println(result);
    }
    
    @Test
    public void testMapToBean(){
    	startService();
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("usErnaMe", "123");
    	map.put("pwd", "333333333");
    	User user = JsonUtils.mapToObj(map, User.class);
        //System.out.println(user.getUserName()+"=="+user.getPwd());
    }
    
    @Test
    public void testInsertSharding(){
    	startService();
    	Map<String, Object> content = new HashMap<String, Object>();
    	content.put("expressnumber", "622001236501");
    	Object result = baseDAL.insert("express", content);
        System.out.println(result);
    }
    
    
	public static void startService(){
		try {
			context = new ClassPathXmlApplicationContext(new String[] { "application.xml"});
			context.start();
			baseDAL = (BaseDAL) context.getBean("baseDAL");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
