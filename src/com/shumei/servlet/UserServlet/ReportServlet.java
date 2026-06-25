package com.shumei.servlet;

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
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {

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

        String productIdStr = req.getParameter("productId");
        String reason = req.getParameter("reason");

        if (productIdStr == null || reason == null || reason.trim().isEmpty()) {
            out.print("{\"success\":false,\"msg\":\"参数错误\"}");
            return;
        }

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO report (product_id, reporter_id, reason) VALUES (?, ?, ?)")) {
            ps.setInt(1, Integer.parseInt(productIdStr));
            ps.setInt(2, user.getId());
            ps.setString(3, reason.trim());
            int result = ps.executeUpdate();
            if (result > 0) {
                out.print("{\"success\":true,\"msg\":\"举报已提交，等待处理\"}");
            } else {
                out.print("{\"success\":false,\"msg\":\"提交失败\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\":false,\"msg\":\"系统错误\"}");
        }
    }
}
