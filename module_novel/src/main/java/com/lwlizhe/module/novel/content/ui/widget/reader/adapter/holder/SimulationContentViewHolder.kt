package com.lwlizhe.module.novel.content.ui.widget.reader.adapter.holder

import android.graphics.Color
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.lwlizhe.module.R
import com.lwlizhe.module.novel.content.ui.widget.reader.manager.canvas.NovelContentCanvasManager
import com.lwlizhe.module.novel.content.ui.widget.reader.view.NovelSimulationContainerLayout

class SimulationContentViewHolder<T>(var containerView: NovelSimulationContainerLayout) :
    BaseContentViewHolder<T>(containerView) {

    var colors = intArrayOf(
        Color.parseColor("#666600"),
        Color.parseColor("#660066"),
        Color.parseColor("#006666")
    )

    override fun setData(data: T, position: Int) {
        val contentPage = itemView.findViewById<TextView>(R.id.tvw_content)
        val contentPageIndex = itemView.findViewById<TextView>(R.id.tvw_page)
        val bg = itemView.findViewById<FrameLayout>(R.id.llt_container)
        val img = itemView.findViewById<ImageView>(R.id.ivw_img)
        contentPage.text = data.toString()
        contentPageIndex.text = position.toString();

        contentPage.setOnClickListener {
            Toast.makeText(context, "这个加上点着玩", Toast.LENGTH_SHORT).show()
        }
        contentPageIndex.setOnClickListener {
            Toast.makeText(
                context, "这个是页码",
                Toast.LENGTH_SHORT
            ).show()
        }

        bg.setBackgroundColor(colors[if (data is Int) data % 3 else 0])

        Glide.with(img).load("https://avatars2.githubusercontent.com/u/25957598").into(img)

        img.setOnClickListener {
            Toast.makeText(
                context,
                "这个是ImageView，表示可以图文混排，用GLide加载个我github的帅气头像",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun bindPathManager(manager: NovelContentCanvasManager) {
        containerView.canvasManager = manager
    }
}