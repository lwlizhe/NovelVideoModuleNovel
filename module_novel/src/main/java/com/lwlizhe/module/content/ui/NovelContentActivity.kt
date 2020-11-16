package com.lwlizhe.module.content.ui

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.lwlizhe.module.R
import com.lwlizhe.module.content.adapter.NovelContentAdapter
import com.lwlizhe.module.content.ui.test.simulation.NovelContentTest2LayoutManager
import com.lwlizhe.module.content.ui.test.simulation.NovelContentTestLayoutManager
import com.lwlizhe.module.content.ui.test.simulation.NovelContentTestSimulationLayoutManager
import com.lwlizhe.module.content.ui.test.simulation.NovelPageTestSimulationSnapHelper
import com.lwlizhe.module.content.ui.widget.reader.adapter.SimulationHorizontallyContentAdapter
import com.lwlizhe.module.content.ui.widget.reader.manager.layout.BaseContentLayoutManager
import com.lwlizhe.module.content.ui.widget.reader.manager.layout.CoverHorizontallyContentLayoutManager
import com.lwlizhe.module.content.ui.widget.reader.manager.snap.NovelPageSimulationSnapHelper
import kotlinx.android.synthetic.main.activity_content.*


class NovelContentActivity : AppCompatActivity() {

    private val novelAdapter by lazy { SimulationHorizontallyContentAdapter<Any>(this) }

    private val painter: Paint = Paint()

    init {
        painter.textSize = 100F
        painter.strokeWidth = 20F
        painter.style = Paint.Style.FILL
        painter.color = Color.BLACK
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val data = (0..100).toList()
        novelAdapter.setData(data)

        rcv_novel_container.adapter = novelAdapter
//        rcv_novel_container.layoutManager =
//            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
//
//            CoverHorizontallyContentLayoutManager(
//                this,
//                RecyclerView.HORIZONTAL,
//                false
//            )
//            CoverHorizontallyContentLayoutManager(
//                this,
//                RecyclerView.HORIZONTAL,
//                false
//            )
//
//            NovelContentTestSimulationLayoutManager(
//                this,
//                RecyclerView.HORIZONTAL,
//                false
//            )
            NovelContentTest2LayoutManager(this, RecyclerView.HORIZONTAL, false)
//            NovelContentTestLayoutManager(this, RecyclerView.HORIZONTAL, false)
//            TestLayoutManager(this, RecyclerView.HORIZONTAL, false)
//            SkidRightLayoutManager(1.5f, 1f)
//        PagerSnapHelper().attachToRecyclerView(rcv_novel_container)
//        NovelPageTestSimulationSnapHelper(BaseContentLayoutManager.ContentLayoutMode.MODE_SIMULATION_HORIZONTALLY)
//            .attachToRecyclerView(rcv_novel_container)
//        NovelPageSnapHelper()
//            .attachToRecyclerView(rcv_novel_container)
//        CardConfig.initConfig(this)
        rcv_novel_container.setHasFixedSize(true)

//        val callback = TanTanCallback(rcv_novel_container, novelAdapter, data)
//
//        //测试竖直滑动是否已经不会被移除屏幕
//        //callback.setHorizontalDeviation(Integer.MAX_VALUE);
//
//        val simpleCallback = object : ItemTouchHelper.SimpleCallback(
//            0,
//            ItemTouchHelper.START or ItemTouchHelper.END
//        ) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun getMovementFlags(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder
//            ): Int {
//                Log.d("test", "getMovementFlags")
//
//                return ItemTouchHelper.Callback.makeMovementFlags(
//                    getDragDirs(recyclerView, viewHolder),
//                    getSwipeDirs(recyclerView, viewHolder)
//                )
//            }
//
//            override fun onChildDraw(
//                c: Canvas,
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                dX: Float,
//                dY: Float,
//                actionState: Int,
//                isCurrentlyActive: Boolean
//            ) {
//
//                Log.d("test", "onChildDraw")
//
//                Log.d(
//                    "test",
//                    "onChildDraw pagePos ：" + recyclerView.layoutManager?.getPosition(viewHolder.itemView)
//                        .toString()
//                )
//
//                if (dX > 0) {
//                    val childView = recyclerView.layoutManager?.getChildAt(2)
//
//                    childView?.elevation = 30F
//                    childView?.clipToOutline = false
//                    childView?.outlineProvider = object : ViewOutlineProvider() {
//                        override fun getOutline(view: View?, outline: Outline?) {
//                            outline!!.setRect(0, 0, view!!.width, view.height)
//                        }
//                    }
//
//                    childView?.translationX = dX
//                    childView?.translationY = dY
//                } else {
//
//                    viewHolder.itemView.elevation = 30F
//                    viewHolder.itemView.clipToOutline = false
//                    viewHolder.itemView.outlineProvider = object : ViewOutlineProvider() {
//                        override fun getOutline(view: View?, outline: Outline?) {
//                            outline!!.setRect(0, 0, view!!.width, view.height)
//                        }
//                    }
//
//                    viewHolder.itemView.translationX = dX
//                    viewHolder.itemView.translationY = dY
//                }
//            }
//
//            override fun onChildDrawOver(
//                c: Canvas,
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder?,
//                dX: Float,
//                dY: Float,
//                actionState: Int,
//                isCurrentlyActive: Boolean
//            ) {
//                Log.d("test", "onChildDrawOver dx ：$dX，dy ：$dY")
//
//                super.onChildDrawOver(
//                    c,
//                    recyclerView,
//                    viewHolder,
//                    dX,
//                    dY,
//                    actionState,
//                    isCurrentlyActive
//                )
//            }
//
//            override fun clearView(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder
//            ) {
//                if(viewHolder.adapterPosition==2) {
//                    val childView = recyclerView.layoutManager?.getChildAt(2)
//
//                    childView?.layout(
//                        -recyclerView.layoutManager!!.getDecoratedMeasuredWidth(
//                            childView!!
//                        ),
//                        0,
//                        0,
//                        recyclerView.layoutManager!!.getDecoratedMeasuredHeight(childView!!)
//                    )
//                }else {
//                    viewHolder.itemView.translationX = 0F
//                    viewHolder.itemView.translationX = 0F
//                }
//            }
//
//            override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
//
//                Log.d("test", "convertToAbsoluteDirection")
//
//                return super.convertToAbsoluteDirection(flags, layoutDirection)
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//                Log.d("test", "onSwiped")
//
//
//                //your code for deleting the item from database or from the list
//                val position = viewHolder.adapterPosition
////
////                //★实现循环的要点
//                if (direction == ItemTouchHelper.END) {
//                    val remove: Any = data.removeAt(0)
//
//                    data.add(remove)
//                } else {
//
//
//                    val remove: Any = data.removeAt(data.size - 1)
//
//                    data.add(0, remove)
//                }
//
////                rcv_novel_container.scrollToPosition(if (direction == ItemTouchHelper.START) position + 1 else position - 1)
//
//                novelAdapter.setData(data)
//
//
//            }
//        }
//
//        rcv_novel_container.setItemViewCacheSize(0)

        //测试竖直滑动是否已经不会被移除屏幕
        //callback.setHorizontalDeviation(Integer.MAX_VALUE);
//        val itemTouchHelper = ItemTouchHelper(simpleCallback)
//        itemTouchHelper.attachToRecyclerView(rcv_novel_container)
//        itemTouchHelper.startSwipe()

//        val itemTouchHelper = ItemTouchHelper(callback)
//        itemTouchHelper.attachToRecyclerView(rcv_novel_container)

    }

    public fun test(view: View?){
        rcv_novel_container.smoothScrollBy(-800,0)
    }
}