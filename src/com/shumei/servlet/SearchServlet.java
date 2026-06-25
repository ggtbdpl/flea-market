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

@WebServlet("/search")
public class SearchServlet extends ViewBaseServlet {

    private ProductDAO productDAO = new ProductDAOImpl();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String keyword = req.getParameter("keyword");
        String categoryIdStr = req.getParameter("categoryId");
        String condition = req.getParameter("condition");
        String sort = req.getParameter("sort");
        String pageStr = req.getParameter("page");

        Integer categoryId = null;
        try {
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (Exception e) {
            categoryId = null;
        }

        int page = 1;
        int pageSize = 12;
        try {
            page = Integer.parseInt(pageStr);
            if (page < 1) page = 1;
        } catch (Exception e) {
            page = 1;
        }

        if (sort == null || sort.isEmpty()) {
            sort = "default";
        }

        ArrayList<Product> allResults = productDAO.getProductByFilter(
                keyword != null ? keyword.trim() : null,
                categoryId,
                condition != null ? condition.trim() : null,
                sort
        );

        int totalItems = allResults.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        if (totalPages < 1) totalPages = 1;
        if (page > totalPages) page = totalPages;

        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);
        List<Product> pageList = allResults.subList(fromIndex, toIndex);

        List<Category> categoryList = categoryDAO.getAll();
        ArrayList<String> conditionList = productDAO.getAllConditions();

        req.setAttribute("keyword", keyword);
        req.setAttribute("categoryId", categoryId);
        req.setAttribute("condition", condition);
        req.setAttribute("sort", sort);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalItems", totalItems);
        req.setAttribute("products", pageList);
        req.setAttribute("categoryList", categoryList);
        req.setAttribute("conditionList", conditionList);

        processTemplate("search", req, resp);
    }
}
