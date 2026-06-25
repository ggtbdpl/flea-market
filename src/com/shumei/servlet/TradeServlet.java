package com.shumei.servlet;

import com.shumei.DAO.ProductDAO;
import com.shumei.DAO.UserDAO;
import com.shumei.DAO.Impl.ProductDAOImpl;
import com.shumei.DAO.Impl.UserDAOImpl;
import com.shumei.pojo.Product;
import com.shumei.pojo.User;
import com.shumei.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/trade")
public class TradeServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAOImpl();
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) {
            out.print("{\"success\":false,\"msg\":\"请先登录\"}");
            return;
        }

        String action = req.getParameter("action");
        if ("create".equals(action)) {
            createTrade(req, out, user);
        } else if ("confirm".equals(action)) {
            confirmTrade(req, out, user);
        } else if ("receive".equals(action)) {
            receiveTrade(req, out, user);
        } else {
            out.print("{\"success\":false,\"msg\":\"未知操作\"}");
        }
    }

    private void createTrade(HttpServletRequest req, PrintWriter out, User buyer) {
        String productIdStr = req.getParameter("productId");
        if (productIdStr == null) {
            out.print("{\"success\":false,\"msg\":\"参数错误\"}");
            return;
        }

        Connection conn = null;
        try {
            int productId = Integer.parseInt(productIdStr);
            Product product = productDAO.getProductById(productId);
            if (product == null) {
                out.print("{\"success\":false,\"msg\":\"商品不存在\"}");
                return;
            }
            if (product.getStatus() != null && product.getStatus() == 3) {
                out.print("{\"success\":false,\"msg\":\"商品已售出\"}");
                return;
            }
            if (product.getUserId() != null && product.getUserId().equals(buyer.getId())) {
                out.print("{\"success\":false,\"msg\":\"不能购买自己的商品\"}");
                return;
            }

            BigDecimal price = product.getPrice();
            BigDecimal balance = userDAO.getBalance(buyer.getId());
            if (balance == null || balance.compareTo(price) < 0) {
                out.print("{\"success\":false,\"msg\":\"余额不足\"}");
                return;
            }

            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            // 扣买家余额
            try (PreparedStatement ps = conn.prepareStatement("UPDATE user SET balance = balance - ? WHERE id = ?")) {
                ps.setBigDecimal(1, price);
                ps.setInt(2, buyer.getId());
                if (ps.executeUpdate() <= 0) {
                    conn.rollback();
                    out.print("{\"success\":false,\"msg\":\"扣款失败\"}");
                    return;
                }
            }

            // 加卖家余额
            if (product.getUserId() != null) {
                try (PreparedStatement ps = conn.prepareStatement("UPDATE user SET balance = balance + ? WHERE id = ?")) {
                    ps.setBigDecimal(1, price);
                    ps.setInt(2, product.getUserId());
                    if (ps.executeUpdate() <= 0) {
                        conn.rollback();
                        out.print("{\"success\":false,\"msg\":\"转账失败\"}");
                        return;
                    }
                }
            }

            // 创建 trade
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO trade (product_id, buyer_id, seller_id, amount, status, remark) VALUES (?, ?, ?, ?, ?, ?)")) {
                ps.setInt(1, productId);
                ps.setInt(2, buyer.getId());
                ps.setInt(3, product.getUserId());
                ps.setBigDecimal(4, price);
                ps.setString(5, "pending");
                ps.setString(6, "买家发起购买请求");
                if (ps.executeUpdate() <= 0) {
                    conn.rollback();
                    out.print("{\"success\":false,\"msg\":\"创建订单失败\"}");
                    return;
                }
            }

            // 更新商品状态为已售出
            try (PreparedStatement ps = conn.prepareStatement("UPDATE product SET status = 3 WHERE id = ?")) {
                ps.setInt(1, productId);
                if (ps.executeUpdate() <= 0) {
                    conn.rollback();
                    out.print("{\"success\":false,\"msg\":\"更新商品状态失败\"}");
                    return;
                }
            }

            conn.commit();
            out.print("{\"success\":true,\"msg\":\"购买请求已发起，等待卖家确认\"}");

        } catch (Exception e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
            out.print("{\"success\":false,\"msg\":\"系统错误\"}");
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        }
    }

    private void confirmTrade(HttpServletRequest req, PrintWriter out, User seller) {
        String tradeIdStr = req.getParameter("tradeId");
        if (tradeIdStr == null) {
            out.print("{\"success\":false,\"msg\":\"参数错误\"}");
            return;
        }

        Connection conn = null;
        try {
            int tradeId = Integer.parseInt(tradeIdStr);
            conn = DBUtil.getConnection();

            // 校验：卖家身份 + pending 状态
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT seller_id, status FROM trade WHERE id = ?")) {
                ps.setInt(1, tradeId);
                var rs = ps.executeQuery();
                if (!rs.next()) {
                    out.print("{\"success\":false,\"msg\":\"订单不存在\"}");
                    return;
                }
                if (rs.getInt("seller_id") != seller.getId()) {

                    out.print("{\"success\":false,\"msg\":\"无权操作\"}");
                    return;
                }
                if (!"pending".equals(rs.getString("status"))) {
                    out.print("{\"success\":false,\"msg\":\"订单状态错误\"}");
                    return;
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("UPDATE trade SET status = 'contacting' WHERE id = ?")) {
                ps.setInt(1, tradeId);
                if (ps.executeUpdate() > 0) {
                    out.print("{\"success\":true,\"msg\":\"已确认交易，等待买家收货\"}");
                } else {
                    out.print("{\"success\":false,\"msg\":\"操作失败\"}");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\":false,\"msg\":\"系统错误\"}");
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        }
    }

    private void receiveTrade(HttpServletRequest req, PrintWriter out, User buyer) {
        String tradeIdStr = req.getParameter("tradeId");
        if (tradeIdStr == null) {
            out.print("{\"success\":false,\"msg\":\"参数错误\"}");
            return;
        }

        Connection conn = null;
        try {
            int tradeId = Integer.parseInt(tradeIdStr);
            conn = DBUtil.getConnection();

            // 校验：买家身份 + contacting 状态
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT buyer_id, status FROM trade WHERE id = ?")) {
                ps.setInt(1, tradeId);
                var rs = ps.executeQuery();
                if (!rs.next()) {
                    out.print("{\"success\":false,\"msg\":\"订单不存在\"}");
                    return;
                }
                if (rs.getInt("buyer_id") != buyer.getId()) {
                    out.print("{\"success\":false,\"msg\":\"无权操作\"}");
                    return;
                }
                if (!"contacting".equals(rs.getString("status"))) {
                    out.print("{\"success\":false,\"msg\":\"订单状态错误\"}");
                    return;
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE trade SET status = 'completed', complete_time = NOW() WHERE id = ?")) {
                ps.setInt(1, tradeId);
                if (ps.executeUpdate() > 0) {
                    out.print("{\"success\":true,\"msg\":\"确认收货成功，交易完成\"}");
                } else {
                    out.print("{\"success\":false,\"msg\":\"操作失败\"}");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\":false,\"msg\":\"系统错误\"}");
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        }
    }
}
