package com.dayi.dy_rate.entity;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/14 10:21
 * 描    述: 类 功能实体（成分）
 * 修订历史:
 * =========================================
 */
public class ComponentEntity {

    /**
     * id : 0160a2291603037c00
     * name : 睡觉
     * projectId : 01609ba15d05743f74
     * moduleId : 0160a1c56503d25908
     * projectName : 雀喜易购-吃饭
     * moduleName : 雀喜麻将
     * progress : 0.000
     * startTime : 2021-05-18 00:00:00
     * endTime : 2021-05-19 00:00:00
     * createdAt : 2021-05-17 16:28:06
     * updatedAt : 2021-05-17 16:28:06
     */

    private String id;
    private String name;
    private String projectId;
    private String moduleId;
    private String projectName;
    private String moduleName;
    private float progress;
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

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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
