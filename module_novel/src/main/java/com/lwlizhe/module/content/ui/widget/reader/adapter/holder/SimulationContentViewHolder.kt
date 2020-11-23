package com.lwlizhe.module.content.ui.widget.reader.adapter.holder

import android.graphics.Color
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.lwlizhe.module.R
import com.lwlizhe.module.content.ui.widget.reader.manager.canvas.NovelContentCanvasManager
import com.lwlizhe.module.content.ui.widget.reader.view.NovelSimulationContainerLayout

class SimulationContentViewHolder<T>(var containerView: NovelSimulationContainerLayout) :
    BaseContentViewHolder<T>(containerView) {

    var colors=intArrayOf(Color.parseColor("#666600"), Color.parseColor("#660066"), Color.parseColor("#006666"))

    override fun setData(data: T, position: Int) {
        val contentPage = itemView.findViewById<TextView>(R.id.tvw_content)
        val bg = itemView.findViewById<FrameLayout>(R.id.llt_container)
        contentPage.text = data.toString()
        contentPage.setOnClickListener { Toast.makeText(context,data.toString(),Toast.LENGTH_SHORT).show() }

        bg.setBackgroundColor(colors[if (data is Int) data%3  else 0])
    }

    fun bindPathManager(manager: NovelContentCanvasManager) {
        containerView.canvasManager = manager
    }
}