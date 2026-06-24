package com.shumei.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;

@WebServlet("/delete")
public class DeleteServlet extends ViewBaseServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imgpath = req.getParameter("url");
        String realPath = req.getServletContext().getRealPath(imgpath);
        System.out.println(realPath);
        File file = new File(realPath);
        if (file.exists()) {
            file.delete();
        }
        resp.sendRedirect(req.getContextPath() + "/upload");
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }
}
