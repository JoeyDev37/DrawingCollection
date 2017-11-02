package com.example.joeyweidman.assignment02

import android.graphics.Color
import android.graphics.Paint

/**
 * Created by pcjoe on 10/18/2017.
 */
class BrushSettings {

    companion object {
        var color: Int = Color.GREEN
        var jointType = Paint.Join.MITER
        var capType = Paint.Cap.BUTT
        var lineWidth = 25.0f
    }
}