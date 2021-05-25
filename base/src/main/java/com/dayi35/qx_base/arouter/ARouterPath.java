package com.dayi35.qx_base.arouter;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/11/12 14:08
 * 描    述: 类 ARouter跳转路径集合类 按模块分类 例如Example示例模块，界面内的方法以_加方法名
 * 修订历史:
 * =========================================
 */
public interface ARouterPath {

    /**
     * 进度管理组件
     */
    interface Rate{
        String MODULE = "/rate/";

        interface ProjectUpdate{
            String PARH = MODULE + "project_update";
        }

        /**
         * 项目详情
         */
        interface ProjectDetail{
            String PATH = MODULE + "project_detail";
            interface Params{
                String PROJECTID = "project_id";    //项目ＩＤ
            }
        }

        /**
         * 组件（模块）详情
         */
        interface ModuleDetail{
            String PATH = MODULE + "module_detail";
            interface Params{
                String MODULEID = "module_id";      //组件ID
                String PROJECTID = "project_id";    //项目ID
            }
        }

        /**
         * 组件（模块）创建/更新
         */
        interface ModuleUpdate{
            String PATH = MODULE + "module_update";
            interface Params{
                String MODULEID = "module_id";      //组件ID
                String PROJECTID = "project_id";    //项目ＩＤ
                String STARTTIME = "start_time";    //开始时间
                String ENDTIME = "end_time";        //结束时间
            }
        }

        /**
         * 功能详情
         */
        interface ComponentDetail{
            String PATH = MODULE + "component_detail";
            interface Params{
                String COMPONENTID = "component_id";
            }
        }

        /**
         * 功能创建/更新
         */
        interface ComponentUpdate{
            String PATH = MODULE + "component_update";
            interface Params{
                String PROJECTID = "project_id";                //项目ID
                String MODULEID = "module_id";                  //组件ID
                String COMPONENTID = "component_id";            //功能ID
                String COMPONENTPROGRESS = "component_progress";//当前进度
                String COMPONENTNAME = "component_name";    //功能名
                String STARTTIME = "start_time";        //开始时间
                String ENDTIME = "end_time";            //结束时间
            }
        }
    }


    /**
     * common
     */
    interface Common {
        String MODULE = "/common/";
        String WX_PAY_RESULT = MODULE + "wx_pay_result";  //微信支付成功

        String WEB_ACTIVITY = MODULE + "webactivity";   //网页
    }


    /**
     * 登录组件
     */
    interface User {
        String MOUDLE = "/user/";
        String USER_CONTRACTIMPL = MOUDLE + "contractimpl";   //user对外调用协议实现类
        String USER_UIINTERFACE = MOUDLE + "useruiinterface";   //界面协议实现类

        String LOGIN_ACTIVITY = MOUDLE + "login";        //login界面
        String SETTING_ACTIVITY = MOUDLE + "setting";    //设置界面
        String INVITER_ACTIVITY = MOUDLE + "inviteractivity";   //邀请界面
        String HELPERCENTERWEB_ACTIVITY = MOUDLE + "helpercenterwebactivity";   //帮助中心

    }

}
