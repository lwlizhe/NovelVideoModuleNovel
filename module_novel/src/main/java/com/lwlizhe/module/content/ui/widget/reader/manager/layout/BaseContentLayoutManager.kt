package com.lwlizhe.module.content.ui.widget.reader.manager.layout

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.recyclerview.widget.SnapHelper
import com.lwlizhe.module.content.ui.widget.reader.manager.canvas.NovelContentCanvasManager
import kotlin.math.max
import kotlin.math.min

abstract class BaseContentLayoutManager : LinearLayoutManager {

    companion object {
        const val STATE_IDLE = 0
        const val STATE_TURN_NEXT = 1
        const val STATE_TURN_PRE = 2
    }
    var layoutMode: ContentLayoutMode = ContentLayoutMode.MODE_COVER_HORIZONTALLY

    var mRecycler: RecyclerView.Recycler? = null
    var mRecyclerView: RecyclerView? = null
    var offset = 0

    var canvasManager: NovelContentCanvasManager = NovelContentCanvasManager()
    lateinit var snapHelper: SnapHelper

    var currentScrollState=SCROLL_STATE_IDLE
    var currentOrientationState = STATE_IDLE
    var touchPoint: Point = Point(0, 0)


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

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    // 如果水平方向上没有滑动，只滑动竖直方向，也应该触发绘制
    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        var result = 0

        if (layoutMode.isCanScrollHorizontally) {
            mRecycler = recycler
            val lastOffset = offset
            offset += dx
            offset = getCurrentOffsetForRange()
            detachAndScrapAttachedViews(recycler)
//            Log.d("offset", "currentOffset:$offset")
//            Log.d("offset", "currentDx:$dx")
            val resultOffset = fill(recycler, dx)
            recycleChildren(recycler)

            result = if (lastOffset == offset) 0 else resultOffset
        } else {
            result = scrollVerticallyBy(0, recycler, state)
        }

        return result
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        return if (layoutMode.isCanScrollVertically) {
            super.scrollVerticallyBy(dy, recycler, state)
        } else {
            scrollHorizontallyBy(0, recycler, state)
        }
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        if (state!!.itemCount == 0 || state.isPreLayout) {
            removeAndRecycleAllViews(recycler)
            return
        }
        mRecycler = recycler
        detachAndScrapAttachedViews(recycler)
        offset = getCurrentOffsetForRange()
        fill(recycler, 0)
        recycleChildren(recycler)
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        mRecyclerView = view
        canvasManager.bindLayoutManager(this, view)
    }

    override fun onDetachedFromWindow(view: RecyclerView, recycler: RecyclerView.Recycler) {
        super.onDetachedFromWindow(view, recycler)
        mRecyclerView = null
        canvasManager.unBind(view, recycler)
    }

    enum class ContentLayoutMode(
        var isCanScrollHorizontally: Boolean,
        var isCanScrollVertically: Boolean,
        var mode: Int
    ) {
        MODE_COVER_HORIZONTALLY(true, false, 0),
        MODE_SIMULATION_HORIZONTALLY(true, false, 3);

    }

    protected fun getCurrentOffsetForRange(): Int {
        return min(max(0, offset), (itemCount - 1) * width)
    }

    private fun recycleChildren(recycler: RecyclerView.Recycler) {
        // 踩坑点：kotlin 中 for循环和增强for循环 加上toList创建一个新list，否则会因为移除操作导致ConcurrentModificationException
        // 或者直接用iterator
        recycler.scrapList.toList().forEach { removeAndRecycleView(it.itemView, recycler) }
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        currentScrollState=state
    }

    /************************************** 需实现的部分 *****************************************/

    abstract fun fill(recycler: RecyclerView.Recycler, distance: Int): Int

    abstract fun onTouchEvent(ev: MotionEvent)

    abstract fun isNeedInterceptEvent(ev: MotionEvent): Boolean


}