package com.shumei.DAO;

import com.shumei.pojo.Message;
import java.util.List;

public interface MessageDAO {

    // 根据商品ID获取所有留言（按时间倒序）
    List<Message> getMessagesByProductId(int productId);

    // 提交留言
    boolean addMessage(Message message);

    // 删除留言
    boolean deleteMessage(int messageId);

    // 标记已读
    boolean markAsRead(int messageId);
}