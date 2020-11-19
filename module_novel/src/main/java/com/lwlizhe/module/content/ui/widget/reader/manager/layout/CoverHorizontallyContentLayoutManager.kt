package com.lwlizhe.module.content.ui.widget.reader.manager.layout

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.lwlizhe.module.content.ui.widget.reader.manager.snap.NovelPageSnapHelper
import kotlin.math.min

class CoverHorizontallyContentLayoutManager : BaseContentLayoutManager {
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


    init {
        layoutMode = ContentLayoutMode.MODE_COVER_HORIZONTALLY
    }

    override fun fill(recycler: RecyclerView.Recycler,distance:Int):Int {
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
        }else{
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
                -offset % width,
                0,
                -offset % width + getDecoratedMeasuredWidth(topView),
                getDecoratedMeasuredHeight(topView)
            )
        }

        return distance

    }

    override fun onTouchEvent(ev: MotionEvent) {

    }

    override fun isNeedInterceptEvent(ev: MotionEvent): Boolean {
        return false
    }

//    override fun calculateOffset(currentDx: Int){
//        offset+=currentDx
//        offset=getCurrentOffsetForRange()
//    }

    private fun resetViewElevation(view:View){
        view.z = 0F
        view.elevation = 0f
        view.clipToOutline = false
        view.outlineProvider=null
    }
    private fun setViewElevation(view:View){
        view.z = 50F
        view.elevation = 50F
        view.clipToOutline = true
        view.outlineProvider= ViewOutlineProvider.BOUNDS
    }

    private fun getNextPageIndex(currentIndex: Int): Int {
        return min(itemCount - 1, currentIndex + 1)
    }
}