package com.icebroken.widget;

/**
 * Created by Administrator on 2018/7/1.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyLetterListView extends View {
    MyLetterListView.OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    String[] b = new String[]{"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "*"};
    int choose = -1;
    Paint paint = new Paint();
    boolean showBkg = false;
    private MyLetterListView.OnResizeListener mListener;

    public MyLetterListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyLetterListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLetterListView(Context context) {
        super(context);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = this.getHeight();
        int width = this.getWidth();
        int singleHeight = height / this.b.length;

        for(int i = 0; i < this.b.length; ++i) {
            this.paint.setColor(Color.parseColor("#70706C"));
            this.paint.setTypeface(Typeface.DEFAULT_BOLD);
            this.paint.setAntiAlias(true);
            this.paint.setTextSize(18.0F);
            if(i == this.choose) {
                this.paint.setColor(Color.parseColor("#3399ff"));
                this.paint.setFakeBoldText(true);
            }

            float xPos = (float)(width / 2) - this.paint.measureText(this.b[i]) / 2.0F;
            float yPos = (float)(singleHeight * i + singleHeight);
            canvas.drawText(this.b[i], xPos, yPos, this.paint);
            this.paint.reset();
        }

    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int oldChoose = this.choose;
        MyLetterListView.OnTouchingLetterChangedListener listener = this.onTouchingLetterChangedListener;
        int c = (int)(y / (float)this.getHeight() * (float)this.b.length);
        switch(action) {
            case 0:
                this.showBkg = true;
                if(oldChoose != c && listener != null && c > 0 && c < this.b.length) {
                    listener.onTouchingLetterChanged(this.b[c]);
                    this.choose = c;
                    this.invalidate();
                }
                break;
            case 1:
                this.showBkg = false;
                this.choose = -1;
                this.invalidate();
                break;
            case 2:
                if(oldChoose != c && listener != null && c > 0 && c < this.b.length) {
                    listener.onTouchingLetterChanged(this.b[c]);
                    this.choose = c;
                    this.invalidate();
                }
        }

        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnTouchingLetterChangedListener(MyLetterListView.OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public void setOnResizeListener(MyLetterListView.OnResizeListener l) {
        this.mListener = l;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(this.mListener != null) {
            this.mListener.OnResize(w, h, oldw, oldh);
        }

    }

    public interface OnResizeListener {
        void OnResize(int var1, int var2, int var3, int var4);
    }

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String var1);
    }
}

