package com.sise.school.teach.constants;

import lombok.Getter;

/**
 * redis里面的常量类
 *
 * @author idea
 * @data 2018/12/2
 */
@Getter
public class RedisConstants {

    private RedisConstants() {
    }

    /**
     * 对话好评记录
     */
    public static final String USER_GOOD_DIALOG_LOG = "dialog_good";

    /**
     * 对话差评记录
     */
    public static final String USER_BAD_DIALOG_LOG = "dialog_bad";

    /**
     * 评论好评记录
     */
    public static final String USER_GOOD_REVIEW_LOG = "review_good";

    /**
     * 评论差评记录
     */
    public static final String USER_BAD_REVIEW_LOG = "review_bad";


    /**
     * 逗号分隔符
     */
    public static final String SPLITE_CODE = ",";


}
