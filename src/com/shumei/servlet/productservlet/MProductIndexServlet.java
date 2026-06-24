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
import java.util.ArrayList;

@WebServlet("/Mindex")
public class MProductIndexServlet extends ViewBaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Admin admin = (Admin) session.getAttribute("admin");
        req.setAttribute("admin", admin);

        ProductDAO productDAO = new ProductDAOImpl();
        ArrayList<Product> list = productDAO.getProductList();
        req.setAttribute("products", list);
        processTemplate("admin/index", req, resp);
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
        String keyword = req.getParameter("keyword");
        req.setAttribute("keyword", keyword);
        ProductDAO productDAO = new ProductDAOImpl();
        ArrayList<Product> list = productDAO.getProductByKeyword(keyword);
        req.setAttribute("products", list);
        processTemplate("admin/index", req, resp);
    }
}
