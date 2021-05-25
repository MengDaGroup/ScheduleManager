package com.dayi.dy_rate.entity;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/7 10:31
 * 描    述: 类 项目实体
 * 修订历史:
 * =========================================
 */
public class ProjectEntity {


    /**
     * id : 016098c9b4004da238
     * name : 雀喜易购
     * status : 4
     * statusStr : 逾期
     * progress : 24.801
     * startTime : 2021-05-01
     * endTime : 2021-05-03
     * os : 安卓
     * createdAt : 2021-05-10 13:50:44
     * updatedAt : 2021-05-11 15:27:40
     */

    private String id;
    private String name;
    private int status;
    private String statusStr;
    private float progress;
    private String startTime;
    private String endTime;
    private String os;
    private String createdAt;
    private String updatedAt;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
