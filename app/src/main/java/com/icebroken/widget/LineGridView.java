package com.icebroken.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.icebroken.R;

public class LineGridView extends GridView {
    /*是否添加边框*/
    public boolean mBorderLine = false;
    /*是否添加顶部线条*/
    public boolean mTopLine = false;
    /*是否添加底部线条*/
    public boolean mBottomLine = false;
    /*是否添加最后一航线条*/
    public boolean mEndBottomLine = false;

    public boolean ismEndBottomLine() {
        return mEndBottomLine;
    }

    public void setmEndBottomLine(boolean mEndBottomLine) {
        this.mEndBottomLine = mEndBottomLine;
    }

    public boolean ismTopLine() {
        return mTopLine;
    }

    public void setmTopLine(boolean mTopLine) {
        this.mTopLine = mTopLine;
    }

    public boolean ismBottomLine() {
        return mBottomLine;
    }

    public void setmBottomLine(boolean mBottomLine) {
        this.mBottomLine = mBottomLine;
    }

    public boolean ismBorderLine() {
        return mBorderLine;
    }

    /**
     * 是否绘制边框，
     * true：绘制
     * deauft:false
     *
     * @param mBorderLine
     */
    public void setmBorderLine(boolean mBorderLine) {
        this.mBorderLine = mBorderLine;
    }

    public LineGridView(Context context) {
        super(context);
    }

    public LineGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!mBorderLine)
            return;
        View localView = getChildAt(0);
        int column = getWidth() / localView.getWidth();
        int childCount = getChildCount();
        Paint mPaint;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getContext().getResources().getColor(R.color.E3E3E3));
        for (int i = 0; i < childCount; i++) {
            View mDrawView = getChildAt(i);
            /*底部*/
            if ((i + 1) % column == 0 && mBottomLine) {
                canvas.drawLine(mDrawView.getLeft(), mDrawView.getBottom(), mDrawView.getRight(), mDrawView.getBottom(), mPaint);
            } else if ((i + 1) > (childCount - (childCount % column))) {/*顶部*/
                canvas.drawLine(mDrawView.getRight(), mDrawView.getTop(), mDrawView.getRight(), mDrawView.getBottom(), mPaint);
            } else {/*底部和右侧*/
                canvas.drawLine(mDrawView.getRight(), mDrawView.getTop(), mDrawView.getRight(), mDrawView.getBottom(), mPaint);
                if (mBottomLine)
                    canvas.drawLine(mDrawView.getLeft(), mDrawView.getBottom(), mDrawView.getRight(), mDrawView.getBottom(), mPaint);
            }
            /*第一行*/
            if (i <= column && mTopLine) {
                canvas.drawLine(mDrawView.getLeft(), mDrawView.getTop(), mDrawView.getRight(), mDrawView.getTop(), mPaint);
            }
            /*最后一行*/
            double linN = Math.ceil((double) childCount / (double) column);
            if (i >= (linN - 1) * column && mEndBottomLine) {
                canvas.drawLine(mDrawView.getLeft(), mDrawView.getBottom(), mDrawView.getRight(), mDrawView.getBottom(), mPaint);
            }
        }
        /*显示出第一行和最后一行边框线*/
        setPadding(0, 1, 0, 1);
    }

}
