package com.icebroken.filter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.TextUtils;
import android.widget.EditText;

import com.sj.emoji.EmojiDisplayListener;
import com.testemticon.DefXhsEmoticons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sj.keyboard.interfaces.EmoticonFilter;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.EmoticonSpan;

/**
 * Created by Dev on 2017/4/5.
 */

public class XhsFilter extends EmoticonFilter {

    public static final int WRAP_DRAWABLE = -1;
    private int emoticonSize = -1;
    //    public static final Pattern XHS_RANGE = Pattern.compile("\\[[a-zA-Z0-9\\u4e00-\\u9fa5]+\\]");
    public static final Pattern XHS_RANGE = Pattern.compile("\\{:[a-zA-Z0-9\\u4e00-\\u9fa5]+_+[0-9]+:\\}");

    private boolean disposed = false;

    public static Matcher getMatcher(CharSequence matchStr) {
        return XHS_RANGE.matcher(matchStr);
    }

    @Override
    public void filter(EditText editText, CharSequence text, int start, int lengthBefore, int lengthAfter) {
        emoticonSize = emoticonSize == -1 ? EmoticonsKeyboardUtils.getFontHeight(editText) : emoticonSize;
//        clearSpan(editText.getText(), start, text.toString().length());
        Matcher m = getMatcher(text.toString().substring(start, start + lengthAfter));

        if (m != null) {
            disposed = false;
            while (m.find()) {
                String key = m.group();
                String icon = DefXhsEmoticons.sXhsEmoticonHashMap.get(key);
                if (!TextUtils.isEmpty(icon)) {
                    emoticonDisplay(editText.getContext(), editText.getText(), key, icon, emoticonSize, start + m.start(), start + m.end());
                    disposed = true;
                }
            }
//            删除表情时删除对应的span
            if (!m.find() && !disposed)
                clearSpan(editText.getText(), start + lengthAfter, start + lengthBefore);
        }
    }

    public static Spannable spannableFilter(Context context, Spannable spannable, CharSequence text, int fontSize, EmojiDisplayListener emojiDisplayListener) {
        Matcher m = getMatcher(text);
        if (m != null) {
            while (m.find()) {
                String key = m.group();
                String icon = DefXhsEmoticons.sXhsEmoticonHashMap.get(key);
                if (emojiDisplayListener == null) {
                    if (!TextUtils.isEmpty(icon)) {
                        emoticonDisplay(context, spannable, key, icon, fontSize, m.start(), m.end());
                    }
                } else {
                    emojiDisplayListener.onEmojiDisplay(context, spannable, icon, fontSize, m.start(), m.end());
                }
            }
        }
        return spannable;
    }

    private void clearSpan(Spannable spannable, int start, int end) {
        if (start < end) {
            EmoticonSpan[] oldSpans = spannable.getSpans(start, end, EmoticonSpan.class);
            for (int i = 0; i < oldSpans.length; i++) {
                int sStart = spannable.getSpanStart(oldSpans[i]);
                int sEnd = spannable.getSpanEnd(oldSpans[i]);
                CharSequence c = spannable.subSequence(sStart, sEnd);
                if (spannable.getSpanEnd(oldSpans[i]) != end && TextUtils.isEmpty(c))
                    spannable.removeSpan(oldSpans[i]);
            }
        }
    }

    static int getTmatcher(Spannable spannable) {

        String txt = spannable.toString();
        String[] txts = txt.split("\\}");
        return txts.length;
    }

    static int getTspans(Spannable spannable) {

        return spannable.getSpans(0, spannable.toString().toString().length(), EmoticonSpan.class).length;
    }

    public static void emoticonDisplay(Context context, Spannable spannable, String keyName, String emoticonName, int fontSize, int start, int end) {
        EmoticonSpan[] oldSpans = spannable.getSpans(start, end, EmoticonSpan.class);
        if (oldSpans.length > 0) {
            for (EmoticonSpan mEmojiSpan : oldSpans) {
                if (getTmatcher(spannable) == getTspans(spannable) && null != mEmojiSpan.getmTag() && TextUtils.equals(mEmojiSpan.getmTag().toString(), keyName)) {
                    return;
                }
            }
        }
        Drawable drawable = getDrawableFromAssets(context, emoticonName);
        if (drawable != null) {
            spannable.setSpan(getSpan(drawable, fontSize, keyName), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    static EmoticonSpan getSpan(Drawable drawable, int fontSize, String keyName) {
        int itemHeight;
        int itemWidth;
        if (fontSize == WRAP_DRAWABLE) {
            itemHeight = drawable.getIntrinsicHeight();
            itemWidth = drawable.getIntrinsicWidth();
        } else {
            itemHeight = fontSize;
            itemWidth = fontSize;
        }

        drawable.setBounds(0, 0, itemHeight, itemWidth);
        EmoticonSpan imageSpan = new EmoticonSpan(drawable);
        imageSpan.setmTag(keyName);
        return imageSpan;
    }
}
