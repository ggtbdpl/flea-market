package com.shumei.DAO.Impl;

import com.shumei.DAO.AdminDAO;
import com.shumei.pojo.Admin;
import com.shumei.util.DBUtil;

import java.sql.*;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public Admin getAdminUser(String username, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // 注意：字段名改成和数据库表一致
        String sql = "SELECT id, username, password, nickname, create_time FROM admin WHERE username = ? AND password = ?";

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));  // 原来是 adminID
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setNickname(rs.getString("nickname"));
                admin.setCreateTime(rs.getTimestamp("create_time"));
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        return null;
    }
}