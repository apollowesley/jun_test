package tom.cocook;


import tom.cocook.log.Logger;
import tom.cocook.log.LoggerFactory;

/**
 * exception 处理
 * @author Administrator
 *
 */
public class AppUtils {
	private static Logger  logger = LoggerFactory.getLog(AppUtils.class);
	public static String autoException(Throwable _e, String _className) {
		StackTraceElement frame = null;
		String[] exceptionClassPrefix = new String[]{};//Constants.syscfg.get("exceptionClassPrefix").split(";");
		String[] exceptionClassPrefixNo = new String[]{};//Constants.syscfg.get("exceptionClassPrefixNo").split(";");
		Throwable cause = null;
		do { 
			StackTraceElement[] ste_cau = _e.getStackTrace();
			for (int i = 0; i < ste_cau.length; i++) {
				boolean show = false;
				/* 有指定类名时,只根据指定类名判断显示异常,匹配到就显示 */
				if ((_className != null) && (_className.length() > 0)) {
					if (ste_cau[i].getClassName().equals(_className)) {
						show = true;
					}
					/* 没有指定类名,根据配置的包名判断是否显示异常 */
				} else {
					for (int j = 0; j < exceptionClassPrefix.length; j++) {
						if (ste_cau[i].getClassName().startsWith(exceptionClassPrefix[j])) {
							show = true;
							break;
						}
					}

					if (show) {// 满足以上一个条件后再判断 不显示的类名
						for (int j = 0; j < exceptionClassPrefixNo.length; j++) {
							if (ste_cau[i].getClassName().startsWith(exceptionClassPrefixNo[j])) {
								show = false;
								break;
							}
						}
					}

				}

				if (show) {
					frame = ste_cau[i];
					break;

				}
			}
			cause = _e;
		} while ((_e=_e.getCause()) != null);

		if (frame == null) {
			logger.error(cause.toString());
			return cause.toString();
		}
		
		logger.error(frame.toString() + ": "+ cause.toString());
		return frame.toString() + ": " + cause.toString();
	}

}
