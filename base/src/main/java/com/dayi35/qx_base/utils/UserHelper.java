package com.dayi35.qx_base.utils;

import android.text.TextUtils;

import com.dayi35.qx_base.entity.jd_user_common;
import com.dayi35.qx_utils.androidcodeutils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/3/31 14:48
 * 描    述: 类    用户本地管理类
 * 修订历史:
 * =========================================
 */
public class UserHelper {
    private static UserHelper instance;
    private final Gson mGson;
    private jd_user_common mUserInfo;
    private String mUserName;
    private String mPassWord;
    private static final String USER_INFO = "user_helper_user_info";
    private static final String USER_NAME = "user_helper_user_name";
    private static final String PASS_WORD = "user_helper_pass_word";

    private UserHelper() {
        mGson = new GsonBuilder().serializeNulls().create();
        loadUserInfoFromSp();
        if (mUserInfo == null) {
            mUserInfo = new jd_user_common();
        }
    }

    public static UserHelper get() {
        if (null == instance) {
            instance = new UserHelper();
        }
        return instance;
    }

    /**
     * 获取用户名
     * @return
     */
    public String getUserName(){
        if (TextUtils.isEmpty(mUserName)){
            mUserName = SPUtils.getInstance().getString(USER_NAME);
        }
        return mUserName;
    }

    /**
     * 获取密码
     * @return
     */
    public String getPassWord(){
        if (TextUtils.isEmpty(mPassWord)){
            mPassWord = SPUtils.getInstance().getString(PASS_WORD);
        }
        return mPassWord;
    }

    /**
     * 获取用户ID
     * @return
     */
    private String getUserId(){
        checkEmptyAndLoad(mUserInfo);
        return mUserInfo.getUser_id();
    }

    /**
     * 退出
     */
    public void logOut(){
        saveUserName("");
        savePassWord("");
        saveUser(new jd_user_common());
    }

    /**
     * 保存用户信息实体
     * @param entity
     */
    public void saveUser(jd_user_common entity){
        String json = mGson.toJson(entity);
        SPUtils.getInstance().put(USER_INFO, json);
    }

    /**
     * 保存用户名
     * @param mUserName
     */
    public void saveUserName(String mUserName){
        SPUtils.getInstance().put(USER_NAME, mUserName);
    }

    /**
     * 保存密码
     * @param mPassWord
     */
    public void savePassWord(String mPassWord){
        SPUtils.getInstance().put(PASS_WORD, mPassWord);
    }

    /**
     * 从SP中加载UserInfo
     */
    private void loadUserInfoFromSp() {
        String json = SPUtils.getInstance().getString(USER_INFO);
        try {
            jd_user_common userInfo = mGson.fromJson(json, jd_user_common.class);
            if (userInfo != null) {
                mUserInfo = userInfo;
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测数据是否为空，如果为空从sp中加载一次
     * @param value
     */
    private void checkEmptyAndLoad(Object value) {
        if (value == null) {
            loadUserInfoFromSp();
        }
    }

    /**
     * 检测int是否为0，如果为0从sp中加载一次
     * @param value
     */
    private void checkEmptyAndLoad(int value) {
        if (0 == value) {
            loadUserInfoFromSp();
        }
    }

}
