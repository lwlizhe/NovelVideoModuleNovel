package com.lwlizhe.module.content.ui.widget.reader.animation

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.view.View
import android.widget.Scroller

abstract class BaseContentViewAnimation {

    protected var mContext: Context? = null

    protected var mScroller: Scroller? = null
    protected var mTargetView: View? = null

    protected var mTouch = PointF()

    protected var isAnimationRunning = false

    protected var mScreenWidth = 0
    protected var mScreenHeight = 0

    protected var isHasPre = false
    protected var isHasNext = false
    protected var isLoading = false


    fun BaseAnimationBitmapLoader(targetView: View) {
        mScroller = Scroller(targetView.context)
        mTargetView = targetView
        mContext = targetView.context
        mTouch.x = mScreenWidth.toFloat()
        mTouch.y = mScreenHeight.toFloat()
    }

    fun onDraw(canvas: Canvas) {

        // 如果页面改变的时候，动画没有执行，那么去画静态页面
        if (isAnimationRunning) {
            calTouchPoint()
            drawCurPage(canvas)
            drawNextPage(canvas)
        } else {
            drawStatic(canvas)
        }
    }

    protected abstract fun calTouchPoint()

    protected abstract fun calcCornerXY(x: Float, y: Float)

    protected abstract fun drawStatic(canvas: Canvas)

    protected abstract fun drawNextPage(canvas: Canvas)

    protected abstract fun drawCurPage(canvas: Canvas)

}