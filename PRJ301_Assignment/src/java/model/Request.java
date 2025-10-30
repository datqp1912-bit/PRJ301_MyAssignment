package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Request {
    private int reqID;
    private Employee createdBy;
    private Date from;
    private Date to;
    private String reason;
    private RequestStatus status;
    private Employee approvedBy;
    private Timestamp createTime;
    private Timestamp approvedTime;

    public Request() {}

    public Request(int reqID, Employee createdBy, Date from, Date to, String reason, RequestStatus status,
                   Employee approvedBy, Timestamp createTime, Timestamp approvedTime) {
        this.reqID = reqID;
        this.createdBy = createdBy;
        this.from = from;
        this.to = to;
        this.reason = reason;
        this.status = status;
        this.approvedBy = approvedBy;
        this.createTime = createTime;
        this.approvedTime = approvedTime;
    }

    public int getReqID() {
        return reqID;
    }
    public void setReqID(int reqID) {
        this.reqID = reqID;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Employee createdBy) {
        this.createdBy = createdBy;
    }

    public Date getFrom() {
        return from;
    }
    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }
    public void setTo(Date to) {
        this.to = to;
    }

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    public RequestStatus getStatus() {
        return status;
    }
    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }
    public void setApprovedBy(Employee approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getApprovedTime() {
        return approvedTime;
    }
    public void setApprovedTime(Timestamp approvedTime) {
        this.approvedTime = approvedTime;
    }
}
