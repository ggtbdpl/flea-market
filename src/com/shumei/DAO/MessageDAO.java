package com.shumei.DAO;

import com.shumei.pojo.Message;
import java.util.List;

public interface MessageDAO {

    // 前台：根据商品ID获取留言
    List<Message> getMessagesByProductId(int productId);

    // 后台：获取所有留言（关联用户名和商品名）
    List<Message> getAllMessages();

    // 提交留言
    boolean addMessage(Message message);

    // 删除留言
    boolean deleteMessage(int messageId);

    // 标记已读
    boolean markAsRead(int messageId);
}