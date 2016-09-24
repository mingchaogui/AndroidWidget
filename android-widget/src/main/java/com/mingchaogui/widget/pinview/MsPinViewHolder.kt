package com.mingchaogui.widget.pinview

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mingchaogui.widget.R

/**
 * Created by apple on 2016/9/24.
 */
class MsPinViewHolder : RecyclerView.ViewHolder {

    private var txt: TextView
    private var clickListener: OnClickListener? = null

    constructor(itemView: View) : super(itemView) {
        txt = itemView.findViewById(R.id.txt) as TextView
        txt.setOnClickListener{
            clickListener?.onClick(adapterPosition)
        }
    }

    fun setBackground(drawable: Drawable?) {
        txt.setBackgroundDrawable(drawable)
    }

    fun setTextColor(textColor: ColorStateList?) {
        txt.setTextColor(textColor)
    }

    fun setText(resId: Int) {
        txt.setText(resId)
    }

    fun setText(text: CharSequence?) {
        txt.text = text
    }

    fun setOnClickListener(listener: OnClickListener?) {
        clickListener = listener
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }
}
