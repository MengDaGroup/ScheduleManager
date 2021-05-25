package com.dayi.dy_rate.entity;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/13 11:01
 * 描    述: 类    组件实体类(模块)
 * 修订历史:
 * =========================================
 */
public class ModuleEntity {

    /**
     * id : 016098c9ef031abf78
     * name : 管理员模块
     * projectId : 016098c9b4004da238
     * progress : 49.600
     * projectName : 雀喜易购
     * startTime : 2021-06-30 00:00:00
     * endTime : 2021-07-31 00:00:00
     * createdAt : 2021-05-10 13:51:43
     * updatedAt : 2021-05-12 08:51:34
     */

    private String id;
    private String name;
    private String projectId;
    private float progress;
    private String projectName;
    private String startTime;
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
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
