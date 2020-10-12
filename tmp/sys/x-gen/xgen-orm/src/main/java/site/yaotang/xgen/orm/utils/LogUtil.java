package site.yaotang.xgen.orm.utils;

/**
 * 日志工具
 * @author hyt
 * @date 2017年12月30日
 */
public class LogUtil {

	public static void error(org.slf4j.Logger log, Exception e) {
		log.error(e.getMessage(), e);
	}

	public static void debug(org.slf4j.Logger log, String msg) {
		log.debug(msg);
	}

	public static void error(org.slf4j.Logger log, String msg) {
		log.error(msg);
	}
}
