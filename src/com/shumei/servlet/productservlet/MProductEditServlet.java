package com.shumei.servlet.productservlet;

import com.shumei.DAO.CategoryDAO;
import com.shumei.DAO.Impl.CategoryDAOImpl;
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

@WebServlet("/Medit")
public class MProductEditServlet extends ViewBaseServlet {

    private ProductDAO productDAO = new ProductDAOImpl();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Admin admin = (Admin) session.getAttribute("admin");
        req.setAttribute("admin", admin);

        int id = Integer.parseInt(req.getParameter("pid"));
        Product product = productDAO.getProductById(id);
        req.setAttribute("product", product);

        req.setAttribute("categoryList", categoryDAO.getAll());

        processTemplate("admin/edit", req, resp);
    }
}