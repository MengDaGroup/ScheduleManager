package com.dayi.dy_rate.entity;

import cn.bmob.v3.BmobObject;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/13 14:46
 * 描    述: 类    项目实体
 * 修订历史:
 * =========================================
 */
public class ProjectRateEntity extends BmobObject {
    private String projectProgress;             //项目进度
    private String projectName;                 //项目名

    public String getProjectProgress() {
        return projectProgress;
    }

    public void setProjectProgress(String projectProgress) {
        this.projectProgress = projectProgress;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
