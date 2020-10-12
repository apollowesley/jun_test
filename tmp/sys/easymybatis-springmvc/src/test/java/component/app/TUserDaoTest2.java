package component.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.myapp.dao.UserDao;
import com.myapp.entity.TUser;
import com.myapp.entity.type.UserState;

import net.oschina.durcframework.easymybatis.query.Query;

@Commit
public class TUserDaoTest2 extends TestBase {

	@Autowired
	private UserDao dao;
	
	@Test
	public void testGet() {
		TUser user = dao.get(3);
		this.print(user);
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

	// 添加-保存所有字段
	@Test
	public void testInsert() {
		TUser user = new TUser();
		user.setAddTime(new Date());
		user.setIsdel(false);
		user.setLeftMoney(22.1F);
		user.setMoney(new BigDecimal(100.5));
		user.setRemark("备注");
		user.setState(UserState.Reg);
		user.setUsername("张三");

		dao.save(user);

		print("添加后的主键:" + user.getId());
		print(user);
	}

	// 更新所有字段
	@Test
	public void testUpdate() {
		TUser user = dao.get(30);
		user.setUsername("李四");
		user.setMoney(user.getMoney().add(new BigDecimal(0.1)));
		user.setState(UserState.Valid);
		user.setIsdel(true);

		int i = dao.update(user);
		print("testUpdate --> " + i);
	}


	// 删除
	@Test
	public void testDel() {
		TUser user = new TUser();
		user.setId(30);
		int i = dao.del(user);
		print("del --> " + i);
	}

}
