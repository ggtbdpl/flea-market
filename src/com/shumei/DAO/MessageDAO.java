package com.shumei.DAO;

import com.shumei.pojo.Message;
import java.util.List;

public interface MessageDAO {
    List<Message> getMessagesByProductId(Integer productId);
    int addMessage(Message message);
    int replyMessage(Integer id, String replyContent);
}
