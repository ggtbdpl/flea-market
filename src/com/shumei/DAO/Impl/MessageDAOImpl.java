package com.shumei.DAO.Impl;

import com.shumei.DAO.MessageDAO;
import com.shumei.pojo.Message;
import com.shumei.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements MessageDAO {

    @Override
    public List<Message> getMessagesByProductId(Integer productId) {
        String sql = "SELECT * FROM message WHERE product_id = ? ORDER BY create_time DESC";
        List<Message> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean addMessage(Message message) {
        String sql = "INSERT INTO message (product_id, from_user_id, to_user_id, content) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, message.getProductId());
            ps.setInt(2, message.getFromUserId());
            ps.setInt(3, message.getToUserId() != null ? message.getToUserId() : 0);
            ps.setString(4, message.getContent());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public int replyMessage(Integer id, String replyContent) {
        String sql = "UPDATE message SET reply_content = ?, reply_time = NOW() WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, replyContent);
            ps.setInt(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public List<Message> getAllMessages() {
        String sql = "SELECT * FROM message ORDER BY create_time DESC";
        List<Message> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteMessage(Integer id) {
        String sql = "DELETE FROM message WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean markAsRead(Integer id) {
        String sql = "UPDATE message SET is_read = 1 WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    private Message mapRow(ResultSet rs) throws SQLException {
        Message msg = new Message();
        msg.setId(rs.getInt("id"));
        msg.setProductId(rs.getInt("product_id"));
        msg.setFromUserId(rs.getInt("from_user_id"));
        msg.setToUserId(rs.getInt("to_user_id"));  // 新增
        msg.setContent(rs.getString("content"));
        msg.setReplyContent(rs.getString("reply_content"));
        msg.setIsRead(rs.getInt("is_read"));  // 新增
        Timestamp replyTime = rs.getTimestamp("reply_time");
        if (replyTime != null) {
            msg.setReplyTime(replyTime.toLocalDateTime());
        }
        msg.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
        return msg;
    }

}
