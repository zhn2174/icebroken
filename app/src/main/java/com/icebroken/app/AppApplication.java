package com.icebroken.app;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.icebroken.bean.UserBean;
import com.icebroken.bean.UserInfo;
import com.icebroken.ui.main.activity.MainActivity;
import com.icebroken.utils.BaseUtil;
import com.icebroken.utils.MyCrashHandler;
import com.mocuz.common.baseapp.BaseApplication;
import com.mocuz.common.baseapp.CacheUtils;
import com.mocuz.common.commonutils.LocationUtils;
import com.mocuz.common.commonutils.LogUtils;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.bugly.Bugly;
import com.zxy.recovery.callback.RecoveryCallback;
import com.zxy.recovery.core.Recovery;


/**
 * APPLICATION
 */
public class AppApplication extends BaseApplication {
    // 自己的全局应用
    private static AppApplication mAppApplication;
    private static UserInfo userInfo = new UserInfo();
    public static String PushToken;
    public static String uid;
    public static String token;
    public static String phone;
    public static String code;
    public static Boolean isExist;
    public static UserBean user;

    /**
     * 用户实体
     */
//    public static  UserItem         userItem;
    @Override
    public void onCreate() {
        super.onCreate();
        setTheme(android.R.style.Theme_Light);
//        InitializeService.start(this);
        //bugly初始化
        Bugly.init(getApplicationContext(), "fac7cd0425", false);
        //缓存初始化
        CacheUtils.init(this);

        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .recoverEnabled(BaseUtil.getDebug())//上线时关闭debug
                .callback(new MyCrashCallback())
                .silent(!BaseUtil.getDebug(), Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                .skip(MainActivity.class)
                .init(this);
        MyCrashHandler.register();
        openPush();
        //百度地图初始化
        SDKInitializer.initialize(BaseApplication.getAppContext());
        LocationUtils.initLocationOption(this);
    }


    @Override
    public AssetManager getAssets() {
        return super.getAssets();
    }

    // 单例模式中获取唯一的ExitApplication 实例
    public static AppApplication getInstance() {
        if (null == mAppApplication) {
            synchronized (AppApplication.class) {
                if (null == mAppApplication) {
                    mAppApplication = new AppApplication();
                }
            }
        }
        return mAppApplication;

    }


    public static PackageInfo getPackageInfo() {
        try {
            PackageManager pm = getAppContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getAppContext().getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 登录时将新 商家登录

    public static final class MyCrashCallback implements RecoveryCallback {
        @Override
        public void stackTrace(String exceptionMessage) {
            LogUtils.loge("exceptionMessage : " + exceptionMessage);
        }

        @Override
        public void cause(String cause) {
            LogUtils.loge("cause : " + cause);
        }

        @Override
        public void exception(String exceptionType, String throwClassName, String throwMethodName, int throwLineNumber) {
            LogUtils.loge("exceptionClassName : " + exceptionType);
            LogUtils.loge("throwClassName : " + throwClassName);
            LogUtils.loge("throwMethodName : " + throwMethodName);
            LogUtils.loge("throwLineNumber : " + throwLineNumber);
        }

        @Override
        public void throwable(Throwable throwable) {

        }
    }


    private void openPush() {
        try {
            XGPushManager.registerPush(this, new XGIOperateCallback() {
                @Override
                public void onSuccess(Object data, int flag) {
                    //token在设备卸载重装的时候有可能会变
                    Log.d("TPush", "注册成功，设备token为：" + data);
                    PushToken = data.toString();
                }

                @Override
                public void onFail(Object data, int errCode, String msg) {
                    Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        AppApplication.userInfo = userInfo;
    }

    //    public void connect(EchoWebSocketListener listener) {
//        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
//                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
//                .writeTimeout(10, TimeUnit.SECONDS)//设置写的超时时间
//                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
//                .build();
//
//        Request request = new Request.Builder().url(ApiConstants.ServerAddress).build();
//        mOkHttpClient.newWebSocket(request, listener);
//        mOkHttpClient.dispatcher().executorService().shutdown();
//    }
}
