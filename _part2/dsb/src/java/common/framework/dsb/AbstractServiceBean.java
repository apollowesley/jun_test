package common.framework.dsb;

import org.apache.ibatis.session.SqlSession;

import common.framework.dsb.orm.SqlSessionProvider;
import common.framework.dsb.service.ServiceContext;

/**
 * @author David Yuan
 * 
 */
public abstract class AbstractServiceBean implements DynamicServiceBean {
	protected ServiceContext serviceContext = null;
	private SqlSessionProvider sqlSessionProvider = null;
	private ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();

	public void start(ServiceContext serviceContext) throws Exception {
		this.serviceContext = serviceContext;
	}

	/**
	 * Lookup dynamic service from the given service name
	 * 
	 * @param serviceName
	 *            the name of service
	 * @return the instance of DynamicService
	 * @throws ServiceException
	 */
	protected <T extends DynamicService> T lookup(String serviceName) throws Exception {
		return serviceContext.lookup(serviceName);
	}

	@Override
	public void setSqlSessionProvider(SqlSessionProvider sqlSessionProvider) {
		this.sqlSessionProvider = sqlSessionProvider;

	}

	@Override
	public SqlSessionProvider getSqlSessionProvider() {
		return this.sqlSessionProvider;
	}

	@Override
	public void setSqlSession(SqlSession sqlSession) {
		threadLocal.set(sqlSession);
	}

	@Override
	public SqlSession getSqlSession() {
		return threadLocal.get();
	}

}