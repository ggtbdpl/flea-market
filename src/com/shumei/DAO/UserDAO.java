package com.shumei.DAO;

import com.shumei.pojo.User;

import java.math.BigDecimal;

import java.util.List;

public interface UserDAO {
    boolean addUser(User user);
    User getUserByUsername(String username);
    User getUserById(Integer id);
    boolean checkUsernameExists(String username);
    BigDecimal getBalance(Integer userId);
    int updateBalance(Integer userId, BigDecimal amount);
    boolean updateProfile(User user);
    boolean updatePassword(Integer id, String password);

    // 新增方法
    List<User> getAllUsers();
    boolean updateUserStatus(Integer id, Integer status);
}
