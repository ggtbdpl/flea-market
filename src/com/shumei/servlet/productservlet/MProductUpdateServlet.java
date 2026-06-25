package com.shumei.servlet.productservlet;

import com.shumei.DAO.Impl.ProductDAOImpl;
import com.shumei.DAO.ProductDAO;
import com.shumei.pojo.Admin;
import com.shumei.pojo.Product;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/Mupdate")
@MultipartConfig
public class MProductUpdateServlet extends ViewBaseServlet {

    private ProductDAO productDAO = new ProductDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Admin admin = (Admin) session.getAttribute("admin");
        req.setAttribute("admin", admin);

        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("pid"));
        String title = req.getParameter("pname");
        String priceStr = req.getParameter("price");
        String condition = req.getParameter("stock");
        String originalPriceStr = req.getParameter("originalPrice");
        String description = req.getParameter("description");
        String contact = req.getParameter("contact");
        String categoryIdStr = req.getParameter("categoryId");
        String statusStr = req.getParameter("status");

        System.out.println("=== Mupdate Debug ===");
        System.out.println("pid: " + id);
        System.out.println("statusStr: " + statusStr);

        Product oldProduct = productDAO.getProductById(id);
        String imagePath = oldProduct != null ? oldProduct.getImage() : null;

        Part part = req.getPart("img");
        if (part != null && part.getSize() > 0) {
            String originalName = part.getSubmittedFileName();
            String fileName = System.currentTimeMillis() + "_" + originalName;
            String realPath = req.getServletContext().getRealPath("/static/images/");
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            part.write(realPath + File.separator + fileName);
            imagePath = fileName;
        }

        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setPrice(new BigDecimal(priceStr));
        product.setCondition(condition);
        product.setImage(imagePath);
        product.setDescription(description);
        product.setContact(contact);

        if (originalPriceStr != null && !originalPriceStr.isEmpty()) {
            product.setOriginalPrice(new BigDecimal(originalPriceStr));
        } else if (oldProduct != null) {
            product.setOriginalPrice(oldProduct.getOriginalPrice());
        }

        if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
            product.setCategoryId(Integer.parseInt(categoryIdStr));
        } else if (oldProduct != null) {
            product.setCategoryId(oldProduct.getCategoryId());
        }

        // 强制更新状态，确保表单传来的值被写入
        if (statusStr != null && !statusStr.trim().isEmpty()) {
            product.setStatus(Integer.parseInt(statusStr.trim()));
            System.out.println("Setting status to: " + statusStr);
        } else if (oldProduct != null) {
            product.setStatus(oldProduct.getStatus());
            System.out.println("Keeping old status: " + oldProduct.getStatus());
        }

        if (oldProduct != null) {
            product.setUserId(oldProduct.getUserId());
            product.setImages(oldProduct.getImages());
        }

        boolean result = productDAO.updateProduct(product);
        System.out.println("Update result: " + result);
        System.out.println("====================");

        resp.sendRedirect(req.getContextPath() + "/admin/product");
    }
}