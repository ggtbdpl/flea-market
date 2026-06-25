package com.shumei.servlet.UserServlet;

import com.shumei.DAO.Impl.ProductDAOImpl;
import com.shumei.DAO.Impl.TradeDAOImpl;
import com.shumei.DAO.ProductDAO;
import com.shumei.DAO.TradeDAO;
import com.shumei.pojo.Product;
import com.shumei.pojo.Trade;
import com.shumei.pojo.User;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/user/center")
public class UserCenterServlet extends ViewBaseServlet {

    private TradeDAO tradeDAO = new TradeDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            User user = (session != null) ? (User) session.getAttribute("user") : null;

            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/index");
                return;
            }

            // 查询该用户发布的商品
            ArrayList<Product> myProducts = new ArrayList<>();
            ArrayList<Product> allProducts = productDAO.getProductList();
            if (allProducts != null) {
                for (Product p : allProducts) {
                    if (p.getUserId() != null && user.getId().equals(p.getUserId())) {
                        myProducts.add(p);
                    }
                }
            }

            // 查询交易订单
            List<Trade> buyerTrades = new ArrayList<>();
            List<Trade> sellerTrades = new ArrayList<>();
            try {
                List<Trade> bt = tradeDAO.getTradesByBuyerId(user.getId());
                if (bt != null) buyerTrades = bt;
            } catch (Exception e) { e.printStackTrace(); }
            try {
                List<Trade> st = tradeDAO.getTradesBySellerId(user.getId());
                if (st != null) sellerTrades = st;
            } catch (Exception e) { e.printStackTrace(); }

            // 补全商品信息（标题、图片）
            Map<Integer, Product> productMap = new HashMap<>();
            if (allProducts != null) {
                for (Product p : allProducts) {
                    productMap.put(p.getId(), p);
                }
            }
            fillTradeProductInfo(buyerTrades, productMap);
            fillTradeProductInfo(sellerTrades, productMap);

            req.setAttribute("user", user);
            req.setAttribute("myProducts", myProducts);
            req.setAttribute("buyerTrades", buyerTrades);
            req.setAttribute("sellerTrades", sellerTrades);
            processTemplate("user-center", req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("text/html;charset=utf-8");
            PrintWriter out = resp.getWriter();
            out.write("<h3>页面渲染异常: " + e.getClass().getName() + "</h3><pre>");
            e.printStackTrace(out);
            out.write("</pre>");
        }
    }

    private void fillTradeProductInfo(List<Trade> trades, Map<Integer, Product> productMap) {
        if (trades == null) return;
        for (Trade t : trades) {
            Product p = productMap.get(t.getProductId());
            if (p != null) {
                t.setProductTitle(p.getTitle());
                t.setProductImage(p.getImage());
            } else {
                t.setProductTitle("商品#" + t.getProductId());
                t.setProductImage("");
            }
        }
    }
}
