package com.dayi35.qx_base.entity;

import cn.bmob.v3.BmobObject;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/3/31 14:47
 * 描    述: 类    用户信息实体
 * 修订历史:
 * =========================================
 */
public class jd_user_common extends BmobObject {
    private String user_id;             //用户ID      用户唯一标识即bmobuser中的objId
    private String nickName;            //用户昵称
    private String userType;            //用户类型    1.车主，2管理员，3超级管理员
    private String carNumber;           //车牌号
    private String teamName;            //车队名
    private String team_id;             //车队ID
    private String phoneIm;             //手机IM码
    private String ipAdress;            //IP

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getPhoneIm() {
        return phoneIm;
    }

    public void setPhoneIm(String phoneIm) {
        this.phoneIm = phoneIm;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }
}
