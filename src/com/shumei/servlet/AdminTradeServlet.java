package com.shumei.servlet;

import com.shumei.DAO.Impl.TradeDAOImpl;
import com.shumei.DAO.TradeDAO;
import com.shumei.pojo.Admin;
import com.shumei.pojo.Trade;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/trade")
public class AdminTradeServlet extends ViewBaseServlet {

    private TradeDAO tradeDAO = new TradeDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 临时去掉登录校验，设置假管理员
        Admin admin = new Admin();
        admin.setUsername("admin");
        req.setAttribute("admin", admin);

        List<Trade> list = tradeDAO.getAllTrades();
        req.setAttribute("tradeList", list);
        processTemplate("admin/trade", req, resp);
    }
}