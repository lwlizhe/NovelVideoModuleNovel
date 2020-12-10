package com.lwlizhe.module.novel.content.ui.widget.reader.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lwlizhe.module.R
import com.lwlizhe.module.novel.content.ui.widget.reader.adapter.holder.BaseContentViewHolder
import com.lwlizhe.module.novel.content.ui.widget.reader.adapter.holder.SimulationContentViewHolder
import com.lwlizhe.module.novel.content.ui.widget.reader.manager.layout.BaseContentLayoutManager
import com.lwlizhe.module.novel.content.ui.widget.reader.manager.layout.SimulationHorizontallyContentLayoutManager
import com.lwlizhe.module.novel.content.ui.widget.reader.view.NovelSimulationContainerLayout

class SimulationHorizontallyContentAdapter<T>(context: Context) :
    BaseContentAdapter<T>(context) {

    lateinit var mRecyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
        val layoutManager = SimulationHorizontallyContentLayoutManager(context)
        mRecyclerView.layoutManager = layoutManager
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_novel_content
    }

    override fun getHolder(v: View, viewType: Int): BaseContentViewHolder<T> {
        var container = NovelSimulationContainerLayout(v.context)
        container.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        container.addView(v)

        val holder = SimulationContentViewHolder<T>(container)
        holder.bindPathManager((mRecyclerView.layoutManager as BaseContentLayoutManager).canvasManager)
        return holder
    }
}