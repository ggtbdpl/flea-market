package com.shumei.servlet.UserServlet;

import com.shumei.DAO.Impl.UserDAOImpl;
import com.shumei.DAO.UserDAO;
import com.shumei.pojo.User;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user/login")
public class UserLoginServlet extends ViewBaseServlet {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            out.print("{\"code\":400,\"msg\":\"用户名和密码不能为空\"}");
            return;
        }

        User user = userDAO.getUserByUsername(username.trim());
        if (user == null) {
            out.print("{\"code\":401,\"msg\":\"用户名不存在\"}");
            return;
        }

        if (!password.equals(user.getPassword())) {
            out.print("{\"code\":401,\"msg\":\"密码错误\"}");
            return;
        }

        if (user.getStatus() == 0) {
            out.print("{\"code\":403,\"msg\":\"账号已被禁用\"}");
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        out.print("{\"code\":200,\"msg\":\"登录成功\",\"nickname\":\"" + user.getNickname() + "\"}");
    }
}
