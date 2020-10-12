package priv.mdc.index.dumper.blog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 从canal服务器读取MySQL binlog数据
 * 简单模式
 * @author xuhuahai
 *
 */
public class SimpleCanalClient extends AbstractCanalClient {
	
	public SimpleCanalClient(String destination) {
		super(destination);
	}
	
}
