package com.shumei.servlet;

import com.shumei.DAO.ReportDAO;
import com.shumei.DAO.Impl.ReportDAOImpl;
import com.shumei.pojo.Report;
import com.shumei.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/report")
public class ReportServlet extends HttpServlet {

    private ReportDAO reportDAO = new ReportDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            out.print("{\"code\":401,\"msg\":\"请先登录\"}");
            return;
        }

        String productIdStr = req.getParameter("productId");
        String reason = req.getParameter("reason");

        if (productIdStr == null || reason == null || reason.trim().isEmpty()) {
            out.print("{\"code\":400,\"msg\":\"参数不完整\"}");
            return;
        }

        Report report = new Report();
        report.setProductId(Integer.parseInt(productIdStr));
        report.setReporterId(user.getId());
        report.setReason(reason.trim());

        boolean result = reportDAO.addReport(report);
        if (result) {
            out.print("{\"code\":200,\"msg\":\"举报成功\"}");
        } else {
            out.print("{\"code\":500,\"msg\":\"举报失败\"}");
        }
    }
}