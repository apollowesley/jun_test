import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.sise.school.teach.utils.mongodb.MongoDBDao;
import com.sise.school.teach.utils.mongodb.MongoDBHelper;
import com.sise.school.teach.utils.mongodb.MongoDBImpl;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author idea
 * @data 2018/11/10
 */
public class MongoDBTest {

    public MongoDBHelper mongoDBHelper;
    public MongoClient mongoClient;
    public MongoDatabase mongoDatabase;
    public MongoDBDao mongoDBDao;
    public HashMap<String, Object> value;

    @Before
    public void setUp() {
        mongoDBHelper = new MongoDBHelper();
        mongoClient = mongoDBHelper.getMongoDBClient();
        mongoDatabase = mongoDBHelper.getmongDatabase(mongoClient);
        mongoDBDao = new MongoDBImpl();
        value = new HashMap<>();
        value.put("id", 1);
        value.put("username", "idea is username");
        value.put("password", "password is 123456");
    }

    @Test
    public void findTest2(){
        BasicDBObject bson=new BasicDBObject("name","张三0");
        mongoDBDao.queryByDoc(mongoDatabase,"user",bson);
    }

    /**
     * 当多次插入mongodb的时候，数据会出现迭代的现象，这个时候，json会变多。
     */
    @Test
    public void insertOne() {
        Document document = new Document("name", "张三3")
                .append("age", 13)
                .append("sex", "男3");
        mongoDBDao.insert(mongoDatabase, "user", document);
    }

    @Test
    public void insertMany() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        List<Document> documentList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Document document = new Document("name", "李四" + i)
                    .append("age", 10 + i)
                    .append("sex", "男");
            documentList.add(document);
        }
        collection.insertMany(documentList);
    }

    @Test
    public void findTest() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        FindIterable findIterable = collection.find();
        Document document = (Document) findIterable.first();
        System.out.println(document);
    }

    @Test
    public void findAllByCollectionName() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        FindIterable findIterable = collection.find();
        MongoCursor cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    @Test
    public void findAllByName(){
        MongoCollection<Document> collection=mongoDatabase.getCollection("user");
        Bson filter=Filters.regex("name","张三");
        FindIterable findIterable=collection.find(filter);
        MongoCursor cursor=findIterable.iterator();
        while(cursor.hasNext()){
            System.out.println(cursor.next());
        }
    }



    @Test
    public void deleteOne() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        Bson filter = Filters.eq("name", "张三");
        collection.deleteOne(filter);
    }

    @Test
    public void deleteMany() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        Bson filter = Filters.exists("name", true);
        collection.deleteMany(filter);
    }

    @Test
    public void updateMany() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        Bson filter=Filters.lt("age",15);
        Document document=new Document("$set",new Document("age",20));
        collection.updateMany(filter,document);
    }
}
