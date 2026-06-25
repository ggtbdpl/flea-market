package com.shumei.servlet.productservlet;

import com.shumei.DAO.Impl.ProductDAOImpl;
import com.shumei.DAO.ProductDAO;
import com.shumei.pojo.Admin;
import com.shumei.pojo.Product;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/Mupdate")
public class MProductUpdateServlet extends ViewBaseServlet {

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
        ProductDAO productDAO = new ProductDAOImpl();
        int id = Integer.parseInt(req.getParameter("pid"));
        String title = req.getParameter("pname");
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        String condition = req.getParameter("stock");
        String tags = req.getParameter("tags");

        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setPrice(price);
        product.setCondition(condition);
        product.setTags(tags);
        productDAO.updateProduct(product);
        resp.sendRedirect(req.getContextPath() + "/Mindex");
    }
}
