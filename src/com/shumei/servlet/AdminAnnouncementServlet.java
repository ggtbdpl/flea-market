package com.shumei.servlet;

import com.shumei.DAO.AnnouncementDAO;
import com.shumei.DAO.Impl.AnnouncementDAOImpl;
import com.shumei.pojo.Admin;
import com.shumei.pojo.Announcement;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/announcement")
public class AdminAnnouncementServlet extends ViewBaseServlet {

    private AnnouncementDAO announcementDAO = new AnnouncementDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 临时去掉登录校验
        /*
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Admin admin = (Admin) session.getAttribute("admin");
        req.setAttribute("admin", admin);
        */
        // 临时设置一个假的管理员，避免页面报错
        Admin admin = new Admin();
        admin.setUsername("admin");
        req.setAttribute("admin", admin);

        String action = req.getParameter("action");
        if ("del".equals(action)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                announcementDAO.deleteAnnouncement(Integer.parseInt(idStr));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/announcement");
            return;
        }

        List<Announcement> list = announcementDAO.getAllAnnouncements();
        req.setAttribute("announcementList", list);
        processTemplate("admin/announcement", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 临时去掉登录校验
        /*
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        */

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            String type = req.getParameter("type");
            Announcement announcement = new Announcement();
            announcement.setTitle(title);
            announcement.setContent(content);
            announcement.setType(type != null ? type : "normal");
            announcement.setStatus(1);
            announcementDAO.addAnnouncement(announcement);
        } else if ("update".equals(action)) {
            String idStr = req.getParameter("id");
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            String type = req.getParameter("type");
            String statusStr = req.getParameter("status");
            Announcement announcement = new Announcement();
            announcement.setId(Integer.parseInt(idStr));
            announcement.setTitle(title);
            announcement.setContent(content);
            announcement.setType(type);
            announcement.setStatus(statusStr != null ? Integer.parseInt(statusStr) : 1);
            announcementDAO.updateAnnouncement(announcement);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/announcement");
    }
}