package com.shumei.servlet.AdminServlet;

import com.shumei.DAO.Impl.ProductDAOImpl;
import com.shumei.DAO.ProductDAO;
import com.shumei.pojo.Product;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/admin/product")
public class AdminProductServlet extends ViewBaseServlet {

    private ProductDAO productDAO = new ProductDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setAttribute("admin", session.getAttribute("admin"));

        String action = req.getParameter("action");
        if ("del".equals(action)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                productDAO.delProduct(Integer.parseInt(idStr));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/product");
            return;
        }

        ArrayList<Product> list = productDAO.getAllProducts();
        req.setAttribute("products", list);

        processTemplate("admin/product", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setAttribute("admin", session.getAttribute("admin"));

        req.setCharacterEncoding("UTF-8");
        String keyword = req.getParameter("keyword");
        req.setAttribute("keyword", keyword);

        ArrayList<Product> list;
        if (keyword != null && !keyword.trim().isEmpty()) {
            list = productDAO.getProductByKeyword(keyword.trim());
        } else {
            list = productDAO.getAllProducts();
        }
        req.setAttribute("products", list);

        processTemplate("admin/product", req, resp);
    }
}