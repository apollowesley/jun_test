package common.framework.dsb.server;


/**
 * Dynamic service provider
 * 
 * @author David Yuan
 * 
 */
public interface DSBServer {
	/**
	 * start service provider.
	 * 
	 * @throws Exception
	 */
	void start() throws Exception;

	/**
	 * close service provider
	 * 
	 * @throws Exception
	 */
	void close() throws Exception;
}
