package com.mocuz.common.commonutils;

import android.widget.TextView;
import android.widget.Toast;

import com.mocuz.common.R;
import com.mocuz.common.baseapp.BaseApplication;

import es.dmoral.toasty.Toasty;


/**
 * Toast统一管理类
 */
public class ToastUitl {
    private static Toast mToast;
    private static Toast mErrorToast;
    private static Toast mSuccessToast;
    private static Toast mImgToast;

    /**
     * 通用短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (null == mToast) {
            synchronized (ToastUitl.class) {
                if (null == mToast)
                    mToast = Toasty.normal(BaseApplication.getAppContext(), message, Toast.LENGTH_SHORT);
                else
                    setText(mToast,message);
            }
        } else
            setText(mToast,message);
        mToast.show();
    }

    /**
     * 通用长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (null == mToast) {
            synchronized (ToastUitl.class) {
                if (null == mToast)
                    mToast = Toasty.normal(BaseApplication.getAppContext(), message, Toast.LENGTH_LONG);
                else
                    setText(mToast,message);
            }
        } else
            setText(mToast,message);
        mToast.show();
    }

    /**
     * 错误提示Toast
     *
     * @param message
     */
    public static void showError(CharSequence message) {
        if (null == mErrorToast) {
            synchronized (ToastUitl.class) {
                if (null == mErrorToast)
                    mErrorToast = Toasty.error(BaseApplication.getAppContext(), message, Toast.LENGTH_LONG, true);
                else
                    setText(mErrorToast,message);
            }
        } else
            setText(mErrorToast,message);
        mErrorToast.show();
    }

    /**
     * 成功提示Toast
     *
     * @param message
     */
    public static void showSuccess(CharSequence message) {
        if (null == mSuccessToast) {
            synchronized (ToastUitl.class) {
                if (null == mSuccessToast)
                    mSuccessToast = Toasty.success(BaseApplication.getAppContext(), message, Toast.LENGTH_LONG, true);
                else
                    setText(mSuccessToast,message);
            }
        } else
            setText(mSuccessToast,message);
        mSuccessToast.show();
    }

    /**
     * 带图片的提示Toast
     *
     * @param message
     * @param icon
     */
    public static void showToastWithImg(String message, int icon) {
        if (null == mImgToast) {
            synchronized (ToastUitl.class) {
                if (null == mImgToast)
                    mImgToast = Toasty.normal(BaseApplication.getAppContext(), message, icon);
                else
                    setText(mImgToast,message);
            }
        } else
            setText(mImgToast,message);
        mImgToast.show();
    }

    static void setText(Toast nToast,CharSequence message) {
        TextView toastTextView = null;
        if (null != nToast && null != nToast.getView())
            toastTextView = (TextView)
                    nToast.getView().findViewById(R.id.toast_text);
        if (null != toastTextView)
            toastTextView.setText(message);
    }
}
