package com.site.common.exception;

import java.util.concurrent.ConcurrentHashMap;

import com.site.common.constants.ErrorCode;

/**
 * 业务异常消息处理器
 * 
 */
public class ExceptionMsg {

    // private final static Logger log = LoggerFactory.getLogger(ExceptionMsg.class);

    private static ConcurrentHashMap<String, String> errorMsgs = new ConcurrentHashMap<String, String>();

    public void init() {

        // if (log.isDebugEnabled()) {
        // for (Entry<String, String> entry : errorMsgs.entrySet()) {
        // log.debug("## ERROR-CODE={} , ERROR-MESSAGE={}", entry.getKey(), entry.getValue());
        // }
        // }

    }

    /**
     * 根据业务异常码获取对应描述
     * 
     * @param errorCode
     *            业务异常码
     * @return 业务异常描述,若找不到返回null
     */
    public static String getErrorMsg(String errorCode) {
        if (errorMsgs.containsKey(errorCode)) {
            return errorMsgs.get(errorCode);
        }
        return null;
    }

    /**
     * 系统异常
     * 
     * @return 系统错误，请联系管理员
     */
    public static String getSystemErrorMsg() {
        return getErrorMsg(ErrorCode.BE_CODE_10000);
    }

}
