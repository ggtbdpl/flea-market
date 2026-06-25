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
import java.util.UUID;

@WebServlet("/Madd")
@MultipartConfig
public class MProductAddServlet extends ViewBaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Admin admin = (Admin) session.getAttribute("admin");
        req.setAttribute("admin", admin);

        processTemplate("admin/add", req, resp);
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
        String title = req.getParameter("pname");
        String priceStr = req.getParameter("price");
        String condition = req.getParameter("stock");
        String tags = req.getParameter("tags");

        String imagePath = null;
        Part part = req.getPart("img");
        if (part != null && part.getSize() > 0) {
            String originalName = part.getSubmittedFileName();
            String fileName = UUID.randomUUID() + "_" + originalName;
            String realPath = req.getServletContext().getRealPath("/view/admin/imgs/");
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            part.write(realPath + File.separator + fileName);
            imagePath = "/view/admin/imgs/" + fileName;
        }

        Product product = new Product();
        product.setTitle(title);
        product.setPrice(new BigDecimal(priceStr));
        product.setCondition(condition);
        product.setImage(imagePath);
        product.setTags(tags);
        product.setStatus(1);
        ProductDAO productDAO = new ProductDAOImpl();
        productDAO.addProduct(product);
        resp.sendRedirect(req.getContextPath() + "/Mindex");
    }
}
