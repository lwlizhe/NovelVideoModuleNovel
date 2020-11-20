package com.lwlizhe.module.content.ui.widget.reader.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import com.lwlizhe.module.content.ui.widget.reader.manager.canvas.NovelContentCanvasManager

class NovelSimulationContainerLayout : FrameLayout {

    var canvasManager: NovelContentCanvasManager? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasManager?.onSizeChanged(w, h)
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (canvasManager?.layoutManager?.getChildAt(1) == this) {
            canvasManager?.buildCanvas(canvas,object : NovelContentCanvasManager.OnNeedDrawCanvas{
                override fun onNeedDrawCanvas(copyCanvas: Canvas) {
                    super@NovelSimulationContainerLayout.dispatchDraw(copyCanvas)
//                    copyCanvas.drawColor(Color.parseColor("#FFB6C1"))
                }
            })
        } else {
            super.dispatchDraw(canvas)
        }
    }

}