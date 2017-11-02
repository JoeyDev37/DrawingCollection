package com.example.joeyweidman.assignment02

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_brush_control.*
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_drawing_canvas.*


class BrushControlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brush_control)

        val lineJoints = arrayOf("Bevel", "Miter", "Round") //Set the joint values for the spinner
        val lineCaps = arrayOf("Butt", "Round", "Square") //Set the cap values for the spinner

        val adapter1 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lineJoints)
        val adapter2 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lineCaps)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner1.adapter = adapter1
        spinner2.adapter = adapter2

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                lineView.strokeJoin = lineJoints[p2]
            }
        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                lineView.strokeCaps = lineCaps[p2]
            }
        }

        colorWheel.setOnColorChangedListener { _, color ->
            lineView.color = color
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                lineView.strokeWidth = progress.toFloat()
            }
        })

        backButton.setOnClickListener {
            val intent: Intent = Intent(applicationContext, DrawingCanvasActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            intent.putExtra("Color", lineView.color)
            intent.putExtra("Width", lineView.strokeWidth)
            intent.putExtra("Joint", lineView.strokeJoin)
            intent.putExtra("Cap", lineView.strokeCaps)
            setResult(Activity.RESULT_OK, intent)
            finish()
            //startActivity(intent)
        }
    }
}
