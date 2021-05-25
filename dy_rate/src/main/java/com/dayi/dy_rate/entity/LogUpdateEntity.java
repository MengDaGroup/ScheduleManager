package com.dayi.dy_rate.entity;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/18 17:51
 * 描    述: 类    更新日志实体
 * 修订历史:
 * =========================================
 */
public class LogUpdateEntity {

    /**
     * id : 0160a472bf0061565c
     * content : 新增交互界面
     * user : 蔡健
     * createdAt : 2021-05-19 10:06:55
     */

    private String id;
    private String content;
    private String user;
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
