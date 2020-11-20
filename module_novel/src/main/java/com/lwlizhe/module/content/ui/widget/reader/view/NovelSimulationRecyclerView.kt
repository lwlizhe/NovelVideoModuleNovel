package com.lwlizhe.module.content.ui.widget.reader.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.lwlizhe.module.content.ui.widget.reader.manager.layout.BaseContentLayoutManager
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

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val layoutManager = layoutManager as BaseContentLayoutManager
        return layoutManager.isNeedInterceptEvent(event)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        val layoutManager = layoutManager as BaseContentLayoutManager
        layoutManager.onTouchEvent(e)
        return true
    }

}