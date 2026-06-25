package com.shumei.pojo;

import java.sql.Timestamp;

public class Report {
    private int id;
    private int productId;
    private int reporterId;
    private String reason;
    private int status;
    private int handlerId;
    private Timestamp handleTime;
    private String feedback;
    private Timestamp createTime;
    private String productName;
    private String reporterName;

    public Report() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getReporterId() { return reporterId; }
    public void setReporterId(int reporterId) { this.reporterId = reporterId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public int getHandlerId() { return handlerId; }
    public void setHandlerId(int handlerId) { this.handlerId = handlerId; }

    public Timestamp getHandleTime() { return handleTime; }
    public void setHandleTime(Timestamp handleTime) { this.handleTime = handleTime; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public Timestamp getCreateTime() { return createTime; }
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getReporterName() { return reporterName; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }
}