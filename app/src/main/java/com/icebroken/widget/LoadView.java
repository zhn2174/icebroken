package com.icebroken.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icebroken.R;

/**
 * Created by Dev on 2017/5/8.
 */

public class LoadView extends LinearLayout {
    private View view;
    public LoadView(Context context) {
        super(context);
        init();
    }

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    private void init(){
         view = LayoutInflater.from(getContext()).inflate(R.layout.load_lay, null);
        addView(view);
        setBackgroundColor(getResources().getColor(R.color.white));
        setGravity(Gravity.CENTER);
    }
    public void Show(){
//        ImageView load = (ImageView)view.findViewById(R.id.load);
//        try {
//            Glide.with(getContext()).load(R.mipmap.loading2).into(new GlideDrawableImageViewTarget(load, 20));
//        }catch (Exception e){
//        }
//        load.setImageResource(R.drawable.loading2);
//        AnimationDrawable  animationDrawable = (AnimationDrawable) load.getDrawable();
//        animationDrawable.start();

        TextView loadingText = (TextView)view.findViewById(com.mocuz.common.R.id.id_tv_loading_dialog_text);
        loadingText.setText("加载中...");
    }
    public void Show(String str){
//        ImageView load = (ImageView)view.findViewById(R.id.load);
//        try {
//            Glide.with(getContext()).load(R.mipmap.loading2).into(new GlideDrawableImageViewTarget(load, 20));
//        }catch (Exception e){
//        }

        TextView loadingText = (TextView)view.findViewById(com.mocuz.common.R.id.id_tv_loading_dialog_text);
        loadingText.setText(str);
    }

}
