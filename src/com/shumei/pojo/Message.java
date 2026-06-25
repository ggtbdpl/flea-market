package com.shumei.pojo;

import java.time.LocalDateTime;

public class Message {
    private Integer id;
    private Integer productId;
    private Integer fromUserId;
    private Integer toUserId;      // 新增
    private String content;
    private String replyContent;
    private Integer isRead;        // 新增 0未读 1已读
    private LocalDateTime replyTime;
    private LocalDateTime createTime;

    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public Integer getFromUserId() { return fromUserId; }
    public void setFromUserId(Integer fromUserId) { this.fromUserId = fromUserId; }

    public Integer getToUserId() { return toUserId; }
    public void setToUserId(Integer toUserId) { this.toUserId = toUserId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getReplyContent() { return replyContent; }
    public void setReplyContent(String replyContent) { this.replyContent = replyContent; }

    public Integer getIsRead() { return isRead; }
    public void setIsRead(Integer isRead) { this.isRead = isRead; }

    public LocalDateTime getReplyTime() { return replyTime; }
    public void setReplyTime(LocalDateTime replyTime) { this.replyTime = replyTime; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
