package com.lwlizhe.module.novel.content.ui.widget.reader.manager.canvas.builder

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Point
import com.lwlizhe.module.novel.content.ui.widget.reader.manager.canvas.NovelContentCanvasManager

open abstract class BaseBuilder {

    protected var canvasManager: NovelContentCanvasManager
    var mTouchPoint = Point()


    constructor(canvasManager: NovelContentCanvasManager) {
        this.canvasManager = canvasManager
    }

    abstract fun setPathArea(width:Int,height:Int)

    abstract fun buildPath(
        x: Int,
        y: Int
    ): Path?

    abstract fun  buildCanvas(baseCanvas: Canvas,copyCanvas:Canvas,copyBitmap: Bitmap)

    abstract fun onFirstTouch(touchPoint:Point)

    abstract fun onDetached()
}