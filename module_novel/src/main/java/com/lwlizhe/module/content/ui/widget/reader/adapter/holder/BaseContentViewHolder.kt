package com.lwlizhe.module.content.ui.widget.reader.adapter.holder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open abstract class BaseContentViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var context:Context = itemView.context

   abstract fun setData(data:T,position:Int)
}