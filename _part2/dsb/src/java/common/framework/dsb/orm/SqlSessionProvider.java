package common.framework.dsb.orm;

import org.apache.ibatis.session.SqlSession;

public interface SqlSessionProvider {

	void close();

	/**
	 * Start SqlSession provider
	 * 
	 * @param configFile
	 * @throws Exception
	 */
	void start(String configFile) throws Exception;

	/**
	 * Get SqlSession from given database environment
	 * 
	 * @param environment
	 * @return
	 */
	SqlSession getSqlSession(String environment);

	/**
	 * Get SqlSession from given database environment
	 * 
	 * @param environment
	 * @param autoCommit
	 * @return
	 */
	SqlSession getSqlSession(String environment, boolean autoCommit);
}