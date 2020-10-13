package com.gitee.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.gitee.app.dao.TUserDao;
import com.gitee.app.entity.TUser;

import net.oschina.durcframework.easymybatis.PageInfo;
import net.oschina.durcframework.easymybatis.query.Column;
import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Sort;
import net.oschina.durcframework.easymybatis.query.expression.ValueExpression;
import net.oschina.durcframework.easymybatis.query.projection.ProjectionQuery;
import net.oschina.durcframework.easymybatis.query.projection.Projections;
import net.oschina.durcframework.easymybatis.util.QueryUtils;

public class TUserDaoTest extends TestBase {

	@Autowired
	TUserDao dao;
	@Resource
	TransactionTemplate transactionTemplate;

	@Test
	public void testGet() {
		TUser user = dao.get(1);
		print(user);
	}

	// 根据字段查询一条记录
	@Test
	public void testGetByProperty() {
		TUser user = dao.getByProperty("username", "王五");
		print(user);
	}

	// 根据条件查询一条记录
	@Test
	public void testGetByExpression() {
		// 查询ID=3的用户
		Query query = Query.build().eq("id", 3);

		TUser user = dao.getByExpression(query);

		print(user);
	}

	// 条件查询
	// 查询username='张三'的用户
	@Test
	public void testFind() {
		Query query = new Query();
		// 添加查询条件
		query.eq("username", "张三");

		List<TUser> list = dao.find(query); // 获取结果集
		long count = dao.countTotal(query); // 获取总数
		print("count:" + count);
		for (TUser user : list) {
			System.out.println(user);
		}
	}
	
	@Test
	public void testPage() {
		Query query = new Query();
		
		query.setPage(1, 5) // 设置pageIndex，pageSize
			.addSort("id", Sort.ASC); // 添加排序
		
		// 查询后的结果，包含总记录数，结果集，总页数等信息
		PageInfo<TUser> pageInfo = QueryUtils.query(dao, query);
		
		List<TUser> rows = pageInfo.getList();
		for (TUser user : rows) {
			System.out.println(user);
		}
	}
	
	// 自定义返回字段查询，只返回两个字段
	// SELECT t.id,t.username FROM `t_user` t LIMIT 0,10
	@Test
	public void testSelfColumns() {
		Query query = new Query();
		// 只返回id,username
		query.setColumns(Arrays.asList("t.id","t.username"));
		
		List<TUser> list = dao.find(query);
		
		for (TUser user : list) {
			System.out.println(user);
		}
	}
	
	/* 
	 *  生成如下sql:
		SELECT 
			t.`id` , t.`username` , t.`state` , t.`isdel` , t.`remark` , t.`add_time` , t.`money` , t.`left_money` 
			, t2.city , t2.address 
		FROM `t_user` t LEFT JOIN user_info t2 ON t.id = t2.user_id 
		WHERE t2.id = ?
		ORDER BY id ASC 
		LIMIT ?,?
	 */
	@Test
	public void testJoin() {
		Query query = new Query();
		// 添加第二张表的字段,跟主表字段一起返回
		query.addOtherColumns(
					"t2.city"
					,"t2.address"
		);
		// 左连接查询,主表的alias默认为t
		query.join("LEFT JOIN user_info t2 ON t.id = t2.user_id"); 
		//添加条件
		query.eq("t2.id", 2);
		
		query.addSort("t.id");
		
		List<TUser> list = dao.find(query);
		
		System.out.println("==============");
		for (TUser user : list) {
			System.out.println(
				user.getId() 
				+ " " + user.getUsername() 
				// 下面两个字段是第二张表里面的
				+ " " + user.getCity() 
				+ " " + user.getAddress()
			);
		}
		System.out.println("==============");
	}
	
	/*
	 * 聚合查询
	 * 按state分组统计money和,并且按照state升序
	 * 并且money大于200的state
	 * 
	 * SELECT 
	 * 	state AS s , SUM(money) AS m 
	 * FROM `t_user` t 
	 * WHERE money > 0
	 * GROUP BY state 
	 * HAVING m > 200
	 * ORDER BY state asc
	 */
	@Test
	public void testProjection() {

		ProjectionQuery query = new ProjectionQuery();
		// 添加列
		query.addProjection(Projections.column("state", "s"));
		query.addProjection(Projections.sum("money", "m"));
		// 添加where
		query.addExpression(new ValueExpression("money",">",0));
		// 添加group by
		query.addGroupBy("state");
		// 添加having
		query.addHaving(new ValueExpression("SUM(money)", ">" ,200));
		
		query.addSort("state",Sort.DESC);

		List<Map<String, Object>> list = dao.findProjection(query);

		Assert.notEmpty(list);

		print(list);
	}
	
	// 添加-保存所有字段
	@Test
	public void testInsert() {
		TUser user = new TUser();
		user.setAddTime(new Date());
		user.setIsdel((byte)0);
		user.setLeftMoney(22.1F);
		user.setMoney(new BigDecimal(100.5));
		user.setRemark("备注");
		user.setState(1);
		user.setUsername("张三");
		
		dao.save(user);
		
		print("添加后的主键:" + user.getId());
		print(user);
	}
	
	// 添加-保存非空字段
	@Test
	public void testInsertIgnoreNull() {
		TUser user = new TUser();
		user.setAddTime(new Date());
		user.setIsdel((byte)1);
		user.setMoney(new BigDecimal(100.5));
		user.setState(1);
		user.setUsername("张三notnull");
		user.setLeftMoney(null);
		user.setRemark(null);
		
		dao.saveIgnoreNull(user);
		
		print("添加后的主键:" + user.getId());
		print(user);
	}
	
	// 批量添加
	/*
	 * 支持mysql,sqlserver2008,sqlite,PostgreSQL 。如需支持其它数据库使用saveMulti方法
	 * INSERT INTO person (id, name, age)
		VALUES
    	(1, 'Kelvin', 22),
    	(2, 'ini_always', 23);
	 */
	@Test
	public void testInsertBatch() {
		List<TUser> users = new ArrayList<>();
		
		for (int i = 0; i < 3; i++) { // 创建3个对象
			TUser user = new TUser();
			user.setUsername("username" + i);
			user.setMoney(new BigDecimal(i));
			user.setRemark("remark" + i);
			user.setState(1);
			user.setIsdel((byte)0);
			user.setAddTime(new Date());
			user.setLeftMoney(200F);
			users.add(user);
		}
		
		int i = dao.saveBatch(users); // 返回成功数
		
		System.out.println("saveBatch --> " + i);
	}
	
	
	// 批量添加指定字段,仅支持msyql，sqlserver2008，sqlite,pgsql. 如需支持其它数据库使用saveMulti方法
	@Test
	public void testInsertBatchWithColumns() {
		List<TUser> users = new ArrayList<>();
		
		for (int i = 0; i < 3; i++) { // 创建3个对象
			TUser user = new TUser();
			user.setUsername("usernameWithColumns" + i);
			user.setMoney(new BigDecimal(i));
			user.setAddTime(new Date());
			
			users.add(user);
		}
		
		/*
		 * INSERT INTO `t_user` ( username , money , add_time ) 
		 * VALUES ( ? , ? , ? ) , ( ? , ? , ? ) , ( ? , ? , ? ) 
		 */
		int i= dao.saveBatchWithColumns(Arrays.asList(
				new Column("username", "username") // 第一个是数据库字段,第二个是java字段
				,new Column("money", "money")
				,new Column("add_time", "addTime")
				), users);
		
		System.out.println("saveBatchWithColumns --> " + i);
		
	}
	
	
	// 事务回滚
	@Test
	public void testUpdateTran() {
		TUser user = transactionTemplate.execute(new TransactionCallback<TUser>() {
			@Override
			public TUser doInTransaction(TransactionStatus arg0) {
				try{
					TUser user = dao.get(3);
					user.setUsername("王五1");
					user.setMoney(user.getMoney().add(new BigDecimal(0.1)));
					user.setIsdel((byte)1);
					
					int i = dao.update(user);
					print("testUpdate --> " + i);
					int j = 1/0; // 模拟错误
					return user;
				}catch(Exception e) {
					e.printStackTrace();
					arg0.setRollbackOnly();
					return null;
				}
			}
			
		});
		
		print(user);
	}
	
	// 更新所有字段
	@Test
	public void testUpdate() {
		TUser user = dao.get(3);
		user.setUsername("李四");
		user.setMoney(user.getMoney().add(new BigDecimal(0.1)));
		user.setState(1);
		user.setIsdel((byte)1);
		
		int i = dao.update(user);
		print("testUpdate --> " + i);
	}
	
	// 更新不为null的字段
	/*
	 *UPDATE [t_user] SET [username]=?, [isdel]=? WHERE [id] = ?  
	 */
	@Test
	public void updateIgnoreNull() {
		TUser user = new TUser();
		user.setId(3);
		user.setUsername("王五");
		user.setState(2);
		user.setIsdel((byte)0);
		int i = dao.updateIgnoreNull(user);
		print("updateNotNull --> " + i);
	}
	
	// 根据条件更新
	// UPDATE t_user SET remark = '批量修改备注' WHERE state = 0
	@Test
	public void testUpdateNotNullByExpression() {
		Query query = new Query();
		query.eq("state", 0);
		
		TUser user = new TUser();
		user.setRemark("批量修改备注");
		
		int i = dao.updateIgnoreNullByExpression(user, query);
		print("testUpdateNotNullByExpression --> " + i);
	}
	
	// 删除
	@Test
	public void testDel() {
		TUser user = new TUser();
		user.setId(36);
		int i = dao.del(user);
		print("del --> " + i);
	}
	
	// 根据条件删除
	// DELETE FROM `t_user` WHERE state = ? 
	@Test
	public void delByExpression() {
		Query query = new Query();
		query.eq("state", 3);
		int i = dao.delByExpression(query);
		print("delByExpression --> " + i);
	}
	

}
