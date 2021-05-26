package com.dayi.dy_rate.contract;

import com.dayi.dy_rate.entity.ComponentDetialEntity;
import com.dayi.dy_rate.entity.ComponentEntity;
import com.dayi.dy_rate.entity.ConditionsEntity;
import com.dayi.dy_rate.entity.LogUpdateEntity;
import com.dayi.dy_rate.entity.ModuleDetailEntity;
import com.dayi.dy_rate.entity.ModuleEntity;
import com.dayi.dy_rate.entity.ProjectDetailEntity;
import com.dayi.dy_rate.entity.ProjectEntity;
import com.dayi.dy_rate.entity.RateBaserPagerEntity;
import com.dayi.dy_rate.entity.TeamUserEntity;
import com.dayi.dy_rate.entity.UserEntity;
import com.dayi35.qx_base.base.mvp.BaseView;

import java.util.List;
import java.util.Map;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/3/31 16:45
 * 描    述: 类    用户逻辑管理类
 * 修订历史:
 * =========================================
 */
public interface UserContract {
    /**
     * 登录
     */
    interface LoginPresenter{
        /**
         * 登录
         * @param account       账号
         * @param password      密码
         */
        void login(String account, String password);

        /**
         * 获取用户信息
         * @param userId
         */
        void getUserInfo(String userId);

    }

    interface LoginView extends BaseView{
        /**
         * 获取到用户信息
         * @param entity
         */
        void onGetUserInfo(UserEntity entity);

        /**
         * 登陆成功
         */
        void onLoginSuccess();

    }

    /**
     * 项目工程
     */
    interface ProjectContract{
        /**
         * 新增项目界面
         */
        interface CreatePresenter{

            /**
             * 新增/编辑项目
             * @param type          1.新增项目  2.编辑项目
             * @param name          项目名
             * @param belongId      归属者
             * @param startTime     开始时间
             * @param endTime       结束时间
             * @param os            端
             * @param teamId        团队
             * @param remark        备注描述
             */
            void projectCreate(int type, String name, String belongId, String startTime, String endTime, int os, String teamId, String remark);
        }

        interface CreateView extends BaseView{
            /**
             * 访问成功
             * @param code
             * @param msg
             */
            void onCommonSuccess(int code, String msg);
        }
    }

    /**
     * 项目详情
     */
    interface ProjectDetailContract{

        interface Presenter{
            /**
             * 根据项目ID查询项目详情
             * @param id
             */
            void projectConsult(String id);

            /**
             * 获取组件列表（模块）
             * @param name          项目名
             * @param projectId     项目ID
             * @param os            端
             * @param pageNo        页码
             */
            void moudleGetList(String name, String projectId, int os, int pageNo);

            /**
             * 删除组件
             * @param id    组件ID    --->    删除组件
             */
            void moduleDelete(String id);

        }

        interface View extends BaseView{
            /**
             * 获取到项目详情
             * @param entity
             */
            void onProjectConsult(ProjectDetailEntity entity);

            /**
             * 获取到组件列表（模块）
             * @param entity
             */
            void onGetMoudleList(RateBaserPagerEntity<ModuleEntity> entity);

            /**
             * 删除组件成功
             * @param msg   ---> 删除组件成功
             */
            void onDeletedModule(String msg);
        }

    }

    /**
     *  组件详情
     */
    interface ModuleDetailContract{
        interface Presenter{
            /**
             *
             * @param id    获取组件（模块详情） ---> 组件ID
             */
            void moduleGetDetail(String id);

            /**
             *
             * @param name          模块名         ---> 获取功能列表
             * @param projectId     项目ID
             * @param moduleId      模块ID
             * @param page          页码
             */
            void componentGetList(String name, String projectId, String moduleId, int page);

            /**
             * 删除功能
             * @param id
             */
            void componentDelete(String id);
        }

        interface View extends BaseView{
            /**
             *
             * @param entity    组件详情实体      ---> 获取到组件详情信息
             */
            void onGetModuleDetail(ModuleDetailEntity entity);

            /**
             *
             * @param entity 功能列表       ---> 获取到功能（成分）列表
             */
            void onGetComponetList(RateBaserPagerEntity<ComponentEntity> entity);

            /**
             * 成功删除功能
             * @param msg
             */
            void onComponentDelete(String msg);
        }
    }

    /**
     * 组件更新
     */
    interface ModuleUpdateContract{

        interface Presenter{
            /**
             *
             * @param id            组件ID            --->ID 为空 ？ 创建/更新组件
             * @param name          项目名
             * @param belongId      归属者
             * @param startTime     开始时间
             * @param endTime       结束时间
             * @param projectId     项目ID
             * @param remark        组件说明
             */
            void moduleCreate(String id, String name, String belongId, String startTime, String endTime, String projectId, String remark);
        }

        interface View extends BaseView{
            /**
             *
             * @param type  成功返回类型  1.创建组件成功    2.更新组件成功
             * @param msg
             */
            void onDoSuccess(int type, String msg);
        }
    }

    /**
     * 项目组工程
     */
    interface ProjectListContract{

        interface Presenter{

            /**
             * 获取项目列表
             * @param name      项目名
             * @param status    状态：1.未开始 2.进行中 3.已结束 4.逾期
             * @param teamId    团队id
             * @param os        客户端类型: 1 - 安卓 2 - IOS
             * @param pageNo    页码
             * @return
             */
            void projectGetList(String name, String status, String teamId, String os, int pageNo);

            /**
             * 获取项目过滤条件
             */
            void projectGetConditions();

            /**
             * 登出
             */
            void logout();
            /**
             * 获取团队成员
             */
            void memberList();

        }

        interface View extends BaseView{
            /**
             * 获取到过滤条件成功
             * @param entity
             */
            void onGetConditions(ConditionsEntity entity);

            /**
             *
             * @param entity  获取到项目列表
             */
            void onGetProjectList(RateBaserPagerEntity<ProjectEntity> entity);

            /**
             * 操作失败
             * @param type  失败来源：1. 新增来源         2.修改来源      3.查询来源
             * @param msg
             */
            void onFailed(int type, String msg);

            /**
             * 退出登录成功
             */
            void onLogoutSuccess();

            /**
             *
             * @param entities  成员列表    --->    获取到成员
             */
            void onGetMemberList(List<TeamUserEntity> entities);
        }

    }

    /**
     * 功能创建/更新
     */
    interface ComponentUpdateContract{

        interface Presenter{
            /**
             *
             * @param id            功能ＩＤ        --->    新建/更新   功能
             * @param name          项目名
             * @param projectId     项目ID
             * @param startTime     开始时间
             * @param endTime       结束时间
             * @param moduleId      组件ID
             * @param remark        功能说明
             */
            void componentCreate(String id, String name, String projectId, String startTime, String endTime, String moduleId, String remark);

            /**
             * @param id        功能ID    --->    更新功能进度
             * @param progress  进度
             * @param remark    更新说明
             * @return
             */
            void componentUpdateProgress(String id, int progress, String remark);
        }

        interface View extends BaseView{
            /**
             * @param type      类型  1.创建    2.更新    --->    创建/更新   功能成功
             * @param msg       msg
             */
            void onComponentCreated(int type, String msg);

            /**
             * @param msg   msg --->    更新进度成功
             */
            void onUpdateProgress(String msg);
        }

    }

    /**
     * 功能详情
     */
    interface ComponentDetailContract{
        interface Presenter{
            /**
             * @param id    功能ID    ---> 根据功能ID查询功能详情
             */
            void componentGetDetail(String id);

            /**
             * @param page          页码      --->获取功能更新日志
             * @param id            功能ＩＤ
             */
            void logGetList(int page, String id);
        }

        interface View extends BaseView{
            /**
             * @param entity    功能详情实体类 ---> 获取到功能详情
             */
            void onGetComponentDetail(ComponentDetialEntity entity);

            /**
             * @param entity    日志列表实体      --->    获取到功能更新日志
             */
            void onGetLogList(RateBaserPagerEntity<LogUpdateEntity> entity);
        }
    }


}
