package com.shumei.servlet;

import com.shumei.DAO.MessageDAO;
import com.shumei.DAO.Impl.MessageDAOImpl;
import com.shumei.pojo.Message;
import com.shumei.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/message")
public class MessageServlet extends HttpServlet {

    private MessageDAO messageDAO = new MessageDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String productIdStr = req.getParameter("productId");
        PrintWriter out = resp.getWriter();

        if (productIdStr == null || productIdStr.trim().isEmpty()) {
            out.print("{\"success\":false,\"msg\":\"参数错误\"}");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            List<Message> messages = messageDAO.getMessagesByProductId(productId);
            StringBuilder json = new StringBuilder("{\"success\":true,\"data\":[");
            for (int i = 0; i < messages.size(); i++) {
                Message m = messages.get(i);
                json.append("{");
                json.append("\"id\":").append(m.getId()).append(",");
                json.append("\"fromUserId\":").append(m.getFromUserId()).append(",");
                json.append("\"content\":\"").append(escapeJson(m.getContent())).append("\",");
                json.append("\"replyContent\":\"").append(m.getReplyContent() != null ? escapeJson(m.getReplyContent()) : "").append("\",");
                json.append("\"createTime\":\"").append(m.getCreateTime()).append("\",");
                json.append("\"replyTime\":\"").append(m.getReplyTime() != null ? m.getReplyTime() : "").append("\"");
                json.append("}");
                if (i < messages.size() - 1) json.append(",");
            }
            json.append("]}");
            out.print(json.toString());
        } catch (Exception e) {
            out.print("{\"success\":false,\"msg\":\"系统错误\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) {
            out.print("{\"success\":false,\"msg\":\"请先登录\"}");
            return;
        }

        String productIdStr = req.getParameter("productId");
        String content = req.getParameter("content");

        if (productIdStr == null || content == null || content.trim().isEmpty()) {
            out.print("{\"success\":false,\"msg\":\"参数错误\"}");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            Message msg = new Message();
            msg.setProductId(productId);
            msg.setFromUserId(user.getId());
            msg.setContent(content.trim());
            int result = messageDAO.addMessage(msg);
            if (result > 0) {
                out.print("{\"success\":true,\"msg\":\"发表成功\"}");
            } else {
                out.print("{\"success\":false,\"msg\":\"发表失败\"}");
            }
        } catch (Exception e) {
            out.print("{\"success\":false,\"msg\":\"系统错误\"}");
        }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}
