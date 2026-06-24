package com.shumei.pojo;

import java.sql.Timestamp;

public class Message {
    private int id;
    private int productId;      // 商品ID
    private int fromUserId;     // 留言人ID
    private int toUserId;       // 接收人ID（卖家ID，或回复对象的用户ID）
    private String content;     // 内容
    private int isRead;         // 是否已读：0未读 1已读
    private Timestamp createTime;

    public Message() {}

    // ===== Getter & Setter =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getFromUserId() { return fromUserId; }
    public void setFromUserId(int fromUserId) { this.fromUserId = fromUserId; }

    public int getToUserId() { return toUserId; }
    public void setToUserId(int toUserId) { this.toUserId = toUserId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getIsRead() { return isRead; }
    public void setIsRead(int isRead) { this.isRead = isRead; }

    public Timestamp getCreateTime() { return createTime; }
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }
}