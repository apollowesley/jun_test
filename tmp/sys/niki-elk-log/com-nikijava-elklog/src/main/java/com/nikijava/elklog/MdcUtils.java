package com.nikijava.elklog;

import org.slf4j.MDC;

/**
 * @ProjectName: niki-common
 * @Package: com.niki.log
 * @ClassName: MdcUtils
 * @Description: java类作用描述
 * @Author: Niki Zheng
 * @CreateDate: 2019/8/26 16:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MdcUtils {

    private static final String UNIQUE_ID = "traceRootId";

    public static boolean insertMDC() {
        String uniqueId = UNIQUE_ID + "-" + JsonUtils.getUUID();
        MDC.put(UNIQUE_ID, uniqueId);
        return true;
    }


    public static void remove() {
        MDC.remove(UNIQUE_ID);
    }
}