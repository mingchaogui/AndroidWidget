package com.mingchaogui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by apple on 16/8/4.
 */
public class MViewPager extends ViewPager {

    private boolean mManualScrollable;

    public MViewPager(Context context) {
        super(context);
    }

    public MViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mManualScrollable && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !mManualScrollable || super.onTouchEvent(ev);
    }

    public void setManualScrollable(boolean manualScrollable) {
        mManualScrollable = manualScrollable;
    }

    @Override
    public void setCurrentItem(int item) {
        // 默认行为:不要滚动动画
        super.setCurrentItem(item, false);
    }
}
