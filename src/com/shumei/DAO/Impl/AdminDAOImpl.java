package com.shumei.DAO.Impl;

import com.shumei.DAO.AdminDAO;
import com.shumei.pojo.Admin;
import com.shumei.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public Admin getAdminUser(String username, String password) {
        Admin admin = null;
        String sql = "select * from admin where username=? and password=?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                admin = new Admin();
                admin.setAdminID(rs.getInt("adminID"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setRole(rs.getInt("role"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return admin;
    }
}
