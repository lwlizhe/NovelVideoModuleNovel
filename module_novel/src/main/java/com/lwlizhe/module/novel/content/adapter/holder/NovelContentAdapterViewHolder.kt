package com.lwlizhe.module.novel.content.adapter.holder

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.lwlizhe.module.R

class NovelContentAdapterViewHolder : RecyclerView.ViewHolder {
    constructor(itemView: View) : super(itemView)

    var contentView: TextView = itemView.findViewById<TextView>(R.id.tvw_content)
    var pageView: TextView = itemView.findViewById<TextView>(R.id.tvw_page)

    init {
        contentView.setOnClickListener {
            Toast.makeText(itemView.context,"clicked",Toast.LENGTH_SHORT).show()
        }
    }

    fun updata() {
        itemView.findViewById<TextView>(R.id.tvw_content).text = "123"
    }
}