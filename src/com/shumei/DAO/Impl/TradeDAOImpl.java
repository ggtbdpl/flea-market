package com.shumei.DAO.Impl;

import com.shumei.DAO.TradeDAO;
import com.shumei.pojo.Trade;
import com.shumei.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TradeDAOImpl implements TradeDAO {

    @Override
    public int createTrade(Trade trade) {
        String sql = "INSERT INTO trade (product_id, buyer_id, seller_id, amount, status, remark) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, trade.getProductId());
            ps.setInt(2, trade.getBuyerId());
            ps.setInt(3, trade.getSellerId());
            ps.setBigDecimal(4, trade.getAmount());
            ps.setString(5, trade.getStatus());
            ps.setString(6, trade.getRemark());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Trade getTradeById(Integer id) {
        String sql = "SELECT * FROM trade WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Trade getTradeByProductAndBuyer(Integer productId, Integer buyerId) {
        String sql = "SELECT * FROM trade WHERE product_id = ? AND buyer_id = ? ORDER BY create_time DESC LIMIT 1";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setInt(2, buyerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Trade> getTradesBySellerId(Integer sellerId) {
        String sql = "SELECT * FROM trade WHERE seller_id = ? ORDER BY create_time DESC";
        List<Trade> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sellerId);
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
    public List<Trade> getTradesByBuyerId(Integer buyerId) {
        String sql = "SELECT * FROM trade WHERE buyer_id = ? ORDER BY create_time DESC";
        List<Trade> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, buyerId);
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
    public int updateTradeStatus(Integer id, String status) {
        String sql = "UPDATE trade SET status = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateTradeCompleteTime(Integer id) {
        String sql = "UPDATE trade SET complete_time = NOW() WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Trade mapRow(ResultSet rs) throws SQLException {
        Trade trade = new Trade();
        trade.setId(rs.getInt("id"));
        trade.setProductId(rs.getInt("product_id"));
        trade.setBuyerId(rs.getInt("buyer_id"));
        trade.setSellerId(rs.getInt("seller_id"));
        trade.setAmount(rs.getBigDecimal("amount"));
        trade.setStatus(rs.getString("status"));
        trade.setRemark(rs.getString("remark"));
        trade.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
        Timestamp completeTime = rs.getTimestamp("complete_time");
        if (completeTime != null) {
            trade.setCompleteTime(completeTime.toLocalDateTime());
        }
        return trade;
    }
}
