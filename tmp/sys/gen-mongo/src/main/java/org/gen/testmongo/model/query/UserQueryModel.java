package org.gen.testmongo.model.query;

import org.gen.testmongo.model.QueryModel;

/**
 * Created by Administrator on 2015/8/11.
 */
public class UserQueryModel extends QueryModel {

    private String usernameLK;
    private String priceEQ;
    private String[] ageIN;

    public String getUsernameLK() {
        return usernameLK;
    }

    public void setUsernameLK(String usernameLK) {
        this.usernameLK = usernameLK;
    }

    public String getPriceEQ() {
        return priceEQ;
    }

    public void setPriceEQ(String priceEQ) {
        this.priceEQ = priceEQ;
    }

    public String[] getAgeIN() {
        return ageIN;
    }

    public void setAgeIN(String[] ageIN) {
        this.ageIN = ageIN;
    }
}
