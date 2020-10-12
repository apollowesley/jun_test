package org.gen.testmongo.dao.mongo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.gen.testmongo.codec.UserCodec;
import org.gen.testmongo.dao.UserDao;
import org.gen.testmongo.entity.User;
import org.gen.testmongo.model.query.UserQueryModel;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Consumer;


@Repository
public class MongoUserDao implements UserDao {

    @Resource(name = "userCollection")
    public MongoCollection<Document> userCollection;

    @Resource(name = "userCodec")
    public UserCodec userCodec;


    @Override
    public void insert(User user) {
        this.userCollection.insertOne(userCodec.encoder(user));
    }

    @Override
    public void update(User user) {
        final Document encoder = userCodec.encoder(user);
        Document dest = new Document();
        encoder.entrySet().forEach(entry -> dest.append("$set", new Document(entry.getKey(), entry.getValue())));
        this.userCollection.updateOne(new Document().append("_id", user.getId()), dest);
    }

    @Override
    public void merge(User user, String... fields) {
        if (fields != null && fields.length > 0) {
            final Document encoder = userCodec.encoder(user);
            final HashSet<String> strings = new HashSet<>(Arrays.asList(fields));
            Document dest = new Document();
            encoder.entrySet().stream().filter(entry -> strings.contains(entry.getKey())).forEach(entry -> dest.append("$set", new Document(entry.getKey(), entry.getValue())));
            this.userCollection.updateOne(new Document().append("_id", user.getId()), dest);
        }
    }


    @Override
    public void delete(ObjectId id) {
        this.userCollection.deleteOne(new Document("_id", id));
    }

    @Override
    public User findOne(ObjectId id) {
        return userCodec.decoder(this.userCollection.find(new Document("_id", id)).first());
    }

    @Override
    public List<User> findAll(UserQueryModel userQueryModel) {
        List<User> result = new ArrayList<>();
        this.userCollection.find(paserQueryModel(userQueryModel)).skip((userQueryModel.getPageNumber() - 1) * userQueryModel.getPageSize()).limit(userQueryModel.getPageSize()).forEach((Consumer<Document>) document -> result.add(userCodec.decoder(document)));
        return result;
    }

    @Override
    public long count(UserQueryModel userQueryModel) {
        return this.userCollection.count(paserQueryModel(userQueryModel));
    }


    private Document paserQueryModel(UserQueryModel userQueryModel) {
        Document doc = new Document();
        if (userQueryModel.getUsernameLK() != null && !"".equals(userQueryModel.getUsernameLK())) {
            doc.append("username", userQueryModel.getUsernameLK());
        }
        return doc;
    }



}
