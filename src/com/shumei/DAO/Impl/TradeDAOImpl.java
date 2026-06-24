package com.shumei.DAO.Impl;

import com.shumei.DAO.TradeDAO;
import com.shumei.pojo.Trade;
import com.shumei.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TradeDAOImpl implements TradeDAO {

    @Override
    public List<Trade> getAllTrades() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Trade> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id, product_id, buyer_id, seller_id, status, remark, create_time, complete_time FROM trade ORDER BY create_time DESC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Trade trade = new Trade();
                trade.setId(rs.getInt("id"));
                trade.setProductId(rs.getInt("product_id"));
                trade.setBuyerId(rs.getInt("buyer_id"));
                trade.setSellerId(rs.getInt("seller_id"));
                trade.setStatus(rs.getString("status"));
                trade.setRemark(rs.getString("remark"));
                trade.setCreateTime(rs.getString("create_time"));
                trade.setCompleteTime(rs.getString("complete_time"));
                list.add(trade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }
}