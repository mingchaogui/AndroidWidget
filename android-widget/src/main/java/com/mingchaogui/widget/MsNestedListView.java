package com.mingchaogui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MsNestedListView extends ListView {

	public MsNestedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MsNestedListView(Context context) {
		super(context);
	}

	public MsNestedListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(
				Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
