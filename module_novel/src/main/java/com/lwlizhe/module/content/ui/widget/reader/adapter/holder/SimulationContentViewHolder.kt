package com.lwlizhe.module.content.ui.widget.reader.adapter.holder

import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.lwlizhe.module.R
import com.lwlizhe.module.content.ui.widget.reader.manager.layout.BaseContentLayoutManager
import com.lwlizhe.module.content.ui.widget.reader.manager.path.NovelContentPathManager
import com.lwlizhe.module.content.ui.widget.reader.view.NovelSimulationContainerLayout

class SimulationContentViewHolder<T>(var containerView: NovelSimulationContainerLayout) :
    BaseContentViewHolder<T>(containerView) {


    init {
    }

    override fun setData(data: T, position: Int) {
        val contentPage = itemView.findViewById<TextView>(R.id.tvw_content)
        contentPage.text = data.toString()
        contentPage.setOnClickListener { Toast.makeText(context,data.toString(),Toast.LENGTH_SHORT).show() }
    }

    fun bindPathManager(manager: NovelContentPathManager) {
        containerView.pathManager = manager
    }
}