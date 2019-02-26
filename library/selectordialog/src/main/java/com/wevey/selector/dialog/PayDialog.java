package com.wevey.selector.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ethanco.lib.PasswordInput;
import com.weavey.utils.ScreenSizeUtils;


/**
 * Created by Weavey on 2016/9/3.
 */
public class PayDialog {


    private static Context mContext;
    LinearLayout back;
    PasswordInput pwd;
    TextView fogpwd;
    private Dialog mDialog;
    private View dialogView;
    private Button bottomBtn;
    private LinearLayout linearLayout;
    private View.OnClickListener onClickListener;
    public PayDialog(Context mContext) {
        this.mContext=mContext;
        mDialog = new Dialog(mContext, R.style.bottomDialogStyle);
        dialogView = View.inflate(mContext, R.layout.pay_dia_lay, null);
        back= (LinearLayout) dialogView.findViewById(R.id.back);
        pwd= (PasswordInput) dialogView.findViewById(R.id.pwd);
        fogpwd= (TextView) dialogView.findViewById(R.id.fogpwd);
        mDialog.setContentView(dialogView); // 一定要在setAttributes(lp)之前才有效
        //设置dialog的宽
        Window dialogWindow = mDialog.getWindow();
        mDialog.setCancelable(true);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenSizeUtils.getInstance(mContext).getScreenWidth() * 1);
        lp.gravity = Gravity.BOTTOM;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        loadItem();
    }

    public PasswordInput getPwd() {
        return pwd;
    }

    public void setPwd(PasswordInput pwd) {
        this.pwd = pwd;
    }

    public TextView getFogpwd() {
        return fogpwd;
    }

    public void setFogpwd(TextView fogpwd) {
        this.fogpwd = fogpwd;
    }

    //根据数据生成item
    private void loadItem() {
        fogpwd.setOnClickListener(onClickListener);
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public boolean isShowing() {

        return mDialog.isShowing();
    }

    public void show() {
        mDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Context context = pwd.getContext();
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                pwd.setFocusable(true);
                pwd.setFocusableInTouchMode(true);
                pwd.requestFocus();
                imm.showSoftInput(pwd, InputMethodManager.SHOW_IMPLICIT);
            }
        },200);

    }

    public void dismiss() {

        mDialog.dismiss();
    }

    public static class Builder {

        //标题属性
        private boolean boolTitle;
        private int titleHeight;
        private String titleText;
        private int titleTextColor;
        private float titleTextSize;
        //item属性
        private DialogOnItemClickListener onItemListener;
        //取消按钮属性
        private String cancleButtonText;

        private boolean isTouchOutside;

        public Builder(Context context) {

            mContext = context;

            boolTitle = false; // 默认关闭标题
            titleHeight = 65; // 默认标题高度  dp
            titleText = "请选择";
            titleTextColor = ContextCompat.getColor(context, R.color.black_light);
            titleTextSize = 13;

            onItemListener = null;

            cancleButtonText = "取消";
            isTouchOutside = true;
        }

        public boolean getTitleVisible() {
            return boolTitle;
        }

        public Builder setlTitleVisible(boolean isVisible) {
            this.boolTitle = isVisible;
            return this;
        }

        public int getTitleHeight() {
            return titleHeight;
        }

        public Builder setTitleHeight(int dp) {
            this.titleHeight = dp;
            return this;
        }

        public String getTitleText() {
            return titleText;
        }

        public Builder setTitleText(String titleText) {
            this.titleText = titleText;
            return this;
        }

        public int getTitleTextColor() {
            return titleTextColor;
        }

        public Builder setTitleTextColor(@ColorRes int titleTextColor) {
            this.titleTextColor = ContextCompat.getColor(mContext, titleTextColor);
            return this;
        }

        public float getTitleTextSize() {
            return titleTextSize;
        }

        public Builder setTitleTextSize(int sp) {

            this.titleTextSize = sp;
            return this;
        }

        public DialogOnItemClickListener getOnItemListener() {
            return onItemListener;
        }

        public Builder setOnItemListener(DialogOnItemClickListener onItemListener) {
            this.onItemListener = onItemListener;
            return this;
        }


        public String getCancleButtonText() {
            return cancleButtonText;
        }

        public Builder setCancleButtonText(String cancleButtonText) {
            this.cancleButtonText = cancleButtonText;
            return this;
        }

        public boolean isTouchOutside() {
            return isTouchOutside;
        }

        public Builder setCanceledOnTouchOutside(boolean isTouchOutside) {

            this.isTouchOutside = isTouchOutside;
            return this;
        }

    }

}