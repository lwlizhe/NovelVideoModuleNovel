package com.lwlizhe.module.content.ui.widget.reader.manager.path

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Point
import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.lwlizhe.module.content.ui.widget.reader.manager.layout.BaseContentLayoutManager
import com.lwlizhe.module.content.ui.widget.reader.manager.path.builder.BasePathBuilder
import com.lwlizhe.module.content.ui.widget.reader.manager.path.builder.SimulationPathBuilder
import kotlin.math.abs

class NovelContentPathManager {

    var layoutManager: BaseContentLayoutManager? = null
    var pathBuilder: BasePathBuilder? = null

    var width: Int = 0
    var height: Int = 0

    var currentPath: Path? = null
    var lastTouchPoint: Point = Point()

    var limitPath: Path? = null

    var isMiddlePath = false

    fun setFirstTouchPoint(point: Point) {

        isMiddlePath = abs(point.y - (height / 2)) <= 100

        lastTouchPoint.x = point.x
        pathBuilder?.onFirstTouch(point)

        if (isMiddlePath) {
            lastTouchPoint.y = height - 1
        } else {
            lastTouchPoint.y = height / 4 * 3
        }
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
//                result = CoverPathBuilder(this)
            }
            BaseContentLayoutManager.ContentLayoutMode.MODE_SIMULATION_HORIZONTALLY.mode -> {
                result = SimulationPathBuilder(this)
            }
        }
        return result
    }


    fun buildPath(dx: Int) {
        var result: Path? = limitPath
        val touchPoint = layoutManager?.touchPoint
        val xPos = touchPoint?.x ?: 0
        val yPos = touchPoint?.y ?: 0

        lastTouchPoint.x -= dx
        lastTouchPoint.y = yPos

//        var dy = if (abs(lastTouchPoint.x - xPos)>3) {
//            ((lastTouchPoint.y - yPos).toFloat() / (lastTouchPoint.x - xPos).toFloat() * dx).toInt()
//        } else {
//            yPos - lastTouchPoint.y
//        }
//
//        if (xPos == 0 && yPos == 0) {
//            dy = 0
//        }
//
//
//        lastTouchPoint.x -= dx
//        lastTouchPoint.y -= dy
//
//        if (isMiddlePath) {
//            dy = 0
//        }
//        Log.d(
//            "path",
//            "dx : $dx , dy : $dy"
//        )

        val calculatePath = pathBuilder?.buildPath(lastTouchPoint.x, lastTouchPoint.y)
//        val isSuccess = calculatePath?.op(limitPath!!, Path.Op.INTERSECT) ?: false

        if (calculatePath != null) {
//        if (calculatePath != null && isSuccess) {
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

        pathBuilder?.setPathArea(width, height)
    }

}