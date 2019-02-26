package com.icebroken.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Synopsis     自动省略TextView
 * Author		Mosr
 * version		${VERSION}
 * Create 	    2017/7/6 15:22
 * Email  		intimatestranger@sina.cn
 */
public class AutoOmitTextView extends TextView {
    private static final String ELLIPSIS = "...";
    /**
     * 是否处理中
     */
    private boolean disposed;
    /**
     * 是否去空
     */
    private boolean trim;
    private String mFullText;
    private int maxLength = 6;

    public AutoOmitTextView(Context context) {
        super(context);
    }

    public AutoOmitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoOmitTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoOmitTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public boolean isTrim() {
        return trim;
    }

    public void setTrim(boolean trim) {
        this.trim = trim;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (!disposed) {
            mFullText = text.toString();
            disposeText();
        }
    }


    private void disposeText() {
        int len = mFullText.length();
        String mResultText = mFullText;
        if (maxLength > 0) {
            if (len > maxLength) {
                mResultText = trim ? mFullText.substring(0, maxLength).trim() : mFullText.substring(0, maxLength);
                mResultText = mResultText + ELLIPSIS;
            }
        }
        if (!mResultText.equals(getText())) {
            disposed = true;
            try {
                setText(mResultText);
            } finally {
                disposed = false;
            }
        }
    }
}
