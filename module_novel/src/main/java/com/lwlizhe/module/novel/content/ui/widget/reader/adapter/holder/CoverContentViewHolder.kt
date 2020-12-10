package com.lwlizhe.module.novel.content.ui.widget.reader.adapter.holder

import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.lwlizhe.module.R

class CoverContentViewHolder<T>(itemView: View) : BaseContentViewHolder<T>(itemView) {

    var contentView: TextView = itemView.findViewById<TextView>(R.id.tvw_content)
    var pageView: TextView = itemView.findViewById<TextView>(R.id.tvw_page)

    init {
        contentView.setOnClickListener {
            Toast.makeText(itemView.context,"clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun setData(data: T, position: Int) {
        itemView.findViewById<TextView>(R.id.tvw_content).text = data.toString()
    }

}