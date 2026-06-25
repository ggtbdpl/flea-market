package com.shumei.servlet.UserServlet;

import com.shumei.DAO.Impl.UserDAOImpl;
import com.shumei.DAO.UserDAO;
import com.shumei.pojo.User;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/user/edit")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
public class UserEditServlet extends ViewBaseServlet {

    private UserDAO userDAO = new UserDAOImpl();
    private static final String AVATAR_DIR = "static/images/avatars/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/index?showLogin=1");
            return;
        }
        req.setAttribute("user", user);
        processTemplate("edit-profile", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/index?showLogin=1");
            return;
        }

        String nickname = req.getParameter("nickname");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        if (nickname != null && !nickname.trim().isEmpty()) {
            user.setNickname(nickname.trim());
        }
        if (phone != null && !phone.trim().isEmpty()) {
            user.setPhone(phone.trim());
        }

        Part filePart = req.getPart("avatar");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = extractFileName(filePart);
            String ext = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
            if (!ext.matches("\\.(jpg|jpeg|png|gif)")) {
                req.setAttribute("msg", "头像仅支持 jpg/png/gif");
                req.setAttribute("user", user);
                processTemplate("edit-profile", req, resp);
                return;
            }
            String newName = UUID.randomUUID().toString() + ext;
            String realPath = req.getServletContext().getRealPath("/") + AVATAR_DIR;
            File dir = new File(realPath);
            if (!dir.exists()) dir.mkdirs();
            filePart.write(realPath + newName);
            user.setAvatar(AVATAR_DIR + newName);
        }

        if (password != null && !password.isEmpty()) {
            if (!password.equals(confirmPassword)) {
                req.setAttribute("msg", "两次密码输入不一致");
                req.setAttribute("user", user);
                processTemplate("edit-profile", req, resp);
                return;
            }
            userDAO.updatePassword(user.getId(), password);
        }

        boolean ok = userDAO.updateProfile(user);
        if (ok) {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/user/center");
        } else {
            req.setAttribute("msg", "保存失败");
            req.setAttribute("user", user);
            processTemplate("edit-profile", req, resp);
        }
    }

    private String extractFileName(Part part) {
        String cd = part.getHeader("content-disposition");
        for (String token : cd.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 2, token.length() - 1);
            }
        }
        return "";
    }
}
