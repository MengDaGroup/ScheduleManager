package com.dayi.dy_rate.entity;


/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/7 10:28
 * 描    述: 类        用户实体
 * 修订历史:
 * =========================================
 */
public class UserEntity {
    private String userId;              //用户ID
    private String userNickName;        //用户昵称

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }
}
