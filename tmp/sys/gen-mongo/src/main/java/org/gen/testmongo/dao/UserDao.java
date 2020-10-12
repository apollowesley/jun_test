package org.gen.testmongo.dao;

import org.bson.types.ObjectId;
import org.gen.testmongo.entity.User;
import org.gen.testmongo.model.query.UserQueryModel;

import java.util.List;

/**
 * Created by Administrator on 2015/8/3.
 */
public interface UserDao {

    void insert(User user);

    void update(User user);

    void merge(User user, String... fields);

    void delete(ObjectId id);

    User findOne(ObjectId id);

    List<User> findAll(UserQueryModel userQueryModel);

    long count(UserQueryModel userQueryModel);

}
