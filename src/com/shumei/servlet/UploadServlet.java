package com.shumei.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.processTemplate("admin/upload", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");

        Part part = req.getPart("myfile");
        String fileName = UUID.randomUUID()+part.getSubmittedFileName();
        System.out.println(fileName);
        //获取项目路径，斜杠代表web文件夹
        String filepath = req.getServletContext().getRealPath("/view/admin/imgs/");
        System.out.println(filepath);
        String imgpath= "/view/admin/imgs/"+fileName;
        req.setAttribute("url",imgpath);
        part.write(filepath + fileName);
//        req.setAttribute("msg", "上传成功");
        super.processTemplate("admin/upload", req, resp);
    }

}
