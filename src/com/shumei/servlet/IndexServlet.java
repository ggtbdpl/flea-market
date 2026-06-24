package com.shumei.servlet;

import com.shumei.DAO.CategoryDAO;
import com.shumei.DAO.Impl.CategoryDAOImpl;
import com.shumei.DAO.Impl.ProductDAOImpl;
import com.shumei.DAO.ProductDAO;
import com.shumei.pojo.Category;
import com.shumei.pojo.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {

    private CategoryDAO categoryDAO = new CategoryDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categoryList = categoryDAO.getAll();
        req.setAttribute("categoryList", categoryList);

        ArrayList<Product> productList = productDAO.getProductList();
        req.setAttribute("productList", productList);

        processTemplate("index", req, resp);
    }
}
