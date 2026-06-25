package com.shumei.servlet.AdminServlet;

import com.shumei.DAO.ReportDAO;
import com.shumei.DAO.Impl.ReportDAOImpl;
import com.shumei.pojo.Report;
import com.shumei.pojo.User;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/report")
public class AdminReportServlet extends ViewBaseServlet {

    private ReportDAO reportDAO =new ReportDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("reject".equals(action)) {
            String reportIdStr = req.getParameter("reportId");
            if (reportIdStr != null) {
                reportDAO.rejectReport(Integer.parseInt(reportIdStr));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/report");
            return;
        }

        if ("delete".equals(action)) {
            String reportIdStr = req.getParameter("reportId");
            if (reportIdStr != null) {
                reportDAO.deleteReport(Integer.parseInt(reportIdStr));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/report");
            return;
        }

        List<Report> reportList = reportDAO.getAllReports();
        req.setAttribute("reportList", reportList);
        processTemplate("admin/report", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String action = req.getParameter("action");

        if ("handle".equals(action)) {
            String reportIdStr = req.getParameter("reportId");
            String feedback = req.getParameter("adminReply");

            if (reportIdStr != null && feedback != null) {
                HttpSession session = req.getSession();
                User admin = (User) session.getAttribute("user");
                int handlerId = (admin != null) ? admin.getId() : 0;

                reportDAO.handleReport(Integer.parseInt(reportIdStr), handlerId, feedback.trim());
            }
        }

        resp.sendRedirect(req.getContextPath() + "/admin/report");
    }
}