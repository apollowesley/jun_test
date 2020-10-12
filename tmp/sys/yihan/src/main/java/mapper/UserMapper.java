package mapper;

import pojo.User;

import java.util.List;

public interface UserMapper {
    List<User> getUserByUsername(String username);

    List<User> getAllUser();

    User getUserById(Integer id);

    void editUser(User user);

    void addUser(User user);

    void userDel(Integer id);
}
