package com.mocuz.common.baserx;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.mocuz.common.R;
import com.mocuz.common.commonutils.NetWorkUtils;
import com.mocuz.common.commonutils.ToastUitl;
import com.mocuz.common.commonwidget.LoadingDialog;

import rx.Subscriber;

import static com.mocuz.common.baseapp.BaseApplication.getAppContext;

/**
 * des:订阅封装
 * Created by xsf
 * on 2016.09.10:16
 */
/********************使用例子********************/
/*_apiService.login(mobile, verifyCode)
        .//省略
        .subscribe(new RxSubscriber<User user>(mContext,false) {
@Override
public void _onNext(User user) {
        // 处理user
        }

@Override
public void _onError(String msg) {
        ToastUtil.showShort(mActivity, msg);
        });*/
public abstract class RxSubscriber<T> extends Subscriber<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog=false;

    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog= true;
    }
    public void hideDialog() {
        this.showDialog= true;
    }

    public RxSubscriber(Context context, String msg,boolean showDialog) {
        this.mContext = context;
        this.msg = msg;
//        this.showDialog=showDialog;
    }
    public RxSubscriber(Context context) {
        this(context, getAppContext().getString(R.string.loading),true);
    }
    public RxSubscriber(Context context,boolean showDialog) {
        this(context, getAppContext().getString(R.string.loading),showDialog);
    }

    @Override
    public void onCompleted() {
        if (showDialog){
            LoadingDialog.cancelDialogForLoading();
            showDialog=false;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            try {
                LoadingDialog.showDialogForLoading((Activity) mContext,msg,true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onNext(T t) {
        _onNext(t);
    }
    @Override
    public void onError(Throwable e) {
        if (null==mContext ){
            return;
        }
        if (showDialog){
            showDialog=false;
            LoadingDialog.cancelDialogForLoading();
        }
        e.printStackTrace();
        //网络
        if (!NetWorkUtils.isNetConnected(getAppContext())) {
            ToastUitl.showShort(getAppContext().getString(R.string.no_net));
            _onError(getAppContext().getString(R.string.no_net));
        }
        //服务器
        else if (e instanceof ServerException) {
            if (!TextUtils.isEmpty(e.getMessage())){
                ToastUitl.showShort(e.getMessage());
            }
            _onError(e.getMessage());
        }  //钱包服务器401
        else if ( null!=e&& e.getMessage()!=null &&  e.getMessage().equals("HTTP 401 Unauthorized")) {
//                ToastUitl.showShort("用户登录信息超时，正在重新获取，请稍后尝试");
//            new RxManager().post("WALLET_TOKEN_TIMEOUT","");
            _onError("401");
        }
        //其它
        else {
//            ToastUitl.showShort(getAppContext().getString(R.string.net_error));
            _onError(getAppContext().getString(R.string.net_error));
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
