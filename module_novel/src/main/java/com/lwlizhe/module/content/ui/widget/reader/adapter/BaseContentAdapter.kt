package com.lwlizhe.module.content.ui.widget.reader.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lwlizhe.module.content.ui.widget.reader.adapter.holder.BaseContentViewHolder

open abstract class BaseContentAdapter<T>(var context: Context) :
    RecyclerView.Adapter<BaseContentViewHolder<T>>() {

    var mInflater: LayoutInflater? = null
    var mData: MutableList<T>
    lateinit var mHolder: BaseContentViewHolder<T>

    init {
        mInflater = LayoutInflater.from(context)
        mData = mutableListOf()
    }

    fun setData(data: List<T>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseContentViewHolder<T> {
        val view =
            LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false)
        mHolder = getHolder(view, viewType)
        return mHolder
    }

    override fun onBindViewHolder(holder: BaseContentViewHolder<T>, position: Int) {
        holder.setData(mData[position], position)
    }

    abstract fun getLayoutId(viewType: Int): Int
    abstract fun getHolder(v: View, viewType: Int): BaseContentViewHolder<T>


}