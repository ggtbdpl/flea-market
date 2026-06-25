package com.shumei.DAO.Impl;

import com.shumei.DAO.ReportDAO;
import com.shumei.pojo.Report;
import com.shumei.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAOImpl implements ReportDAO {

    @Override
    public boolean addReport(Report report) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO report (product_id, reporter_id, reason, status) VALUES (?, ?, ?, 0)";
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, report.getProductId());
            ps.setInt(2, report.getReporterId());
            ps.setString(3, report.getReason());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return false;
    }

    @Override
    public List<Report> getAllReports() {
        List<Report> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT r.id, r.product_id, r.reporter_id, r.reason, r.status, " +
                "r.handler_id, r.handle_time, r.feedback, r.create_time, " +
                "p.title as product_name, u.username as reporter_name " +
                "FROM report r " +
                "LEFT JOIN product p ON r.product_id = p.id " +
                "LEFT JOIN user u ON r.reporter_id = u.id " +
                "ORDER BY r.create_time DESC";
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Report r = new Report();
                r.setId(rs.getInt("id"));
                r.setProductId(rs.getInt("product_id"));
                r.setReporterId(rs.getInt("reporter_id"));
                r.setReason(rs.getString("reason"));
                r.setStatus(rs.getInt("status"));
                r.setHandlerId(rs.getInt("handler_id"));
                r.setHandleTime(rs.getTimestamp("handle_time"));
                r.setFeedback(rs.getString("feedback"));
                r.setCreateTime(rs.getTimestamp("create_time"));
                r.setProductName(rs.getString("product_name"));
                r.setReporterName(rs.getString("reporter_name"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    @Override
    public boolean handleReport(int reportId, int handlerId, String feedback) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE report SET status = 1, handler_id = ?, handle_time = NOW(), feedback = ? WHERE id = ?";
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, handlerId);
            ps.setString(2, feedback);
            ps.setInt(3, reportId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return false;
    }

    @Override
    public boolean rejectReport(int reportId) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE report SET status = 2 WHERE id = ?";
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, reportId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return false;
    }

    @Override
    public boolean deleteReport(int reportId) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM report WHERE id = ?";
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, reportId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return false;
    }
}