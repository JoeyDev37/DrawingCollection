package com.example.joeyweidman.assignment02

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_drawing_canvas.view.*
import java.io.Serializable
import java.util.*

/**
 * Created by pcjoe on 10/18/2017.
 */
class DrawingCanvas : View, Serializable {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    var color: Int
        get() = linePaint.color
        set(newColor) {
            linePaint.color = newColor
            invalidate()
        }

    //Sets the width of the line
    var strokeWidth: Float = 25.0f
        set(newStrokeWidth) {
            linePaint.strokeWidth = newStrokeWidth
            invalidate()
        }

    //Sets the Join type of the line
    //Can choose between Bevel, Miter, or Round
    var strokeJoin: String = "Bevel"
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
    var strokeCaps: String = "Butt"
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
        val testLinePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        testLinePaint.style = Paint.Style.STROKE
        testLinePaint.color = Color.GREEN
        testLinePaint.strokeJoin = Paint.Join.MITER
        testLinePaint.strokeCap = Paint.Cap.BUTT
        testLinePaint.strokeWidth = 25.0F
        testLinePaint
    }()

    var redoStack: Stack<Pair<Path, Paint>> = Stack()
    var pathStack: Stack<Pair<Path, Paint>> = Stack()
    lateinit var pair: Pair<Path, Paint>

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if(event !is MotionEvent) {
            return false
        }

        val x = event.x //x coord of finger position
        val y = event.y //y coord of finger position

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                redoStack = Stack() //Reset the redo's when you draw a new line
                var newPath: Path = Path()
                newPath.moveTo(x, y)

                var newPaint: Paint = Paint(linePaint)
                var newPair: Pair<Path, Paint> = Pair(newPath, newPaint)
                pair = newPair
                pathStack.push(pair)
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                Log.e("Drawing Canvas", pathStack.size.toString())
                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                pair.first.lineTo(x, y)
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas !is Canvas) {
            return
        }

        for(p in pathStack) {
            canvas.drawPath(p.first, p.second)
        }
        //canvas.drawPath(currentPath, linePaint)
    }
}