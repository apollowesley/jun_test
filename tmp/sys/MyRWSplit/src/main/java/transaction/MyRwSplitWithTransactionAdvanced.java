package transaction;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;

import com.freedom.mysql.myrwsplit.bean.Role;
import com.freedom.mysql.myrwsplit.helper.LoggerHelper;
import com.freedom.mysql.myrwsplit.interfaces.RoleMapper;
import com.freedom.mysql.myrwsplit.runnable.MyRwSplitTransactionRunnable;

public class MyRwSplitWithTransactionAdvanced {
	private static LoggerHelper LOGGER = LoggerHelper.getLogger(MyRwSplitWithTransactionAdvanced.class);

	public static void main(String[] args) throws Exception {
		// 初始化时，请设定你需要的参数，比如:Executor类型,是否自动提交,事务级别
		LOGGER.debug("begin to execute ");
		Role result = new MyRwSplitTransactionRunnable<Role>(ExecutorType.SIMPLE,TransactionIsolationLevel.READ_COMMITTED) {
			@Override
			public Role execute(SqlSession sqlSession) {
				// 从这里,开始写任何你需要的的业务代码,
				// 处于一个事务里，事务相关的东西，业务不需要关心,框架已经做好了
				RoleMapper userMapper = sqlSession.getMapper(RoleMapper.class);// 获得mapper
				// delete
				Role role = new Role();
				role.setId(13);
				userMapper.deleteRole(role);
				// insert
				role.setId(13);
				role.setTitle("xxx");
				role.setAuthor("yyy");
				userMapper.insertRole(role);
				// select
				role = userMapper.getRole0(11);
				role = userMapper.getRole0(11);
				role = userMapper.getRole0(11);
				return role;
			}
		}.run();
		//
		LOGGER.debug("" + result);
	}
}
