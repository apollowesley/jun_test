package cn.uncode.dal.mybatis;

import java.io.Serializable;

import cn.uncode.dal.annotation.Field;
import cn.uncode.dal.annotation.Table;


@Table(name="tb_user")
public class UserBase implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4799201163494761002L;
    


    @Field(name="pay_pwd")
    protected String payPwd;


	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

    
    
    

}
