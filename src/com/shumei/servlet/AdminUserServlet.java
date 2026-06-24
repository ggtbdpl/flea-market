package com.shumei.servlet;

import com.shumei.DAO.Impl.UserDAOImpl;
import com.shumei.DAO.UserDAO;
import com.shumei.pojo.Admin;
import com.shumei.pojo.User;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/user")
public class AdminUserServlet extends ViewBaseServlet {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 临时去掉登录校验，设置假管理员
        Admin admin = new Admin();
        admin.setUsername("admin");
        req.setAttribute("admin", admin);

        String action = req.getParameter("action");
        if ("toggle".equals(action)) {
            String idStr = req.getParameter("id");
            String statusStr = req.getParameter("status");
            if (idStr != null && statusStr != null) {
                int newStatus = Integer.parseInt(statusStr) == 1 ? 0 : 1;
                userDAO.updateUserStatus(Integer.parseInt(idStr), newStatus);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/user");
            return;
        }

        List<User> list = userDAO.getAllUsers();
        req.setAttribute("userList", list);
        processTemplate("admin/user", req, resp);
    }
}