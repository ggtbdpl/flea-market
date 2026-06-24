package com.shumei.servlet.categoryservlet;

import com.shumei.DAO.CategoryDAO;
import com.shumei.DAO.Impl.CategoryDAOImpl;
import com.shumei.pojo.Category;
import com.shumei.servlet.ViewBaseServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/category")
public class MCategoryIndexServlet extends ViewBaseServlet {

    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> list = categoryDAO.getAll();
        req.setAttribute("categoryList", list);


        processTemplate("admin/category", req, resp);
    }
}
