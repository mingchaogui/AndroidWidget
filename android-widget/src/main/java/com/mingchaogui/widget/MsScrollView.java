package com.mingchaogui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by apple on 16/8/1.
 */
public class MsScrollView extends ScrollView {

    private OnScrollListener mOnScrollListener;

    public MsScrollView(Context context) {
        super(context);
    }

    public MsScrollView(Context context, AttributeSet attrs,
                        int defStyle) {
        super(context, attrs, defStyle);
    }

    public MsScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);

        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollChanged(this, x, y, oldX, oldY);
        }
    }

    public interface OnScrollListener {
        void onScrollChanged(MsScrollView scrollView, int x, int y, int oldX, int oldY);
    }
}