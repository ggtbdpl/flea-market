package com.shumei.DAO.Impl;

import com.shumei.DAO.MessageDAO;
import com.shumei.pojo.Message;
import com.shumei.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements MessageDAO {

    @Override
    public List<Message> getMessagesByProductId(int productId) {
        List<Message> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, product_id, from_user_id, to_user_id, content, is_read, create_time " +
                "FROM message WHERE product_id = ? ORDER BY create_time DESC";

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Message msg = new Message();
                msg.setId(rs.getInt("id"));
                msg.setProductId(rs.getInt("product_id"));
                msg.setFromUserId(rs.getInt("from_user_id"));
                msg.setToUserId(rs.getInt("to_user_id"));
                msg.setContent(rs.getString("content"));
                msg.setIsRead(rs.getInt("is_read"));
                msg.setCreateTime(rs.getTimestamp("create_time"));
                list.add(msg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        return list;
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT m.id, m.product_id, m.from_user_id, m.to_user_id, " +
                "m.content, m.is_read, m.create_time, " +
                "u.username, p.title as product_name " +
                "FROM message m " +
                "LEFT JOIN user u ON m.from_user_id = u.id " +
                "LEFT JOIN product p ON m.product_id = p.id " +
                "ORDER BY m.create_time DESC";

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Message msg = new Message();
                msg.setId(rs.getInt("id"));
                msg.setProductId(rs.getInt("product_id"));
                msg.setFromUserId(rs.getInt("from_user_id"));
                msg.setToUserId(rs.getInt("to_user_id"));
                msg.setContent(rs.getString("content"));
                msg.setIsRead(rs.getInt("is_read"));
                msg.setCreateTime(rs.getTimestamp("create_time"));
                msg.setUsername(rs.getString("username"));
                msg.setProductName(rs.getString("product_name"));
                list.add(msg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        return list;
    }

    @Override
    public boolean addMessage(Message message) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO message (product_id, from_user_id, to_user_id, content, is_read) VALUES (?, ?, ?, ?, 0)";

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, message.getProductId());
            ps.setInt(2, message.getFromUserId());
            ps.setInt(3, message.getToUserId());
            ps.setString(4, message.getContent());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }

        return false;
    }

    @Override
    public boolean deleteMessage(int messageId) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "DELETE FROM message WHERE id = ?";

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, messageId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }

        return false;
    }

    @Override
    public boolean markAsRead(int messageId) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE message SET is_read = 1 WHERE id = ?";

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, messageId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }

        return false;
    }
}