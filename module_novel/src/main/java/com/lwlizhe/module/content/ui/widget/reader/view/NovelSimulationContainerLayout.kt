package com.lwlizhe.module.content.ui.widget.reader.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import com.lwlizhe.module.content.ui.widget.reader.adapter.holder.BaseContentViewHolder
import com.lwlizhe.module.content.ui.widget.reader.manager.path.NovelContentPathManager

class NovelSimulationContainerLayout : FrameLayout {

    var pathManager: NovelContentPathManager? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        pathManager?.onSizeChanged(w, h)
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (pathManager?.layoutManager?.getChildAt(1) == this) {
            pathManager?.currentPath?.let {
                canvas.clipPath(pathManager?.currentPath!!)
                canvas.drawColor(Color.parseColor("#99ff6666"))
            } ?: let {
                super.dispatchDraw(canvas)
                canvas.drawColor(Color.parseColor("#9966ff66"))
            }
        } else {
            super.dispatchDraw(canvas)
        }
    }

}