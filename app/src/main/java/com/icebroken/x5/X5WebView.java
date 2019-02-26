package com.icebroken.x5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Synopsis     X5webView
 * Author		Mosr
 * version		V4.0
 * Create 	    2017/3/30 15:38
 * Email  		intimatestranger@sina.cn
 */
public class X5WebView extends WebView {
    private static final String TAG = "mosr";
    //返回键上次点击的时间
    private long mLastClickTime;
    //图片资源Url
    private String mResourceUrl = "";
    //进度条
    private ProgressBar mProgressBar;
    //文件上传
    private ValueCallback<Uri> mUploadFile;
    //加载状态监听
    private WebViewOnLoadListener mWebViewonLoadListener;
    /*
     * 分享标题
     **/
    public String shareTitile = "帖子标题";
    /*
     * 分享内容
     **/
    public String shareContent = "帖子内容";
    /*
     * * 分享链接 *
     */
    public String shareLink = "https://www.baidu.com/";
    private String shareIcon;// 分享的图标地址
    private boolean ishide;//是否隐藏底部和分享
    /*
     * true 已收藏 false 未收藏
     */
    private boolean collectFlag = false;

    public X5WebView(Context context) {
        super(context);
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initWebClient();
        initJS();
        initDownLoad();
        initImageEvent();
    }


    public X5WebView setmUrl(String mUrl, Map<String, String> mHeadMap) {
        this.loadUrl(mUrl, mHeadMap);
        return this;
    }


    public X5WebView setmProgressBar(ProgressBar mProgressBar) {
        this.mProgressBar = mProgressBar;
        return this;
    }


    public void setmWebViewonLoadListener(WebViewOnLoadListener mWebViewonLoadListener) {
        this.mWebViewonLoadListener = mWebViewonLoadListener;
    }

    public boolean isIshide() {
        return ishide;
    }

    public void setIshide(boolean ishide) {
        this.ishide = ishide;
    }

    /**
     * 初始化图片长按时间
     */
    private void initImageEvent() {
        this.getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult hitTestResult = X5WebView.this.getHitTestResult();
//                final String path = mResourceUrl = hitTestResult.getExtra();
                switch (hitTestResult.getType()) {
                    case WebView.HitTestResult.IMAGE_TYPE://获取点击的标签是否为图片
//                        Toast.makeText(getContext(), "当前选定的图片的URL是" + path, Toast.LENGTH_LONG).show();
                        break;
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE://获取点击的标签是否为图片
//                        Toast.makeText(getContext(), "当前选定的图片的URL是" + path, Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });

        this.getView().setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

                                                          @Override
                                                          public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                                                              MenuItem.OnMenuItemClickListener onMenuItemClickListener = new MenuItem.OnMenuItemClickListener() {
//                                                                  public boolean onMenuItemClick(MenuItem item) {
//                                                                      // do the menu action
//                                                                      switch (item.getItemId()) {
//                                                                          case 1:
//                                                                              Toast.makeText(getContext(), "分享", Toast.LENGTH_SHORT).show();
//                                                                              break;
//                                                                          case 2:
//                                                                              Toast.makeText(getContext(), "下载", Toast.LENGTH_SHORT).show();
//                                                                              break;
//                                                                      }
//                                                                      return true;
//                                                                  }
//                                                              };
//
//                                                              WebView.HitTestResult result = X5WebView.this.getHitTestResult();
//                                                              int resultType = result.getType();
//                                                              if ((resultType == WebView.HitTestResult.IMAGE_TYPE)) {
//                                                                  menu.setHeaderTitle("你想干嘛？");
//                                                                  Intent mIntent = new Intent();
//                                                                  MenuItem item = menu.add(0, 1, 0, "分享")
//                                                                          .setOnMenuItemClickListener(onMenuItemClickListener);
//                                                                  item.setIntent(mIntent);
//
//                                                                  item = menu.add(0, 2, 0, "下载").setOnMenuItemClickListener(
//                                                                          onMenuItemClickListener);
//                                                                  item.setIntent(mIntent);
//                                                              }
//                                                              return;
                                                          }
                                                      }
        );
    }

    /**
     * 初始化下载监听
     */
    private void initDownLoad() {
        this.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(final String url, String userAgent, final String contentDisposition,
                                        String arg3, long arg4) {
                Log.d(TAG, "url: " + url);
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("确定下载？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Uri uri = Uri.parse(url);
                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                        getContext().startActivity(intent);
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).show();
            }
        });
    }

    /**
     * java 调用 js方法 并且 传值
     * 步骤：1、调用 js函数  2、js回调一个android方法得到参数  3、js处理函数
     */
    private void initJS() {
        this.addJavascriptInterface(new WebViewJavaScriptFunction() {
            @Override
            public void onJsFunctionCalled(String tag) {

            }


        }, "myjs");
    }

    /**
     * 初始化Client
     */
    private void initWebClient() {
        WebSettings webSetting = this.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(getContext().getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(getContext().getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(getContext().getDir("geolocation", 0)
                .getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setLoadWithOverviewMode(true);
//        webSetting.setDatabaseEnabled(false);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        //webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        CookieSyncManager.createInstance(getContext());
        CookieSyncManager.getInstance().sync();

        /**
         * 防止加载网页时调起系统浏览器
         */
        this.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              WebResourceRequest request) {
                Log.e(TAG, "request.getUrl().toString() is " + request.getUrl().toString());

                return super.shouldInterceptRequest(view, request);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (null != mWebViewonLoadListener)
                    mWebViewonLoadListener.onLoadFinish(view, url);
            }
        });

        this.setWebChromeClient(new WebChromeClient() {

            /**更新进度条
             * @param webView
             * @param i
             */
            @Override
            public void onProgressChanged(WebView webView, int i) {

                if (null != mProgressBar) {
                    if (i != 100 && mProgressBar.getVisibility() == GONE)
                        mProgressBar.setVisibility(VISIBLE);
                    else if (i == 100 && mProgressBar.getVisibility() == VISIBLE)
                        mProgressBar.setVisibility(GONE);
                    mProgressBar.setProgress(i);
                }
                super.onProgressChanged(webView, i);
            }

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            View myVideoView;
            View myNormalView;
            IX5WebChromeClient.CustomViewCallback callback;

            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
//                FrameLayout normalView = (FrameLayout) findViewById(R.id.frl_parent);
//                FrameLayout normalView = (FrameLayout) getParent();
                ViewGroup viewGroup = (ViewGroup) getParent();
//                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                myVideoView = view;
//                myNormalView = normalView;
                callback = customViewCallback;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }

            @Override
            public boolean onShowFileChooser(WebView arg0,
                                             ValueCallback<Uri[]> arg1, FileChooserParams arg2) {
                Log.e(TAG, "onShowFileChooser");
                return super.onShowFileChooser(arg0, arg1, arg2);
            }

            /**
             * 文件选择
             * @param uploadFile
             * @param acceptType
             * @param captureType
             */
            @Override
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String captureType) {

                mUploadFile = uploadFile;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType(TextUtils.isEmpty(acceptType) ? "*/*" : acceptType);//"*/*" 表示全部类型文件
                ((Activity) getContext()).startActivityForResult(Intent.createChooser(i, "选择文件"), 0);
            }


            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2, JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                // AlertDialog.Builder builder = new Builder(getContext());
                // builder.setTitle("X5内核");
                // builder.setPositiveButton("确定", new
                // DialogInterface.OnClickListener() {
                //
                // @Override
                // public void onClick(DialogInterface dialog, int which) {
                // // TODO Auto-generated method stub
                // dialog.dismiss();
                // }
                // });
                // builder.show();
                // arg3.confirm();
                // return true;
                Log.i(TAG, "setX5webview = null");
                return super.onJsAlert(null, "www.baidu.com", "aa", arg3);
            }

            /**
             * 对应js 的通知弹框 ，可以用来实现js 和 android之间的通信
             */


            @Override
            public void onReceivedTitle(WebView arg0, final String arg1) {
                super.onReceivedTitle(arg0, arg1);
                Log.i(TAG, "webpage title is " + arg1);

            }
        });
    }

    /**
     * 文件选择回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != mUploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        mUploadFile.onReceiveValue(result);
                        mUploadFile = null;
                    }
                    break;
                case 1:
                    Uri uri = data.getData();
                    String path = uri.getPath();
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != mUploadFile) {
                mUploadFile.onReceiveValue(null);
                mUploadFile = null;
            }

        }
    }

    /**
     * 返回键事件处理
     *
     * @param keyCode
     * @param event
     *
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && System.currentTimeMillis() - mLastClickTime > 500
                && this != null && this.canGoBack()) {
            this.goBack();
            mLastClickTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public interface WebViewJavaScriptFunction {

        void onJsFunctionCalled(String tag);
    }

    /**
     * webView 加载监听
     */
    public interface WebViewOnLoadListener {

        void onLoadFinish(WebView view, String url);
    }

    public static String getTAG() {
        return TAG;
    }

    public long getmLastClickTime() {
        return mLastClickTime;
    }

    public void setmLastClickTime(long mLastClickTime) {
        this.mLastClickTime = mLastClickTime;
    }

    public String getmResourceUrl() {
        return mResourceUrl;
    }

    public void setmResourceUrl(String mResourceUrl) {
        this.mResourceUrl = mResourceUrl;
    }

    public ValueCallback<Uri> getmUploadFile() {
        return mUploadFile;
    }

    public void setmUploadFile(ValueCallback<Uri> mUploadFile) {
        this.mUploadFile = mUploadFile;
    }

    public String getShareTitile() {
        return shareTitile;
    }

    public void setShareTitile(String shareTitile) {
        this.shareTitile = shareTitile;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(String shareIcon) {
        this.shareIcon = shareIcon;
    }


    public boolean isCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(boolean collectFlag) {
        this.collectFlag = collectFlag;
    }
}
