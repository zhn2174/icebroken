package com.icebroken.ui.x5;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.icebroken.R;
import com.icebroken.base.BaseActivity;
import com.icebroken.widget.MyToolbar;
import com.icebroken.x5.X5WebView;
import com.tencent.smtt.sdk.WebView;
import com.zhy.autolayout.AutoFrameLayout;

import butterknife.Bind;

/**
 * Synopsis     TBS X5 webview
 * Author		Mosr
 * version		V4.0
 * Create 	    2017/3/24 16:49
 * Email  		intimatestranger@sina.cn
 */
public class X5WebViewActivity extends BaseActivity {
    public static final String TITLE = "title";
    public static final String URL = "url";
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;
    @Bind(R.id.wbv_x5)
    X5WebView wbvX5;
    @Bind(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.frl_parent)
    AutoFrameLayout frlParent;

    //webView 标题
    private String mTitle;
    private String mUrl="https://www.baidu.com/";

    @Override
    public int getLayoutId() {
        return R.layout.activity_x5webview;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationIcon(R.mipmap.return_icon);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//设置窗口支持透明度
        if (null != getIntent()) {
            mTitle = getIntent().getStringExtra(TITLE);
            mUrl = getIntent().getStringExtra(URL);
        }
        if (!TextUtils.isEmpty(mTitle))
        /**
         * 自定义进度条样式
         */
        if (null != mProgressBar)
//            mProgressBar.setProgressDrawable(new ClipDrawable(BaseUtil.drawSystemBar(this, BaseUtil.getStartColor(), BaseUtil.getEndColor()), ClipDrawable.VERTICAL, ClipDrawable.HORIZONTAL));
        wbvX5.loadUrl(mUrl);
        wbvX5  .setmProgressBar(mProgressBar)
                .setmWebViewonLoadListener(new X5WebView.WebViewOnLoadListener() {
                    @Override
                    public void onLoadFinish(WebView view, String url) {
                        if (TextUtils.isEmpty(mTitle)){

                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        wbvX5.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return wbvX5.onKeyDown(keyCode, event) ? true : super.onKeyDown(keyCode, event);
    }
    /**
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, X5WebViewActivity.class);
        Bundle bundle = new Bundle();
//        bundle.putSerializable("advInfo", advInfo);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
}
