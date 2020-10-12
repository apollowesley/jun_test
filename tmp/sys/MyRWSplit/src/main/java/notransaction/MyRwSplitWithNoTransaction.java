package notransaction;

import org.apache.ibatis.session.SqlSession;
import com.freedom.mysql.myrwsplit.bean.Role;
import com.freedom.mysql.myrwsplit.helper.LoggerHelper;
import com.freedom.mysql.myrwsplit.helper.SqlSessionFactoryHelper;
import com.freedom.mysql.myrwsplit.interfaces.RoleMapper;

public class MyRwSplitWithNoTransaction {
	private static LoggerHelper LOGGER = LoggerHelper.getLogger(MyRwSplitWithNoTransaction.class);

	public static void main(String[] args) {
		SqlSession sqlSession = null;
		try {
			// 借助于辅助类获得session
			// 不开启事务,自动提交
			// delete
			sqlSession = SqlSessionFactoryHelper.getSqlSessionFactory().openSession(true);
			RoleMapper userMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = new Role();
			role.setId(13);
			userMapper.deleteRole(role);
			sqlSession.close();
			//
			//
			// insert
			sqlSession = SqlSessionFactoryHelper.getSqlSessionFactory().openSession(true);
			userMapper = sqlSession.getMapper(RoleMapper.class);
			role = new Role();
			role.setId(13);
			role.setTitle("xxxxxxx");
			role.setAuthor("yyyyyyy");
			userMapper.insertRole(role);
			sqlSession.close();
			//
			//
			// Select
			sqlSession = SqlSessionFactoryHelper.getSqlSessionFactory().openSession(true);
			userMapper = sqlSession.getMapper(RoleMapper.class);
			role = userMapper.getRole0(13);
			LOGGER.info("role ------> " + role);
			sqlSession.close();
			// sqlSession.commit();自动提交不需要这个
		} catch (Exception e) {
			// sqlSession.rollback();自动提交不需要这个
			LOGGER.equals(e.toString());
		}
	}
}
