package com.dayi.dy_rate.entity;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/7 10:31
 * 描    述: 类 项目集合实体
 * 修订历史:
 * =========================================
 */
public class ProjectTeamEntity extends BmobObject {
    private List<String> projectNote;           //项目备注
    private String projectOS;                   //项目端1.Android 2.IOS
    private String projectEndTime;              //项目结束时间
    private String projectStartTime;            //项目开始时间
    private String createTime;                  //项目要创建时间
    private String createUser;                  //项目创建者
    private String updateTime;                  //项目更新时间
    private String updateUser;                  //项目更新者
    private String projectProgress;             //项目进度
    private String projectBelong;               //项目归属
    private String projectName;                 //项目名
    private String projectRateId;               //项目归属ID
    private String projectRateName;             //项目归属名
    private int projectState;                   //项目状态 1.进行中 2.已结束

    public String getProjectRateId() {
        return projectRateId;
    }

    public void setProjectRateId(String projectRateId) {
        this.projectRateId = projectRateId;
    }

    public String getProjectRateName() {
        return projectRateName;
    }

    public void setProjectRateName(String projectRateName) {
        this.projectRateName = projectRateName;
    }

    public List<String> getProjectNote() {
        return projectNote;
    }

    public void setProjectNote(List<String> projectNote) {
        this.projectNote = projectNote;
    }

    public String getProjectOS() {
        return projectOS;
    }

    public void setProjectOS(String projectOS) {
        this.projectOS = projectOS;
    }

    public String getProjectEndTime() {
        return projectEndTime;
    }

    public void setProjectEndTime(String projectEndTime) {
        this.projectEndTime = projectEndTime;
    }

    public String getProjectStartTime() {
        return projectStartTime;
    }

    public void setProjectStartTime(String projectStartTime) {
        this.projectStartTime = projectStartTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getProjectProgress() {
        return projectProgress;
    }

    public void setProjectProgress(String projectProgress) {
        this.projectProgress = projectProgress;
    }

    public String getProjectBelong() {
        return projectBelong;
    }

    public void setProjectBelong(String projectBelong) {
        this.projectBelong = projectBelong;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectState() {
        return projectState;
    }

    public void setProjectState(int projectState) {
        this.projectState = projectState;
    }
}
