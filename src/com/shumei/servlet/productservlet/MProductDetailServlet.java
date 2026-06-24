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

@WebServlet("/Mdetail")
public class MProductDetailServlet extends ViewBaseServlet {

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
        int id = Integer.parseInt(req.getParameter("pid"));
        Product product = productDAO.getProductById(id);
        req.setAttribute("product", product);
        processTemplate("admin/detail", req, resp);
    }
}
