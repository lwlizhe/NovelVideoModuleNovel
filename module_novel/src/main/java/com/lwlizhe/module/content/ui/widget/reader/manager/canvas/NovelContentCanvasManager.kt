package com.lwlizhe.module.content.ui.widget.reader.manager.canvas

import android.graphics.*
import androidx.recyclerview.widget.RecyclerView
import com.lwlizhe.module.content.ui.widget.reader.manager.layout.BaseContentLayoutManager
import com.lwlizhe.module.content.ui.widget.reader.manager.canvas.builder.BaseBuilder
import com.lwlizhe.module.content.ui.widget.reader.manager.canvas.builder.SimulationBuilder
import kotlin.math.abs

class NovelContentCanvasManager {

    var layoutManager: BaseContentLayoutManager? = null
    var builder: BaseBuilder? = null

    var width: Int = 0
    var height: Int = 0

    lateinit var tempBitmap: Bitmap
    lateinit var tempCanvas: Canvas

    var currentOutLinePath: Path? = null
    var lastTouchPoint: Point = Point()

//    var limitPath: Path? = null

    var isMiddlePath = false
    var isCanvasReady= false

    fun setFirstTouchPoint(point: Point) {

        isMiddlePath = abs(point.y - (height / 2)) <= 100

        lastTouchPoint.x = point.x
        builder?.onFirstTouch(point)

        if (isMiddlePath) {
            lastTouchPoint.y = height - 1
        } else {
            lastTouchPoint.y = height / 4 * 3
        }
    }

    fun bindLayoutManager(manager: BaseContentLayoutManager, view: RecyclerView) {
        layoutManager = manager
        builder = buildPathBuilder()
    }

    fun unBind(view: RecyclerView, recycler: RecyclerView.Recycler) {
        layoutManager = null
    }

    private fun buildPathBuilder(): BaseBuilder? {
        var result: BaseBuilder? = null

        when (layoutManager?.layoutMode?.mode ?: -1) {
            BaseContentLayoutManager.ContentLayoutMode.MODE_COVER_HORIZONTALLY.mode -> {
//                result = CoverPathBuilder(this)
            }
            BaseContentLayoutManager.ContentLayoutMode.MODE_SIMULATION_HORIZONTALLY.mode -> {
                result = SimulationBuilder(this)
            }
        }
        return result
    }


    fun buildPath(dx: Int) {
        var result: Path? = null
        val touchPoint = layoutManager?.touchPoint
        val xPos = touchPoint?.x ?: 0
        val yPos = touchPoint?.y ?: 0

        lastTouchPoint.x -= dx
        lastTouchPoint.y = yPos


        val calculatePath = builder?.buildPath(lastTouchPoint.x, lastTouchPoint.y)
//        val isSuccess = calculatePath?.op(limitPath!!, Path.Op.INTERSECT) ?: false

        if (calculatePath != null) {
//        if (calculatePath != null && isSuccess) {
            result = calculatePath
        }
        currentOutLinePath = result
    }

    fun buildCanvas(baseCanvas: Canvas, canvasDrawCallback: OnNeedDrawCanvas) {
        if(!isCanvasReady) {
            canvasDrawCallback.onNeedDrawCanvas(tempCanvas)
            isCanvasReady=true
        }
        builder?.buildCanvas(baseCanvas, tempCanvas, tempBitmap)
    }

    private fun pretreatmentCanvas(canvas: Canvas){
        canvas.drawColor(Color.parseColor("#FFFFF0"))
    }

    fun clearCanvas() {
        pretreatmentCanvas(tempCanvas)
        isCanvasReady=false
    }

    fun onSizeChanged(newWidth: Int, newHeight: Int) {
        width = newWidth
        height = newHeight

        tempBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        tempCanvas = Canvas(tempBitmap)
        pretreatmentCanvas(tempCanvas)

//        if (limitPath == null) {
//            limitPath = Path()
//            limitPath!!.reset()
//            limitPath!!.moveTo(0F, 0F)
//            limitPath!!.lineTo(width.toFloat(), 0F)
//            limitPath!!.lineTo(width.toFloat(), height.toFloat())
//            limitPath!!.lineTo(0F, height.toFloat())
//            limitPath!!.close()
//        }

        builder?.setPathArea(width, height)
    }

    interface OnNeedDrawCanvas {
        fun onNeedDrawCanvas(copyCanvas: Canvas)
    }

    fun onRecycler() {
        tempBitmap.recycle()
        builder?.onDetached()
    }

}