package com.dayi.dy_rate.api;

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

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/10 10:47
 * 描    述: 类
 * 修订历史:
 * =========================================
 */
public interface RateApi {

    /**
     * 用户登录
     * @param account       账号
     * @param password      密码
     * @return
     */
    @POST(RateApiService.V1.USER_LOGIN)
    Observable<RateBaseEntity<DyUser>> userLogin(@Query("account") String account, @Query("password") String password);

    /**
     * 用户退出登录
     * @return
     */
    @GET(RateApiService.V1.USER_LOGOUT)
    Observable<RateBaseEntity<Object>> userLogout();

    /**
     * 获取团队成员
     * @return
     */
    @GET(RateApiService.V1.GROUP_GETMEMBERINFO)
    Observable<RateBaseEntity<List<TeamUserEntity>>> memberList();

    /**
     * 获取项目列表
     * @param name      项目名
     * @param status    状态：1.未开始 2.进行中 3.已结束 4.逾期
     * @param teamId    团队id
     * @param os        客户端类型: 1 - 安卓 2 - IOS
     * @return
     */
    @GET(RateApiService.V1.PROJECT_PROJECTLIST)
    Observable<RateBaseEntity<RateBaserPagerEntity<ProjectEntity>>> projectGetList(@Query("id") String name,
                                                                   @Query("status") int status,
                                                                   @Query("teamId") String teamId,
                                                                   @Query("os") int os,
                                                                   @Query("page") int page,
                                                                   @Query("pageSize") int pageSize);

    /**
     * 获取项目筛选条件数据
     * @return
     */
    @GET(RateApiService.V1.PROJECT_CONDITIONS)
    Observable<RateBaseEntity<ConditionsEntity>> projectGetConditions();

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
    @POST(RateApiService.V1.PROJECT_CREATE)
    @Headers(Constant.ValueConstants.COMMONHEADER)
    Observable<RateBaseEntity<String>> projectCreate(@Query("name") String name,
                                                     @Query("belongId") String belongId,
                                                     @Query("startTime") String startTime,
                                                     @Query("endTime") String endTime,
                                                     @Query("os") int os,
                                                     @Query("teamId") String teamId,
                                                     @Query("remark") String remark);

    /**
     * 根据项目ID查询项目详情
     * @param id    项目的ID
     * @return
     */
    @GET(RateApiService.V1.PROJECT_CONSULT)
    Observable<RateBaseEntity<ProjectDetailEntity>> projectConsult(@Path("id") String id);

    /**
     * 根据条件查询项目下的组件（模块）
     * @param name          项目name
     * @param projectId     项目ID
     * @param os            端
     * @return
     */
    @GET(RateApiService.V1.MODULE_MODULELIST)
    Observable<RateBaseEntity<RateBaserPagerEntity<ModuleEntity>>> moudleGetList(@Query("name") String name,
                                                                                 @Query("projectId") String projectId,
                                                                                 @Query("os") int os,
                                                                                 @Query("page") int page,
                                                                                 @Query("pageSize") int pageSize);

    /**
     * 根据组件（模块）ID查询组件详情
     * @param id    ID
     * @return
     */
    @GET(RateApiService.V1.MODULE_DETAIL)
    Observable<RateBaseEntity<ModuleDetailEntity>> moduleGetDetail(@Path("id") String id);

    /**
     *新建组件
     * @param name          项目名
     * @param belongId      归属者
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param projectId     项目ID
     */
    @POST(RateApiService.V1.MODULE_CREATE)
    @Headers(Constant.ValueConstants.COMMONHEADER)
    Observable<RateBaseEntity<String>> moduleCreate(@Query("name") String name,
                                                    @Query("belongId") String belongId,
                                                    @Query("startTime") String startTime,
                                                    @Query("endTime") String endTime,
                                                    @Query("projectId") String projectId,
                                                    @Query("remark") String remark);

    /**
     *  更新组件
     * @param id            组件ID  更新组件
     * @param name          项目名
     * @param belongId      归属者
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param projectId     项目ID
     */
    @POST(RateApiService.V1.MODULE_UPDATE)
    @Headers(Constant.ValueConstants.COMMONHEADER)
    Observable<RateBaseEntity<String>> moduleUpdate(@Path("id") String id,
                                                    @Query("name") String name,
                                                    @Query("belongId") String belongId,
                                                    @Query("startTime") String startTime,
                                                    @Query("endTime") String endTime,
                                                    @Query("projectId") String projectId,
                                                    @Query("remark") String remark);

    /**
     * 删除组件（模块）
     * @param id  组件（模块）ID
     * @return
     */
    @POST(RateApiService.V1.MODULE_DELETE)
    @Headers(Constant.ValueConstants.COMMONHEADER)
    Observable<RateBaseEntity<String>> moduleDelete(@Path("id") String id);




    /**
     * 获取功能列表
     * @param name          模块名
     * @param projectId     项目ID
     * @param moduleId      模块ID
     * @param page          页码
     * @param pageSize      条数
     * @return
     */
    @GET(RateApiService.V1.COMPONENT_LIST)
    Observable<RateBaseEntity<RateBaserPagerEntity<ComponentEntity>>> componentGetList(@Query("name") String name,
                                                                                       @Query("projectId") String projectId,
                                                                                       @Query("moduleId") String moduleId,
                                                                                       @Query("page") int page,
                                                                                       @Query("pageSize") int pageSize);

    /**
     * @param id 功能ID       --->    获取功能详情
     * @return
     */
    @GET(RateApiService.V1.COMPONENT_DETAIL)
    Observable<RateBaseEntity<ComponentDetialEntity>> componentGetDetail(@Path("id") String id);

    /**
     *  新建功能
     * @param name          项目名
     * @param projectId     项目ID
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param moduleId      组件ID
     */
    @POST(RateApiService.V1.COMPONENT_CREATE)
    @Headers(Constant.ValueConstants.COMMONHEADER)
    Observable<RateBaseEntity<String>> componentCreate(@Query("name") String name,
                                                       @Query("projectId") String projectId,
                                                       @Query("startTime") String startTime,
                                                       @Query("endTime") String endTime,
                                                       @Query("moduleId") String moduleId,
                                                       @Query("remark") String remark);

    /**
     *  更新功能
     * @param name          项目名
     * @param projectId     项目ID
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param moduleId      组件ID
     */
    @POST(RateApiService.V1.COMPONENT_UPDATE)
    @Headers(Constant.ValueConstants.COMMONHEADER)
    Observable<RateBaseEntity<String>> componentUpdate(@Path("id") String id,
                                                       @Query("name") String name,
                                                       @Query("projectId") String projectId,
                                                       @Query("startTime") String startTime,
                                                       @Query("endTime") String endTime,
                                                       @Query("moduleId") String moduleId,
                                                       @Query("remark") String remark);

    /**
     * 删除功能块
     * @param id  功能ID
     * @return
     */
    @POST(RateApiService.V1.COMPONENT_DELETE)
    @Headers(Constant.ValueConstants.COMMONHEADER)
    Observable<RateBaseEntity<String>> componentDelete(@Path("id") String id);

    /**
     * @param id        功能ID    --->    更新功能进度
     * @param progress  进度
     * @return
     */
    @POST(RateApiService.V1.COMPONENT_UPDATEPROGRESS)
    Observable<RateBaseEntity<String>> componentUpdateProgress(@Path("id") String id,
                                                               @Query("progress") int progress,
                                                               @Query("remark") String remark);

    /**
     * @param page          页码      --->获取功能更新日志
     * @param pageSize      page_size
     * @param id            功能ＩＤ
     * @return
     */
    @GET(RateApiService.V1.LOG_LOGLIST)
    Observable<RateBaseEntity<RateBaserPagerEntity<LogUpdateEntity>>> logGetList(@Query("page") int page,
                                                                                 @Query("pageSize") int pageSize,
                                                                                 @Query("id") String id);

}
