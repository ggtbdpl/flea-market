package com.shumei.DAO;

import com.shumei.pojo.Message;
import java.util.List;

public interface MessageDAO {
    List<Message> getMessagesByProductId(Integer productId);
    boolean addMessage(Message message);
    int replyMessage(Integer id, String replyContent);

    // 组员新增
    List<Message> getAllMessages();
    boolean deleteMessage(Integer id);
    boolean markAsRead(Integer id);
}
