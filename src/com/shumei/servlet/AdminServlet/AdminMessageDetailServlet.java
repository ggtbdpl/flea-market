package com.shumei.servlet.AdminServlet;

import com.shumei.DAO.Impl.MessageDAOImpl;
import com.shumei.DAO.Impl.ProductDAOImpl;
import com.shumei.DAO.MessageDAO;
import com.shumei.DAO.ProductDAO;
import com.shumei.pojo.Message;
import com.shumei.pojo.Product;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/message/detail")
public class AdminMessageDetailServlet extends ViewBaseServlet {

    private MessageDAO messageDAO = new MessageDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdStr = req.getParameter("productId");
        if (productIdStr == null || productIdStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/message");
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(productIdStr.trim());
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/message");
            return;
        }

        Product product = productDAO.getProductById(productId);
        List<Message> messages = messageDAO.getMessagesByProductId(productId);

        req.setAttribute("product", product);
        req.setAttribute("messages", messages);
        processTemplate("admin/message-detail", req, resp);
    }
}