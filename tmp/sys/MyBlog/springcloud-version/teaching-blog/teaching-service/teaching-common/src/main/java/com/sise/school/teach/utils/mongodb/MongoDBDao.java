package com.sise.school.teach.utils.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;
import java.util.Map;

/**
 * dao接口
 *
 * @author idea
 * @data 2018/11/10
 */
public interface MongoDBDao {

    /**
     * 查询单个对象(精确查询)
     *
     * @param db
     * @param table
     * @param doc
     * @return
     */
    List<Map<String, Object>> queryByDoc(MongoDatabase db, String table, BasicDBObject doc);

    /**
     * 插入对象
     *
     * @param db
     * @param table
     * @param doc
     * @return
     */
    boolean insert(MongoDatabase db, String table, Document doc);

    /**
     * 删除对象
     *
     * @param db
     * @param table
     * @param doc
     * @return
     */
    boolean delete(MongoDatabase db, String table, BasicDBObject doc);


    /**
     * 更新对象
     *
     * @param db
     * @param collectionName
     * @param filter
     * @param newDoc
     * @return
     */
    boolean update(MongoDatabase db, String collectionName, BasicDBObject filter, BasicDBObject newDoc);

}
