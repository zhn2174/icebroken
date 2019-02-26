package com.wevey.selector;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weavey.utils.ScreenSizeUtils;
import com.wevey.selector.dialog.R;


public class BtSelectionDialog {


    private Dialog mDialog;
    private View dialogView;
    private TextView action_dialog_btn1;
    private TextView action_dialog_btn2;
    private LinearLayout mCancel;
    private LinearLayout btn1;
    private LinearLayout btn2;
    private LinearLayout linearLayout;


    public BtSelectionDialog(Context mContext ,String text,String text2, View.OnClickListener l, View.OnClickListener l2) {

        mDialog = new Dialog(mContext, R.style.bottomDialogStyle);
        dialogView = View.inflate(mContext, R.layout.bottom_dialog, null);
        mDialog.setContentView(dialogView); // 一定要在setAttributes(lp)之前才有效
        //设置dialog的宽
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenSizeUtils.getInstance(mContext).getScreenWidth() );
        lp.gravity = Gravity.BOTTOM;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);


        action_dialog_btn1 = (TextView) dialogView.findViewById(R.id.action_dialog_btn1);
        action_dialog_btn2 = (TextView) dialogView.findViewById(R.id.action_dialog_btn2);
        if (!TextUtils.isEmpty(text)) {
            action_dialog_btn1.setText(text);
        }
        if (!TextUtils.isEmpty(text2)) {
            action_dialog_btn2.setText(text2);
        }
        linearLayout = (LinearLayout) dialogView.findViewById(R.id.action_dialog_linearlayout);
        mCancel = (LinearLayout) dialogView.findViewById(R.id.cancel_lay);
        mCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mDialog.dismiss();
            }
        });
        btn1 = (LinearLayout) dialogView.findViewById(R.id.action_dialog_linearlayout1);
        btn1.setOnClickListener(l);

        btn2 = (LinearLayout) dialogView.findViewById(R.id.action_dialog_linearlayout2);
        btn2.setOnClickListener(l2);


        mDialog.setCanceledOnTouchOutside(true);
    }


    public boolean isShowing() {

        return mDialog.isShowing();
    }

    public void show() {

        mDialog.show();

    }

    public void dismiss() {

        mDialog.dismiss();
    }

}
