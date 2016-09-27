package com.mingchaogui.widget.pinview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mingchaogui.widget.R

/**
 * Created by apple on 2016/9/24.
 */
class PinAdapter : RecyclerView.Adapter<PinViewHolder> {

    companion object {
        var CLEAR_RES = R.string.pin_clear
        var BACKSPACE_RES = R.string.pin_backspace
    }

    private var background: Drawable?
    private var textColor: ColorStateList

    private var itemClickListener: OnItemClickListener? = null

    constructor(context: Context) : super() {
        background = context.resources.getDrawable(R.drawable.bg_pin_btn_selector)
        textColor = ColorStateList.valueOf(context.resources.getColor(R.color.pin_text_color))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pin_view_button, parent, false)
        val holder = PinViewHolder(view)
        holder.setOnClickListener(object : PinViewHolder.OnClickListener {
            override fun onClick(position: Int) {
                when(position) {
                    in 0..8 -> itemClickListener?.onNumber(position + 1)
                    9 -> itemClickListener?.onClear()
                    10 -> itemClickListener?.onNumber(0)
                    11 -> itemClickListener?.onBackSpace()
                }
            }
        })
        return holder
    }

    override fun onBindViewHolder(holder: PinViewHolder, position: Int) {
        when(position) {
            in 0..8 -> holder.setText((position + 1).toString())
            9 -> holder.setText(PinAdapter.CLEAR_RES)
            10 -> holder.setText("0")
            11 -> holder.setText(PinAdapter.BACKSPACE_RES)
        }
        //holder.setBackground(background)
        holder.setTextColor(textColor)
    }

    override fun getItemCount(): Int {
        return 12
    }

    fun setBackground(background: Drawable?) {
        this.background = background
        notifyDataSetChanged()
    }

    fun setTextColor(textColor: ColorStateList) {
        this.textColor = textColor
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onNumber(num: Int)
        fun onClear()
        fun onBackSpace()
    }
}
