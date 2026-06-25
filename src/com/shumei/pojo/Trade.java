package com.shumei.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Trade {
    private Integer id;
    private Integer productId;
    private Integer buyerId;
    private Integer sellerId;
    private BigDecimal amount;
    private String status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime completeTime;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public Integer getBuyerId() { return buyerId; }
    public void setBuyerId(Integer buyerId) { this.buyerId = buyerId; }

    public Integer getSellerId() { return sellerId; }
    public void setSellerId(Integer sellerId) { this.sellerId = sellerId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public LocalDateTime getCompleteTime() { return completeTime; }
    public void setCompleteTime(LocalDateTime completeTime) { this.completeTime = completeTime; }
}
