package com.dayi35.qx_base.arouter;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import timber.log.Timber;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/11/19 14:52
 * 描    述: 类 ARouter跳转管理类 1.绑定（activity  和  fragment）  2.跳转（几种方式）
 * 修订历史:
 * =========================================
 */
public class ARouterHelper {

    /**
     * 在activity中添加
     *
     * @param activity activity_test
     */
    public static void injectActivity(AppCompatActivity activity) {
        if (activity == null) {
            return;
        }
        ARouter.getInstance().inject(activity);
    }

    /**
     * 在fragment中添加
     *
     * @param fragment fragment
     */
    public static void injectFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        ARouter.getInstance().inject(fragment);
    }

    /**
     * 销毁资源
     */
    public static void destroy() {
        Timber.i("销毁路由资源");
        ARouter.getInstance().destroy();
    }

    /**
     * 简单的跳转页面
     *
     * @param string string目标界面对应的路径
     */
    public static void navigation(String string) {
        if (string == null) {
            return;
        }
        ARouter.getInstance()
                .build(string)
                .navigation();
    }

    /**
     * 跳转 ， 绑定上下文
     * @param path
     * @param context
     */
    public static void navigation(String path, Context context){
        if (path == null) {
            return;
        }
        ARouter.getInstance()
                .build(path)
                .navigation(context);
    }

    /**
     * 简单的跳转页面
     *
     * @param string string目标界面对应的路径
     */
    public static void navigation(String string, String flag) {
        if (string == null) {
            return;
        }
        ARouter.getInstance()
                .build(string)
                .navigation();
    }

    /**
     * 根据path 获取对象
     * @param path
     * @return
     */
    public static Object navigationObj(String path){
        if (path == null) {
            return null;
        }
        return ARouter.getInstance()
                .build(path)
                .navigation();
    }

    /**
     * 简单的跳转页面
     *
     * @param string string目标界面对应的路径
     */
    public static void navigationGroup(String string, String group) {
        if (string == null) {
            return;
        }
        ARouter.getInstance()
                .build(string, group)
                .navigation();
    }

    /**
     * 简单的跳转页面
     *
     * @param string   string目标界面对应的路径
     * @param callback 监听路由过程
     */
    public static void navigation(String string, Context context, NavigationCallback callback) {
        if (string == null) {
            return;
        }
        ARouter.getInstance()
                .build(string)
                .navigation(context, callback);
    }


    /**
     * 简单的跳转页面
     *
     * @param uri uri
     */
    public static void navigation(Uri uri) {
        if (uri == null) {
            return;
        }
        ARouter.getInstance()
                .build(uri)
                .navigation();
    }


    /**
     * 简单的跳转页面
     *
     * @param string    string目标界面对应的路径
     * @param bundle    bundle参数
     * @param enterAnim 进入时候动画
     * @param exitAnim  退出动画
     */
    public static void navigation(String string, Bundle bundle, int enterAnim, int exitAnim) {
        if (string == null) {
            return;
        }
        if (bundle == null) {
            ARouter.getInstance()
                    .build(string)
                    .withTransition(enterAnim, exitAnim)
                    .navigation();
        } else {
            ARouter.getInstance()
                    .build(string)
                    .with(bundle)
                    .withTransition(enterAnim, exitAnim)
                    .navigation();
        }
    }

    /**
     * forresult 跳转
     *
     * @param context
     * @param path
     * @param bundle
     * @param code    请求码，需大于0
     */
    public static void navigation(Activity context, String path, Bundle bundle, int code) {
        navigation(context,path,bundle,code,-1,-1);
    }

    /**
     * forresult 跳转
     * @param context
     * @param path
     * @param bundle
     * @param code  请求码，需大于0
     * @param enterAnim   进入动画  -1系统默认
     * @param exitAnim  退出动画  -1系统默认
     */
    public  static void navigation(Activity context,String path,Bundle bundle,int code,int enterAnim,int exitAnim){
        if (path == null) {
            return;
        }
        if (bundle == null) {
            ARouter.getInstance()
                    .build(path)
                    .withTransition(enterAnim,exitAnim)
                    .navigation(context, code);
        } else {
            ARouter.getInstance()
                    .build(path)
                    .with(bundle)
                    .withTransition(enterAnim,exitAnim)
                    .navigation(context, code);
        }
    }

    /**
     * 携带参数跳转页面
     *
     * @param path   path目标界面对应的路径
     * @param bundle bundle参数
     */
    public static void navigation(String path, Bundle bundle) {
        if (path == null || bundle == null) {
            return;
        }
        ARouter.getInstance()
                .build(path)
                .with(bundle)
                .navigation();
    }


    /**
     * 跨模块实现ForResult返回数据（activity中使用）,在fragment中使用不起作用
     * 携带参数跳转页面
     *
     * @param path   path目标界面对应的路径
     * @param bundle bundle参数
     */
    public static void navigation(String path, Bundle bundle, Activity context, int code) {
        if (path == null) {
            return;
        }
        if (bundle == null) {
            ARouter.getInstance()
                    .build(path)
                    .navigation(context, code);
        } else {
            ARouter.getInstance()
                    .build(path)
                    .with(bundle)
                    .navigation(context, code);
        }
    }


    /**
     * 使用绿色通道(跳过所有的拦截器)
     *
     * @param path  path目标界面对应的路径
     * @param green 是否使用绿色通道
     */
    public static void navigation(String path, boolean green) {
        if (path == null) {
            return;
        }
        if (green) {
            ARouter.getInstance()
                    .build(path)
                    .greenChannel()
                    .navigation();
        } else {
            ARouter.getInstance()
                    .build(path)
                    .navigation();
        }
    }

    /**
     * 跳转到web
     * @param url   weburl
     * @param title webtitle
     */
    public static void navigationWeb(String url, String title){
        Bundle bundle = new Bundle();
        navigation(ARouterPath.Common.WEB_ACTIVITY, bundle);
    }

    private NavigationCallback getCallback() {
        NavigationCallback callback = new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                Timber.i("ARouterHelper" + "---跳转完了");
            }

            @Override
            public void onFound(Postcard postcard) {
                super.onFound(postcard);
                Timber.i("ARouterHelper" + "---找到了");
            }

            @Override
            public void onInterrupt(Postcard postcard) {
                super.onInterrupt(postcard);
                Timber.i("ARouterHelper" + "---被拦截了");
            }

            @Override
            public void onLost(Postcard postcard) {
                super.onLost(postcard);
                Timber.i("ARouterHelper" + "---找不到了");
                //降级处理
                //DegradeServiceImpl degradeService = new DegradeServiceImpl();
                //degradeService.onLost(CacheUtils.getApp(),postcard);

                //无法找到路径，作替换处理
                PathReplaceServiceImpl pathReplaceService = new PathReplaceServiceImpl();
                pathReplaceService.replacePath(ARouterPath.User.LOGIN_ACTIVITY, ARouterPath.User.LOGIN_ACTIVITY);
            }
        };
        return callback;
    }


}
