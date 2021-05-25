package com.dayi.dy_rate.entity;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/14 10:19
 * 描    述: 类 组件详情实体（模块）
 * 修订历史:
 * =========================================
 */
public class ModuleDetailEntity {

    /**
     * id : 01609ba2ef0487aa38
     * name : 雀喜易购-睡觉
     * belongId : 016001329d0055a9b1
     * projectId : 01609ba15d05743f74
     * projectName : 雀喜易购-吃饭
     * startTime : 2021-05-15 00:00:00
     * progress : 0.000
     * endTime : 2021-05-20 00:00:00
     * createdAt : 2021-05-12 17:42:07
     * updatedAt : 2021-05-12 17:42:07
     */

    private String id;
    private String name;
    private String belongId;
    private String projectId;
    private String projectName;
    private String startTime;
    private float progress;
    private String endTime;
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

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
