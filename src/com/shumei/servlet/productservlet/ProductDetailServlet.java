package com.shumei.servlet.productservlet;

import com.shumei.DAO.CategoryDAO;
import com.shumei.DAO.Impl.CategoryDAOImpl;
import com.shumei.DAO.Impl.ProductDAOImpl;
import com.shumei.DAO.Impl.UserDAOImpl;
import com.shumei.DAO.ProductDAO;
import com.shumei.DAO.UserDAO;
import com.shumei.pojo.Category;
import com.shumei.pojo.Product;
import com.shumei.pojo.User;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.shumei.DAO.MessageDAO;
import com.shumei.DAO.TradeDAO;
import com.shumei.DAO.Impl.MessageDAOImpl;
import com.shumei.DAO.Impl.TradeDAOImpl;
import com.shumei.pojo.Message;
import com.shumei.pojo.Trade;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/product")
public class ProductDetailServlet extends ViewBaseServlet {

    private ProductDAO productDAO = new ProductDAOImpl();
    private UserDAO userDAO = new UserDAOImpl();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();
    private MessageDAO messageDAO = new MessageDAOImpl();
    private TradeDAO tradeDAO = new TradeDAOImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr.trim());
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }

        Product product = productDAO.getProductById(id);
        if (product == null) {
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }

        User seller = null;
        if (product.getUserId() != null) {
            seller = userDAO.getUserById(product.getUserId());
        }

        Category category = null;
        if (product.getCategoryId() != null) {
            category = categoryDAO.getById(product.getCategoryId());
        }

        req.setAttribute("product", product);
        req.setAttribute("seller", seller);
        req.setAttribute("category", category);

        // 留言列表
        List<Message> messages = messageDAO.getMessagesByProductId(id);
        Map<Integer, String> messageUserNames = new HashMap<>();
        for (Message msg : messages) {
            if (msg.getFromUserId() != null && !messageUserNames.containsKey(msg.getFromUserId())) {
                User u = userDAO.getUserById(msg.getFromUserId());
                messageUserNames.put(msg.getFromUserId(), u != null ? u.getNickname() : "未知用户");
            }
        }

        // 当前登录用户
        HttpSession session = req.getSession(false);
        User currentUser = null;
        if (session != null) {
            currentUser = (User) session.getAttribute("user");
        }

        // 当前用户与该商品的最新交易
        Trade currentTrade = null;
        if (currentUser != null && product.getUserId() != null) {
            currentTrade = tradeDAO.getTradeByProductAndBuyer(id, currentUser.getId());
        }

        // 多图处理：images 字段逗号分隔
        String[] imageList = null;
        if (product.getImages() != null && !product.getImages().trim().isEmpty()) {
            imageList = product.getImages().split(",");
        }

        req.setAttribute("messages", messages);
        req.setAttribute("messageUserNames", messageUserNames);
        req.setAttribute("currentUser", currentUser);
        req.setAttribute("currentTrade", currentTrade);
        req.setAttribute("imageList", imageList);


        processTemplate("product", req, resp);
    }
}
