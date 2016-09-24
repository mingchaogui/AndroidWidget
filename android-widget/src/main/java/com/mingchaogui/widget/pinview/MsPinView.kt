package com.mingchaogui.widget.pinview

import android.annotation.TargetApi
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.mingchaogui.widget.GridSpacingItemDecoration
import com.mingchaogui.widget.R

/**
 * Created by apple on 2016/9/23.
 */
class MsPinView : LinearLayout {

    private val txtTitle by lazy {
        findViewById(R.id.txt_title) as TextView
    }
    private val txtPin by lazy {
        findViewById(R.id.txt_pin) as TextView
    }
    private val rvBtn by lazy {
        findViewById(R.id.rv_btn) as RecyclerView
    }

    private var pinListener: PinListener? = null

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        orientation = VERTICAL
        initView()
        initAttrs(attrs)
        initListener()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.view_pin, this, true)

        val spanCount = 3
        val spacing = resources.getDimensionPixelOffset(R.dimen.msp_item_spacing)

        rvBtn.setHasFixedSize(true)
        rvBtn.layoutManager = GridLayoutManager(context, 3)
        rvBtn.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, false))
        rvBtn.adapter = MsPinAdapter()
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MsPinView)

        val titleText = a.getString(R.styleable.MsPinView_msp_title_text)
        val titleTextColor = a.getColorStateList(R.styleable.MsPinView_msp_title_text_color)
        val pinBackground = a.getDrawable(R.styleable.MsPinView_msp_pin_background)
        val pinTextColor = a.getColorStateList(R.styleable.MsPinView_msp_pin_text_color)
        val buttonBackground = a.getDrawable(R.styleable.MsPinView_msp_button_background)
        val buttonTextColor = a.getColorStateList(R.styleable.MsPinView_msp_button_text_color)

        setTitleText(titleText ?: "请输入")
        setTitleTextColor(titleTextColor ?: ColorStateList.valueOf(Color.GRAY))
        setPinBackground(pinBackground ?: ColorDrawable(Color.WHITE))
        setPinTextColor(pinTextColor ?: ColorStateList.valueOf(Color.BLACK))
        setButtonBackground(buttonBackground ?: ColorDrawable(Color.LTGRAY))
        setButtonTextColor(buttonTextColor ?: ColorStateList.valueOf(Color.BLACK))

        a.recycle()
    }

    private fun initListener() {
        val adapter = rvBtn.adapter as MsPinAdapter
        adapter.setOnItemClickListener(object : MsPinAdapter.OnItemClickListener {
            override fun onNumber(num: Int) {
                txtPin.text = txtPin.text.toString() + num
                pinListener?.onChanged(txtPin.text)
            }

            override fun onClear() {
                if (txtPin.text.isNotEmpty()) {
                    clearPin()
                    pinListener?.onChanged(txtPin.text)
                }
            }

            override fun onBackSpace() {
                val text = txtPin.text
                if (text.isNotEmpty()) {
                    txtPin.text = text.subSequence(0, text.lastIndex)
                    pinListener?.onChanged(txtPin.text)
                }
            }
        })
    }

    fun setTitleText(title: CharSequence?) {
        txtTitle.text = title
    }

    fun setTitleTextColor(textColor: ColorStateList?) {
        txtTitle.setTextColor(textColor)
    }

    fun setPinBackground(background: Drawable?) {
        txtPin.setBackgroundDrawable(background)
    }

    fun setPinTextColor(textColor: ColorStateList?) {
        txtPin.setTextColor(textColor)
    }

    fun setButtonBackground(background: Drawable?) {
        val adapter = rvBtn.adapter as MsPinAdapter
        adapter.setBackground(background)
    }

    fun setButtonTextColor(textColor: ColorStateList?) {
        val adapter = rvBtn.adapter as MsPinAdapter
        adapter.setTextColor(textColor)
    }

    fun setPinListener(listener: PinListener?) {
        pinListener = listener
    }

    fun clearPin() {
        txtPin.text = null
    }
}
