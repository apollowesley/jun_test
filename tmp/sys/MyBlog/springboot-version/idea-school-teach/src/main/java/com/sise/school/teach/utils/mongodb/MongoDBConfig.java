package com.sise.school.teach.utils.mongodb;

import lombok.Getter;

/**
 * mongodb配置类
 * @author idea
 * @data 2018/11/10
 */
@Getter
public class MongoDBConfig {

    private MongoDBConfig(){}

    /**
     * 数据库名称
     */
    public static final String DBNAME="review";

    /**
     * 评论中心
     */
    public static final String COLLECTION_NAME="review_center";

    /**
     * 链接地址
     */
    public static final String SERVER_ADDRESS="127.0.0.1";

    /**
     * 端口号
     */
    public static final Integer PORT=27017;

}
