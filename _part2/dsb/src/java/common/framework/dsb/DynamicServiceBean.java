package common.framework.dsb;

import org.apache.ibatis.session.SqlSession;

import common.framework.dsb.orm.SqlSessionProvider;
import common.framework.dsb.service.ServiceContext;

/**
 * Bean class of the dynamic service, all dynamic service must implement this
 * interface
 * 
 * @author David Yuan
 * 
 */
public interface DynamicServiceBean {
	/**
	 * start this dynamic service
	 * 
	 * @param serviceContext
	 *            service context
	 * @throws ServiceException
	 */
	void start(ServiceContext serviceContext) throws Exception;

	/**
	 * close this dynamic service
	 * 
	 * @throws ServiceException
	 */
	void close() throws Exception;

	/**
	 * Refresh this dynamic service
	 * 
	 * @throws Exception
	 */
	void refresh() throws Exception;

	/**
	 * set SqlSessionProvider for DynamicServiceBean
	 * 
	 * @param sqlSessionProvider
	 */
	void setSqlSessionProvider(SqlSessionProvider sqlSessionProvider);

	/**
	 * get SqlSessionProvider
	 * 
	 * @return
	 */
	SqlSessionProvider getSqlSessionProvider();

	/**
	 * set SqlSession for DynamicServiceBean
	 * 
	 * @param sqlSession
	 */
	void setSqlSession(SqlSession sqlSession);

	/**
	 * get SqlSession from DynamicServiceBean
	 * 
	 * @return
	 */
	SqlSession getSqlSession();

}