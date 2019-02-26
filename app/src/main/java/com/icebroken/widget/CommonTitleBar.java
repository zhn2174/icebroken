package com.icebroken.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mocuz.common.baserx.RxManager;
import com.mocuz.common.commonwidget.StatusBarCompat;
import com.mocuz.common.flyn.Eyes;
import com.mocuz.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import com.icebroken.R;
import com.icebroken.api.AppConstant;
import com.icebroken.utils.BaseUtil;
import rx.functions.Action1;


/**
 * Created by yuqiang on 2017/3/13.
 */
public class CommonTitleBar extends LinearLayout {
    /*父布局*/
    public RelativeLayout rel_parent;
    /*头部标题layout*/
    public RelativeLayout common_title;
    /*头部标题文字*/
    public TextView tv_title;
    /*子版块箭头*/
    public TextView icon_sub;
    /*左侧图标layout*/
    public RelativeLayout lay_image_back;
    /*左侧图标*/
    public ImageView image_back;
    /*左侧头像图标*/
    public ImageView image_person;
    /*左侧头像消息数*/
    public TextView text_user_msg;
    /*右侧图标layout*/
    public RelativeLayout lay_image_right;
    /*右侧图标*/
    public ImageView image_right;
    /*右侧文字layout*/
    public RelativeLayout lay_tv_right;
    /*右侧文字*/
    public TextView tv_right;
    /*右侧文字layout*/
    public RelativeLayout lay_left_text;
    /*右侧文字*/
    public TextView left_text;
    /*tablayout*/
    public SlidingTabLayout biu_slt_title;
    /*个人头部*/
    public SlidingTabLayout mine_tablayout;
    /*个人layout*/
    public LinearLayout ll_tablayout;
    /*社区搜索layout*/
    public LinearLayout bbs_search;
    /*分割线 白色背景用的*/
    public View viewLine;
    public TextView btnIcon;
    public EditText editSearch;

    public CommonTitleBar(Context context) {
        super(context);
        initView(context);
    }

    public CommonTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * 右侧按钮是否可用
     *
     * @param enabled
     */
    public void setRightBtnEnable(boolean enabled) {
        lay_image_right.setEnabled(enabled);
    }

    /**
     * 右侧按钮隐藏
     */
    public void hidRight() {
        lay_image_right.setVisibility(GONE);
    }

    private void initView(final Context context) {
        View.inflate(context, R.layout.bar_common, this);
        rel_parent = (RelativeLayout) findViewById(R.id.rel_parent);
        common_title = (RelativeLayout) findViewById(R.id.common_title);
        tv_title = (TextView) findViewById(R.id.tv_title);
        icon_sub = (TextView) findViewById(R.id.icon_sub);
        lay_image_back = (RelativeLayout) findViewById(R.id.lay_image_back);
        image_back = (ImageView) findViewById(R.id.image_back);
        image_person = (ImageView) findViewById(R.id.image_person);
        lay_image_right = (RelativeLayout) findViewById(R.id.lay_image_right);
        image_right = (ImageView) findViewById(R.id.image_right);
        lay_tv_right = (RelativeLayout) findViewById(R.id.lay_tv_right);
        tv_right = (TextView) findViewById(R.id.tv_right);
        text_user_msg = (TextView) findViewById(R.id.text_user_msg);
        lay_left_text = (RelativeLayout) findViewById(R.id.lay_left_text);
        left_text = (TextView) findViewById(R.id.left_text);
        biu_slt_title = (SlidingTabLayout) findViewById(R.id.biu_slt_title);
        mine_tablayout = (SlidingTabLayout) findViewById(R.id.mine_tab_layout);
        ll_tablayout = (LinearLayout) findViewById(R.id.ll_tablayout);
        bbs_search = (LinearLayout) findViewById(R.id.bbs_search);
        btnIcon = (TextView) findViewById(R.id.btnIcon);
        editSearch = (EditText) findViewById(R.id.editSearch);
        viewLine = (View) findViewById(R.id.viewLine);

        text_user_msg.setTextColor(Color.parseColor(BaseUtil.baseColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            rel_parent.setPadding(0, StatusBarCompat.getStatusBarHeight(context), 0, 0);
//        Eyes.setStatusBarColor((Activity)getContext(),Color.parseColor( BaseUtil.getStartColor()));
        //用户登录
        new RxManager().on(AppConstant.LOGIN, new Action1<Object>() {
            @Override
            public void call(Object o) {
//                if (AppApplication.isTitleLogin() == true) {
//                    context.startActivity(new Intent(context, PersonIndexActivity.class));
//                }
            }
        });
        //用户登出
        new RxManager().on(AppConstant.LOGIN_OUT, new Action1<Object>() {
            @Override
            public void call(Object o) {
                text_user_msg.setVisibility(GONE);
            }
        });


//        rel_parent.setBackgroundDrawable(BaseUtil.getTitleColor());


    }

    /*设置中间标题*/
    public void setTitle(CharSequence title) {
        tv_title.setVisibility(VISIBLE);
        tv_title.setText(title);
        tv_title.setTypeface(Typeface.DEFAULT_BOLD);
    }

    /*设置返回按钮*/
    public void setBack() {
        lay_image_back.setVisibility(VISIBLE);
        image_back.setVisibility(VISIBLE);
        text_user_msg.setVisibility(GONE);
        image_person.setVisibility(GONE);
        image_back.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.btn_back));
        lay_image_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).finish();
            }
        });
    }

    /*设置用户头像按钮*/
    public void setUser() {
        lay_image_back.setVisibility(VISIBLE);
        image_back.setVisibility(GONE);
        image_person.setVisibility(VISIBLE);
        image_person.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.icon_user));
        lay_image_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }


    /*设置左侧图标和点击事件*/
    public void setLeftImage(int icon, OnClickListener clickListener) {
        text_user_msg.setVisibility(GONE);
        lay_image_back.setVisibility(VISIBLE);
        image_back.setImageDrawable(ContextCompat.getDrawable(getContext(), icon));
        lay_image_back.setOnClickListener(clickListener);
    }

    /*设置右侧图标和点击事件*/
    public void setRightImage(int icon, OnClickListener clickListener) {
        lay_image_right.setVisibility(VISIBLE);
        image_right.setImageResource(icon);

        lay_image_right.setOnClickListener(clickListener);
    }

    /*
     白色背景设置右侧文字和点击事件
     */
    public void setRightText(CharSequence title, OnClickListener clickListener) {
        lay_tv_right.setVisibility(VISIBLE);
        tv_right.setText(title);
//        tv_right.setTextColor(BaseUtil.getEndColor_int());
        lay_tv_right.setOnClickListener(clickListener);
    }

    /*
     渐变色背景设置右侧文字和点击事件
     */
    public void setRightText1(CharSequence title, OnClickListener clickListener) {
        lay_tv_right.setVisibility(VISIBLE);
        tv_right.setText(title);
        lay_tv_right.setOnClickListener(clickListener);
    }

    /*设置左侧文字和点击事件*/
    public void setLeftText(CharSequence title, OnClickListener clickListener) {
        lay_left_text.setVisibility(VISIBLE);
        left_text.setText(title);
        lay_left_text.setOnClickListener(clickListener);
    }

    /*设置左侧隐藏*/
    public void setLeftGone() {
        lay_left_text.setVisibility(GONE);
    }

    /*设置左侧隐藏*/
    public void setLeftAllGone() {
        lay_left_text.setVisibility(GONE);
        lay_image_back.setVisibility(GONE);
    }

    /*设置左侧文字在图标左边*/
    public void setLefttoLeft() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lay_left_text.getLayoutParams();
        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.lay_image_back);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        int width = (int) BaseUtil.AutoPX(135, getContext());
        RelativeLayout.LayoutParams backlayoutParams = new RelativeLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        backlayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lay_image_back.setLayoutParams(backlayoutParams);
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        left_text.setLayoutParams(textParams);
        lay_left_text.setLayoutParams(layoutParams);
    }


    //设置登陆注册的titlebar样式
    public void setMenberTitleBarStyle(ViewPager viewPager, String[] titles, FragmentActivity fa, ArrayList<Fragment> fragments) {
        ll_tablayout.setVisibility(VISIBLE);
        mine_tablayout.setTabWidthpx(476);
        mine_tablayout.setTextUnselectColor(getResources().getColor(R.color.gray_4C));
//        mine_tablayout.setTextSelectColor(Color.parseColor(BaseUtil.getEndColor()));
//        mine_tablayout.setIndicatorColor(Color.parseColor(BaseUtil.getEndColor()));
        mine_tablayout.setViewPager(viewPager, titles, fa, fragments);
    }

    /*
    设置左侧关闭图标和点击事件
    */
    public void setLeftCloseImage() {
        lay_image_back.setVisibility(VISIBLE);
        image_back.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.btn_close));
        lay_image_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }

    /*
    设置个人中心title样式
    */
    public void setMyCenterTitleStyle(CharSequence title) {
        Eyes.setStatusTextBlack(((Activity) getContext()));
        rel_parent.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.bg_white));
        viewLine.setVisibility(VISIBLE);
        tv_title.setVisibility(VISIBLE);
        tv_title.setText(title);
        tv_title.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_4C));
        lay_image_back.setVisibility(VISIBLE);
        image_back.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_back_fanbai));
//        image_back.setBackgroundColor(BaseUtil.getEndColor_int());
        lay_image_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }

    public void SetnoPad() {
        rel_parent.setPadding(0, 0, 0, 0);
    }
}
