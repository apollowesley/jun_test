package com.sise.school.teach.utils.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author idea
 * @data 2018/11/10
 */
@Slf4j
public class MongoDBImpl implements MongoDBDao {


    @Override
    public List<Map<String, Object>> queryByDoc(MongoDatabase db, String collectionName, BasicDBObject doc) {
        MongoCollection<Document> collection=db.getCollection(collectionName);
        FindIterable<Document> iterable=collection.find(doc);
        List<Map<String,Object>> list = new ArrayList<>();
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            Document obj = cursor.next();
            String jsonString = obj.toJson();
            System.out.println(jsonString);
        }
        return list;
    }

    @Override
    public boolean insert(MongoDatabase db, String collectionName, Document doc) {
        MongoCollection<Document> mongoCollection = db.getCollection(collectionName);
        mongoCollection.insertOne(doc);
        return mongoCollection.count(doc) == 1;
    }

    @Override
    public boolean delete(MongoDatabase db, String collectionName, BasicDBObject doc) {
        MongoCollection<Document> mongoCollection = db.getCollection(collectionName);
        DeleteResult deleteResult = mongoCollection.deleteMany(doc);
        return deleteResult.getDeletedCount() > 0;
    }

    @Override
    public boolean update(MongoDatabase db, String collectionName, BasicDBObject filter, BasicDBObject newDoc) {
        MongoCollection<Document> mongoCollection = db.getCollection(collectionName);
        UpdateResult updateResult = mongoCollection.updateMany(filter, new Document("$set", new Document(newDoc)));
        return updateResult.getModifiedCount() > 0;
    }
}
