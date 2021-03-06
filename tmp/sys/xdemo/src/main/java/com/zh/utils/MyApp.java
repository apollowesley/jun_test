package com.zh.utils;

/**
 * MyApp工具类
 * @author zhanghang
 * @date 2017/12/19
 */
public class MyApp {
    /**
     * 主键id
     */
    public static final String ID = "id";
    /**
     * 排序：id
     */
    public static final String SORT_ID = "id";
    /**
     * 全局操作日志集合长度
     */
    public static final long OPERATE_LOG_LIST_LIMIT_SIZE = 2;
    /**
     * 全局操作日志集合
     */
    public static final String OPERATE_LOG_LIST = "operateLogList";

    public static final String INSERT = "INSERT";

    public static final String DELETE = "DELETE";

    public static final String UPDATE = "UPDATE";

//    public static final String SEARCH = "SEARCH";//默认

    public static final String INSERT_UPDATE = "INSERT_UPDATE";


    public static final String MQ_DIRECT_MESSAGE = "message";

    public static final String MQ_DIRECT_OPERATE_LOG = "operateLog";

    public static final String MQ_TOPIC_TEST = "test_topic";

    public static final String MQ_TOPIC_TEST2 = "test2_topic";

    public static final String MQ_EXCHANGE_TOPIC_TEST = "test_topic_exchange";

    public static final String MQ_TOPIC_TEST_ROUTING_KEY = "test";

    public static final String MQ_TOPIC_TEST2_ROUTING_KEY = "test2";

    public static final String MQ_EXCHANGE_FANOUT_TEST = "test_fanout_exchange";

    public static final String MQ_QUEUE_ZH = "zh-queue";

    public static final String MQ_QUEUE_DELAY = "delay";

    public static final String MQ_QUEUE_DELAY_ROUTING_KEY = "delay";

    public static final String MQ_EXCHANGE_DIRECT_DELAY = "delay_exchange";

    public static final String MQ_QUEUE_RECEIVE_DELAY = "receive_delay";

    public static final String MQ_QUEUE_RECEIVE_DELAY_ROUTING_KEY = "receive_delay";

    public static final String MQ_EXCHANGE_DIRECT_RECEIVE_DELAY = "receive_delay_exchange";





    /**
     * 系统用户在职
     */
    public static final String SYS_USER_STATUS_Y = "Y";
    /**
     * 系统用户离职
     */
    public static final String SYS_USER_STATUS_N = "N";
}
