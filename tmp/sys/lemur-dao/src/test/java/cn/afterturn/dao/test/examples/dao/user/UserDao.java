package cn.afterturn.dao.test.examples.dao.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.afterturn.dao.annotation.IDaoParam;

@Repository
public interface UserDao {

    public List<Map<String, Object>> listUserByAge(@IDaoParam("age") Integer age);

    public void updateUserBirthday(@IDaoParam("name") String name, @IDaoParam("age") Integer age,
                                   @IDaoParam("birthday") Date birthday);

}
