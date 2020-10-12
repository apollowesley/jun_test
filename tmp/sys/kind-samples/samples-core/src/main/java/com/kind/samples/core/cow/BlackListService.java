package com.kind.samples.core.cow;

import java.util.Map;

/**
 * Created by weiguo.liu on 2017/1/4.
 */
public class BlackListService {
    private static CopyOnWriteMap<String, Boolean> blackListMap = new CopyOnWriteMap<String, Boolean>(1000);

    public static boolean isBlackList(String id) {
        return blackListMap.get(id) == null ? false : true;
    }

    public static void addBlackList(String id) {
        blackListMap.put(id, Boolean.TRUE);
    }

    /**
     * 批量添加黑名单
     * <p>
     *
     * @param ids
     */
    public static void addBlackList(Map<String, Boolean> ids) {
        blackListMap.putAll(ids);
    }


}
