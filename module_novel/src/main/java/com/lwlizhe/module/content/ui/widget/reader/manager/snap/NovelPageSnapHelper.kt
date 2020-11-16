package com.lwlizhe.module.content.ui.widget.reader.manager.snap

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
import com.lwlizhe.module.content.ui.widget.reader.manager.layout.BaseContentLayoutManager
import kotlin.math.abs

class NovelPageSnapHelper : PagerSnapHelper {

    private var mVerticalHelper: OrientationHelper? = null
    private var mHorizontalHelper: OrientationHelper? = null

    private var tempVelocityX:Int=0
    private var tempVelocityY:Int=0

    private var layoutMode: BaseContentLayoutManager.ContentLayoutMode

    constructor(layoutMode: BaseContentLayoutManager.ContentLayoutMode) : super() {
        this.layoutMode = layoutMode
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {

        tempVelocityX=velocityX
        tempVelocityY=velocityY

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

        tempVelocityX=0
        tempVelocityY=0

        return out
    }

    private fun distanceToCenter(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View, helper: OrientationHelper
    ): Int {
        val decoratedStart = helper.getDecoratedStart(targetView)
        val measurementWidth = helper.getDecoratedMeasurement(targetView)

        return if(isForwardFling(layoutManager,tempVelocityX,tempVelocityY)){
            measurementWidth - abs(decoratedStart)
        }else{
            decoratedStart
        }


//        return if (measurementWidth / 2 <= abs(decoratedStart)) {
//            measurementWidth - abs(decoratedStart)
//        } else {
//            decoratedStart
//        }
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

    private fun isReverseLayout(layoutManager: RecyclerView.LayoutManager): Boolean {
        val itemCount = layoutManager.itemCount
        if (layoutManager is ScrollVectorProvider) {
            val vectorProvider = layoutManager as ScrollVectorProvider
            val vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1)
            if (vectorForEnd != null) {
                return vectorForEnd.x < 0 || vectorForEnd.y < 0
            }
        }
        return false
    }

    private fun getOrientationHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        return when {
            layoutMode.isCanScrollVertically -> {
                getVerticalHelper(layoutManager)
            }
            layoutMode.isCanScrollHorizontally -> {
                getHorizontalHelper(layoutManager)
            }
            else -> {
                null
            }
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
}