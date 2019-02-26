package com.icebroken.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.icebroken.R;

/**
 * Created by wwy on 2017/4/11.
 */

public class ClearEditTextLayout extends RelativeLayout implements View.OnFocusChangeListener, TextWatcher {
    Context context;
    private ImageView iv_clear;
    private EditText ed_code;
    /**
     * 控件是否有焦点
     */
    private boolean hasFocus;

    public ClearEditTextLayout(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public ClearEditTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.clear_edittext, this);
        ed_code = (EditText) findViewById(R.id.ed_code);
//        ed_code.setInputType(InputType.TYPE_CLASS_PHONE);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        ed_code.setOnFocusChangeListener(this);
        ed_code.addTextChangedListener(this);
        iv_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_code.setText("");
            }
        });
    }

    private void setClearIconVisible(boolean visible) {
        if (visible == true) {
            iv_clear.setVisibility(VISIBLE);
        } else {
            iv_clear.setVisibility(GONE);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            setClearIconVisible(ed_code.getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (hasFocus) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public EditText getEd_code() {
        return ed_code;
    }
}
