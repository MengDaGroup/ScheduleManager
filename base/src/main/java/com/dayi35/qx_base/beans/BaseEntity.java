package com.dayi35.qx_base.beans;

/**
 * Created by ljc on 2018/4/24.
 */

public class BaseEntity<T>{

    private String msg;
    /**
     * SUCCESS(1, "操作成功"
     * REPEAT(2, "操作重复"
     *
     * 全局默认错误FAIL(100,"操作失败"),
     * 全局异常EXCEPTION(101,"系统出了小差")
     * PARAM_IS_INVALID(111, "参数无效"
     * PARAM_IS_BLANK(112, "参数为空")
     * PARAM_TYPE_BIND_ERROR(113, "参数类型错误")
     * PARAM_NOT_COMPLETE(114, "参数缺失")
     *
     * USER_LOGIN_ERROR(202, "账号不存在或密码错误")
     * USER_ACCOUNT_FORBIDDEN(203, "账号已被禁用")
     * USER_NOT_EXIST(204, "用户不存在")
     * USER_HAS_EXISTED(205, "用户已存在")
     * DATA_NOT_EXISTED(210, "数据不存在")
     * ORDER_HAS_CLOSED(211, "订单已关闭")
     *
     * MATCH_TIME(301, "匹配时间禁止操作")
     *
     * USER_NOT_LOGIN(401, "用户未登录")
     * USER_NOT_PERMISSION(403, "用户未授权")
     *
     * DATABASE_CONNECT_ERROR(501, "数据库连接错误")
     * WAIT_TIMEOUT(502, "等待超时")
     *
     * INTERFACE_INNER_INVOKE_ERROR(601, "内部系统接口调用异常")
     * INTERFACE_OUTTER_INVOKE_ERROR(602, "外部系统接口调用异常")
     * INTERFACE_FORBID_VISIT(603, "该接口禁止访问")
     * INTERFACE_ADDRESS_INVALID(604, "接口地址无效")
     * INTERFACE_REQUEST_TIMEOUT(605, "接口请求超时")
     * INTERFACE_EXCEED_LOAD(606, "接口负载过高")
     */
    private int retCode;
    private Boolean succ;
    private String extra;
    private T result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public Boolean getSucc() {
        return succ;
    }

    public void setSucc(Boolean succ) {
        this.succ = succ;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
