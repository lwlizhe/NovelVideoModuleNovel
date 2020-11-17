package com.lwlizhe.module.content.ui.widget.reader.manager.snap

import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.lwlizhe.module.content.ui.widget.reader.manager.layout.BaseContentLayoutManager
import com.lwlizhe.module.content.ui.widget.reader.manager.layout.SimulationHorizontallyContentLayoutManager
import kotlin.math.abs
import kotlin.math.max

class NovelPageSimulationSnapHelper(private var layoutMode: BaseContentLayoutManager.ContentLayoutMode) :
    PagerSnapHelper() {

    private var mVerticalHelper: OrientationHelper? = null
    private var mHorizontalHelper: OrientationHelper? = null

    private var tempVelocityX: Int = 0
    private var tempVelocityY: Int = 0

    var mRecyclerView: RecyclerView? = null

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {

        tempVelocityX = velocityX
        tempVelocityY = velocityY

        return if (layoutManager.getChildAt(1) == null) {
            layoutManager.getPosition(layoutManager.getChildAt(0)!!)
        } else {
            layoutManager.getPosition(layoutManager.getChildAt(1)!!)
        }
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        return layoutManager?.getChildAt(1)
    }

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray? {
        val out = IntArray(2)
        if (layoutMode.isCanScrollHorizontally) {
            out[0] = distanceToCenter(
                layoutManager, targetView,
                getHorizontalHelper(layoutManager)
            )
        } else {
            out[0] = 0
        }

        if (layoutMode.isCanScrollVertically) {
            out[1] = distanceToCenter(
                layoutManager, targetView,
                getVerticalHelper(layoutManager)
            )
        } else {
            out[1] = 0
        }

        tempVelocityX = 0
        tempVelocityY = 0

        return out
    }

    private fun distanceToCenter(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View, helper: OrientationHelper
    ): Int {
        var contentLayoutManager = layoutManager as BaseContentLayoutManager
        var position = layoutManager.getPosition(targetView)
        val measurementWidth = helper.getDecoratedMeasurement(targetView)

        var distance = 0
        if (contentLayoutManager.currentOrientationState == BaseContentLayoutManager.STATE_IDLE) {
            distance = if (isForwardFling(layoutManager, tempVelocityX, tempVelocityY)) {
                (position + 1) * measurementWidth - contentLayoutManager.offset
            } else {
                (position) * measurementWidth - contentLayoutManager.offset
            }
        } else {
            distance =
                if (contentLayoutManager.currentOrientationState == BaseContentLayoutManager.STATE_TURN_PRE)
                    -(max(
                        0,
                        contentLayoutManager.offset / measurementWidth - 1
                    ) * measurementWidth + contentLayoutManager.touchPointF.x.toInt())
                else
                    contentLayoutManager.offset / measurementWidth * measurementWidth + (measurementWidth - contentLayoutManager.touchPointF.x.toInt()) - contentLayoutManager.offset
        }


        return distance

    }

    private fun isForwardFling(
        layoutManager: RecyclerView.LayoutManager, velocityX: Int,
        velocityY: Int
    ): Boolean {
        return if (layoutMode.isCanScrollHorizontally) {
            velocityX > 0
        } else {
            velocityY > 0
        }
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return mVerticalHelper!!
    }

    private fun getHorizontalHelper(
        layoutManager: RecyclerView.LayoutManager
    ): OrientationHelper {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return mHorizontalHelper!!
    }

    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        super.attachToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun createScroller(layoutManager: RecyclerView.LayoutManager?): RecyclerView.SmoothScroller? {
        return object : LinearSmoothScroller(mRecyclerView?.context) {
            override fun onTargetFound(
                targetView: View,
                state: RecyclerView.State,
                action: Action
            ) {
                if (mRecyclerView == null) {
                    // The associated RecyclerView has been removed so there is no action to take.
                    return
                }
                val snapDistances = calculateDistanceToFinalSnap(
                    mRecyclerView?.layoutManager!!,
                    targetView
                )
                val dx = snapDistances!![0]
                val dy = snapDistances[1]
                val time = calculateTimeForDeceleration(
                    max(
                        abs(dx),
                        abs(dy)
                    )
                )
                if (time > 0) {
                    action.update(dx, dy, time, mDecelerateInterpolator)
                }
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return 50F / displayMetrics.densityDpi
            }

        }
    }

    fun snapToTargetExistingView() {
        if (mRecyclerView == null) {
            return
        }
        val layoutManager = mRecyclerView!!.layoutManager ?: return
        val snapView = findSnapView(layoutManager) ?: return
        val snapDistance = calculateDistanceToFinalSnap(layoutManager, snapView)
//        Log.d("test", "snapToTargetExistingView ; dx:" + snapDistance!![0].toString())
        if (snapDistance!![0] != 0 || snapDistance[1] != 0) {

            mRecyclerView!!.smoothScrollBy(snapDistance[0], snapDistance[1],
                LinearInterpolator(),300)
        }
    }
}