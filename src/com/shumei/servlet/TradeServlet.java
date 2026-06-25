package com.shumei.servlet;

import com.shumei.DAO.Impl.ProductDAOImpl;
import com.shumei.DAO.Impl.TradeDAOImpl;
import com.shumei.DAO.Impl.UserDAOImpl;
import com.shumei.DAO.ProductDAO;
import com.shumei.DAO.TradeDAO;
import com.shumei.DAO.UserDAO;
import com.shumei.pojo.Product;
import com.shumei.pojo.Trade;
import com.shumei.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.net.URLEncoder;

@WebServlet("/trade")
public class TradeServlet extends ViewBaseServlet {

    private TradeDAO tradeDAO = new TradeDAOImpl();
    private UserDAO userDAO = new UserDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=请先登录");
            return;
        }

        String action = req.getParameter("action");

        if ("create".equals(action)) {
            doCreate(req, resp, user);
        } else if ("confirm".equals(action)) {
            doConfirm(req, resp, user);
        } else if ("receive".equals(action)) {
            doReceive(req, resp, user);
        } else {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=非法操作");
        }
    }

    private void doCreate(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        String productIdStr = req.getParameter("productId");
        int productId;
        try {
            productId = Integer.parseInt(productIdStr);
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=参数错误");
            return;
        }

        Product product = productDAO.getProductById(productId);
        if (product == null) {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=商品不存在");
            return;
        }
        if (product.getStatus() != 1) {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=商品已下架或已售出");
            return;
        }
        if (product.getUserId().equals(user.getId())) {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=不能购买自己的商品");
            return;
        }

        BigDecimal price = product.getPrice();
        BigDecimal balance = userDAO.getBalance(user.getId());
        if (balance == null) balance = BigDecimal.ZERO;
        if (balance.compareTo(price) < 0) {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=余额不足");
            return;
        }

        // 扣除买家余额
        userDAO.updateBalance(user.getId(), balance.subtract(price));
        // 增加卖家余额
        User seller = userDAO.getUserById(product.getUserId());
        if (seller != null) {
            BigDecimal sellerBalance = userDAO.getBalance(seller.getId());
            if (sellerBalance == null) sellerBalance = BigDecimal.ZERO;
            userDAO.updateBalance(seller.getId(), sellerBalance.add(price));
        }

        // 创建交易记录
        Trade trade = new Trade();
        trade.setBuyerId(user.getId());
        trade.setSellerId(product.getUserId());
        trade.setProductId(productId);
        trade.setAmount(price);
        trade.setStatus("pending");
        trade.setCreateTime(LocalDateTime.now());
        tradeDAO.createTrade(trade);

        // 更新商品状态为已售出
        productDAO.updateStatus(productId, 3);

        resp.sendRedirect(req.getContextPath() + "/user/center?toast=" + URLEncoder.encode("购买成功，等待卖家确认", "UTF-8"));
    }

    private void doConfirm(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        String tradeIdStr = req.getParameter("tradeId");
        int tradeId;
        try {
            tradeId = Integer.parseInt(tradeIdStr);
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=参数错误");
            return;
        }

        Trade trade = tradeDAO.getTradeById(tradeId);
        if (trade == null) {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=订单不存在");
            return;
        }
        if (!trade.getSellerId().equals(user.getId())) {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=无权操作");
            return;
        }
        if (!"pending".equals(trade.getStatus())) {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=订单状态异常");
            return;
        }

        tradeDAO.updateTradeStatus(tradeId, "contacting");
        resp.sendRedirect(req.getContextPath() + "/user/center?toast=" + URLEncoder.encode("交易已确认，等待买家收货", "UTF-8"));
    }

    private void doReceive(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        String tradeIdStr = req.getParameter("tradeId");
        int tradeId;
        try {
            tradeId = Integer.parseInt(tradeIdStr);
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=参数错误");
            return;
        }

        Trade trade = tradeDAO.getTradeById(tradeId);
        if (trade == null) {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=订单不存在");
            return;
        }
        if (!trade.getBuyerId().equals(user.getId())) {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=无权操作");
            return;
        }
        if (!"contacting".equals(trade.getStatus())) {
            resp.sendRedirect(req.getContextPath() + "/user/center?toast=订单状态异常");
            return;
        }

        tradeDAO.updateTradeStatus(tradeId, "completed");
        tradeDAO.updateTradeCompleteTime(tradeId);
        resp.sendRedirect(req.getContextPath() + "/user/center?toast=" + URLEncoder.encode("确认收货成功，交易完成", "UTF-8"));
    }
}
