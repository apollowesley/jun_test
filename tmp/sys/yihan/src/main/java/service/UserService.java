package service;

import pojo.User;

import java.util.List;

public interface UserService {
    User getUserByUsername(String username);

    List<User> getAllUser();

    User getUserById(Integer id);

    void editUser(User user);

    void userDel(Integer id);
}
