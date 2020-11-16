package com.lwlizhe.module.content.ui.widget.reader.manager.path

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.PointF
import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.lwlizhe.module.content.ui.widget.reader.manager.layout.BaseContentLayoutManager
import com.lwlizhe.module.content.ui.widget.reader.manager.path.builder.BasePathBuilder
import com.lwlizhe.module.content.ui.widget.reader.manager.path.builder.CoverPathBuilder
import com.lwlizhe.module.content.ui.widget.reader.manager.path.builder.SimulationPathBuilder

class NovelContentPathManager {

    var layoutManager: BaseContentLayoutManager? = null
    var pathBuilder: BasePathBuilder? = null

    var width: Int = 0
    var height: Int = 0

    var currentPath: Path? = null
    var touchPointF: PointF = PointF()

    var limitPath: Path? = null

    fun setFirstTouchPoint(pointF: PointF){
        touchPointF.x = pointF.x
        touchPointF.y = pointF.y

        pathBuilder?.mTouchPointF?.x=pointF.x
        pathBuilder?.mTouchPointF?.y=pointF.y
    }

    fun bindLayoutManager(manager: BaseContentLayoutManager, view: RecyclerView) {
        layoutManager = manager
        pathBuilder = buildPathBuilder()
    }

    fun unBind(view: RecyclerView, recycler: RecyclerView.Recycler) {
        layoutManager = null
    }

    private fun buildPathBuilder(): BasePathBuilder? {
        var result: BasePathBuilder? = null

        when (layoutManager?.layoutMode?.mode ?: -1) {
            BaseContentLayoutManager.ContentLayoutMode.MODE_COVER_HORIZONTALLY.mode -> {
                result = CoverPathBuilder(this)
            }
            BaseContentLayoutManager.ContentLayoutMode.MODE_SIMULATION_HORIZONTALLY.mode -> {
                result = SimulationPathBuilder(this)
            }
        }
        return result
    }

    fun buildPath(dx: Int) {

        buildPath(dx, false)

    }

    private fun buildPath(dx: Int, isOperateByUser: Boolean) {
        var result: Path? = limitPath
        val calculatePath = pathBuilder?.buildPath(touchPointF, dx, width, height, isOperateByUser)
        val isSuccess = calculatePath?.op(limitPath!!, Path.Op.INTERSECT) ?: false
        if (calculatePath != null && isSuccess) {
            result = calculatePath
        }
        currentPath = result
    }

    fun releasePath() {
        currentPath = null
    }

    fun onSizeChanged(newWidth: Int, newHeight: Int) {
        width = newWidth
        height = newHeight

        if (limitPath == null) {
            limitPath = Path()
            limitPath!!.reset()
            limitPath!!.moveTo(0F, 0F)
            limitPath!!.lineTo(width.toFloat(), 0F)
            limitPath!!.lineTo(width.toFloat(), height.toFloat())
            limitPath!!.lineTo(0F, height.toFloat())
            limitPath!!.close()
        }
    }

}