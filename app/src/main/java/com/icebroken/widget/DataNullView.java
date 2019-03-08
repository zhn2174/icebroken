package com.icebroken.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.icebroken.R;
import com.icebroken.utils.BaseUtil;

/**
 * Created by Dev on 2017/4/20.
 */

public class DataNullView extends FrameLayout {

    private TextView text1;
    private TextView text2;

    public DataNullView(Context context) {
        super(context);
        initView();
    }

    public DataNullView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DataNullView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public DataNullView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.data_null_view, null);
        text1 = ((TextView) view.findViewById(R.id.text1));
        text2 = ((TextView) view.findViewById(R.id.text2));
        addView(view);
    }

    public void setData(String str1, String str2) {
        if (BaseUtil.isEmpty(str1)) {
            text1.setVisibility(GONE);
        } else {
            text1.setVisibility(VISIBLE);
            text1.setText(str1);
        }
        if (BaseUtil.isEmpty(str2)) {
            text2.setVisibility(GONE);
        } else {
            text2.setVisibility(VISIBLE);
            text2.setText(str2);
        }
    }
}
