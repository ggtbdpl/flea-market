package com.shumei.servlet.UserServlet;

import com.shumei.DAO.Impl.UserDAOImpl;
import com.shumei.DAO.UserDAO;
import com.shumei.pojo.User;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/register")
public class UserRegisterServlet extends ViewBaseServlet {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String nickname = req.getParameter("nickname");
        String phone = req.getParameter("phone");

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            out.print("{\"code\":400,\"msg\":\"用户名和密码不能为空\"}");
            return;
        }

        if (userDAO.checkUsernameExists(username)) {
            out.print("{\"code\":409,\"msg\":\"该用户名已被注册\"}");
            return;
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(password);
        user.setNickname(nickname != null && !nickname.isEmpty() ? nickname.trim() : username.trim());
        user.setPhone(phone);
        user.setStatus(1);

        boolean flag = userDAO.addUser(user);
        if (flag) {
            out.print("{\"code\":200,\"msg\":\"注册成功\"}");
        } else {
            out.print("{\"code\":500,\"msg\":\"注册失败，请稍后重试\"}");
        }
    }
}
