package model;

import java.util.Date;

public class Request {

    private int reqID;
    private int createBy;
    private Date createTime;
    private Date from;
    private Date to;
    private String reason;
    private int statusID;
    private Integer approvedBy;    // Có thể null
    private Date approvedTime;
    private String depName;
    private String roleName;
    private String statusName;
    private String name;
    private String approvedName;

    public Request() {
    }

    public Request(int reqID, int createBy, Date createTime, Date from, Date to,
            String reason, int statusID, Integer approvedBy, Date approvedTime) {
        this.reqID = reqID;
        this.createBy = createBy;
        this.createTime = createTime;
        this.from = from;
        this.to = to;
        this.reason = reason;
        this.statusID = statusID;
        this.approvedBy = approvedBy;
        this.approvedTime = approvedTime;
    }

    public Request(int reqID, int createBy, Date createTime, Date from, Date to, String reason, int statusID, Integer approvedBy, Date approvedTime, String depName, String roleName, String statusName, String name, String approvedName) {
        this.reqID = reqID;
        this.createBy = createBy;
        this.createTime = createTime;
        this.from = from;
        this.to = to;
        this.reason = reason;
        this.statusID = statusID;
        this.approvedBy = approvedBy;
        this.approvedTime = approvedTime;
        this.depName = depName;
        this.roleName = roleName;
        this.statusName = statusName;
        this.name = name;
        this.approvedName = approvedName;
    }

    
    
    

    // Getters & Setters
    public int getReqID() {
        return reqID;
    }

    public void setReqID(int reqID) {
        this.reqID = reqID;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public Integer getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedTime() {
        return approvedTime;
    }

    public void setApprovedTime(Date approvedTime) {
        this.approvedTime = approvedTime;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public String getApprovedName() {
        return approvedName;
    }

    public void setApprovedName(String approvedName) {
        this.approvedName = approvedName;
    }
    
    

    @Override
    public String toString() {
        return "Request{" + "reqID=" + reqID + ", createBy=" + createBy + ", createTime=" + createTime + ", from=" + from + ", to=" + to + ", reason=" + reason + ", statusID=" + statusID + ", approvedBy=" + approvedBy + ", approvedTime=" + approvedTime + ", depName=" + depName + ", roleName=" + roleName + ", statusName=" + statusName + '}';
    }

    
    

    
}
