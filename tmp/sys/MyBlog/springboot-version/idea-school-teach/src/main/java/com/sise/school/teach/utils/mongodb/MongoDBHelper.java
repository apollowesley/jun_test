package com.sise.school.teach.utils.mongodb;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;

import static com.sise.school.teach.utils.mongodb.MongoDBConfig.DBNAME;
import static com.sise.school.teach.utils.mongodb.MongoDBConfig.PORT;
import static com.sise.school.teach.utils.mongodb.MongoDBConfig.SERVER_ADDRESS;

/**
 * 专门用于处理mongodb的数据内容
 * @author idea
 * @data 2018/11/10
 */
@Slf4j
public class MongoDBHelper {


    public MongoClient getMongoDBClient(){
        MongoClient mongoClient=null;
        mongoClient=new MongoClient(SERVER_ADDRESS,PORT);
        log.info("链接到mongodb服务器，服务器地址为{}",SERVER_ADDRESS+":"+PORT);
        return mongoClient;
    }

    public DB getDb(MongoClient mongoClient) {
        DB db = null;
        try {
            if (mongoClient != null) {
                db = mongoClient.getDB(DBNAME);
                log.info("获取mongodb数据库成功！数据库名称为{}", DBNAME);
            }
        } catch (Exception e) {
            log.error("mongodb数据库获取失败，数据库名称为{}", DBNAME);
        }
        return db;
    }

    public MongoDatabase getmongDatabase(MongoClient mongoClient){
        MongoDatabase mongoDatabase=null;
        try{
            if(mongoClient!=null){
                mongoDatabase=mongoClient.getDatabase(DBNAME);
                log.info("获取mongodb数据库成功！数据库名称为{}",DBNAME);
            }
        }catch (Exception e){
            log.error("mongodb数据库获取失败，数据库名称为{}",DBNAME);
        }
        return mongoDatabase;
    }

    public void closeMongoClient(MongoDatabase mongoDatabase,MongoClient mongoClient){
        if(mongoDatabase!=null){
            mongoDatabase=null;
        }
        if(mongoClient!=null){
            mongoClient.close();
        }
        log.info("mongodb链接断开！");
    }
}
