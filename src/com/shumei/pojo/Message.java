package com.shumei.pojo;

import java.time.LocalDateTime;

public class Message {
    private Integer id;
    private Integer productId;
    private Integer fromUserId;
    private String content;
    private String replyContent;
    private LocalDateTime replyTime;
    private LocalDateTime createTime;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public Integer getFromUserId() { return fromUserId; }
    public void setFromUserId(Integer fromUserId) { this.fromUserId = fromUserId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getReplyContent() { return replyContent; }
    public void setReplyContent(String replyContent) { this.replyContent = replyContent; }

    public LocalDateTime getReplyTime() { return replyTime; }
    public void setReplyTime(LocalDateTime replyTime) { this.replyTime = replyTime; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
