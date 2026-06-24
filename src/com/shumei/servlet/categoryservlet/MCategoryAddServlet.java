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

@WebServlet("/admin/category/add")
public class MCategoryAddServlet extends ViewBaseServlet {

    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Category c = new Category();
        c.setName(req.getParameter("name"));
        c.setDescription(req.getParameter("description"));
        c.setIcon(req.getParameter("icon"));
        c.setSortOrder(Integer.parseInt(req.getParameter("sortOrder")));
        c.setStatus(1);
        categoryDAO.add(c);
        resp.sendRedirect(req.getContextPath() + "/admin/category");
    }
}
