package com.dayi.dy_rate.contract;

import com.dayi.dy_rate.entity.DyUser;
import com.dayi.dy_rate.entity.ProjectEntity;
import com.dayi.dy_rate.entity.ProjectTeamEntity;
import com.dayi.dy_rate.entity.UserEntity;
import com.dayi35.qx_base.base.mvp.BaseView;

import java.util.List;

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
         * @param entity
         */
        void login(DyUser entity, String password);

        /**
         * 获取用户信息
         * @param userId
         */
        void getUserInfo(String userId);
    }

    interface LoginView extends BaseView{
        void onGetUserInfo(UserEntity entity);
    }

    /**
     * 项目工程
     */
    interface ProjectContract{

        interface Presenter{
            /**
             * 增
             * @param entity        增
             */
            void create(ProjectEntity entity);

            /**
             * 改
             * @param entity        改
             */
            void update(ProjectEntity entity);

            /**
             * 查
             * @param searchId      查询ID
             * @param entity        查
             */
            void search(String searchId, ProjectEntity entity);


        }

        interface View extends BaseView{
            /**
             * 操作成功
             * @param type          1.新增成功      2.修改成功      3.查询成功
             * @param entity
             */
            void onDoSuccess(int type, List<ProjectEntity> entity);

            /**
             * 操作失败
             * @param type  失败来源：1. 新增来源         2.修改来源      3.查询来源
             * @param msg
             */
            void onFailed(int type, String msg);
        }

    }

    /**
     * 项目组工程
     */
    interface ProjectTeamContract{

        interface Presenter{
            /**
             * 增
             * @param entity        增
             */
            void create(ProjectTeamEntity entity);

            /**
             * 改
             * @param entity        改
             */
            void update(ProjectTeamEntity entity);

            /**
             * 查
             * @param entity        查
             */
            void search(ProjectTeamEntity entity);


        }

        interface View extends BaseView{
            /**
             * 操作成功
             * @param type          1.新增成功      2.修改成功      3.查询成功
             * @param entity
             */
            void onDoSuccess(int type, List<ProjectTeamEntity> entity);

            /**
             * 操作失败
             * @param type  失败来源：1. 新增来源         2.修改来源      3.查询来源
             * @param msg
             */
            void onFailed(int type, String msg);
        }

    }

}
