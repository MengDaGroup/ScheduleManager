package com.dayi35.qx_base.http;

import com.dayi35.qx_base.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/7/2 14:08
 * 描    述: 类 统一接口实例的管理类RetrofitWrapper
 * 修订历史:
 * =========================================
 */
public class RetrofitManager {

    private Retrofit mRetrofit;
    private final OkHttpClient.Builder builder;


    /**
     * 获取实例，使用单利模式
     * 这里传递url参数，是因为项目中需要访问不同基类的地址
     * @param url               baseUrl
     * @return                  实例对象
     */
    public static RetrofitManager getInstance(String url){
        //synchronized 避免同时调用多个接口，导致线程并发
        RetrofitManager instance;
//        synchronized (RetrofitManager.class){
            instance = new RetrofitManager(url);
//        }
        return instance;
    }

    /**
     * 获取实例，使用单利模式
     * 这里传递url参数，是因为项目中需要访问不同基类的地址
     * @param url               baseUrl
     * @return                  实例对象
     */
    public static RetrofitManager getInstance(String url, ArrayList<Interceptor> interceptors){
        //synchronized 避免同时调用多个接口，导致线程并发
        RetrofitManager instance;
//        synchronized (RetrofitManager.class){
            instance = new RetrofitManager(url,interceptors);
//        }
        return instance;
    }

    /**
     * 创建Retrofit
     * @param url               baseUrl
     */
    public RetrofitManager(String url) {
        builder = new OkHttpClient.Builder();
        //拦截日志，依赖
        builder.addNetworkInterceptor(InterceptorUtils.getHttpLoggingInterceptor(BuildConfig.DEBUG));
        //添加请求头拦截器
        builder.addInterceptor(InterceptorUtils.getRequestHeader());
        //添加统一请求拦截器
        //builder.addInterceptor(InterceptorUtils.commonParamsInterceptor());
        //设置缓存
        //builder.addNetworkInterceptor(InterceptorUtils.getCacheInterceptor());
        builder.addInterceptor(InterceptorUtils.getCacheInterceptor());
        InterceptorUtils.addCookie(builder);
        OkHttpClient build = builder.build();
        initBuilder(url,build);
    }

    /**
     * 创建Retrofit
     * @param url               baseUrl
     */
    public RetrofitManager(String url , ArrayList<Interceptor> interceptors) {
        builder = new OkHttpClient.Builder();
        //拦截日志，依赖
        builder.addNetworkInterceptor(InterceptorUtils.getHttpLoggingInterceptor(BuildConfig.DEBUG));
        builder.addInterceptor(InterceptorUtils.getRequestHeader());
        builder.addInterceptor(InterceptorUtils.getCacheInterceptor());
        if (interceptors!=null && interceptors.size()>0){
            for (Interceptor interceptor : interceptors){
                builder.addInterceptor(interceptor);
            }
        }
        //添加自定义CookieJar
        InterceptorUtils.addCookie(builder);
        OkHttpClient build = builder.build();
        initBuilder(url,build);
    }


    private void initBuilder(String url, OkHttpClient build) {
        initSSL();
        initTimeOut();
        if(BuildConfig.DEBUG){
            //不需要错误重连
            builder.retryOnConnectionFailure(false);
        }else {
            //错误重连
            builder.retryOnConnectionFailure(true);
        }
        //获取实例
        mRetrofit = new Retrofit
                //设置OKHttpClient,如果不设置会提供一个默认的
                .Builder()
                //设置baseUrl
                .baseUrl(url)
                //添加转换器Converter(将json 转为JavaBean)，用来进行响应数据转化(反序列化)的ConvertFactory
                .addConverterFactory(GsonConverterFactory.create(JsonUtils.getJson()))
                //添加自定义转换器
                //.addConverterFactory(buildGsonConverterFactory())
                //添加rx转换器，用来生成对应"Call"的CallAdapter的CallAdapterFactory
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(build)
                .build();
    }


    public <T> T create(final Class<T> service) {
        return mRetrofit.create(service);
    }



    /**
     * 初始化完全信任的信任管理器
     */
    @SuppressWarnings("deprecation")
    private void initSSL() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置读取超时时间，连接超时时间，写入超时时间值
     */
    private void initTimeOut() {
        builder.readTimeout(20000, TimeUnit.SECONDS);
        builder.connectTimeout(10000, TimeUnit.SECONDS);
        builder.writeTimeout(20000, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
    }


    /**
     * 构建GSON转换器
     * 这里，你可以自己去实现
     * @return GsonConverterFactory
     */
    private static GsonConverterFactory buildGsonConverterFactory(){
        GsonBuilder builder = new GsonBuilder();
        builder.setLenient();
        // 注册类型转换适配器
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)  {
                return null == json ? null : new Date(json.getAsLong());
            }
        });

        Gson gson = builder.create();
        return GsonConverterFactory.create(gson);
    }




}
