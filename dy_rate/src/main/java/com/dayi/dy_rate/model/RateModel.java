package com.dayi.dy_rate.model;

import android.text.TextUtils;

import com.dayi.dy_rate.api.RateApi;
import com.dayi.dy_rate.entity.ComponentDetialEntity;
import com.dayi.dy_rate.entity.ComponentEntity;
import com.dayi.dy_rate.entity.ConditionsEntity;
import com.dayi.dy_rate.entity.DyUser;
import com.dayi.dy_rate.entity.LogUpdateEntity;
import com.dayi.dy_rate.entity.ModuleDetailEntity;
import com.dayi.dy_rate.entity.ModuleEntity;
import com.dayi.dy_rate.entity.ProjectDetailEntity;
import com.dayi.dy_rate.entity.ProjectEntity;
import com.dayi.dy_rate.entity.RateBaseEntity;
import com.dayi.dy_rate.entity.RateBaserPagerEntity;
import com.dayi.dy_rate.entity.TeamUserEntity;
import com.dayi35.qx_base.constant.Constant;
import com.dayi35.qx_base.http.RetrofitManager;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/11 10:00
 * 描    述: 类 model
 * 修订历史:
 * =========================================
 */
public class RateModel {
    private static RateModel model;
    private RateApi mApi;

    private RateModel() {
        mApi = RetrofitManager
                .getInstance(Constant.Host.HOST)
                .create(RateApi.class);
    }

    public static RateModel getInstance(){
        if(model == null) {
            model = new RateModel();
        }
        return model;
    }

    /**
     * 用户登录
     * @param account       账号
     * @param password      密码
     * @return
     */
    public Observable<RateBaseEntity<DyUser>> userLogin(String account, String password){
        return mApi.userLogin(account, password);
    }

    /**
     * 用户退出登录
     * @return
     */
    public Observable<RateBaseEntity<Object>> userLogout(){
        return mApi.userLogout();
    }

    /**
     * 获取团队成员
     * @return
     */
    public Observable<RateBaseEntity<List<TeamUserEntity>>> memberList(){
        return mApi.memberList();
    }

    /**
     * 获取项目过滤条件
     * @return
     */
    public Observable<RateBaseEntity<ConditionsEntity>> projectGetConditions(){
        return mApi.projectGetConditions();
    }

    /**
     * 获取项目列表
     * @param name      项目名
     * @param status    状态：1.未开始 2.进行中 3.已结束 4.逾期
     * @param teamId    团队id
     * @param os        客户端类型: 1 - 安卓 2 - IOS
     * @return
     */
    public Observable<RateBaseEntity<RateBaserPagerEntity<ProjectEntity>>> projectGetList(String name, String status, String teamId, String os, int pageNo){
        return mApi.projectGetList(name, Integer.valueOf(status), teamId, Integer.valueOf(os), pageNo, Constant.ValueConstants.PAGESIZE);
    }

    /**
     * 新建项目
     * @param name          项目名
     * @param belongId      归属者
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param os            端
     * @param teamId        团队
     * @return
     */
    public Observable<RateBaseEntity<String>> projectCreate(String name, String belongId, String startTime, String endTime, int os, String teamId, String remark){
        return mApi.projectCreate(name, belongId, startTime, endTime, os, teamId, remark);
    }

    /**
     * 根据项目ID查询项目详情
     * @param id
     * @return
     */
    public Observable<RateBaseEntity<ProjectDetailEntity>> projectConsult(String id){
        return mApi.projectConsult(id);
    }

    /**
     * 根据条件查询项目下的组件（模块）
     * @param name          项目name
     * @param projectId     项目ID
     * @param os            端
     * @return
     */
    public Observable<RateBaseEntity<RateBaserPagerEntity<ModuleEntity>>> moudleGetList(String name, String projectId, int os, int pageNo){
        return mApi.moudleGetList(name, projectId, os, pageNo, Constant.ValueConstants.PAGESIZE);
    }

    /**
     * 根据组件（模块）ID查询组件详情
     * @param id    ID
     * @return
     */
    public Observable<RateBaseEntity<ModuleDetailEntity>> moduleGetDetail(String id){
        return mApi.moduleGetDetail(id);
    }

    /**
     *
     * @param id            组件ID            --->ID 为空 ？ 创建/更新组件
     * @param name          项目名
     * @param belongId      归属者
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param projectId     项目ID
     */
    public Observable<RateBaseEntity<String>> moduleCreate(String id, String name, String belongId, String startTime, String endTime, String projectId, String remark){
        if (TextUtils.isEmpty(id)) {//新建
            return mApi.moduleCreate(name, belongId, startTime, endTime, projectId, remark);
        }else {//更新
            return mApi.moduleUpdate(id, name, belongId, startTime, endTime, projectId, remark);
        }
    }

    /**
     * 删除组件（模块）
     * @param id  组件（模块）ID
     * @return
     */
    public Observable<RateBaseEntity<String>> moduleDelete(String id){
        return mApi.moduleDelete(id);
    }

    /**
     * 获取功能列表
     * @param name          模块名
     * @param projectId     项目ID
     * @param moduleId      模块ID
     * @param page          页码
     * @return
     */
    public Observable<RateBaseEntity<RateBaserPagerEntity<ComponentEntity>>> componentGetList(String name, String projectId, String moduleId, int page){
        return mApi.componentGetList(name, projectId, moduleId, page, Constant.ValueConstants.PAGESIZE);
    }

    /**
     * @param id 功能ID       --->    获取功能详情
     * @return
     */
    public Observable<RateBaseEntity<ComponentDetialEntity>> componentGetDetail(String id){
        return mApi.componentGetDetail(id);
    }

    /**
     *  新建/更新   功能
     * @param id            功能ＩＤ
     * @param name          项目名
     * @param projectId     项目ID
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param moduleId      组件ID
     */
    public Observable<RateBaseEntity<String>> componentCreate(String id, String name, String projectId, String startTime, String endTime, String moduleId, String remark){
        if (TextUtils.isEmpty(id)) {
            return mApi.componentCreate(name, projectId, startTime, endTime, moduleId, remark);
        }else {
            return mApi.componentUpdate(id, name, projectId, startTime, endTime, moduleId, remark);
        }
    }

    /**
     * 删除功能块
     * @param id  功能ID
     * @return
     */
    public Observable<RateBaseEntity<String>> componentDelete(String id){
        return mApi.componentDelete(id);
    }

    /**
     * @param id        功能ID    --->    更新功能进度
     * @param progress  进度
     * @return
     */
    public Observable<RateBaseEntity<String>> componentUpdateProgress(String id, int progress, String remark){
        return mApi.componentUpdateProgress(id, progress, remark);
    }

    /**
     * @param page          页码      --->获取功能更新日志
     * @param id            功能ＩＤ
     * @return
     */
    public Observable<RateBaseEntity<RateBaserPagerEntity<LogUpdateEntity>>> logGetList(int page, String id){
        return mApi.logGetList(page, Constant.ValueConstants.PAGESIZE, id);
    }

}
