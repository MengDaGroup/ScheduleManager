package com.dayi.dy_rate.entity;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/12 16:16
 * 描    述: 类    项目详情实体
 * 修订历史:
 * =========================================
 */
public class ProjectDetailEntity {

    /**
     * id : 016098c9b4004da238
     * name : 雀喜易购
     * status : 4
     * belongId : 016001329d0055a9b0
     * teamId : 1
     * progress : 24.801
     * startTime : 2021-05-01
     * endTime : 2021-05-03
     * os : 1
     * createdAt : 2021-05-10 13:50:44
     * updatedAt : 2021-05-11 15:27:40
     */

    private String id;
    private String name;
    private int status;
    private String belongId;
    private String teamId;
    private float progress;
    private String startTime;
    private String endTime;
    private int os;
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

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
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

    public int getOs() {
        return os;
    }

    public void setOs(int os) {
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
