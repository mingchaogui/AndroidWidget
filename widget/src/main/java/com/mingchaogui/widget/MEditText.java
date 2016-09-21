package com.mingchaogui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;


/**
 * 自带清除按钮的输入框，清除按钮的作用是清空输入框的输入内容；
 * 需要注意的是，清除按钮会占据drawableRight的位置，所以设置drawableRight会无效
 * Created by liruchun on 2015/7/2.
 */
public class MEditText extends EditText {
    /** 默认的清除按钮图标资源 */
    private static final int ICON_CLEAR_DEFAULT = R.drawable.ic_clear_text;

    /** 是否显示清除按钮 */
    private boolean mShowClear;
    /** 清除按钮的图标 */
    private Drawable mClearDrawable;

    public MEditText(Context context) {
        super(context);
        init(context, null);
    }

    public MEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // 获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MEditText);
        // 获取清除按钮
        mShowClear = typedArray.getBoolean(R.styleable.MEditText_showClear, true);
        int iconClear =
                typedArray.getResourceId(R.styleable.MEditText_clearIcon, ICON_CLEAR_DEFAULT);
        mClearDrawable = getResources().getDrawable(iconClear);
        typedArray.recycle();
        updateIconClear();

        // 设置TextWatcher用于更新清除按钮显示状态
        addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                updateIconClear();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            // 点击是的 x 坐标
            int xDown = (int) event.getX();
            // 清除按钮的起始区间大致为[getWidth() - getCompoundPaddingRight(), getWidth()]，
            // 点击的x坐标在此区间内则可判断为点击了清除按钮
            if (xDown >= (getWidth() - getCompoundPaddingRight()) && xDown < getWidth())
            {
                // 清空文本
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 更新清除按钮图标显示
     */
    public void updateIconClear() {
        // 获取设置好的drawableLeft、drawableTop、drawableRight、drawableBottom
        Drawable[] drawables = getCompoundDrawables();
        if (mShowClear && length() > 0) {//输入框有输入内容才显示删除按钮
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], mClearDrawable,
                    drawables[3]);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], null,
                    drawables[3]);
        }
    }

    /**
     * 设置清除按钮图标样式
     * @param resId 图标资源id
     */
    public void setIconClear(@DrawableRes int resId) {
        mClearDrawable = getResources().getDrawable(resId);
        updateIconClear();
    }
}
