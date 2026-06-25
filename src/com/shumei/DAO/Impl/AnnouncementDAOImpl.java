package com.shumei.DAO.Impl;

import com.shumei.DAO.AnnouncementDAO;
import com.shumei.pojo.Announcement;
import com.shumei.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDAOImpl implements AnnouncementDAO {

    @Override
    public boolean addAnnouncement(Announcement announcement) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO announcement (title, content, type, status, create_time) VALUES (?, ?, ?, ?, NOW())";
            ps = conn.prepareStatement(sql);
            ps.setString(1, announcement.getTitle());
            ps.setString(2, announcement.getContent());
            ps.setString(3, announcement.getType() != null ? announcement.getType() : "normal");
            ps.setInt(4, announcement.getStatus() != null ? announcement.getStatus() : 1);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    @Override
    public boolean deleteAnnouncement(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM announcement WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    @Override
    public boolean updateAnnouncement(Announcement announcement) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE announcement SET title = ?, content = ?, type = ?, status = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, announcement.getTitle());
            ps.setString(2, announcement.getContent());
            ps.setString(3, announcement.getType());
            ps.setInt(4, announcement.getStatus());
            ps.setInt(5, announcement.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    @Override
    public Announcement getAnnouncementById(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id, title, content, type, status, create_time FROM announcement WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Announcement announcement = new Announcement();
                announcement.setId(rs.getInt("id"));
                announcement.setTitle(rs.getString("title"));
                announcement.setContent(rs.getString("content"));
                announcement.setType(rs.getString("type"));
                announcement.setStatus(rs.getInt("status"));
                announcement.setCreateTime(rs.getString("create_time"));
                return announcement;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Announcement> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id, title, content, type, status, create_time FROM announcement ORDER BY create_time DESC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Announcement announcement = new Announcement();
                announcement.setId(rs.getInt("id"));
                announcement.setTitle(rs.getString("title"));
                announcement.setContent(rs.getString("content"));
                announcement.setType(rs.getString("type"));
                announcement.setStatus(rs.getInt("status"));
                announcement.setCreateTime(rs.getString("create_time"));
                list.add(announcement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }
}