package com.icebroken.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.icebroken.R;

/**
 * Created by wwy on 2017/3/30.
 */

public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;
    Context context;
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval,Context context) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
        this.context=context;
    }
    public CountDownTimerUtils(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);

    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText("重新获取(" + (millisUntilFinished / 1000) + ")");  //设置倒计时时间
        mTextView.setTextColor(ContextCompat.getColor(context, R.color.gray_C3));
//        mTextView.setBackgroundResource(R.drawable.bg_identify_code_press); //设置按钮背景
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取");
        mTextView.setClickable(true);//重新获得点击
        mTextView.setTextColor(ContextCompat.getColor(context, R.color._7F7F7F));
    }
    public void Cancel(){
        cancel();
        mTextView.setText("获取验证码");
        mTextView.setClickable(true);//重新获得点击
        mTextView.setTextColor(ContextCompat.getColor(context, R.color._7F7F7F));
    }
}
