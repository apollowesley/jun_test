package service.impl;

import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.User;
import service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;

    @Override
    public User getUserByUsername(String username) {
        List<User> list = mapper.getUserByUsername(username);
        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<User> getAllUser() {
       return mapper.getAllUser();
    }

    @Override
    public User getUserById(Integer id) {
        if (id != null) {
            return mapper.getUserById(id);
        }
        return null;
    }

    @Override
    public void editUser(User user) {
        if (user != null) {
            if (user.getId() != null) {
                mapper.editUser(user);
            } else {
                mapper.addUser(user);
            }
        }
    }

    @Override
    public void userDel(Integer id) {
        mapper.userDel(id);
    }
}