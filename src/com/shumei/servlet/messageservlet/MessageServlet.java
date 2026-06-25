package com.shumei.servlet.messageservlet;

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

@WebServlet("/api/message")
public class MessageServlet extends HttpServlet {

    private MessageDAO messageDAO = new MessageDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();

        String action = req.getParameter("action");

        // 获取留言列表
        if ("list".equals(action)) {
            String productIdStr = req.getParameter("productId");
            if (productIdStr == null || productIdStr.isEmpty()) {
                out.print("{\"code\":400,\"msg\":\"缺少商品ID\"}");
                return;
            }
            int productId = Integer.parseInt(productIdStr);
            List<Message> list = messageDAO.getMessagesByProductId(productId);

            // 手动拼JSON
            StringBuilder json = new StringBuilder();
            json.append("{\"code\":200,\"data\":[");
            for (int i = 0; i < list.size(); i++) {
                Message m = list.get(i);
                if (i > 0) json.append(",");
                json.append("{");
                json.append("\"id\":").append(m.getId()).append(",");
                json.append("\"productId\":").append(m.getProductId()).append(",");
                json.append("\"fromUserId\":").append(m.getFromUserId()).append(",");
                json.append("\"toUserId\":").append(m.getToUserId()).append(",");
                json.append("\"content\":\"").append(escapeJson(m.getContent())).append("\",");
                json.append("\"isRead\":").append(m.getIsRead()).append(",");
                json.append("\"createTime\":\"").append(m.getCreateTime()).append("\"");
                json.append("}");
            }
            json.append("]}");
            out.print(json.toString());
        } else {
            out.print("{\"code\":400,\"msg\":\"未知操作\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();

        String action = req.getParameter("action");

        // 检查是否登录
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            out.print("{\"code\":401,\"msg\":\"请先登录\"}");
            return;
        }

        // 提交留言
        if ("add".equals(action)) {
            String productIdStr = req.getParameter("productId");
            String content = req.getParameter("content");
            String toUserIdStr = req.getParameter("toUserId");

            if (productIdStr == null || content == null || content.trim().isEmpty()) {
                out.print("{\"code\":400,\"msg\":\"参数不完整\"}");
                return;
            }

            Message message = new Message();
            message.setProductId(Integer.parseInt(productIdStr));
            message.setFromUserId(user.getId());  // Integer 自动拆箱为 int
            message.setContent(content.trim());

            // toUserId 可选，如果传了就设置
            if (toUserIdStr != null && !toUserIdStr.isEmpty()) {
                message.setToUserId(Integer.parseInt(toUserIdStr));
            } else {
                message.setToUserId(0);
            }

            boolean result = messageDAO.addMessage(message);
            if (result) {
                out.print("{\"code\":200,\"msg\":\"留言成功\"}");
            } else {
                out.print("{\"code\":500,\"msg\":\"留言失败\"}");
            }
        }

        // 删除留言
        else if ("delete".equals(action)) {
            String messageIdStr = req.getParameter("messageId");
            if (messageIdStr == null) {
                out.print("{\"code\":400,\"msg\":\"缺少留言ID\"}");
                return;
            }
            boolean result = messageDAO.deleteMessage(Integer.parseInt(messageIdStr));
            if (result) {
                out.print("{\"code\":200,\"msg\":\"删除成功\"}");
            } else {
                out.print("{\"code\":500,\"msg\":\"删除失败\"}");
            }
        }

        // 标记已读
        else if ("read".equals(action)) {
            String messageIdStr = req.getParameter("messageId");
            if (messageIdStr == null) {
                out.print("{\"code\":400,\"msg\":\"缺少留言ID\"}");
                return;
            }
            boolean result = messageDAO.markAsRead(Integer.parseInt(messageIdStr));
            if (result) {
                out.print("{\"code\":200,\"msg\":\"标记成功\"}");
            } else {
                out.print("{\"code\":500,\"msg\":\"标记失败\"}");
            }
        }

        else {
            out.print("{\"code\":400,\"msg\":\"未知操作\"}");
        }
    }

    // JSON 转义辅助方法
    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}