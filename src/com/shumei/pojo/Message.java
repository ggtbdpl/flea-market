package com.shumei.pojo;

import java.sql.Timestamp;

public class Message {
    private int id;
    private int productId;
    private int fromUserId;
    private int toUserId;
    private String content;
    private int isRead;
    private Timestamp createTime;

    // 新增：用于后台展示
    private String username;      // 留言人用户名
    private String productName;   // 商品名称

    public Message() {}

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

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
}