package test.mongo.model.query;

import org.gen.testmongo.model.QueryModel;

/**
 * Created by Administrator on 2015/8/12.
 */
public class AdminQueryModel extends QueryModel {
    private String usernameEQ;

    public String getUsernameEQ() {
        return usernameEQ;
    }

    public void setUsernameEQ(String usernameEQ) {
        this.usernameEQ = usernameEQ;
    }
}
