package com.shumei.DAO.Impl;

import com.shumei.DAO.UserDAO;
import com.shumei.pojo.User;
import com.shumei.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean addUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO user (username, password, nickname, phone, wechat, avatar, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNickname());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getWechat());
            ps.setString(6, user.getAvatar() != null ? user.getAvatar() : "default-avatar.png");
            ps.setInt(7, user.getStatus() != null ? user.getStatus() : 1);


            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id, username, password, nickname, phone, wechat, avatar, status, create_time FROM user WHERE username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setPhone(rs.getString("phone"));
                user.setWechat(rs.getString("wechat"));
                user.setAvatar(rs.getString("avatar"));
                user.setStatus(rs.getInt("status"));
                user.setCreateTime(rs.getString("create_time"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    @Override
    public User getUserById(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id, username, password, nickname, phone, wechat, avatar, status, create_time,balance FROM user WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setPhone(rs.getString("phone"));
                user.setWechat(rs.getString("wechat"));
                user.setAvatar(rs.getString("avatar"));
                user.setStatus(rs.getInt("status"));
                user.setCreateTime(rs.getString("create_time"));
                user.setBalance(rs.getBigDecimal("balance"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    @Override
    public boolean checkUsernameExists(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return false;
    }

    @Override
    public BigDecimal getBalance(Integer userId) {
        String sql = "SELECT balance FROM user WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return java.math.BigDecimal.ZERO;
    }

    @Override
    public int updateBalance(Integer userId, java.math.BigDecimal amount) {
        String sql = "UPDATE user SET balance = balance + ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, amount);
            ps.setInt(2, userId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
