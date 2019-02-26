package com.icebroken.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

import com.icebroken.utils.BaseUtil;

/**
 * Created by wwy on 2017/5/19.
 */

public class BiuVideoView extends VideoView {

    public BiuVideoView(Context context, String path) {
        super(context);


    }

    public BiuVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BiuVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (int) BaseUtil.AutoPX(225, getContext());
        int height = (int) BaseUtil.AutoPX(225, getContext());
        setMeasuredDimension(width, height);
    }
}
