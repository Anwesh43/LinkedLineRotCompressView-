package com.anwesh.uiprojects.linerotcompressview

/**
 * Created by anweshmishra on 13/08/20.
 */

import android.view.View
import android.view.MotionEvent
import android.content.Context
import android.app.Activity
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Color

val sizeFactor : Float = 5.6f
val strokeFactor : Float = 90f
val delay : Long = 20
val parts : Int = 4
val colors : Array<String> = arrayOf("#3F51B5", "#4CAF50", "#F44336", "#2196F3", "#FFEB3B")
val scGap : Float = 0.02f / parts
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()


fun Canvas.drawLineRotCompress(scale : Float, w : Float, h : Float, paint : Paint) {
    val size : Float = Math.min(w, h) / sizeFactor
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val sf3 : Float = sf.divideScale(2, parts)
    val sf4 : Float = sf.divideScale(3, parts)
    save()
    translate(w / 10 + (w / 2 - w / 10) * sf2, 0f)
    rotate(90f * sf3)
    drawLine(0f, -size * sf1, 0f, size * sf1, paint)
    drawRect(RectF(-size * sf4, -size, 0f, size), paint)
    restore()
}

fun Canvas.drawBiLineRotCompress(scale : Float, w : Float, h : Float, paint : Paint) {
    for (j in 0..1) {
        save()
        scale(1f - 2 * j, 1f)
        drawLineRotCompress(scale, w, h, paint)
        restore()
    }
}

fun Canvas.drawLRCNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = Color.parseColor(colors[i])
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawBiLineRotCompress(scale, w, h, paint)
}

class LineRotCompressView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}