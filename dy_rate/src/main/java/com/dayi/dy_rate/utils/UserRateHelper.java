package com.dayi.dy_rate.utils;

import android.text.TextUtils;

import com.dayi.dy_rate.entity.UserEntity;
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
public class UserRateHelper {
    private static UserRateHelper instance;
    private final Gson mGson;
    private UserEntity mUserInfo;
    private String mUserName;
    private String mPassWord;
    private static final String USER_INFO = "user_helper_user_info";
    private static final String USER_NAME = "user_helper_user_name";
    private static final String PASS_WORD = "user_helper_pass_word";

    private UserRateHelper() {
        mGson = new GsonBuilder().serializeNulls().create();
        loadUserInfoFromSp();
        if (mUserInfo == null) {
            mUserInfo = new UserEntity();
        }
    }

    public static UserRateHelper get() {
        if (null == instance) {
            instance = new UserRateHelper();
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
     * 获取用户昵称
     * @return
     */
    public String getUserNickName(){
        checkEmptyAndLoad(mUserInfo.getUserNickName());
        return mUserInfo.getUserNickName();
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
        checkEmptyAndLoad(mUserInfo.getUserId());
        return mUserInfo.getUserId();
    }

    /**
     * 退出
     */
    public void logOut(){
        saveUserName("");
        savePassWord("");
        saveUser(new UserEntity());
    }

    /**
     * 保存用户信息实体
     * @param entity
     */
    public void saveUser(UserEntity entity){
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
            UserEntity userInfo = mGson.fromJson(json, UserEntity.class);
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
