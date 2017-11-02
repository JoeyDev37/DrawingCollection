package com.example.joeyweidman.assignment02

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Created by Joey Weidman.
 */
class LineView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    var path: Path = Path() //The path of the line preview

    var color: Int
        get() = linePaint.color
        set(newColor) {
            linePaint.color = newColor
            invalidate()
        }

    //Sets the width of the line
    var strokeWidth: Float
        get() = linePaint.strokeWidth
        set(newStrokeWidth) {
            linePaint.strokeWidth = newStrokeWidth
            invalidate()
        }

    //Sets the Join type of the line
    //Can choose between Bevel, Miter, or Round
    var strokeJoin: String
        get() {
            if(linePaint.strokeJoin == Paint.Join.BEVEL)
                return "Bevel"
            else if(linePaint.strokeJoin == Paint.Join.MITER)
                return "Miter"
            else
                return "Round"
        }
        set(newLineJoint) {
            if(newLineJoint == "Bevel") {
                linePaint.strokeJoin = Paint.Join.BEVEL
                invalidate()
            }
            else if (newLineJoint == "Miter") {
                linePaint.strokeJoin = Paint.Join.MITER
                invalidate()
            }
            else {
                linePaint.strokeJoin = Paint.Join.ROUND
                invalidate()
            }
        }

    //Sets the Cap type of the line
    //Can choose between Butt, Round, or Square
    var strokeCaps: String
        get() {
            if(linePaint.strokeCap == Paint.Cap.BUTT)
                return "Butt"
            else if(linePaint.strokeCap == Paint.Cap.ROUND)
                return "Round"
            else
                return "Square"
        }
        set(newLineJoint) {
            if(newLineJoint == "Butt") {
                linePaint.strokeCap = Paint.Cap.BUTT
                invalidate()
            }
            else if (newLineJoint == "Round") {
                linePaint.strokeCap = Paint.Cap.ROUND
                invalidate()
            }
            else {
                linePaint.strokeCap = Paint.Cap.SQUARE
                invalidate()
            }
        }

    private val linePaint: Paint = {
        val linePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        linePaint.style = Paint.Style.STROKE
        linePaint.color = Color.GREEN
        linePaint.strokeJoin = Paint.Join.MITER
        linePaint.strokeCap = Paint.Cap.BUTT
        linePaint.strokeWidth = 25.0F
        linePaint
    }()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas !is Canvas) {
            return //Lets Kotlin know to smart cast the canvas object to a non-null version throughout the remainder of the function
        }

        val availableWidth: Float = (width - paddingLeft - paddingRight).toFloat()
        val availableHeight: Float = (height - paddingTop - paddingBottom).toFloat()

        //Set the path of the line
        path.moveTo(availableWidth * 0.15F, availableHeight * 0.80F)
        path.lineTo(availableWidth * 0.25F, availableHeight * 0.20F)
        path.lineTo(availableWidth * 0.35F, availableHeight * 0.70F)
        path.lineTo(availableWidth * 0.45F, availableHeight * 0.25F)
        path.lineTo(availableWidth * 0.55F, availableHeight * 0.60F)
        path.lineTo(availableWidth * 0.65F, availableHeight * 0.35F)
        path.lineTo(availableWidth * 0.75F, availableHeight * 0.50F)
        path.lineTo(availableWidth * 0.85F, availableHeight * 0.40F)

        //Draw the path
        canvas.drawPath(path, linePaint)
    }
}