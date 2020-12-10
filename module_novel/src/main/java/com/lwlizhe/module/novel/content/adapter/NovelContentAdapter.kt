package com.lwlizhe.module.novel.content.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lwlizhe.module.R
import com.lwlizhe.module.novel.content.adapter.holder.NovelContentAdapterViewHolder

class NovelContentAdapter<T>(context: Context) :
    RecyclerView.Adapter<NovelContentAdapterViewHolder>() {

    var mInflater: LayoutInflater? = null
    var mData: MutableList<T>
    lateinit var mRecyclerView:RecyclerView

    val colorArray = intArrayOf(
        Color.parseColor("#ff6666"),
        Color.parseColor("#ff9933"),
        Color.parseColor("#e5e600"),
        Color.parseColor("#009900"),
        Color.parseColor("#00e5e6"),
        Color.parseColor("#005ce6"),
        Color.parseColor("#e600e5")
    )

    init {
        mInflater = LayoutInflater.from(context)
        mData = mutableListOf()
    }

    fun setData(data: List<T>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NovelContentAdapterViewHolder {
        var containerView: View? =mInflater?.inflate(R.layout.item_novel_content_test,parent,false)
//        val container = containerView?.findViewById<TestSimulationView>(R.id.llt_container)
//        container?.bindLayoutManager(mRecyclerView.layoutManager as NovelContentTestSimulationLayoutManager)
        return NovelContentAdapterViewHolder(containerView!!)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: NovelContentAdapterViewHolder, position: Int) {
        val value = mData[position] as Int
        holder.itemView.findViewById<TextView>(R.id.tvw_content).text = value.toString()
//        val container = holder.itemView.findViewById<TestSimulationView>(R.id.llt_container)
//        container.resetInit()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView=recyclerView
    }
}