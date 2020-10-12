package org.gen.testmongo.codec;

import org.bson.Document;
import org.gen.testmongo.entity.User;
import org.springframework.stereotype.Service;


@Service
public class UserCodec  {


    public Document encoder(User user) {
        return new Document().append("_id",user.getId()).append("name", user.getName());
    }


    public User decoder(Document doc) {
        User user = new User();
        user.setId(doc.getObjectId("_id"));
        user.setName(doc.get("name", String.class));
        return user;
    }


}
