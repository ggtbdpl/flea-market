package com.shumei.servlet;

import com.shumei.DAO.CategoryDAO;
import com.shumei.DAO.Impl.CategoryDAOImpl;
import com.shumei.DAO.Impl.ProductDAOImpl;
import com.shumei.DAO.ProductDAO;
import com.shumei.pojo.Category;
import com.shumei.pojo.Product;
import com.shumei.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/publish")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 20)
public class PublishServlet extends ViewBaseServlet {

    private ProductDAO productDAO = new ProductDAOImpl();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();
    private static final String UPLOAD_DIR = "static/images/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/index?showLogin=1");
            return;
        }
        List<Category> categories = categoryDAO.getAll();
        req.setAttribute("categories", categories != null ? categories : new ArrayList<>());
        processTemplate("publish", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/index?showLogin=1");
            return;
        }

        String title = req.getParameter("title");
        String categoryIdStr = req.getParameter("categoryId");
        String priceStr = req.getParameter("price");
        String originalPriceStr = req.getParameter("originalPrice");
        String condition = req.getParameter("condition");
        String description = req.getParameter("description");
        String contact = req.getParameter("contact");

        if (title == null || title.trim().isEmpty() || priceStr == null || priceStr.isEmpty()) {
            req.setAttribute("msg", "标题和价格为必填项");
            doGet(req, resp);
            return;
        }

        // 保存封面图
        String coverImage = "";
        Part coverPart = req.getPart("coverImage");
        if (coverPart != null && coverPart.getSize() > 0) {
            coverImage = saveFile(coverPart, req);
        }

        // 保存详情图
        List<String> detailImages = new ArrayList<>();
        for (Part part : req.getParts()) {
            if ("detailImages".equals(part.getName()) && part.getSize() > 0) {
                String fn = saveFile(part, req);
                if (!fn.isEmpty()) detailImages.add(fn);
            }
        }

        Product p = new Product();
        p.setTitle(title.trim());
        p.setCategoryId(Integer.parseInt(categoryIdStr));
        p.setPrice(new BigDecimal(priceStr));
        p.setOriginalPrice(originalPriceStr != null && !originalPriceStr.isEmpty() ? new BigDecimal(originalPriceStr) : null);
        p.setCondition(condition != null ? condition : "9成新");
        p.setDescription(description != null ? description : "");
        p.setImage(coverImage);
        p.setImages(String.join(",", detailImages));
        p.setContact(contact != null ? contact : "");
        p.setUserId(user.getId());
        p.setStatus(1);

        boolean ok = productDAO.addProduct(p);
        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/user/center");
        } else {
            req.setAttribute("msg", "发布失败，请重试");
            doGet(req, resp);
        }
    }

    private String saveFile(Part part, HttpServletRequest req) throws IOException {
        String fileName = extractFileName(part);
        if (fileName.isEmpty()) return "";
        String ext = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
        if (!ext.matches("\\.(jpg|jpeg|png|gif)")) return "";
        String newName = UUID.randomUUID().toString() + ext;
        String realPath = req.getServletContext().getRealPath("/") + UPLOAD_DIR;
        File dir = new File(realPath);
        if (!dir.exists()) dir.mkdirs();
        part.write(realPath + newName);
        return newName;
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
