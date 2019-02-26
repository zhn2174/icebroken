package com.icebroken.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.icebroken.R;
import com.icebroken.utils.StringUtils;
import com.icebroken.widget.LoadView;
import com.mocuz.common.base.BaseModel;
import com.mocuz.common.base.BasePresenter;
import com.mocuz.common.baseapp.AppManager;
import com.mocuz.common.baserx.RxManager;
import com.mocuz.common.commonutils.TUtil;
import com.mocuz.common.commonutils.ToastUitl;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.wevey.selector.dialog.NormalSelectionDialog;

import butterknife.ButterKnife;


/**
 * des:基类fragment
 * Created by xsf
 * on 2016.07.12:38
 */
public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment {
    protected View                  rootView;
    public    T                     mPresenter;
    public    E                     mModel;
    public    RxManager             mRxManager;
    private NormalSelectionDialog dialog;//底部单选
    private NormalAlertDialog adialog;//底部单选
    private   LoadView              loadView;
    private   ProgressDialog        mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        FrameLayout frameLayout = new FrameLayout(getActivity());
        loadView = new LoadView(getActivity());
        loadView.setVisibility(View.GONE);
        try {
            frameLayout.addView(rootView);
        } catch (Exception e) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
            frameLayout.addView(rootView);
        }
        mProgressDialog = new ProgressDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        frameLayout.addView(loadView);
        mRxManager = new RxManager();
        ButterKnife.bind(this, rootView);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }
        initPresenter();
        initView();
        return frameLayout;
    }

    //获取布局文件
    protected abstract int getLayoutResource();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    protected abstract void initView();


    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
        if (null == loadView.getTag() || null != loadView.getTag() && !TextUtils.equals(loadView.getTag().toString(), "0")) {
            loadView.setVisibility(View.VISIBLE);
//        loadView.animate().alpha(1f).setDuration(1000)
//                .setListener(null);
            loadView.Show();
//        LoadingDialog.showDialogForLoading(this);
        }
    }

    /*
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        if (null == loadView.getTag() || null != loadView.getTag() && !TextUtils.equals(loadView.getTag().toString(), "0")) {
            loadView.setVisibility(View.VISIBLE);
            loadView.Show(msg);
        }
//        LoadingDialog.showDialogForLoading(getActivity(), msg, true);
    }

    /**
     * 停止浮动加载进度条
     */
    public void stopProgressDialog() {
        loadView.animate().alpha(1f).setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (null != loadView) {
                            loadView.setTag("0");
                            loadView.setVisibility(View.GONE);
                        }
                    }
                });
        rootView.animate().alpha(1f).setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }
                });
//        LoadingDialog.cancelDialogForLoading();
    }

    /**
     * 开启浮动加载进度条
     */
    public void showProgressDialog() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /*
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void showProgressDialog(String msg) {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
            mProgressDialog.setMessage(msg);
        }
    }

    /**
     * 停止浮动加载进度条
     */
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUitl.showShort(text);
    }


//    public void showToastWithImg(String text, int res) {
//        ToastUitl.showToastWithImg(text, res);
//    }
//
//    /**
//     * 网络访问错误提醒
//     */
//    public void showNetErrorTip() {
//        ToastUitl.showToastWithImg(getText(com.mocuz.common.R.string.net_error).toString(), com.mocuz.common.R.drawable.ic_wifi_off);
//    }
//
//    public void showNetErrorTip(String error) {
//        ToastUitl.showToastWithImg(error, com.mocuz.common.R.drawable.ic_wifi_off);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
    }

//    /**
//     * 底部单选弹出框
//     * title 标题
//     * list 显示内容list
//     * clickListener 点击事件
//     */
//    public void ShowSelectionDialog(String title, ArrayList<String> list, DialogOnItemClickListener clickListener) {
//        dialog = new NormalSelectionDialog.Builder(getActivity())
//                .setlTitleVisible(true)   //设置是否显示标题
//                .setTitleHeight(65)   //设置标题高度
//                .setTitleText(title)  //设置标题提示文本
//                .setTitleTextSize(14) //设置标题字体大小 sp
//                .setTitleTextColor(R.color.colorPrimary) //设置标题文本颜色
//                .setItemHeight(40)  //设置item的高度
//                .setItemWidth(0.9f)  //屏幕宽度*0.9
//                .setItemTextColor(R.color.colorPrimaryDark)  //设置item字体颜色
//                .setItemTextSize(14)  //设置item字体大小
//                .setCancleButtonText("取消")  //设置最底部“取消”按钮文本
//                .setOnItemListener(clickListener)
//                .setCanceledOnTouchOutside(true)  //设置是否可点击其他地方取消dialog
//                .build();
//        dialog.setDataList(list);
//        dialog.show();
//    }

    /**
     * 2个按钮提示弹出框
     * title 标题
     * lefttext 左边按钮，righttext右边按钮文字
     * clickListener 按钮点击事件
     */
    public void ShowAleryDialog(String title, String content, String lefttext, String righttext, DialogInterface.OnClickListener oklistener) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(StringUtils.isEmpty(righttext) ? "确定" : righttext, oklistener)
                .setNegativeButton(StringUtils.isEmpty(lefttext) ? "取消" : lefttext, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        dialog.show();
//        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(BaseUtil.getEndColor_int());
//        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(BaseUtil.getEndColor_int());
    }

    /**
     * 提示弹出框
     * title 标题
     * text 按钮文字
     * clickListener 按钮点击事件
     */
    public void ShowAleryDialog(String title, String content, String text, View.OnClickListener clickListener) {
        adialog = new NormalAlertDialog.Builder(getActivity())
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(title)
                .setTitleTextColor(R.color.colorPrimary)
                .setContentText(content)
                .setContentTextColor(R.color.colorPrimaryDark)
                .setSingleMode(true)
                .setSingleButtonText(text)
                .setSingleButtonTextColor(R.color.colorAccent)
                .setCanceledOnTouchOutside(true)
                .setSingleListener(clickListener)
                .build();
        adialog.show();
    }


    public void dismiss() {
        adialog.dismiss();
        dialog.dismiss();
    }

    public UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            showShortToast("开始分享");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            showShortToast("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            showShortToast(throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            if (!(share_media.equals(SHARE_MEDIA.QQ) || share_media.equals(SHARE_MEDIA.QZONE))) {
                showShortToast("取消分享");
            }
        }
    };

    /*
     * 不重复打开
     * @param cls
     */
    public void startActivitySingle(Class<?> cls) {
        if (AppManager.getAppManager().isOpenActivity(cls))
            AppManager.getAppManager().returnToActivity(cls);
        else
            startActivity(new Intent(getActivity(), cls));
    }
}

