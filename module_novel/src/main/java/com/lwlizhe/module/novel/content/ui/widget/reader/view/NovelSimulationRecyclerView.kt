package com.lwlizhe.module.novel.content.ui.widget.reader.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.lwlizhe.module.novel.content.ui.widget.reader.manager.layout.BaseContentLayoutManager
import java.lang.RuntimeException

class NovelSimulationRecyclerView : RecyclerView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun setLayoutManager(layout: LayoutManager?) {
        if (layout is BaseContentLayoutManager) {
            super.setLayoutManager(layout)
        } else {
            throw RuntimeException("The layoutManager must extends BaseContentLayoutManager")
        }
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        val layoutManager = layoutManager as BaseContentLayoutManager

        return if (layoutManager.isNeedInterceptEvent(e)) {
            layoutManager.onInterceptTouchEvent(e)
            super.onInterceptTouchEvent(e)
        } else {
            super.onInterceptTouchEvent(e)
        }
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        val layoutManager = layoutManager as BaseContentLayoutManager

        return if (layoutManager.isNeedInterceptEvent(e)) {
            layoutManager.onTouchEvent(e)
            true
        } else {
            super.onTouchEvent(e)
        }
    }

}