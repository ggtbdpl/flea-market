package com.shumei.DAO;

import com.shumei.pojo.User;

public interface UserDAO {
    boolean addUser(User user);
    User getUserByUsername(String username);
    User getUserById(Integer id);
    boolean checkUsernameExists(String username);
}
