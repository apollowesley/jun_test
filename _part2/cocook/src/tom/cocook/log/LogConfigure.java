package tom.cocook.log;

/**
 * 对日志文件进行设置
 */
public interface LogConfigure {

	/**
	 * 
	 * 
	 * <context-param> 只实现web唯一路径
		<param-name>log4jConfigLocation</param-name>
		<param-value>/WEB-INF/config/log4j.properties</param-value>
	</context-param>
	 * 
	 */
    void configure();


}
