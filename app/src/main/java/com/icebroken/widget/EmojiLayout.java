package com.icebroken.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.icebroken.R;
import com.icebroken.utils.SimpleCommonUtils;
import com.sj.emoji.EmojiBean;
import com.zhy.autolayout.AutoRelativeLayout;

import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.data.EmoticonEntity;
import sj.keyboard.data.PageSetEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.widget.EmoticonsFuncView;
import sj.keyboard.widget.EmoticonsIndicatorView;

/**
 * Synopsis     ${SYNOPSIS}
 * version		${VERSION}
 * Create 	    2017/5/16 0:51
 * Email  		intimatestranger@sina.cn
 */
public class EmojiLayout extends AutoRelativeLayout {
    private LayoutInflater mInflater;
    private Context mContext;
    private EmoticonsFuncView layout_emoji;
    private EmoticonsIndicatorView layout_dot;

    private EditText mContentEdt;


    public EditText getmContentEdt() {
        return mContentEdt;
    }

    public void setmContentEdt(EditText mContentEdt) {
        this.mContentEdt = mContentEdt;
    }

    PageSetAdapter pageSetAdapter;

    public EmojiLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initView();
    }

    private void initView() {
        mInflater.inflate(R.layout.layout_emoji, this);

        layout_emoji = ((EmoticonsFuncView) findViewById(R.id.layout_emoji));
        layout_dot = ((EmoticonsIndicatorView) findViewById(R.id.layout_dot));
        layout_emoji.setOnIndicatorListener(new EmoticonsFuncView.OnEmoticonsPageViewListener() {
            @Override
            public void emoticonSetChanged(PageSetEntity pageSetEntity) {

            }

            @Override
            public void playTo(int position, PageSetEntity pageSetEntity) {
                layout_dot.playTo(position, pageSetEntity);
            }

            @Override
            public void playBy(int oldPosition, int newPosition, PageSetEntity pageSetEntity) {
                layout_dot.playBy(oldPosition, newPosition, pageSetEntity);
            }

        });
        setAdapter();
    }

    void setAdapter() {
        pageSetAdapter = SimpleCommonUtils.getCommonAdapter(mContext, new EmoticonClickListener() {
            @Override
            public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {
                if (null == mContentEdt)
                    return;
                if (isDelBtn) {
                    SimpleCommonUtils.delClick(mContentEdt);
                } else {
                    if (o == null) {
                        return;
                    }
                    String content = null;
                    if (o instanceof EmojiBean) {
                        content = ((EmojiBean) o).emoji;
                    } else if (o instanceof EmoticonEntity) {
                        content = ((EmoticonEntity) o).getContent();
                    }

                    if (TextUtils.isEmpty(content)) {
                        return;
                    }
                    Editable editable = mContentEdt.getText();

                    int start = mContentEdt.getSelectionStart();
                    int end = mContentEdt.getSelectionEnd();
                    start = (start < 0 ? 0 : start);
                    end = (start < 0 ? 0 : end);
                    editable.replace(start, end, content);
                }

            }
        });

//        if (pageSetAdapter != null) {
//            ArrayList<PageSetEntity> pageSetEntities = pageSetAdapter.getPageSetEntityList();
//            if (pageSetEntities != null) {
//                for (PageSetEntity pageSetEntity : pageSetEntities) {
//                    mEmoticonsToolBarView.addToolItemView(pageSetEntity);
//                }
//            }
//        }
        layout_emoji.setAdapter(pageSetAdapter);
    }
}
