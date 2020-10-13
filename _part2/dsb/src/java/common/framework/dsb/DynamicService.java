package common.framework.dsb;

/**
 * The interface of dynamic service.
 * 
 * @author David Yuan
 * 
 */
public interface DynamicService {
	/**
	 * Refresh this dynamic service
	 * 
	 * @throws Exception
	 */
	void refresh() throws Exception;
}