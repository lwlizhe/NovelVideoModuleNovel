package com.lwlizhe.module.content.ui.widget.reader.manager.layout

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING
import com.lwlizhe.module.content.ui.widget.reader.manager.snap.NovelPageSimulationSnapHelper
import kotlin.math.max
import kotlin.math.min

class SimulationHorizontallyContentLayoutManager : BaseContentLayoutManager {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    var firstTouchPointF: PointF = PointF(0F, 0F)

    //    var isNeedResetPos = false
//    var extraOffset = 0

    init {
        layoutMode = ContentLayoutMode.MODE_SIMULATION_HORIZONTALLY
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {

        if (offset + dx < 0 || offset + dx > (itemCount - 1) * width) {
            return 0
        }
        pathManager.buildPath(dx)

        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    override fun fill(recycler: RecyclerView.Recycler, distance: Int): Int {

        val topPos = offset / width
        var nextIndex = getNextPageIndex(topPos)

        if (topPos == nextIndex) {
            return 0
        }

        var topView = recycler.getViewForPosition(topPos)

        if (offset % width == 0) {
            addView(topView)
            measureChild(topView, 0, 0)
            resetViewElevation(topView)
            layoutDecorated(
                topView,
                0,
                0,
                getDecoratedMeasuredWidth(topView),
                getDecoratedMeasuredHeight(topView)
            )
        } else {
            var nextView = recycler.getViewForPosition(nextIndex)

            measureChild(topView, 0, 0)
            measureChild(nextView, 0, 0)

            resetViewElevation(nextView)
            addView(nextView)
            layoutDecorated(
                nextView,
                0,
                0,
                getDecoratedMeasuredWidth(nextView),
                getDecoratedMeasuredHeight(nextView)
            )

            setViewElevation(topView)
            addView(topView)
            layoutDecorated(
                topView,
                0,
                0, getDecoratedMeasuredWidth(topView),
                getDecoratedMeasuredHeight(topView)
            )
//            layoutDecorated(
//                topView,
//                -offset % width,
//                0,
//                -offset % width + getDecoratedMeasuredWidth(topView),
//                getDecoratedMeasuredHeight(topView)
//            )
        }

        return distance
    }

    override fun dispatchTouchEvent(ev: MotionEvent) {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                touchPointF.x = ev.x
                touchPointF.y = ev.y
                firstTouchPointF.x = ev.x
                firstTouchPointF.y = ev.y

                pathManager.setFirstTouchPoint(touchPointF)

            }

            MotionEvent.ACTION_MOVE -> {

                val isTurnNext = ev.x - firstTouchPointF.x <= 0

                touchPointF.x = ev.x
                touchPointF.y = ev.y



                if (currentOrientationState == Companion.STATE_IDLE && !isTurnNext) {
                    if (offset / width > 0) {
                        currentOrientationState = STATE_TURN_PRE
                        scrollToPosition(max(0, offset / width - 1) * width + ev.x.toInt())

//                        offset = max(0, offset / width - 1) * width + ev.x.toInt()
//                        offset = getCurrentOffsetForRange()
//                        val i = -(max(0, offset / width - 1) * width + ev.x.toInt())
//                        mRecyclerView?.smoothScrollBy(i, 0, LinearInterpolator(), 100)
                    }
                }

                if (currentOrientationState == Companion.STATE_IDLE && isTurnNext) {
                    if (offset / width < itemCount - 1) {
                        currentOrientationState = Companion.STATE_TURN_NEXT
                        scrollToPosition(offset / width * width + (width - ev.x.toInt()))

//                        offset =offset / width * width + (width - ev.x.toInt())
//                        offset = getCurrentOffsetForRange()
//                        val i = offset / width * width + (width - ev.x.toInt()) - offset
//                        mRecyclerView?.smoothScrollBy(i, 0, LinearInterpolator(), 100)
                    }
                }

            }

            MotionEvent.ACTION_UP -> {
                firstTouchPointF.x = 0F
                firstTouchPointF.y = 0F

                touchPointF.x = 0F
                touchPointF.y = 0F

                currentOrientationState = Companion.STATE_IDLE
                (snapHelper as NovelPageSimulationSnapHelper).snapToTargetExistingViewForIDLE()

            }
        }
    }

    override fun isNeedConsumptionEvent(ev: MotionEvent): Boolean {
        if (currentScrollState == SCROLL_STATE_SETTLING || isSmoothScrolling) {
            return true
        }


        return false
    }

    override fun scrollToPosition(position: Int) {
        offset = position
        offset = getCurrentOffsetForRange()
        super.scrollToPosition(offset)
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)

//        if (state == SCROLL_STATE_IDLE && (touchPointF.x.toInt() != offset)) {
//            (snapHelper as NovelPageSimulationSnapHelper).snapToTargetExistingView()
//        }
//        Log.d("test", "onScrollStateChanged ; $state")
    }

    private fun resetViewElevation(view: View) {
        view.z = 0F
        view.elevation = 0f
        view.clipToOutline = false
        view.outlineProvider = null
    }

    private fun setViewElevation(view: View) {
        view.z = 50F
        view.elevation = 50F
        view.clipToOutline = true
        view.outlineProvider = ViewOutlineProvider.BOUNDS
//        view.outlineProvider = pathManager.currentPath?.let {
//            object : ViewOutlineProvider() {
//                override fun getOutline(view: View?, outline: Outline?) {
//
//                    outline?.setConvexPath(pathManager.currentPath!!)
//
//                }
//            }
//        } ?: ViewOutlineProvider.BOUNDS
    }

    private fun getNextPageIndex(currentIndex: Int): Int {
        return min(itemCount - 1, currentIndex + 1)
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        snapHelper = NovelPageSimulationSnapHelper(layoutMode)
        snapHelper.attachToRecyclerView(view)
    }
}