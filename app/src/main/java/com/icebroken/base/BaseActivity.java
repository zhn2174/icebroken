package com.icebroken.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.icebroken.utils.RomUtils;
import com.mocuz.common.base.BaseModel;
import com.mocuz.common.base.BasePresenter;
import com.mocuz.common.baseapp.AppManager;
import com.mocuz.common.baserx.RxManager;
import com.mocuz.common.commonutils.TUtil;
import com.mocuz.common.commonutils.ToastUitl;
import com.mocuz.common.commonwidget.StatusBarCompat;
import com.mocuz.common.flyn.Eyes;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wevey.selector.dialog.DialogOnItemClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.wevey.selector.dialog.NormalSelectionDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import com.icebroken.R;
import com.icebroken.api.AppConstant;
import com.icebroken.utils.StringUtils;
import com.icebroken.widget.LoadView;

import net.simonvt.numberpicker.NumberPicker;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/*
 * Created by Dev on 2017/3/8.
 */

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends SwipeBackActivity {
    public T mPresenter;
    public E mModel;
    public Context mContext;
    public RxManager mRxManager;
    /**
     * 根布局
     */
    private LinearLayout mRootLayout;
    /**
     * 默认布局参数
     */
    private LinearLayout.LayoutParams mDefaultLayoutParamsMM = null;
    private LayoutInflater mInflater;
    private NormalSelectionDialog dialog;//底部单选
    private NormalAlertDialog adialog;//
    private AppBarLayout appBarLayout;
    private LoadView loadView;
    private View rootView;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxManager = new RxManager();
        doBeforeSetcontentView();
        initTitle();
        initContenView();
        ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        setmSwipeBackLayout();
        this.initPresenter();
        this.initView();
    }

    private void setmSwipeBackLayout() {
        SwipeBackLayout mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {

            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
//                vibrate(VIBRATE_DURATION);
            }

            @Override
            public void onScrollOverThreshold() {
//                vibrate(VIBRATE_DURATION);
            }
        });
    }



    private void initTitle() {
        appBarLayout = new AppBarLayout(this);
        mRootLayout.addView(appBarLayout);
    }

    public void hideShadow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout.setElevation(0f);
        }
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
//        // 无标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 默认着色状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || RomUtils.getMiuiVersion() >= 6 || RomUtils.checkIsMeizuRom()) {
            Eyes.translucentStatusBar(this, true);
        } else {
            Eyes.translucentStatusBar(this, false);
        }

        SetStatusBarColor();
        mRootLayout = new LinearLayout(this);
//        mRootLayout.setBackgroundColor(Color.rgb(255, 255, 255));
//        mRootLayout.setFitsSystemWindows(false);
//        mRootLayout.setClipToPadding(false);
        mRootLayout.setOrientation(LinearLayout.VERTICAL);
        mDefaultLayoutParamsMM = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mInflater = LayoutInflater.from(this);
    }

    private void initContenView() {
        mProgressDialog = new ProgressDialog(this, AlertDialog.THEME_HOLO_LIGHT);
        FrameLayout frameLayout = new FrameLayout(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mRootLayout.addView(frameLayout, mDefaultLayoutParamsMM);
        rootView = mInflater.inflate(getLayoutId(), null, false);
        frameLayout.addView(rootView, params);
        loadView = new LoadView(this);
        loadView.setVisibility(View.GONE);
        frameLayout.addView(loadView, params);
        setContentView(mRootLayout);
    }

    /*********************
     * 子类实现
     *****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    public abstract void initView();


    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
//        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, com.mocuz.common.R.color.main_color));
//        StatusBarCompat.setStatusBarColor(this, BaseUtil.getBaseColor());
        StatusBarCompat.setTranslucentStatus(this, true);
    }


    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }


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
        intent.setClass(this, cls);
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
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
//        loadView.setVisibility(View.VISIBLE);
//        loadView.Show();
        //加0.1S延迟 ，解决 GIF图片本身卡顿
        if (null == loadView.getTag() || null != loadView.getTag() && !TextUtils.equals(loadView.getTag().toString(), "0")) {
            loadView.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadView.Show();
                }
            }, 100);
        }

    }

    /*
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(final String msg) {
        if (null == loadView.getTag() || null != loadView.getTag() && !TextUtils.equals(loadView.getTag().toString(), "0")) {
            loadView.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadView.Show(msg);
                }
            }, 100);
        }
    }

    /**
     * 停止浮动加载进度条
     */
    public void stopProgressDialog() {
        loadView.animate().alpha(0f).setDuration(1)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loadView.setTag("0");
                        loadView.setVisibility(View.GONE);
                    }
                });
        rootView.animate().alpha(1f).setDuration(0);
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

    /*
     * 停止浮动加载进度条
     */
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    /*
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUitl.showShort(text);
    }


//    /*
//     * 带图片的toast
//     *
//     * @param text
//     * @param res
//     */
//    public void showToastWithImg(String text, int res) {
//        ToastUitl.showToastWithImg(text, res);
//    }

//    /*
//     * 网络访问错误提醒
//     */
//    public void showNetErrorTip() {
//        ToastUitl.showToastWithImg(getText(com.mocuz.common.R.string.net_error).toString(), com.mocuz.common.R.drawable.ic_wifi_off);
//    }

//    public void showNetErrorTip(String error) {
//        ToastUitl.showToastWithImg(error, com.mocuz.common.R.drawable.ic_wifi_off);
//    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
        ButterKnife.unbind(this);
        AppManager.getAppManager().finishActivity(this);
        fixInputMethodManagerLeak(this);
        //Glide 回收
        if(Util.isOnMainThread()&&!this.isFinishing())
        {
            Glide.with(this).pauseRequests();
        }
    }

    /*
     * 内存泄露现象及解决
     * @param destContext
     */
    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) return;
        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f;
        Object obj_get;
        for (String param : arr) {
            try {
                f = imm.getClass().getDeclaredField(param);
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
//    /**
//     * 底部单选弹出框
//     * title 标题
//     * list 显示内容list
//     * clickListener 点击事件
//     */
//    public void ShowSelectionDialog(String title, ArrayList<String> list, DialogOnItemClickListener clickListener) {
//        dialog = new NormalSelectionDialog.Builder(this)
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

    /*
     * 底部单选弹出框 无标题
     * list 显示内容list
     * clickListener 点击事件
     */
    public void ShowSelectionNoTitle(ArrayList<String> list, DialogOnItemClickListener clickListener) {
        dialog = new NormalSelectionDialog.Builder(this)
                .setlTitleVisible(false)   //设置是否显示标题
                .setItemHeight(40)  //设置item的高度
                .setItemWidth(0.9f)  //屏幕宽度*0.9
                .setItemTextColor(R.color.colorPrimaryDark)  //设置item字体颜色
                .setItemTextSize(14)  //设置item字体大小
                .setCancleButtonText("取消")  //设置最底部“取消”按钮文本
                .setOnItemListener(clickListener)
                .setCanceledOnTouchOutside(true)  //设置是否可点击其他地方取消dialog
                .build();
        dialog.setDataList(list);
        dialog.show();
    }

//    /**
//     * 底部单选弹出框 无标题
//     * id 显示内容布局
//     */
//    public void ShowAdminDialog(int id) {
//        dialog = new NormalSelectionDialog.Builder(this)
//                .setlTitleVisible(false)   //设置是否显示标题
//                .setItemHeight(40)  //设置item的高度
//                .setItemWidth(0.9f)  //屏幕宽度*0.9
//                .setItemTextColor(R.color.colorPrimaryDark)  //设置item字体颜色
//                .setItemTextSize(14)  //设置item字体大小
//                .setCancleButtonText("取消")  //设置最底部“取消”按钮文本
//                .setCanceledOnTouchOutside(true)  //设置是否可点击其他地方取消dialog
//                .build();
//        dialog.setContentView(id);
//        dialog.show();
//    }

    /**
     * 2个按钮提示弹出框
     * title 标题
     * lefttext 左边按钮，righttext右边按钮文字
     * clickListener 按钮点击事件
     * 右边确定，左边取消
     */
    public void ShowAleryDialog(String title, String content, String lefttext, String righttext, DialogInterface.OnClickListener oklistener) {
        AlertDialog dialog = new AlertDialog.Builder(this)
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
     * 2个按钮提示弹出框
     * title 标题
     * lefttext 左边按钮，righttext右边按钮文字
     * clickListener 按钮点击事件
     * 右边确定，左边取消
     */
    public void ShowAleryDialog2(String title, String content, String lefttext, String righttext
            , DialogInterface.OnClickListener oklistener, DialogInterface.OnClickListener cancellistener) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(StringUtils.isEmpty(righttext) ? "确定" : righttext, oklistener)
                .setNegativeButton(StringUtils.isEmpty(lefttext) ? "取消" : lefttext, cancellistener).create();
        dialog.show();
//        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(BaseUtil.getEndColor_int());
//        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(BaseUtil.getEndColor_int());
    }


    /**
     * title 标题
     * lefttext 左边按钮，righttext右边按钮文字
     * clickListener 按钮点击事件
     * 右边确定，左边取消
     */
    public void ShowDialog(String content, String lefttext, String righttext, DialogInterface.OnClickListener oklistener) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
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
    public void ShowAleryDialog(String title, String content, String text, DialogInterface.OnClickListener oklistener) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(StringUtils.isEmpty(text) ? "确定" : text, oklistener)
                .create();
        dialog.show();
//        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(BaseUtil.getEndColor_int());
    }

    /**
     * 默认提示弹
     * title 标题
     */
    public void ShowNomalDialog(String content) {
        adialog = new NormalAlertDialog.Builder(this)
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText("提示")
                .setTitleTextColor(R.color.colorPrimary)
                .setContentText(content)
                .setContentTextColor(R.color.colorPrimaryDark)
                .setSingleMode(true)
                .setSingleButtonText("确定")
                .setSingleButtonTextColor(R.color.colorAccent)
                .setCanceledOnTouchOutside(true)
                .setSingleListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                })
                .build();
        adialog.show();
    }

    public void dismiss() {
        if (null != adialog) {
            adialog.dismiss();
        } else if (null != dialog) {
            dialog.dismiss();
        }

    }

    public UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            showShortToast("开始分享");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            showShortToast("分享成功");
            new RxManager().post(AppConstant.SHARESUCCESS, true);
//            mWebView.loadUrl("javascript:Share_success()");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    // px转dp
    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /*
     * 不重复打开
     *
     * @param cls
     */
    public void startActivitySingle(Class<?> cls) {
        if (AppManager.getAppManager().isOpenActivity(cls))
            AppManager.getAppManager().returnToActivity(cls);
        else
            startActivity(new Intent(this, cls));
    }

    /**
     * 底部弹框
     *
     * @param
     */
    public int position;
    public void ShowPop(final String[] list, final List<String> value, String title, final TextView selectView) {
        Dialog mDialog = new Dialog(mContext, R.style.pop_style);
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View mView = mLayoutInflater.inflate(R.layout.layout_select_pop2, null);
        final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mContext.getResources().getDisplayMetrics().widthPixels,
                WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams localLayoutParams = mDialog.getWindow()
                .getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
        mDialog.onWindowAttributesChanged(localLayoutParams);
        mDialog.addContentView(mView, params);
        mDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
        mDialog.show();
        final Dialog nDialog = mDialog;

        GradientDrawable mCancelDrawable = new GradientDrawable();
        int strokeWidth = 1; // 3dp 边框宽度
        int roundRadius = 10; // 8dp 圆角半径
        mCancelDrawable.setCornerRadius(roundRadius);
        mCancelDrawable.setStroke(strokeWidth, mContext.getResources().getColor(R.color.font_1));
        mView.findViewById(R.id.cancel).setBackgroundDrawable(mCancelDrawable);
        mView.findViewById(R.id.sure).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        selectView.setText(list[position]);
                        selectView.setTag(value.get(position));
                        nDialog.dismiss();
                    }
                });

        GradientDrawable mOKDrawable = new GradientDrawable();
        mOKDrawable.setCornerRadius(roundRadius);
        mOKDrawable.setColor(getResources().getColor(R.color.blue_d));
        mView.findViewById(R.id.sure).setBackgroundDrawable(mOKDrawable);
        mView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                nDialog.dismiss();

            }
        });
        ((TextView) mView.findViewById(R.id.poptitle)).setText(title);
        NumberPicker numberPicker = (NumberPicker) mView.findViewById(R.id.np);
        numberPicker.setDisplayedValues(list);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(list.length - 1);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker arg0, int oldVlue,
                                      int newVlue) {
                position = newVlue;
            }
        });

    }
}
