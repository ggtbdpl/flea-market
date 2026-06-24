package com.shumei.servlet.UserServlet;

import com.shumei.DAO.Impl.ProductDAOImpl;
import com.shumei.DAO.ProductDAO;
import com.shumei.pojo.Product;
import com.shumei.pojo.User;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/user/center")
public class UserCenterServlet extends ViewBaseServlet {

    private ProductDAO productDAO = new ProductDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }

        // 查询该用户发布的商品
        ArrayList<Product> myProducts = new ArrayList<>();
        ArrayList<Product> allProducts = productDAO.getProductList();
        for (Product p : allProducts) {
            if (user.getId().equals(p.getUserId())) {
                myProducts.add(p);
            }
        }

        req.setAttribute("user", user);
        req.setAttribute("myProducts", myProducts);
        processTemplate("user-center", req, resp);
    }
}
