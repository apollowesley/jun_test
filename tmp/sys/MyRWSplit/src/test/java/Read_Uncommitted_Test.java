
import java.util.concurrent.CountDownLatch;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;

import com.freedom.mysql.myrwsplit.bean.Role;
import com.freedom.mysql.myrwsplit.helper.LoggerHelper;
import com.freedom.mysql.myrwsplit.interfaces.RoleMapper;
import com.freedom.mysql.myrwsplit.runnable.MyRwSplitTransactionRunnable;

public class Read_Uncommitted_Test {
	private static LoggerHelper LOGGER = LoggerHelper.getLogger(Read_Uncommitted_Test.class);
	private static CountDownLatch cdl0 = new CountDownLatch(1);
	private static CountDownLatch cdl1 = new CountDownLatch(1);

	public static void main(String[] args) {
		//主要演示线程1未提交,线程2就可以读
		//然后线程1抛出异常回滚数据,mysql里查询无数据存在 
		// 线程1
		new Thread(new Runnable() {
			@Override
			public void run() {
				LOGGER.debug("begin to execute ");
				Role result = null;
				try {
					result = new MyRwSplitTransactionRunnable<Role>(ExecutorType.SIMPLE,
							TransactionIsolationLevel.READ_UNCOMMITTED) {
						@Override
						public Role execute(SqlSession sqlSession) throws Exception {
							// 从这里,开始写任何你需要的的业务代码,
							// 处于一个事务里，事务相关的东西，业务不需要关心,框架已经做好了
							LOGGER.info("第1个线程开始删除");
							RoleMapper userMapper = sqlSession.getMapper(RoleMapper.class);// 获得mapper
							// delete
							Role role = new Role();
							role.setId(14);
							userMapper.deleteRole(role);
							LOGGER.info("第1个线程删除14完毕");
							// insert
							role.setId(14);
							role.setTitle("Read_Uncommitted_test");
							role.setAuthor("Read_Uncommitted_test");
							userMapper.insertRole(role);
							LOGGER.info("第1个线程插入14完毕");
							// LOGGER.info("record inserted ok...");
							// 故意设置1个休眠，此时还没有提交
							try {
								cdl0.countDown();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// select
							// role = userMapper.getRole0(11);
							LOGGER.info("第1个线程等待第2个线程完毕");
							try {
								cdl1.await();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							LOGGER.info("第1个线程结束,开始抛出异常");
							if(1==1)throw new Exception();
							return null;
						}
					}.run();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//
				LOGGER.debug("" + result);

			}

		}).start();
		// 线程2
		new Thread(new Runnable() {

			@Override
			public void run() {

				Role result = null;
				try {
					result = new MyRwSplitTransactionRunnable<Role>(ExecutorType.SIMPLE,
							TransactionIsolationLevel.READ_UNCOMMITTED) {
						@Override
						public Role execute(SqlSession sqlSession) {
							// 从这里,开始写任何你需要的的业务代码,
							// 处于一个事务里，事务相关的东西，业务不需要关心,框架已经做好了
							LOGGER.debug("线程2开始");
							try {
								cdl0.await();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							RoleMapper userMapper = sqlSession.getMapper(RoleMapper.class);// 获得mapper
							// 看看是不是可以查询得到
							// select
							LOGGER.info("第2个线程开始查询14");
							Role role = userMapper.getRole0(14);
							if (null != role)
								LOGGER.info("标题--->" + role.getTitle());
							LOGGER.info("第2个线程查询14完毕");
							cdl1.countDown();
							return role;
						}
					}.run();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//
				LOGGER.debug("" + result);
			}

		}).start();

	}
}
