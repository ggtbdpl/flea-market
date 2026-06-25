package com.shumei.servlet.AdminServlet;

import com.shumei.DAO.MessageDAO;
import com.shumei.DAO.Impl.MessageDAOImpl;
import com.shumei.pojo.Message;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/message")
public class AdminMessageServlet extends ViewBaseServlet {

    private MessageDAO messageDAO = new MessageDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("read".equals(action)) {
            String messageIdStr = req.getParameter("messageId");
            if (messageIdStr != null) {
                messageDAO.markAsRead(Integer.parseInt(messageIdStr));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/message");
            return;
        }

        if ("delete".equals(action)) {
            String messageIdStr = req.getParameter("messageId");
            if (messageIdStr != null) {
                messageDAO.deleteMessage(Integer.parseInt(messageIdStr));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/message");
            return;
        }

        List<Message> messageList = messageDAO.getAllMessages();
        req.setAttribute("messageList", messageList);
        processTemplate("admin/message", req, resp);
    }
}