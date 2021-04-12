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
     * 活动组件
     */
    interface Activity {
        String MOUDLE = "/act/";
        String COMMISSION_ACTIVITY = MOUDLE + "commission";                         //高佣界面
        String COMMISSIONPRO_ACTIVITY = MOUDLE + "commission_pro";                  //高佣活动场次详情界面
        String SNAPUP_ACTIVITY = MOUDLE + "snapupactivity";                         //限时购界面
        String ORDERGROUP_ACTIVITY = MOUDLE + "ordergroupactivity";                 //拼团活动界面
    }

    /**
     * 广告（推广）组件
     */
    interface AD{
        String MOUDLE = "/ad/";
        String AD_CONTRACTIMPL = MOUDLE + "contractimpl";       //ad广告对外调用协议实现类

        String SHOW_ACTIVITY = MOUDLE + "showactivity";         //展示指引界面
    }

    /**
     * APP组件
     */
    interface APP{
        String MOUDLE = "/app/";

        String HOME_ACTIVITY = MOUDLE + "homeactivity";                      //主界面
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
     * 评论组件
     */
    interface Project{
        String MOUDLE = "/Project/";
        //项目列表界面
        interface ProjectList{
            String path = MOUDLE + "projectlist";
            interface Param{

            }
        }
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
