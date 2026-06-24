package com.shumei.DAO;

import com.shumei.pojo.User;

import java.util.List;

public interface UserDAO {
    boolean addUser(User user);
    User getUserByUsername(String username);
    User getUserById(Integer id);
    boolean checkUsernameExists(String username);
    // 新增方法
    List<User> getAllUsers();
    boolean updateUserStatus(Integer id, Integer status);
}
