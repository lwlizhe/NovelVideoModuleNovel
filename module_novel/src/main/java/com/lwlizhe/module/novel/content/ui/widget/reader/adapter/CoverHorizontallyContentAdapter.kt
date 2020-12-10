package com.lwlizhe.module.novel.content.ui.widget.reader.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lwlizhe.module.R
import com.lwlizhe.module.novel.content.ui.widget.reader.adapter.holder.BaseContentViewHolder
import com.lwlizhe.module.novel.content.ui.widget.reader.adapter.holder.CoverContentViewHolder
import com.lwlizhe.module.novel.content.ui.widget.reader.manager.layout.CoverHorizontallyContentLayoutManager
import com.lwlizhe.module.novel.content.ui.widget.reader.manager.snap.NovelPageSnapHelper

class CoverHorizontallyContentAdapter<T>(context: Context) :
    BaseContentAdapter<T>(context) {

    lateinit var mRecyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
        val layoutManager = CoverHorizontallyContentLayoutManager(
            context,
            RecyclerView.HORIZONTAL,
            false
        )
        mRecyclerView.layoutManager = layoutManager
        NovelPageSnapHelper(layoutManager.layoutMode).attachToRecyclerView(mRecyclerView)

    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_novel_content
    }

    override fun getHolder(v: View, viewType: Int): BaseContentViewHolder<T> {
        return CoverContentViewHolder<T>(v)
    }
}