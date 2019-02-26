package com.icebroken.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by wwy on 2017/5/19.
 */

public class FullScreenVideoView extends VideoView {
    String path;
    private int height2;
    private WindowManager wm;

    public FullScreenVideoView(Context context, String path ){
        super(context);
        this.path = path;
        wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Glide.with(getContext()).load(path).asBitmap().into(new SimpleTarget<Bitmap>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                int imageWidth = resource.getWidth();
                int imageHeight = resource.getHeight();
                height2 = wm.getDefaultDisplay().getWidth() * imageHeight / imageWidth;
            }
        });

    }

    public FullScreenVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = wm.getDefaultDisplay().getWidth();
        int height = height2;
        setMeasuredDimension(width, height);
    }
}
