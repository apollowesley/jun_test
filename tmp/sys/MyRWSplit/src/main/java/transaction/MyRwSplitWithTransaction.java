package transaction;

import org.apache.ibatis.session.SqlSession;
import com.freedom.mysql.myrwsplit.bean.Role;
import com.freedom.mysql.myrwsplit.helper.LoggerHelper;
import com.freedom.mysql.myrwsplit.helper.SqlSessionFactoryHelper;
import com.freedom.mysql.myrwsplit.interfaces.RoleMapper;

public class MyRwSplitWithTransaction {
	private static LoggerHelper LOGGER = LoggerHelper.getLogger(MyRwSplitWithTransaction.class);

	public static void main(String[] args) {
		// 可以考虑一个Runnable来封装
		SqlSession sqlSession = null;
		try {
			// 借助于辅助类获得session
			// 开启事务
			sqlSession = SqlSessionFactoryHelper.getSqlSessionFactory().openSession(false);
			{
				// 从这里,开始写你的业务代码
				// 获得mapper
				//LOGGER.info("---------------------------------------------------");
				RoleMapper userMapper = sqlSession.getMapper(RoleMapper.class);
				LOGGER.info("---------------------------------------------------");
				// delete
				Role role = new Role();
				role.setId(13);
				userMapper.deleteRole(role);
				LOGGER.info("---------------------------------------------------");
				// insert
				role.setId(13);
				role.setTitle("xxxxxxx");
				role.setAuthor("yyyyyyy");
				userMapper.insertRole(role);
				LOGGER.info("---------------------------------------------------");
				// Select
				role = userMapper.getRole0(13);
				LOGGER.info("---------------------------------------------------");
				LOGGER.info("userMapper--->"+userMapper);
				role = userMapper.getRole0(13);
				LOGGER.info("role ---> " + role);
				LOGGER.info("---------------------------------------------------");
			}
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();
			LOGGER.equals(e.toString());
		} finally {
			if (null != sqlSession) {
				sqlSession.close();
			}
		}
		// end
	}
}
