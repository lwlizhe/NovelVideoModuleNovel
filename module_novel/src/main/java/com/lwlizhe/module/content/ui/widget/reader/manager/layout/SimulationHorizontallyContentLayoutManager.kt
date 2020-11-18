package com.lwlizhe.module.content.ui.widget.reader.manager.layout

import android.content.Context
import android.graphics.Point
import android.graphics.PointF
import android.os.SystemClock
import android.util.Log
import android.view.*
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.lwlizhe.module.content.ui.widget.reader.manager.snap.NovelPageSimulationSnapHelper
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class SimulationHorizontallyContentLayoutManager(context: Context) :
    BaseContentLayoutManager(context) {

    private var mVelocityTracker: VelocityTracker? = null
    private var vc: ViewConfiguration = ViewConfiguration.get(context)

    private var firstTouchPoint: Point = Point(0, 0)

    private var mMinFlingVelocity = vc.scaledMinimumFlingVelocity
    private var mMaxFlingVelocity = vc.scaledMaximumFlingVelocity

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

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker?.addMovement(ev)

        val roundX = (ev.x + 0.5F).toInt()
        val roundY = (ev.y + 0.5F).toInt()

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                touchPoint.x = roundX
                touchPoint.y = roundY
                firstTouchPoint.x = roundX
                firstTouchPoint.y = roundY

                pathManager.setFirstTouchPoint(touchPoint)

            }

            MotionEvent.ACTION_MOVE -> {

                val isTurnNext = ev.x - firstTouchPoint.x <= 0
                val dx = roundX - touchPoint.x
                val dy = roundY - touchPoint.y

                touchPoint.x = roundX
                touchPoint.y = roundY

                if (currentOrientationState == Companion.STATE_IDLE) {
                    if (!isTurnNext) {
                        if (offset / width > 0) {
                            currentOrientationState = STATE_TURN_PRE
//                            val i = (max(0, offset / width) * width + ev.x.toInt())-offset
                            val i = (offset - (offset / width) * width) - (ev.x.toInt())
                            pathManager.setFirstTouchPoint(Point(width/3,touchPoint.y))
                            mRecyclerView?.smoothScrollBy(i, 0, LinearInterpolator(), 2000)
                        }
                    } else {
                        if (offset / width < itemCount - 1) {
                            currentOrientationState = STATE_TURN_NEXT
                            val i = offset / width * width + (width - ev.x.toInt()) - offset
                            pathManager.setFirstTouchPoint(Point(width/3*2,touchPoint.y))
                            mRecyclerView?.smoothScrollBy(i, 0, LinearInterpolator(), 2000)
                        }
                    }
                } else {
                    mRecyclerView?.scrollBy(-dx.toInt(), dy.toInt())
                }
            }

            MotionEvent.ACTION_UP -> {
                firstTouchPoint.x = 0
                firstTouchPoint.y = 0

                touchPoint.x = 0
                touchPoint.y = 0

                currentOrientationState = Companion.STATE_IDLE

                mVelocityTracker?.computeCurrentVelocity(
                    1000,
                    mMaxFlingVelocity.toFloat()
                )

                var xVelocity = mVelocityTracker?.xVelocity ?: 0F
                var yVelocity = mVelocityTracker?.yVelocity ?: 0F

                if (!layoutMode.isCanScrollHorizontally || abs(xVelocity) < mMinFlingVelocity) {
                    xVelocity = 0F
                }
                if (!layoutMode.isCanScrollVertically || abs(yVelocity) < mMinFlingVelocity) {
                    yVelocity = 0F
                }


                (snapHelper as NovelPageSimulationSnapHelper).setCurrentVelocity(
                    xVelocity,
                    yVelocity
                )
//                mRecyclerView?.stopScroll()

                if (xVelocity == 0F) {
                    (snapHelper as NovelPageSimulationSnapHelper).snapToTargetExistingView()
                } else {
                    (snapHelper as NovelPageSimulationSnapHelper).snapFromFling(
                        this,
                        xVelocity.toInt(),
                        yVelocity.toInt()
                    )
                }

//                mRecyclerView?.smoothScrollBy(1024,0)
            }
        }
    }

    override fun isNeedConsumptionEvent(ev: MotionEvent): Boolean {

        return true
    }

    override fun scrollToPosition(position: Int) {
        offset = position
        offset = getCurrentOffsetForRange()
        super.scrollToPosition(offset)
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

    override fun onDetachedFromWindow(view: RecyclerView, recycler: RecyclerView.Recycler) {
        super.onDetachedFromWindow(view, recycler)
        mVelocityTracker?.recycle()
        mVelocityTracker = null
    }
}