package com.mingchaogui.widget.pinview

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import com.mingchaogui.widget.R

/**
 * Created by apple on 2016/9/24.
 */
class PinViewHolder : RecyclerView.ViewHolder {

    private var btn: Button
    private var clickListener: OnClickListener? = null

    constructor(itemView: View) : super(itemView) {
        btn = itemView.findViewById(R.id.btn) as Button
        itemView.setOnClickListener{
            clickListener?.onClick(adapterPosition)
        }
    }

    fun setBackground(drawable: Drawable?) {
        btn.setBackgroundDrawable(drawable)
    }

    fun setTextColor(textColor: ColorStateList) {
        btn.setTextColor(textColor)
    }

    fun setText(resId: Int) {
        btn.setText(resId)
    }

    fun setText(text: CharSequence?) {
        btn.text = text
    }

    fun setOnClickListener(listener: OnClickListener?) {
        clickListener = listener
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }
}
