package com.example.joeyweidman.assignment02

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_brush_control.*
import kotlinx.android.synthetic.main.activity_drawing_canvas.*
import kotlinx.android.synthetic.main.activity_main.*

class DrawingCanvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_canvas)

        brushControlButton.setOnClickListener {
            val intent: Intent = Intent(applicationContext, BrushControlActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivityForResult(intent, 1)
        }

        undo.setOnClickListener {
            if(drawingCanvas.pathStack.isNotEmpty()) {
                drawingCanvas.redoStack.push(drawingCanvas.pathStack.pop())
                drawingCanvas.invalidate()
            }
        }

        redo.setOnClickListener {
            if(drawingCanvas.redoStack.isNotEmpty()) {
                drawingCanvas.pathStack.push(drawingCanvas.redoStack.pop())
                drawingCanvas.invalidate()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data !is Intent) {
            return
        }
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            drawingCanvas.color = data.getIntExtra("Color", Color.GREEN)
            drawingCanvas.strokeWidth = data.getFloatExtra("Width", 25.0F)
            drawingCanvas.strokeJoin = data.getStringExtra("Joint")
            drawingCanvas.strokeCaps = data.getStringExtra("Cap")
        }
    }
}
