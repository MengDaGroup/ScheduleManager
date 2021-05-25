package com.dayi.dy_rate.presenter;

import android.content.Context;

import com.dayi.dy_rate.entity.RateBaseEntity;
import com.dayi35.qx_base.base.mvp.BasePresent;
import com.dayi35.qx_base.base.mvp.BaseView;
import com.dayi35.qx_base.http.ExceptionUtils;
import com.dayi35.qx_base.http.RequestCallback;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/6/12 15:27
 * 描    述: 类
 * 修订历史: 增加conver 方法
 * =========================================
 */
public abstract class BasePresentHttpImp<V extends BaseView> implements BasePresent {
    protected V mView;
    protected Context mContext;

    private CompositeDisposable mCompositeDisposable;


    public BasePresentHttpImp(V mView) {
        this.mView = mView;
    }

    @Override
    public void subscribe(Disposable subscription) {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    @Override
    public void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
            mView = null;
        }
    }

    @Override
    public void subscribe() {

    }

    /**
     * 请求
     * @param observable        请求主题
     * @param callBack          回调
     * @param <T>               实例
     */
    protected <T> void conver(Observable<T> observable , final RequestCallback<T> callBack){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(new Observer<T>() {
                    Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        subscribe(d);
                        disposable = d;
                        if (null != callBack){
                            callBack.onSubscribe(d);
                        }
                    }
                    @Override
                    public void onNext(T t) {
                        if (!(t instanceof RateBaseEntity)){
                            callBack.onError("实体解析错误");
                            return;
                        }
                        RateBaseEntity baseEntity = (RateBaseEntity) t;
                        if (baseEntity.isSuccess()) {
                            callBack.onSuccess(t);
                        }else {
                            callBack.onError(baseEntity.getMessage());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        //统一处理HttpException错误
                        ExceptionUtils.handleException(e);
                        //临时处理401跳转的问题
                        if(e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            int code = httpException.code();
                            //TODO 特殊code的处理
                        }
                        if (null != callBack) {
                            callBack.onError(e.getMessage());
                        }
                        disposable.dispose();
                    }
                    @Override
                    public void onComplete() {
                        if (null != callBack) {
                            callBack.onComplete();
                        }
                        disposable.dispose();
                    }
                });
    }

}
