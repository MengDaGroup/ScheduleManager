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
    interface Evaluate{
        String MOUDLE = "/evaluate/";
        String EVALUATEMINE_ACTIVITY = MOUDLE + "evaluatemineactivity";                     //我的评价界面
        String EVALUATIONCOMMIT_ACTIVITY = MOUDLE + "evaluationcommitactivity";             //提交评论界面
        String EVALUATIONAPPEND_ACTIVITY = MOUDLE + "evaluationappendactivity";             //追加评论界面
        String EVALUATEDPRODUCT_ACTIVITY = MOUDLE + "evaluatedproductactivity";             //产品评论信息界面
    }

    /**
     * 好友（师徒）组件
     */
    interface Friends{
        String MOUDLE = "/friends/";
        String FRIENDSAPI_CONTRACTIMP = MOUDLE + "friendsapicontractimpl";      //friends组件对外协议实现路径

        String FRIENDSINVITER_ACTIVITY = MOUDLE + "friendsinviteractivity";     //好友邀请界面
        String FRIENDSLIST_ACTIVITY = MOUDLE + "friendslistactivity";           //好友管理（列表）界面
        String APPRENTICE_ACTIVITY = MOUDLE + "apprenticeactivity";             //徒弟列表界面
    }

    /**
     * 收益组件
     */
    interface Income {
        String MOUDLE = "/income/";
        String INCOME_CONTRACTIMPL = MOUDLE + "contractimpl";       //income对外调用协议实现类

        String USERINCOME_ACTIVITY = MOUDLE + "userincomeactivity"; //用户收益界面
        String COMMISSIONINCOM_ACTIVITY = MOUDLE + "commissionactivity";//活动佣金界面
        String PLATFORMREWORDSACTIVITY = MOUDLE + "platformrewordsactivity";    //平台奖励界面
    }

    /**
     * 物流地址组件
     */
    interface Logistics{
        String MOUDLE = "/logistics/";
        String LOGISTICS_CONTRACTIMPL = MOUDLE + "contractimpl";                //物流组件对外调用协议实现类

        String ADDRESSLIST_ACTIVITY = MOUDLE + "addresslistactivity";           //地址管理界面
        String ADDRESSUPDATE_ACTIVITY = MOUDLE + "addressupdateactivity";       //地址创建界面
        String LOGISTICSDETAIL_ACTIVITY = MOUDLE + "logisticsdetailactivity";   //物流详情界面
    }

    /**
     * 会员
     */
    interface Member{
        String MOUDLE = "/member/";
        String MEMBER_CONTRACTIMPL = MOUDLE + "contractimpl";
        String MEMBER_UIINTERFACE = MOUDLE + "memberuiinterface";                           //界面协议实现

        String VIPRECORD_ACTIVITY = MOUDLE + "viprecordactivity";                           //VIP开通记录

        /**
         * 网页介绍svip/vip等的内容界面
         */
        interface IntroductWebActivity{
            String PATH = MOUDLE + "introductwebactivity";                                  //界面路径
            interface Param{
                String cardType = "cardType";           //年卡类型
            }
        }

    }

    /**
     * 产品
     */
    interface Product {
        String MODULE = "/product/";
        String PRODUCT_UIINTERFACE = MODULE + "productuiinterface";                     //界面协议实现
        String PRODUCT_CONTRACTIMPL = MODULE + "productcontractimpl";                   //对外协议

        String PRODUCT_DETAIL_ACTIVITY = MODULE + "product_detail";//商品详情页
        String PRODUCT_REVIEW_DETAIL_ACTIVITY = MODULE + "product_review_detail";//商品评论
        String BIG_IMAGE_ACTIVITY = MODULE + "big_imagey"; //大图
        String PROMOTION_SCOPE_ACTIVITY = MODULE + "promotion_scope";//卡券适用，商品凑单，满减凑单区
        String CONFIRM_ORDER_ACTIVITY = MODULE + "confirm_order";  //确认订单
        String SELECT_USABLE_COUPON_ACTIVITY = MODULE + "select_usable_coupon";//选择卡券
        String PRODUCT_TOPIC_ACTIVITY = MODULE+"product_topic";     //商品专题、导航管理
        String SEARCH_ACTIVITY  =MODULE+"search_activity";     //搜索
        String SEARCH_RESULT_ACTIVITY = MODULE+"search_result_activity";   //搜索结果
        String CATEGORY_LIST_ACTIVITY = MODULE+"category_list_activitiy";     //品类列表
    }



    /**
     * 售后组件
     */
    interface Service {
        String MOUDLE = "/service_/";
        String SERVICE_APICONTRACTIMPL = MOUDLE + "api_contractimpl";                   //service对外调用协议实现

        String SERVICEORDER_ACTIVITY = MOUDLE + "serviceorderactivity";                 //售后单列表界面
        String SERVICEORDERDETAIL_ACTIVITY = MOUDLE + "serviceorderdetailactivity";     //售后单详情界面
        String CUSTOMERAPPLY_ACTIVITY = MOUDLE + "customerapplyactivity";               //申请客服介入界面
        String SERVICEPROGRSS_ACTIVITY = MOUDLE + "serviceprogressactivity";            //售后进度界面
        String SERVICESUBMIT_ACTIVITY = MOUDLE + "servicesubmitactivity";               //售后订单提交
        String LOGISTNUMBERACTIVITY = MOUDLE + "logistnumberactivity";                  //物流单号界面
        String REFUNDONLY_ACTIVITY = MOUDLE + "refundonlyactivity";                     //退款界面
        String DELIVERORDR_ACTIVITY = MOUDLE + "deliverordractivity";                   //发货界面
    }

    /**
     * 店铺组件
     */
    interface Store{
        String MOUDLE = "/store/";
        String STORE_APICONTRACTIMPL = MOUDLE + "storeapicontracimpl";                  //协议地址

        String STOREINFO_ACTIVITY = MOUDLE + "storeinfoactivity";                       //店铺信息界面
        String STOREPRODUCT_MANAGERACTIVITY = MOUDLE + "storeproductmanageractivity";   //店铺商品管理界面
        String PRODUCTSELECT_ACTIVITY = MOUDLE + "productselectactivity";               //店铺选货界面
        String PRODUCTGROUP_ACTIVITY = MOUDLE + "productgroupactivity";                 //商品礼包
        String STOREPREVIEW_ACTIVITY = MOUDLE + "storepreviewactivity";                 //店铺商品预览
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

    /**
     * 订单
     */
    interface Orders {
        String MODULE = "/orders/";
        String ORDER_CONTRACTIMPL = MODULE + "ordercontractimpl";   //协议

        String MY_ORDER_ACTIVITY = MODULE+"my_order";     //我的订单
        String ORDER_DETAIL_ACTIVITY = MODULE+"order_detail";     //物流详情
    }

    /**
     * 卡券
     */
    interface Coupons {
        String MODULE = "/coupons/";
        String COUPONS_CONTRACTIMPL = MODULE + "couponscontractimpl";   //协议

        String BALANCE_ACTIVITY = MODULE+"balance";     //我的订单
        String BIND_BANK_CARD_ACTIVITY = MODULE+"bind_bank_card";     //我的订单
        String MY_COUPON_LIST_ACTIVITY = MODULE+"my_coupon_list";     //我的订单
        String REAL_NAME_ACTIVITY = MODULE+"real_name";     //我的订单
        String TRANSFER_DETAIL_ACTIVITY = MODULE+"transfer_detail";     //我的订单
        String TRANSFER_TO_CARD_ACTIVITY = MODULE+"transfer_to_card";     //我的订单
        String TRANSFER_TO_WECHCAT_ACTIVITY = MODULE+"tranfer_to_wechat";     //我的订单
        String SUPPORT_BANK_ACTIVITY = MODULE+"support_bank";                          //支持银行
    }
}
