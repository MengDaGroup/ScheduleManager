package com.dayi.dy_rate.api;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/10 10:48
 * 描    述: 类
 * 修订历史:
 * =========================================
 */
public interface RateApiService {
    interface V1{
        //用户登录
        String USER_LOGIN = "/api/login";
        //用户登出
        String USER_LOGOUT = "/api/logout";
        //获取成员数据
        String GROUP_GETMEMBERINFO = "/api/common/getTeamList";
        //获取团队项目数据(筛选条件)
        String PROJECT_CONDITIONS = "/api/common/getProjectList";

        //获取项目列表
        String PROJECT_PROJECTLIST = "/api/uc/project/list";
        //新增项目
        String PROJECT_CREATE = "/api/uc/project/create";
        //删除项目
        String PROJECT_DELETE = "/api/uc/project/delete/{id}";
        //更新项目（改）
        String PROJECT_UPDATE = "/api/uc/project/update/{id}";
        //查阅项目内容
        String PROJECT_CONSULT = "/api/uc/project/detail/{id}";

        //获取组件列表
        String MODULE_MODULELIST = "/api/uc/module/list";
        //获取组件详情
        String MODULE_DETAIL = "/api/uc/module/detail/{id}";
        //新建组件
        String MODULE_CREATE = "/api/uc/module/create";
        //删除组件
        String MODULE_DELETE = "/api/uc/module/delete/{id}";
        //更新组件
        String MODULE_UPDATE = "/api/uc/module/update/{id}";

        //获取功能列表
        String COMPONENT_LIST = "/api/uc/component/list";
        //查询功能详情
        String COMPONENT_DETAIL = "/api/uc/component/detail/{id}";
        //新建功能
        String COMPONENT_CREATE = "/api/uc/component/create";
        //删除功能
        String COMPONENT_DELETE = "/api/uc/component/delete/{id}";
        //更新功能
        String COMPONENT_UPDATE = "/api/uc/component/update/{id}";
        //更新功能完成的进度
        String COMPONENT_UPDATEPROGRESS = "/api/uc/component/updateProgress/{id}";

        //获取功能更新日志
        String LOG_LOGLIST = "/api/uc/component/getLogs";

    }
}
