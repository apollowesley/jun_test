package org.neuedu.fly.dao.impl;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.neuedu.fly.dao.IUserDao;
import org.neuedu.fly.entity.User;

import java.util.Objects;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/8/21-9:57
 **/
public class UserDaoImpl extends BaseDao implements IUserDao {

    @Override
    public boolean register(User user) {
        String sql = "insert into tbl_user(tel,nick_name,sex,pwd) values(?,?,?,?)";
        return this.update(sql,user.getTel(),user.getNikeName(),user.getSex(),user.getPwd());
    }

    @Override
    public boolean findByTel(String tel) {
        String sql = "select tel from tbl_user where tel=? ";
        User user = this.findOne(sql, User.class, tel);
        return Objects.isNull(user);
    }
}
