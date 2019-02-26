package com.icebroken.services;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.mocuz.common.baseapp.BaseApplication;
import com.mocuz.common.commonutils.LogUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.umeng.socialize.UMShareAPI;

import com.icebroken.utils.BaseUtil;


/**
 * Synopsis     用于初始化所需组件
 * Author		Mosr
 * version		${VERSION}
 * Create 	    2017/6/9 10:20
 * Email  		intimatestranger@sina.cn
 */
public class InitializeService extends IntentService {
    private static final String ACTION_INIT_WHEN_APP_CREATE = "juronggvo.com.juronggov.services.action.INIT";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public InitializeService() {
        super("InitializeService");
    }

    /**
     * This method is invoked on the worker thread with a request to process.
     * Only one Intent is processed at a time, but the processing happens on a
     * worker thread that runs independently from other application logic.
     * So, if this code takes a long time, it will hold up other requests to
     * the same IntentService, but it will not hold up anything else.
     * When all requests have been handled, the IntentService stops itself,
     * so you should not call {@link #stopSelf}.
     *
     * @param intent The value passed to {@link
     *               Context#startService(Intent)}.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @SuppressLint("SdCardPath")
    private void performInit() {
        LogUtils.logInit(BaseUtil.getDebug());
        //友盟社会化初始化
        UMShareAPI.get(this);
        LogUtils.logd("AppApplication : " + "Recovery: init");
//        PlatformConfig.setWeixin(getString(R.string.wx_appid), getString(R.string.wx_appsecret));
//        PlatformConfig.setQQZone(getString(R.string.qq_appid), getString(R.string.qq_appkey));
//        PlatformConfig.setSinaWeibo("100424468", "c7394704798a158208a74ab60104f0ba");

//        openPush();
//        new UmengMessageHandler(BaseApplication.getAppContext()).uMengListing();
//TBS_X5 start
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                Log.e("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub

            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.d("app", "onDownloadFinish is " + i);
            }

            @Override
            public void onInstallFinish(int i) {
                Log.d("app", "onInstallFinish is " + i);
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.d("app", "onDownloadProgress:" + i);
            }
        });
        QbSdk.initX5Environment(BaseApplication.getAppContext().getApplicationContext(), cb);
        //TBS_X5 end

        SDKInitializer.initialize(BaseApplication.getAppContext());

    }


}
