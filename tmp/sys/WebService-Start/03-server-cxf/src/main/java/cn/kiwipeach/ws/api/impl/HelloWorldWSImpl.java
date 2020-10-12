package cn.kiwipeach.ws.api.impl;

import cn.kiwipeach.entity.User;
import cn.kiwipeach.ws.api.HelloWorldWS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create Date: 2017/11/12
 * Description:
 *
 * @author kiwipeach [1099501218@qq.com]
 */

public class HelloWorldWSImpl implements HelloWorldWS {
    private static ArrayList<User> listUser = new ArrayList<>();

    static {
        listUser.add(new User("cn/kiwipeach", "123456"));
        listUser.add(new User("kakaluote", "lbr123"));
        listUser.add(new User("suwukong", "swk123"));
        listUser.add(new User("karaota", "krt123"));
        listUser.add(new User("apple", "app456"));
    }


    @Override
    public String sayHello(String word) {
        StringBuffer resultWord = new StringBuffer();
        resultWord.append("You Say '").append(word).append("' To World");
        return resultWord.toString();
    }

    @Override
    public User getUser(String name) {
        User result = null;
        for (User temp :listUser) {
            if (temp.getUsername().equals(name)) {
                result = temp;
                break;
            }
        }
        return result;
    }

    @Override
    public List<User> getListUser() {
        return listUser;
    }

    //不允许使用Map进行返回
    @Override
    public Map<String, User> getMapUser(String key, String username) {
        return null;
    }
}
