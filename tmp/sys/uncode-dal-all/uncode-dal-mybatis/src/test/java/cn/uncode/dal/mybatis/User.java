package cn.uncode.dal.mybatis;

import cn.uncode.dal.annotation.Field;
import cn.uncode.dal.annotation.Table;


@Table(name="tb_user")
public class User extends UserBase {

    /**
     * 
     */
    private static final long serialVersionUID = 4799201163494761002L;
    
    public static final String ID = "id";
    public static final String USER_NAME = "user_name";
    public static final String PWD = "pay_pwd";
    
    private Integer id;

    @Field(name="user_name")
    private String userName;

//    @Field(name="pay_pwd")
//    private String payPwd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

//	public String getPayPwd() {
//		return payPwd;
//	}
//
//	public void setPayPwd(String payPwd) {
//		this.payPwd = payPwd;
//	}

    public String toString(){
    	return String.format("id:%s#userName:%s#payPwd:%s", new Object[]{id, userName, payPwd});
    }

    
    
    

}
