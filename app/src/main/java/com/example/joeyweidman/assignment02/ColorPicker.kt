package com.example.joeyweidman.assignment02

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


/**
 * Created by Joey Weidman.
 */
class ColorPicker : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    interface OnColorChangedListener {
        fun onColorChanged(colorPicker: ColorPicker, color: Int)
    }

    var color: Int = 0
        get() {
            //Get the bitmap of what is rendered on the screen
            setDrawingCacheEnabled(true)
            buildDrawingCache()
            val screenBitmap = getDrawingCache()
            val pixel = screenBitmap.getPixel(colorPickerCenter.x.toInt(), colorPickerCenter.y.toInt())
            setDrawingCacheEnabled(false);
            return pixel
        }

    private var theta: Double = 0.0
        set(value) {
            field = value
            invalidate()
        }

    //Get a bitmap of the color wheel to render on the view
    val bitmap = BitmapFactory.decodeResource(resources, R.drawable.color_wheel)

    private val colorWheelRect: RectF = RectF()
    private val colorPickerCenter: PointF = PointF()
    private val colorPickerRect: RectF = RectF()

    private val colorPickerPaint: Paint = {
        val colorPickerPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        colorPickerPaint.color = Color.LTGRAY
        colorPickerPaint.strokeWidth = 10.0F
        colorPickerPaint.style = Paint.Style.STROKE
        colorPickerPaint
    }()

    /*Draws the color wheel and the color picker
     *The color picker will move around the color wheel relative to where you put your finger
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas !is Canvas) {
            return //Lets Kotlin know to smart cast the canvas object to a non-null version throughout the remainder of the function
        }

        val availableWidth: Float = (width - paddingLeft - paddingRight).toFloat()
        val availableHeight: Float = (height - paddingTop - paddingBottom).toFloat()
        val controlKnobEdgeLength: Float = Math.min(availableWidth, availableHeight)

        colorWheelRect.left = paddingLeft.toFloat()
        colorWheelRect.top = paddingTop.toFloat()
        colorWheelRect.right = paddingLeft.toFloat() + controlKnobEdgeLength
        colorWheelRect.bottom = paddingTop.toFloat() + controlKnobEdgeLength

        val controlKnobVerticalDisplacement: Float = (availableHeight - colorWheelRect.height()) * 0.5F
        colorWheelRect.top += controlKnobVerticalDisplacement
        colorWheelRect.bottom += controlKnobVerticalDisplacement

        val centerDisplacement: Float = colorWheelRect.width() * 0.394F
        colorPickerCenter.x = colorWheelRect.centerX() + (centerDisplacement * Math.cos(theta)).toFloat()
        colorPickerCenter.y = colorWheelRect.centerY() + (centerDisplacement * Math.sin(theta)).toFloat()

        val colorPickerRadius: Float = centerDisplacement * 0.183F
        colorPickerRect.left = colorPickerCenter.x - colorPickerRadius
        colorPickerRect.top = colorPickerCenter.y - colorPickerRadius
        colorPickerRect.right = colorPickerCenter.x + colorPickerRadius
        colorPickerRect.bottom = colorPickerCenter.y + colorPickerRadius

        canvas.drawBitmap(bitmap, null, colorWheelRect, null)
        canvas.drawOval(colorPickerRect, colorPickerPaint)

    }

    private var onColorChangedListener: OnColorChangedListener? = null

    fun setOnColorChangedListener(onColorChangedListener: OnColorChangedListener) {
        this.onColorChangedListener = onColorChangedListener
    }

    fun setOnColorChangedListener(onColorChangedListener: ((colorPicker: ColorPicker, color: Int) -> Unit)) {
        this.onColorChangedListener = object : OnColorChangedListener {
            override fun onColorChanged(colorPicker: ColorPicker, color: Int) {
                onColorChangedListener(colorPicker, color)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event !is MotionEvent) {
            return false
        }
        val relativeX: Double = (event.x - colorWheelRect.centerX()).toDouble()
        val relativeY: Double = (event.y - colorWheelRect.centerY()).toDouble()
        theta = Math.atan2(relativeY, relativeX)
        onColorChangedListener?.onColorChanged(this, color)
        return true
    }
}