import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.gen.testmongo.dao.mongo.MongoUserDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */

public class App {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
        MongoUserDao userDao = context.getBean(MongoUserDao.class);
        System.out.println(userDao);

        final FindIterable<Document> documents = userDao.userCollection.find(new Document("_id", new Document("$ne", new ObjectId("55c06e6f934af404acf9866f"))));
        System.out.println(documents.first()
        );


    }

}
