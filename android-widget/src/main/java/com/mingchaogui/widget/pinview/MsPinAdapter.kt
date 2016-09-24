package com.mingchaogui.widget.pinview

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mingchaogui.widget.R

/**
 * Created by apple on 2016/9/24.
 */
class MsPinAdapter : RecyclerView.Adapter<MsPinViewHolder>() {

    companion object {
        var CLEAR_RES = R.string.msp_clear
        var BACKSPACE_RES = R.string.msp_backspace
    }

    private var background: Drawable? = null
    private var textColor: ColorStateList? = null
    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsPinViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.button_pin, parent, false)
        val viewHolder = MsPinViewHolder(view)
        viewHolder.setOnClickListener(object : MsPinViewHolder.OnClickListener {
            override fun onClick(position: Int) {
                when(position) {
                    in 0..8 -> itemClickListener?.onNumber(position + 1)
                    9 -> itemClickListener?.onClear()
                    10 -> itemClickListener?.onNumber(0)
                    11 -> itemClickListener?.onBackSpace()
                }
            }
        })
        return viewHolder
    }

    override fun onBindViewHolder(holder: MsPinViewHolder, position: Int) {
        when(position) {
            in 0..8 -> holder.setText((position + 1).toString())
            9 -> holder.setText(MsPinAdapter.CLEAR_RES)
            10 -> holder.setText("0")
            11 -> holder.setText(MsPinAdapter.BACKSPACE_RES)
        }
        holder.setBackground(background)
        holder.setTextColor(textColor)
    }

    override fun getItemCount(): Int {
        return 12
    }

    fun setBackground(background: Drawable?) {
        this.background = background
        notifyDataSetChanged()
    }

    fun setTextColor(textColor: ColorStateList?) {
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