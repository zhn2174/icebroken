package com.icebroken.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.icebroken.R;


public class RectImageView extends AppCompatImageView {
    float radio = 1f;//长宽比例 长/宽

    public RectImageView(Context context) {
        this(context, null);
    }

    public RectImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RectImageView);
        radio = typedArray.getFloat(R.styleable.RectImageView_sizeRadio, 1.0f);
        typedArray.recycle();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取宽度的模式和尺寸
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //获取高度的模式和尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //宽确定，高不确定
        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
            heightSize = (int) (widthSize * radio);//根据宽度和比例计算高度
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        } else if (widthMode != MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            widthSize = (int) (heightSize / radio);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        } else {
            if (widthSize <= heightSize) {
                heightSize = (int) (widthSize * radio);
            } else {
                widthSize = (int) (heightSize / radio);
            }
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        //必须调用下面的两个方法之一完成onMeasure方法的重写，否则会报错
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

}
