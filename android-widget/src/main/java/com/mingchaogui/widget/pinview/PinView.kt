package com.mingchaogui.widget.pinview

import android.annotation.TargetApi
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
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
class PinView : LinearLayout {

    private val txtTitle by lazy {
        findViewById(R.id.txt_title) as TextView
    }
    private val txtPin by lazy {
        findViewById(R.id.txt_pin) as TextView
    }
    private val rvBtn by lazy {
        findViewById(R.id.rv_btn) as RecyclerView
    }

    private var pinLength = 6
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
        LayoutInflater.from(context).inflate(R.layout.pin_view, this, true)

        val spanCount = 3
        val hSpacing = resources.getDimensionPixelOffset(R.dimen.pin_horizontal_padding)
        val vSpacing = resources.getDimensionPixelOffset(R.dimen.pin_vertical_padding)

        rvBtn.setHasFixedSize(true)
        rvBtn.layoutManager = GridLayoutManager(context, spanCount)
        rvBtn.addItemDecoration(GridSpacingItemDecoration(spanCount, hSpacing, vSpacing, false))
        rvBtn.adapter = PinAdapter(context)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PinView)

        pinLength = a.getInt(R.styleable.PinView_pin_length, pinLength)

        val titleText = a.getString(R.styleable.PinView_pin_title_text)
        val titleTextColor = a.getColorStateList(R.styleable.PinView_pin_title_text_color)
        val pinTextBackground = a.getDrawable(R.styleable.PinView_pin_text_background)
        val pinTextColor = a.getColorStateList(R.styleable.PinView_pin_text_color)
        val buttonBackground = a.getDrawable(R.styleable.PinView_pin_button_background)
        val buttonTextColor = a.getColorStateList(R.styleable.PinView_pin_button_text_color)

        background ?: setBackgroundDrawable(resources.getDrawable(R.drawable.bg_pin))
        setTitleText(titleText ?: resources.getString(R.string.pin_title))
        setTitleTextColor(titleTextColor ?: ColorStateList.valueOf(resources.getColor(R.color.pin_text_color)))
        setPinTextBackground(pinTextBackground ?: resources.getDrawable(R.drawable.bg_pin_text))
        setPinTextColor(pinTextColor ?: ColorStateList.valueOf(Color.BLACK))
        setButtonBackground(buttonBackground ?: resources.getDrawable(R.drawable.bg_pin_btn_selector))
        setButtonTextColor(buttonTextColor ?: ColorStateList.valueOf(resources.getColor(R.color.pin_text_color)))

        a.recycle()
    }

    private fun initListener() {
        val adapter = rvBtn.adapter as PinAdapter
        adapter.setOnItemClickListener(object : PinAdapter.OnItemClickListener {
            override fun onNumber(num: Int) {
                if (txtPin.text.length >= pinLength) {
                    return
                }
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

    fun setTitleTextColor(textColor: ColorStateList) {
        txtTitle.setTextColor(textColor)
    }

    fun setPinTextBackground(background: Drawable?) {
        txtPin.setBackgroundDrawable(background)
    }

    fun setPinTextColor(textColor: ColorStateList) {
        txtPin.setTextColor(textColor)
    }

    fun setButtonBackground(background: Drawable?) {
        val adapter = rvBtn.adapter as PinAdapter
        adapter.setBackground(background)
    }

    fun setButtonTextColor(textColor: ColorStateList) {
        val adapter = rvBtn.adapter as PinAdapter
        adapter.setTextColor(textColor)
    }

    fun setPinListener(listener: PinListener?) {
        pinListener = listener
    }

    fun clearPin() {
        txtPin.text = null
    }
}
