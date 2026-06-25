package com.shumei.servlet.AdminServlet;

import com.shumei.DAO.AdminDAO;
import com.shumei.DAO.Impl.AdminDAOImpl;
import com.shumei.pojo.Admin;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class Adminlogin extends ViewBaseServlet {
    private AdminDAO adminDAO = new AdminDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processTemplate("admin/login", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Admin admin = adminDAO.getAdminUser(username, password);

        if (admin == null) {
            request.setAttribute("error", "用户名或密码错误");
            processTemplate("admin/login", request, response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            response.sendRedirect(request.getContextPath() + "/admin/home");
        }
    }
}